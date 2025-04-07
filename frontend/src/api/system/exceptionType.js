import request from '@/utils/request'

// 查询异常类型列表
export function listExceptionType(query) {
  return request({
    url: '/system/exceptionType/list',
    method: 'get',
    params: query
  })
}

// 查询异常类型详细
export function getExceptionType(extId) {
  return request({
    url: '/system/exceptionType/' + extId,
    method: 'get'
  })
}

// 新增异常类型
export function addExceptionType(data) {
  return request({
    url: '/system/exceptionType',
    method: 'post',
    data: data
  })
}

// 修改异常类型
export function updateExceptionType(data) {
  return request({
    url: '/system/exceptionType',
    method: 'put',
    data: data
  })
}

// 删除异常类型
export function delExceptionType(extId) {
  return request({
    url: '/system/exceptionType/' + extId,
    method: 'delete'
  })
}
