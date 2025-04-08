import request from '@/utils/request'

// 查询异常生命周期列表
export function listExceptionLifecycle(query) {
  return request({
    url: '/system/exceptionLifecycle/list',
    method: 'get',
    params: query
  })
}

// 查询异常生命周期详细
export function getExceptionLifecycle(exlId) {
  return request({
    url: '/system/exceptionLifecycle/' + exlId,
    method: 'get'
  })
}

// 新增异常生命周期
export function addExceptionLifecycle(data) {
  return request({
    url: '/system/exceptionLifecycle',
    method: 'post',
    data: data
  })
}

// 修改异常生命周期
export function updateExceptionLifecycle(data) {
  return request({
    url: '/system/exceptionLifecycle',
    method: 'put',
    data: data
  })
}

// 删除异常生命周期
export function delExceptionLifecycle(exlId) {
  return request({
    url: '/system/exceptionLifecycle/' + exlId,
    method: 'delete'
  })
}
