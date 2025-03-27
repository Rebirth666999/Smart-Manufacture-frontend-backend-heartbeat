import request from '@/utils/request'

// 查询仓库列表
export function listStore(query) {
  return request({
    url: '/system/store/list',
    method: 'get',
    params: query
  })
}

// 查询仓库详细
export function getStore(stId) {
  return request({
    url: '/system/store/' + stId,
    method: 'get'
  })
}

// 新增仓库
export function addStore(data) {
  return request({
    url: '/system/store',
    method: 'post',
    data: data
  })
}

// 修改仓库
export function updateStore(data) {
  return request({
    url: '/system/store',
    method: 'put',
    data: data
  })
}

// 删除仓库
export function delStore(stId) {
  return request({
    url: '/system/store/' + stId,
    method: 'delete'
  })
}
