import request from '@/utils/request'

// 查询异常预警配置列表
export function listExceptionWarning(query) {
  return request({
    url: '/system/exceptionWarning/list',
    method: 'get',
    params: query
  })
}

// 查询异常预警配置详细
export function getExceptionWarning(exwId) {
  return request({
    url: '/system/exceptionWarning/' + exwId,
    method: 'get'
  })
}

// 新增异常预警配置
export function addExceptionWarning(data) {
  return request({
    url: '/system/exceptionWarning',
    method: 'post',
    data: data
  })
}

// 修改异常预警配置
export function updateExceptionWarning(data) {
  return request({
    url: '/system/exceptionWarning',
    method: 'put',
    data: data
  })
}

// 删除异常预警配置
export function delExceptionWarning(exwId) {
  return request({
    url: '/system/exceptionWarning/' + exwId,
    method: 'delete'
  })
}
