import request from '@/utils/request'

// 查询产品台账列表
export function listProductRecord(query) {
  return request({
    url: '/system/productRecord/list',
    method: 'get',
    params: query
  })
}

// 查询产品台账详细
export function getProductRecord(prrId) {
  return request({
    url: '/system/productRecord/' + prrId,
    method: 'get'
  })
}

// 新增产品台账
export function addProductRecord(data) {
  return request({
    url: '/system/productRecord',
    method: 'post',
    data: data
  })
}

// 修改产品台账
export function updateProductRecord(data) {
  return request({
    url: '/system/productRecord',
    method: 'put',
    data: data
  })
}

// 删除产品台账
export function delProductRecord(prrId) {
  return request({
    url: '/system/productRecord/' + prrId,
    method: 'delete'
  })
}
