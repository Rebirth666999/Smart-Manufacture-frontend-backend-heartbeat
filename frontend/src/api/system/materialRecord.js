import request from '@/utils/request'

// 查询原料台账列表
export function listMaterialRecord(query) {
  return request({
    url: '/system/materialRecord/list',
    method: 'get',
    params: query
  })
}

// 查询原料台账详细
export function getMaterialRecord(mrId) {
  return request({
    url: '/system/materialRecord/' + mrId,
    method: 'get'
  })
}

// 新增原料台账
export function addMaterialRecord(data) {
  return request({
    url: '/system/materialRecord',
    method: 'post',
    data: data
  })
}

// 修改原料台账
export function updateMaterialRecord(data) {
  return request({
    url: '/system/materialRecord',
    method: 'put',
    data: data
  })
}

// 删除原料台账
export function delMaterialRecord(mrId) {
  return request({
    url: '/system/materialRecord/' + mrId,
    method: 'delete'
  })
}
