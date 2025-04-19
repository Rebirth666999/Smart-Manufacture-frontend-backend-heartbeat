import request from '@/utils/request'

// 查询产品台账列表
export function listProductLedger(query) {
  return request({
    url: '/system/productLedger/list',
    method: 'get',
    params: query
  })
}

// 查询产品台账详细
export function getProductLedger(plId) {
  return request({
    url: '/system/productLedger/' + plId,
    method: 'get'
  })
}

// 新增产品台账
export function addProductLedger(data) {
  return request({
    url: '/system/productLedger',
    method: 'post',
    data: data
  })
}

// 修改产品台账
export function updateProductLedger(data) {
  return request({
    url: '/system/productLedger',
    method: 'put',
    data: data
  })
}

// 删除产品台账
export function delProductLedger(plId) {
  return request({
    url: '/system/productLedger/' + plId,
    method: 'delete'
  })
}
