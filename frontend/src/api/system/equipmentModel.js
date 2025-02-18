import request from '@/utils/request'

// 查询设备模型列表
export function listEquipmentModel(query) {
  return request({
    url: '/system/equipmentModel/list',
    method: 'get',
    params: query
  })
}

// 查询设备模型详细
export function getEquipmentModel(emId) {
  return request({
    url: '/system/equipmentModel/' + emId,
    method: 'get'
  })
}

// 新增设备模型
export function addEquipmentModel(data) {
  return request({
    url: '/system/equipmentModel',
    method: 'post',
    data: data
  })
}

// 修改设备模型
export function updateEquipmentModel(data) {
  return request({
    url: '/system/equipmentModel',
    method: 'put',
    data: data
  })
}

// 删除设备模型
export function delEquipmentModel(emId) {
  return request({
    url: '/system/equipmentModel/' + emId,
    method: 'delete'
  })
}
