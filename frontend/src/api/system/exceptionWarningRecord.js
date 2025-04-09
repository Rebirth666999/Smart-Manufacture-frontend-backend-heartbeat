import request from '@/utils/request'

// 查询异常预警记录列表
export function listExceptionWarningRecord(query) {
  return request({
    url: '/system/exceptionWarningRecord/list',
    method: 'get',
    params: query
  })
}

// 查询异常预警记录详细
export function getExceptionWarningRecord(exwrId) {
  return request({
    url: '/system/exceptionWarningRecord/' + exwrId,
    method: 'get'
  })
}

// 新增异常预警记录
export function addExceptionWarningRecord(data) {
  return request({
    url: '/system/exceptionWarningRecord',
    method: 'post',
    data: data
  })
}

// 修改异常预警记录
export function updateExceptionWarningRecord(data) {
  return request({
    url: '/system/exceptionWarningRecord',
    method: 'put',
    data: data
  })
}

// 删除异常预警记录
export function delExceptionWarningRecord(exwrId) {
  return request({
    url: '/system/exceptionWarningRecord/' + exwrId,
    method: 'delete'
  })
}
