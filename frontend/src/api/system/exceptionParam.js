import request from '@/utils/request'

// 查询异常参数列表
export function listExceptionParam(query) {
  return request({
    url: '/system/exceptionParam/list',
    method: 'get',
    params: query
  })
}

// 查询异常参数详细
export function getExceptionParam(expId) {
  return request({
    url: '/system/exceptionParam/' + expId,
    method: 'get'
  })
}

// 新增异常参数
export function addExceptionParam(data) {
  return request({
    url: '/system/exceptionParam',
    method: 'post',
    data: data
  })
}

// 修改异常参数
export function updateExceptionParam(data) {
  return request({
    url: '/system/exceptionParam',
    method: 'put',
    data: data
  })
}

// 删除异常参数
export function delExceptionParam(expId) {
  return request({
    url: '/system/exceptionParam/' + expId,
    method: 'delete'
  })
}
