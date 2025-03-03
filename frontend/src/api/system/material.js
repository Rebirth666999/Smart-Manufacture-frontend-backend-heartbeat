import request from '@/utils/request'

// 查询原料列表
export function listMaterial(query) {
  return request({
    url: '/system/material/list',
    method: 'get',
    params: query
  })
}

// 查询原料详细
export function getMaterial(maId) {
  return request({
    url: '/system/material/' + maId,
    method: 'get'
  })
}

// 新增原料
export function addMaterial(data) {
  return request({
    url: '/system/material',
    method: 'post',
    data: data
  })
}

// 修改原料
export function updateMaterial(data) {
  return request({
    url: '/system/material',
    method: 'put',
    data: data
  })
}

// 删除原料
export function delMaterial(maId) {
  return request({
    url: '/system/material/' + maId,
    method: 'delete'
  })
}
