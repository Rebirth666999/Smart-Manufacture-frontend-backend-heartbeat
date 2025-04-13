import request from '@/utils/request'

// 查询异常列表
export function listException(query) {
  return request({
    url: '/system/exception/list',
    method: 'get',
    params: query
  })
}

// 查询异常详细
export function getException(exId) {
  return request({
    url: '/system/exception/' + exId,
    method: 'get'
  })
}

// 新增异常
export function addException(data) {
  return request({
    url: '/system/exception',
    method: 'post',
    data: data
  })
}

// 修改异常
export function updateException(data) {
  return request({
    url: '/system/exception',
    method: 'put',
    data: data
  })
}

// 删除异常
export function delException(exId) {
  return request({
    url: '/system/exception/' + exId,
    method: 'delete'
  })
}
