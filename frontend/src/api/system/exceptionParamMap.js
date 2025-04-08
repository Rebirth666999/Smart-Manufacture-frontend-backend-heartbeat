import request from '@/utils/request'

// 查询异常参数模板条目列表
export function listExceptionParamMap(query) {
  return request({
    url: '/system/exceptionParamMap/list',
    method: 'get',
    params: query
  })
}

// 查询异常参数模板条目详细
export function getExceptionParamMap(expmId) {
  return request({
    url: '/system/exceptionParamMap/' + expmId,
    method: 'get'
  })
}

// 新增异常参数模板条目
export function addExceptionParamMap(data) {
  return request({
    url: '/system/exceptionParamMap',
    method: 'post',
    data: data
  })
}

// 修改异常参数模板条目
export function updateExceptionParamMap(data) {
  return request({
    url: '/system/exceptionParamMap',
    method: 'put',
    data: data
  })
}

// 删除异常参数模板条目
export function delExceptionParamMap(expmId) {
  return request({
    url: '/system/exceptionParamMap/' + expmId,
    method: 'delete'
  })
}
