import request from '@/utils/request'

// 查询客户贸易信息列表
export function listClientTrade(query) {
  return request({
    url: '/system/clientTrade/list',
    method: 'get',
    params: query
  })
}

// 查询客户贸易信息详细
export function getClientTrade(ctId) {
  return request({
    url: '/system/clientTrade/' + ctId,
    method: 'get'
  })
}

// 新增客户贸易信息
export function addClientTrade(data) {
  return request({
    url: '/system/clientTrade',
    method: 'post',
    data: data
  })
}

// 修改客户贸易信息
export function updateClientTrade(data) {
  return request({
    url: '/system/clientTrade',
    method: 'put',
    data: data
  })
}

// 删除客户贸易信息
export function delClientTrade(ctId) {
  return request({
    url: '/system/clientTrade/' + ctId,
    method: 'delete'
  })
}
