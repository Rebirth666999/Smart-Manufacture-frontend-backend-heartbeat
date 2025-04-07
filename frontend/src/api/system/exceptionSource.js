import request from '@/utils/request'

// 查询异常源列表
export function listExceptionSource(query) {
  return request({
    url: '/system/exceptionSource/list',
    method: 'get',
    params: query
  })
}

// 查询异常源详细
export function getExceptionSource(exsId) {
  return request({
    url: '/system/exceptionSource/' + exsId,
    method: 'get'
  })
}

// 新增异常源
export function addExceptionSource(data) {
  return request({
    url: '/system/exceptionSource',
    method: 'post',
    data: data
  })
}

// 修改异常源
export function updateExceptionSource(data) {
  return request({
    url: '/system/exceptionSource',
    method: 'put',
    data: data
  })
}

// 删除异常源
export function delExceptionSource(exsId) {
  return request({
    url: '/system/exceptionSource/' + exsId,
    method: 'delete'
  })
}
