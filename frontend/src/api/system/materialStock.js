import request from '@/utils/request'

// 查询关联-车间原料库存列表
export function listMaterialStock(query) {
  return request({
    url: '/system/materialStock/list',
    method: 'get',
    params: query
  })
}

// 查询关联-车间原料库存详细
export function getMaterialStock(msId) {
  return request({
    url: '/system/materialStock/' + msId,
    method: 'get'
  })
}

// 新增关联-车间原料库存
export function addMaterialStock(data) {
  return request({
    url: '/system/materialStock',
    method: 'post',
    data: data
  })
}

// 修改关联-车间原料库存
export function updateMaterialStock(data) {
  return request({
    url: '/system/materialStock',
    method: 'put',
    data: data
  })
}

// 删除关联-车间原料库存
export function delMaterialStock(msId) {
  return request({
    url: '/system/materialStock/' + msId,
    method: 'delete'
  })
}
