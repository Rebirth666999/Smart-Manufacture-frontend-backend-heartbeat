package com.ruoyi.web.controller.system;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.stp.StpUtil;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.enums.UserStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/qrLogin")
public class QrLoginController {

    private final long TOKEN_EXPIRE_SECONDS = 120; // 2分钟有效期
    private static final String QR_LOGIN_KEY = "qrLogin:";
    // 增加常量
    private static final String QR_LOGIN_TEMP_TOKEN_KEY = "qrLogin:tempToken:";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private ISysUserService userService;


    /**
     * 生成登录二维码
     */
    @SaIgnore
    @GetMapping("/generate")
    public R<Map<String, Object>> generateQr(HttpServletRequest request) {  // 添加HttpServletRequest参数
        String token = UUID.randomUUID().toString();
        Map<String, Object> status = new HashMap<>();
        status.put("state", "WAITING"); // WAITING/SCANNED/CONFIRMED/EXPIRED
        status.put("userId", null);
        status.put("ip", getIpAddr(request));  // 存储IP地址

        redisTemplate.opsForValue().set(QR_LOGIN_KEY + token, status, TOKEN_EXPIRE_SECONDS, TimeUnit.SECONDS);

        Map<String, Object> result = new HashMap<>();
        result.put("qrToken", token);
        result.put("expireTime", System.currentTimeMillis() + TOKEN_EXPIRE_SECONDS * 1000);
        return R.ok(result);
    }

    /**
     * 检查登录状态
     */
    @SuppressWarnings("unchecked")
    @SaIgnore
    @GetMapping("/check/{token}")
    public R<Map<String, Object>> checkStatus(@PathVariable String token) {
        Map<String, Object> status = (Map<String, Object>) redisTemplate.opsForValue().get(QR_LOGIN_KEY + token);

        if (status == null) {
            return R.fail("二维码已过期");
        }

        Map<String, Object> result = new HashMap<>();
        result.put("state", status.get("state"));
        result.put("userId", status.get("userId"));

        // 修改状态检查逻辑
        if ("CONFIRMED".equals(status.get("state"))) {
            // 返回临时令牌
            String tempToken = (String) status.get("tempToken");
            result.put("tempToken", tempToken);
            // 不删除Redis状态，自然过期
        }

        return R.ok(result);
    }

    /**
     * APP确认登录
     */
    @SaIgnore
    @PostMapping("/confirm")
    public R<?> confirmLogin(HttpServletRequest request, @RequestBody Map<String, String> params) {
        // 1. 从请求头获取并验证Authorization令牌
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return R.fail("缺少有效的Authorization令牌");
        }
        String mobileToken = authHeader.substring(7);
        try {
            StpUtil.getLoginIdByToken(mobileToken);
        } catch (Exception e) {
            return R.fail("APP登录失效，请重新登录");
        }

        System.out.println("收到Authorization: " + authHeader); // 保留日志用于调试

        // 2. 获取并验证QR Token
        String qrToken = params.get("qrToken");
        if (qrToken == null || qrToken.isEmpty()) {
            return R.fail("缺少必要参数: qrToken");
        }

        // 3. 通过APP令牌获取用户ID并验证用户状态
        Object loginId = StpUtil.getLoginIdByToken(mobileToken);
        if (loginId == null) {
            return R.fail("APP登录失效，请重新登录");
        }

        long userId;
        try {
            // 处理可能带前缀的用户ID格式（如 "sys_user:1"）
            String loginIdStr = loginId.toString();
            if (loginIdStr.contains(":")) {
                // 如果包含冒号，取冒号后的部分
                String[] parts = loginIdStr.split(":");
                userId = Long.valueOf(parts[1]);
            } else {
                // 否则直接转换
                userId = Long.valueOf(loginIdStr);
            }
        } catch (NumberFormatException e) {
            return R.fail("用户ID格式错误");
        }

        SysUser user = userService.selectUserById(userId);
        if (user == null) {
            return R.fail("用户不存在");
        }
        if (UserStatus.DELETED.getCode().equals(user.getDelFlag())) {
            return R.fail("对不起，您的账号：" + user.getUserName() + " 已被删除");
        }
        if (UserStatus.DISABLE.getCode().equals(user.getStatus())) {
            return R.fail("对不起，您的账号：" + user.getUserName() + " 已禁用，请联系管理员");
        }

        // 4. 验证二维码状态
        String redisKey = QR_LOGIN_KEY + qrToken;
        Map<String, Object> status = (Map<String, Object>) redisTemplate.opsForValue().get(redisKey);
        if (status == null) {
            return R.fail("二维码已过期，请刷新后重试");
        }

        // 5. 验证IP地址（可选安全验证）
        String storedIp = (String) status.get("ip");
        String clientIp = getIpAddr(request);
        // 如需严格验证IP一致性可取消注释
        // if (!clientIp.equals(storedIp)) {
        //     return R.fail("IP地址不匹配，拒绝登录");
        // }

        // 生成临时令牌
        String tempToken = UUID.randomUUID().toString();
        // 存储临时令牌与用户ID的映射
        redisTemplate.opsForValue().set(
            QR_LOGIN_TEMP_TOKEN_KEY + tempToken,
            userId,
            5, TimeUnit.MINUTES
        );

        // 6. 更新状态为已确认并延长有效期
        status.put("state", "CONFIRMED");
        status.put("tempToken", tempToken); // 存储临时令牌
        redisTemplate.opsForValue().set(redisKey, status, 30, TimeUnit.SECONDS);

        // 7. 记录登录日志
        System.out.println("用户扫码确认登录: " +
            "userId=" + userId + ", " +
            "qrToken=" + qrToken + ", " +
            "clientIp=" + clientIp + ", " +
            "storedIp=" + storedIp);

        return R.ok("登录确认成功");
    }

    /**
     * 获取IP地址
     */
    private String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }
}
