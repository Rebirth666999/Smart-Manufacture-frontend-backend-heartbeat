import request from '@/utils/request'

// 查询原料仓库列表
export function listMaterialStore(query) {
  return request({
    url: '/system/materialStore/list',
    method: 'get',
    params: query
  })
}

// 查询原料仓库详细
export function getMaterialStore(msId) {
  return request({
    url: '/system/materialStore/' + msId,
    method: 'get'
  })
}

// 新增原料仓库
export function addMaterialStore(data) {
  return request({
    url: '/system/materialStore',
    method: 'post',
    data: data
  })
}

// 修改原料仓库
export function updateMaterialStore(data) {
  return request({
    url: '/system/materialStore',
    method: 'put',
    data: data
  })
}

// 删除原料仓库
export function delMaterialStore(msId) {
  return request({
    url: '/system/materialStore/' + msId,
    method: 'delete'
  })
}
