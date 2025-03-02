import request from '@/utils/request'

// 查询设备事件日志列表
export function listEquipmentRecord(query) {
  return request({
    url: '/system/equipmentRecord/list',
    method: 'get',
    params: query
  })
}

// 查询设备事件日志详细
export function getEquipmentRecord(erId) {
  return request({
    url: '/system/equipmentRecord/' + erId,
    method: 'get'
  })
}

// 新增设备事件日志
export function addEquipmentRecord(data) {
  return request({
    url: '/system/equipmentRecord',
    method: 'post',
    data: data
  })
}

// 修改设备事件日志
export function updateEquipmentRecord(data) {
  return request({
    url: '/system/equipmentRecord',
    method: 'put',
    data: data
  })
}

// 删除设备事件日志
export function delEquipmentRecord(erId) {
  return request({
    url: '/system/equipmentRecord/' + erId,
    method: 'delete'
  })
}
