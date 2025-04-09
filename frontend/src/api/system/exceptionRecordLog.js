import request from '@/utils/request'

// 查询异常处理日志列表
export function listExceptionRecordLog(query) {
  return request({
    url: '/system/exceptionRecordLog/list',
    method: 'get',
    params: query
  })
}

// 查询异常处理日志详细
export function getExceptionRecordLog(exrlId) {
  return request({
    url: '/system/exceptionRecordLog/' + exrlId,
    method: 'get'
  })
}

// 新增异常处理日志
export function addExceptionRecordLog(data) {
  return request({
    url: '/system/exceptionRecordLog',
    method: 'post',
    data: data
  })
}

// 修改异常处理日志
export function updateExceptionRecordLog(data) {
  return request({
    url: '/system/exceptionRecordLog',
    method: 'put',
    data: data
  })
}

// 删除异常处理日志
export function delExceptionRecordLog(exrlId) {
  return request({
    url: '/system/exceptionRecordLog/' + exrlId,
    method: 'delete'
  })
}
