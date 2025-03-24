import request from '@/utils/request'

// 查询客户等级列表
export function listClientLevel(query) {
  return request({
    url: '/system/clientLevel/list',
    method: 'get',
    params: query
  })
}

// 查询客户等级详细
export function getClientLevel(cllId) {
  return request({
    url: '/system/clientLevel/' + cllId,
    method: 'get'
  })
}

// 新增客户等级
export function addClientLevel(data) {
  return request({
    url: '/system/clientLevel',
    method: 'post',
    data: data
  })
}

// 修改客户等级
export function updateClientLevel(data) {
  return request({
    url: '/system/clientLevel',
    method: 'put',
    data: data
  })
}

// 删除客户等级
export function delClientLevel(cllId) {
  return request({
    url: '/system/clientLevel/' + cllId,
    method: 'delete'
  })
}
