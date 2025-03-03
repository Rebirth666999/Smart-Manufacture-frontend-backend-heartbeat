import request from '@/utils/request'

// 查询设备操作列表
export function listEquipmentOperation(query) {
  return request({
    url: '/system/equipmentOperation/list',
    method: 'get',
    params: query
  })
}

// 查询设备操作详细
export function getEquipmentOperation(eoId) {
  return request({
    url: '/system/equipmentOperation/' + eoId,
    method: 'get'
  })
}

// 新增设备操作
export function addEquipmentOperation(data) {
  return request({
    url: '/system/equipmentOperation',
    method: 'post',
    data: data
  })
}

// 修改设备操作
export function updateEquipmentOperation(data) {
  return request({
    url: '/system/equipmentOperation',
    method: 'put',
    data: data
  })
}

// 删除设备操作
export function delEquipmentOperation(eoId) {
  return request({
    url: '/system/equipmentOperation/' + eoId,
    method: 'delete'
  })
}
