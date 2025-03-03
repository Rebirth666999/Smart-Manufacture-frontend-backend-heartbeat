import request from '@/utils/request'

// 查询主控节点列表
export function listAreaControl(query) {
  return request({
    url: '/system/areaControl/list',
    method: 'get',
    params: query
  })
}

// 查询主控节点详细
export function getAreaControl(acId) {
  return request({
    url: '/system/areaControl/' + acId,
    method: 'get'
  })
}

// 新增主控节点
export function addAreaControl(data) {
  return request({
    url: '/system/areaControl',
    method: 'post',
    data: data
  })
}

// 修改主控节点
export function updateAreaControl(data) {
  return request({
    url: '/system/areaControl',
    method: 'put',
    data: data
  })
}

// 删除主控节点
export function delAreaControl(acId) {
  return request({
    url: '/system/areaControl/' + acId,
    method: 'delete'
  })
}
