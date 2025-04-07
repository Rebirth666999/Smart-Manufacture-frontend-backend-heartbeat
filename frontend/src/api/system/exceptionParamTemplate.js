import request from '@/utils/request'

// 查询异常参数模板列表
export function listExceptionParamTemplate(query) {
  return request({
    url: '/system/exceptionParamTemplate/list',
    method: 'get',
    params: query
  })
}

// 查询异常参数模板详细
export function getExceptionParamTemplate(exptId) {
  return request({
    url: '/system/exceptionParamTemplate/' + exptId,
    method: 'get'
  })
}

// 新增异常参数模板
export function addExceptionParamTemplate(data) {
  return request({
    url: '/system/exceptionParamTemplate',
    method: 'post',
    data: data
  })
}

// 修改异常参数模板
export function updateExceptionParamTemplate(data) {
  return request({
    url: '/system/exceptionParamTemplate',
    method: 'put',
    data: data
  })
}

// 删除异常参数模板
export function delExceptionParamTemplate(exptId) {
  return request({
    url: '/system/exceptionParamTemplate/' + exptId,
    method: 'delete'
  })
}
