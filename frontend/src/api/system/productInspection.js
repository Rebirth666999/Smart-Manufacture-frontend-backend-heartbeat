import request from '@/utils/request'

// 查询产品质检单列表
export function listProductInspection(query) {
  return request({
    url: '/system/productInspection/list',
    method: 'get',
    params: query
  })
}

// 查询产品质检单详细
export function getProductInspection(piId) {
  return request({
    url: '/system/productInspection/' + piId,
    method: 'get'
  })
}

// 新增产品质检单
export function addProductInspection(data) {
  return request({
    url: '/system/productInspection',
    method: 'post',
    data: data
  })
}

// 修改产品质检单
export function updateProductInspection(data) {
  return request({
    url: '/system/productInspection',
    method: 'put',
    data: data
  })
}

// 删除产品质检单
export function delProductInspection(piId) {
  return request({
    url: '/system/productInspection/' + piId,
    method: 'delete'
  })
}
