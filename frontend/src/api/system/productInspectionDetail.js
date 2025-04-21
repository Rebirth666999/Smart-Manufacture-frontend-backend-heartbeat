import request from '@/utils/request'

// 查询产品质检单明细列表
export function listProductInspectionDetail(query) {
  return request({
    url: '/system/productInspectionDetail/list',
    method: 'get',
    params: query
  })
}

// 查询产品质检单明细详细
export function getProductInspectionDetail(pidId) {
  return request({
    url: '/system/productInspectionDetail/' + pidId,
    method: 'get'
  })
}

// 新增产品质检单明细
export function addProductInspectionDetail(data) {
  return request({
    url: '/system/productInspectionDetail',
    method: 'post',
    data: data
  })
}

// 修改产品质检单明细
export function updateProductInspectionDetail(data) {
  return request({
    url: '/system/productInspectionDetail',
    method: 'put',
    data: data
  })
}

// 删除产品质检单明细
export function delProductInspectionDetail(pidId) {
  return request({
    url: '/system/productInspectionDetail/' + pidId,
    method: 'delete'
  })
}

// 新增产品质检单明细
export function listInspectionProduct(data) {
  return request({
    url: '/system/productInspectionDetail/getProduct',
    method: 'post',
    data: data
  })
}