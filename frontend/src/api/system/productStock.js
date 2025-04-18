import request from '@/utils/request'

// 查询仓库产品库存列表
export function listProductStock(query) {
  return request({
    url: '/system/productStock/list',
    method: 'get',
    params: query
  })
}

// 查询仓库产品库存详细
export function getProductStock(pssId) {
  return request({
    url: '/system/productStock/' + pssId,
    method: 'get'
  })
}

// 新增仓库产品库存
export function addProductStock(data) {
  return request({
    url: '/system/productStock',
    method: 'post',
    data: data
  })
}

// 修改仓库产品库存
export function updateProductStock(data) {
  return request({
    url: '/system/productStock',
    method: 'put',
    data: data
  })
}

// 删除仓库产品库存
export function delProductStock(pssId) {
  return request({
    url: '/system/productStock/' + pssId,
    method: 'delete'
  })
}
