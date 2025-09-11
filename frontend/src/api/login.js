import request from '@/utils/request'

// 登录方法
export function login(username, password, code, uuid) {
  const data = {
    username,
    password,
    code,
    uuid
  }
  return request({
    url: '/login',
    headers: {
      isToken: false
    },
    method: 'post',
    data: data
  })
}

// 注册方法
export function register(data) {
  return request({
    url: '/register',
    headers: {
      isToken: false
    },
    method: 'post',
    data: data
  })
}

// 获取用户详细信息
export function getInfo() {
  return request({
    url: '/getInfo',
    method: 'get'
  })
}

// 退出方法
export function logout() {
  return request({
    url: '/logout',
    method: 'post'
  })
}

// 获取验证码
export function getCodeImg() {
  return request({
    url: '/captchaImage',
    headers: {
      isToken: false
    },
    method: 'get',
    timeout: 20000
  })
}

// 短信验证码
export function getCodeSms() {
  return request({
    url: '/captchaSms',
    headers: {
      isToken: false
    },
    method: 'get',
    timeout: 20000
  })
}

// login.js 新增以下方法
// 生成二维码
export function generateQr() {
  return request({
    url: '/qrLogin/generate',
    method: 'get'
  })
}

// 检查二维码状态
export function checkQrStatus(token) {
  return request({
    url: '/qrLogin/check/' + token,
    method: 'get'
  })
}

// 修改二维码登录方法
export function qrLogin(tempToken) {
  return request({
    url: '/qrTokenLogin', // 指向新接口
    method: 'post',
    params: { tempToken } // 传递临时令牌
  })
}
