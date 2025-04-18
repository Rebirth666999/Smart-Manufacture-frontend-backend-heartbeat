import request from '@/utils/request'

// 查询仓库原料库存列表
export function listMaterialStock(query) {
  return request({
    url: '/system/materialStock/list',
    method: 'get',
    params: query
  })
}

// 查询仓库原料库存详细
export function getMaterialStock(mssId) {
  return request({
    url: '/system/materialStock/' + mssId,
    method: 'get'
  })
}

// 新增仓库原料库存
export function addMaterialStock(data) {
  return request({
    url: '/system/materialStock',
    method: 'post',
    data: data
  })
}

// 修改仓库原料库存
export function updateMaterialStock(data) {
  return request({
    url: '/system/materialStock',
    method: 'put',
    data: data
  })
}

// 删除仓库原料库存
export function delMaterialStock(mssId) {
  return request({
    url: '/system/materialStock/' + mssId,
    method: 'delete'
  })
}
