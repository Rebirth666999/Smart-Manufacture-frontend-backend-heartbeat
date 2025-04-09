import request from '@/utils/request'

// 查询异常消息记录列表
export function listExceptionMessageRecord(query) {
  return request({
    url: '/system/exceptionMessageRecord/list',
    method: 'get',
    params: query
  })
}

// 查询异常消息记录详细
export function getExceptionMessageRecord(exmrId) {
  return request({
    url: '/system/exceptionMessageRecord/' + exmrId,
    method: 'get'
  })
}

// 新增异常消息记录
export function addExceptionMessageRecord(data) {
  return request({
    url: '/system/exceptionMessageRecord',
    method: 'post',
    data: data
  })
}

// 修改异常消息记录
export function updateExceptionMessageRecord(data) {
  return request({
    url: '/system/exceptionMessageRecord',
    method: 'put',
    data: data
  })
}

// 删除异常消息记录
export function delExceptionMessageRecord(exmrId) {
  return request({
    url: '/system/exceptionMessageRecord/' + exmrId,
    method: 'delete'
  })
}
