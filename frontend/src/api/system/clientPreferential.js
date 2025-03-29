import request from '@/utils/request'

// 查询客户优惠策略列表
export function listClientPreferential(query) {
  return request({
    url: '/system/clientPreferential/list',
    method: 'get',
    params: query
  })
}

// 查询客户优惠策略详细
export function getClientPreferential(cpId) {
  return request({
    url: '/system/clientPreferential/' + cpId,
    method: 'get'
  })
}

// 新增客户优惠策略
export function addClientPreferential(data) {
  return request({
    url: '/system/clientPreferential',
    method: 'post',
    data: data
  })
}

// 修改客户优惠策略
export function updateClientPreferential(data) {
  return request({
    url: '/system/clientPreferential',
    method: 'put',
    data: data
  })
}

// 删除客户优惠策略
export function delClientPreferential(cpId) {
  return request({
    url: '/system/clientPreferential/' + cpId,
    method: 'delete'
  })
}
