import request from '@/utils/request'

// 查询产品仓库列表
export function listProductStore(query) {
  return request({
    url: '/system/productStore/list',
    method: 'get',
    params: query
  })
}

// 查询产品仓库详细
export function getProductStore(prsId) {
  return request({
    url: '/system/productStore/' + prsId,
    method: 'get'
  })
}

// 新增产品仓库
export function addProductStore(data) {
  return request({
    url: '/system/productStore',
    method: 'post',
    data: data
  })
}

// 修改产品仓库
export function updateProductStore(data) {
  return request({
    url: '/system/productStore',
    method: 'put',
    data: data
  })
}

// 删除产品仓库
export function delProductStore(prsId) {
  return request({
    url: '/system/productStore/' + prsId,
    method: 'delete'
  })
}
