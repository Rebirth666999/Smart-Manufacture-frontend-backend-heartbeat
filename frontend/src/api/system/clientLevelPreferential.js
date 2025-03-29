import request from '@/utils/request'

// 查询关联-客户等级对应的优惠策略列表
export function listClientLevelPreferential(query) {
  return request({
    url: '/system/clientLevelPreferential/list',
    method: 'get',
    params: query
  })
}

// 查询关联-客户等级对应的优惠策略详细
export function getClientLevelPreferential(clpId) {
  return request({
    url: '/system/clientLevelPreferential/' + clpId,
    method: 'get'
  })
}

// 新增关联-客户等级对应的优惠策略
export function addClientLevelPreferential(data) {
  return request({
    url: '/system/clientLevelPreferential',
    method: 'post',
    data: data
  })
}

// 修改关联-客户等级对应的优惠策略
export function updateClientLevelPreferential(data) {
  return request({
    url: '/system/clientLevelPreferential',
    method: 'put',
    data: data
  })
}

// 删除关联-客户等级对应的优惠策略
export function delClientLevelPreferential(clpId) {
  return request({
    url: '/system/clientLevelPreferential/' + clpId,
    method: 'delete'
  })
}
