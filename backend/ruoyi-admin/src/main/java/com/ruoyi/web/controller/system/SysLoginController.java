package com.ruoyi.web.controller.system;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.stp.StpUtil;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.entity.SysMenu;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.EmailLoginBody;
import com.ruoyi.common.core.domain.model.LoginBody;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.domain.model.SmsLoginBody;
import com.ruoyi.common.helper.LoginHelper;
import com.ruoyi.system.domain.vo.RouterVo;
import com.ruoyi.system.service.ISysMenuService;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.system.service.SysLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 登录验证
 *
 * @author Lion Li
 */
@Validated
@RequiredArgsConstructor
@RestController
public class SysLoginController {

    private final SysLoginService loginService;
    private final ISysMenuService menuService;
    private final ISysUserService userService;

    // 注入RedisTemplate
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 登录方法
     *
     * @param loginBody 登录信息
     * @return 结果
     */
    @SaIgnore
    @PostMapping("/login")
    public R<Map<String, Object>> login(@Validated @RequestBody LoginBody loginBody) {
        Map<String, Object> ajax = new HashMap<>();
        // 生成令牌
        String token = loginService.login(loginBody.getUsername(), loginBody.getPassword(), loginBody.getCode(),
            loginBody.getUuid());
        ajax.put(Constants.TOKEN, token);
        return R.ok(ajax);
    }

    /**
     * 短信登录
     *
     * @param smsLoginBody 登录信息
     * @return 结果
     */
    @SaIgnore
    @PostMapping("/smsLogin")
    public R<Map<String, Object>> smsLogin(@Validated @RequestBody SmsLoginBody smsLoginBody) {
        Map<String, Object> ajax = new HashMap<>();
        // 生成令牌
        String token = loginService.smsLogin(smsLoginBody.getPhonenumber(), smsLoginBody.getSmsCode());
        ajax.put(Constants.TOKEN, token);
        return R.ok(ajax);
    }

    /**
     * 邮件登录
     *
     * @param body 登录信息
     * @return 结果
     */
    @PostMapping("/emailLogin")
    public R<Map<String, Object>> emailLogin(@Validated @RequestBody EmailLoginBody body) {
        Map<String, Object> ajax = new HashMap<>();
        // 生成令牌
        String token = loginService.emailLogin(body.getEmail(), body.getEmailCode());
        ajax.put(Constants.TOKEN, token);
        return R.ok(ajax);
    }

    /**
     * 小程序登录(示例)
     *
     * @param xcxCode 小程序code
     * @return 结果
     */
    @SaIgnore
    @PostMapping("/xcxLogin")
    public R<Map<String, Object>> xcxLogin(@NotBlank(message = "{xcx.code.not.blank}") String xcxCode) {
        Map<String, Object> ajax = new HashMap<>();
        // 生成令牌
        String token = loginService.xcxLogin(xcxCode);
        ajax.put(Constants.TOKEN, token);
        return R.ok(ajax);
    }

    /**
     * 退出登录
     */
    @SaIgnore
    @PostMapping("/logout")
    public R<Void> logout() {
        loginService.logout();
        return R.ok("退出成功");
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @GetMapping("getInfo")
    public R<Map<String, Object>> getInfo() {
        LoginUser loginUser = LoginHelper.getLoginUser();
        SysUser user = userService.selectUserById(loginUser.getUserId());
        Map<String, Object> ajax = new HashMap<>();
        ajax.put("user", user);
        ajax.put("roles", loginUser.getRolePermission());
        ajax.put("permissions", loginUser.getMenuPermission());
        return R.ok(ajax);
    }

    /**
     * 获取路由信息
     *
     * @return 路由信息
     */
    @GetMapping("getRouters")
    public R<List<RouterVo>> getRouters() {
        Long userId = LoginHelper.getUserId();
        List<SysMenu> menus = menuService.selectMenuTreeByUserId(userId);
        return R.ok(menuService.buildMenus(menus));
    }

    /**
     * 扫码登录方法
     *
     * @param sysToken 临时令牌
     * @return 结果
     */
    @SaIgnore
    @PostMapping("/qrLogin")
    public R<Map<String, Object>> qrLogin(@RequestParam String sysToken) {
        try {
            // 使用Sa-Token验证临时令牌
            StpUtil.setTokenValue(sysToken);
            if (!StpUtil.isLogin()) {
                return R.fail("登录令牌无效");
            }

            // 获取登录用户ID
            long userId = StpUtil.getLoginIdAsLong();

            // 重新生成正式系统令牌
            StpUtil.logoutByTokenValue(sysToken); // 注销临时令牌
            StpUtil.login(userId); // 重新登录生成新令牌
            String newToken = StpUtil.getTokenValue();

            Map<String, Object> ajax = new HashMap<>();
            ajax.put(Constants.TOKEN, newToken);
            return R.ok(ajax);
        } catch (Exception e) {
            return R.fail("登录令牌无效");
        }
    }

    // 在SysLoginController中添加以下方法
// 在SysLoginController中添加以下方法
    @SaIgnore
    @PostMapping("/qrTokenLogin")
    public R<Map<String, Object>> qrTokenLogin(@RequestParam String tempToken) {
        String redisKey = "qrLogin:tempToken:" + tempToken;
        // 从Redis获取用户ID（可能是Integer或Long类型）
        Object userIdObj = redisTemplate.opsForValue().get(redisKey);

        if (userIdObj == null) {
            return R.fail("临时令牌已过期或无效");
        }

        // 关键修复：将Object转为Long（兼容Integer和Long）
        long userId;
        if (userIdObj instanceof Integer) {
            userId = ((Integer) userIdObj).longValue(); // Integer转Long
        } else if (userIdObj instanceof Long) {
            userId = (Long) userIdObj; // 直接强转
        } else {
            return R.fail("用户ID类型错误，无法登录");
        }

        try {
            // 删除临时令牌，防止重复使用
            redisTemplate.delete(redisKey);

            // 执行登录操作
            StpUtil.login(userId);
            String sysToken = StpUtil.getTokenValue();

            // 记录登录日志
            System.out.println("扫码登录成功 userId=" + userId + ", tempToken=" + tempToken);

            // 构建返回结果
            Map<String, Object> result = new HashMap<>();
            result.put(Constants.TOKEN, sysToken);

            return R.ok(result);
        } catch (Exception e) {
            return R.fail("登录失败: " + e.getMessage());
        }
    }

}
