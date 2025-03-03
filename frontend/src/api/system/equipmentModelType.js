import request from '@/utils/request'

// 查询设备模型类型列表
export function listEquipmentModelType(query) {
  return request({
    url: '/system/equipmentModelType/list',
    method: 'get',
    params: query
  })
}

// 查询设备模型类型详细
export function getEquipmentModelType(emtId) {
  return request({
    url: '/system/equipmentModelType/' + emtId,
    method: 'get'
  })
}

// 新增设备模型类型
export function addEquipmentModelType(data) {
  return request({
    url: '/system/equipmentModelType',
    method: 'post',
    data: data
  })
}

// 修改设备模型类型
export function updateEquipmentModelType(data) {
  return request({
    url: '/system/equipmentModelType',
    method: 'put',
    data: data
  })
}

// 删除设备模型类型
export function delEquipmentModelType(emtId) {
  return request({
    url: '/system/equipmentModelType/' + emtId,
    method: 'delete'
  })
}
