import request from '@/utils/request'

// 查询原料台账列表
export function listMaterialLedger(query) {
  return request({
    url: '/system/materialLedger/list',
    method: 'get',
    params: query
  })
}

// 查询原料台账详细
export function getMaterialLedger(mlId) {
  return request({
    url: '/system/materialLedger/' + mlId,
    method: 'get'
  })
}

// 新增原料台账
export function addMaterialLedger(data) {
  return request({
    url: '/system/materialLedger',
    method: 'post',
    data: data
  })
}

// 修改原料台账
export function updateMaterialLedger(data) {
  return request({
    url: '/system/materialLedger',
    method: 'put',
    data: data
  })
}

// 删除原料台账
export function delMaterialLedger(mlId) {
  return request({
    url: '/system/materialLedger/' + mlId,
    method: 'delete'
  })
}
