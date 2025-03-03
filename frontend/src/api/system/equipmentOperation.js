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

// 保存流程模型
export function saveModel(data) {
  return request({
    url: '/system/equipmentOperation/model/save',
    method: 'post',
    headers: {
      'Content-Type': 'text/plain;charset=utf-8'
    },
    data: data
  })
}

// 获取流程模型
export function getBpmnXml(eoModel) {
  return request({
    url: '/system/equipmentOperation/model/bpmnXml/' + eoModel,
    method: 'get'
  })
}