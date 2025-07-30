import request from '@/utils/request'

// 查询异常记录（新）列表
export function listExceptionRecordNew(query) {
  return request({
    url: '/system/exceptionRecordNew/list',
    method: 'get',
    params: query
  })
}

// 查询异常记录（新）详细
export function getExceptionRecordNew(exrId) {
  return request({
    url: '/system/exceptionRecordNew/' + exrId,
    method: 'get'
  })
}

// 新增异常记录（新）
export function addExceptionRecordNew(data) {
  return request({
    url: '/system/exceptionRecordNew',
    method: 'post',
    data: data
  })
}

// 修改异常记录（新）
export function updateExceptionRecordNew(data) {
  return request({
    url: '/system/exceptionRecordNew',
    method: 'put',
    data: data
  })
}

// 删除异常记录（新）
export function delExceptionRecordNew(exrId) {
  return request({
    url: '/system/exceptionRecordNew/' + exrId,
    method: 'delete'
  })
}
