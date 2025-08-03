import request from '@/utils/request'

// 查询异常记录列表
export function listExceptionRecord(query) {
  return request({
    url: '/system/exceptionRecord/list',
    method: 'get',
    params: query
  })
}

// 查询异常记录详细
export function getExceptionRecord(exrId) {
  return request({
    url: '/system/exceptionRecord/' + exrId,
    method: 'get'
  })
}

// 新增异常记录
export function addExceptionRecord(data) {
  return request({
    url: '/system/exceptionRecord',
    method: 'post',
    data: data
  })
}

// 修改异常记录
export function updateExceptionRecord(data) {
  return request({
    url: '/system/exceptionRecord',
    method: 'put',
    data: data
  })
}

// 删除异常记录
export function delExceptionRecord(exrId) {
  return request({
    url: '/system/exceptionRecordNew/' + exrId,
    method: 'delete'
  })
}

// 传送异常信息到知识库
export function saveDescToKnowledge(descObj) {
  return request({
    url: '',//需要给出知识库地址
    method: 'post',
    data: { exrDesc: descObj }
  })
}
