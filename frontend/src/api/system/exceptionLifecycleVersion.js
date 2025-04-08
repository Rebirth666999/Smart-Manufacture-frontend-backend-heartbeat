import request from '@/utils/request'

// 查询异常生命周期版本列表
export function listExceptionLifecycleVersion(query) {
  return request({
    url: '/system/exceptionLifecycleVersion/list',
    method: 'get',
    params: query
  })
}

// 查询异常生命周期版本详细
export function getExceptionLifecycleVersion(exlvId) {
  return request({
    url: '/system/exceptionLifecycleVersion/' + exlvId,
    method: 'get'
  })
}

// 新增异常生命周期版本
export function addExceptionLifecycleVersion(data) {
  return request({
    url: '/system/exceptionLifecycleVersion',
    method: 'post',
    data: data
  })
}

// 修改异常生命周期版本
export function updateExceptionLifecycleVersion(data) {
  return request({
    url: '/system/exceptionLifecycleVersion',
    method: 'put',
    data: data
  })
}

// 删除异常生命周期版本
export function delExceptionLifecycleVersion(exlvId) {
  return request({
    url: '/system/exceptionLifecycleVersion/' + exlvId,
    method: 'delete'
  })
}
