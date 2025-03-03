import request from '@/utils/request'

// 查询车间列表
export function listArea(query) {
  return request({
    url: '/system/area/list',
    method: 'get',
    params: query
  })
}

// 查询车间详细
export function getArea(arId) {
  return request({
    url: '/system/area/' + arId,
    method: 'get'
  })
}

// 新增车间
export function addArea(data) {
  return request({
    url: '/system/area',
    method: 'post',
    data: data
  })
}

// 修改车间
export function updateArea(data) {
  return request({
    url: '/system/area',
    method: 'put',
    data: data
  })
}

// 删除车间
export function delArea(arId) {
  return request({
    url: '/system/area/' + arId,
    method: 'delete'
  })
}
