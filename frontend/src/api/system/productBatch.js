import request from '@/utils/request'

// 查询实际产品批次编码列表
export function listProductBatch(query) {
  return request({
    url: '/system/productBatch/list',
    method: 'get',
    params: query
  })
}

// 查询实际产品批次编码详细
export function getProductBatch(pbId) {
  return request({
    url: '/system/productBatch/' + pbId,
    method: 'get'
  })
}

// 新增实际产品批次编码
export function addProductBatch(data) {
  return request({
    url: '/system/productBatch',
    method: 'post',
    data: data
  })
}

// 修改实际产品批次编码
export function updateProductBatch(data) {
  return request({
    url: '/system/productBatch',
    method: 'put',
    data: data
  })
}

// 删除实际产品批次编码
export function delProductBatch(pbId) {
  return request({
    url: '/system/productBatch/' + pbId,
    method: 'delete'
  })
}
