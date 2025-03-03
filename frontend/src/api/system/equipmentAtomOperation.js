import request from '@/utils/request'

// 查询设备原子操作列表
export function listEquipmentAtomOperation(query) {
  return request({
    url: '/system/equipmentAtomOperation/list',
    method: 'get',
    params: query
  })
}

// 查询设备原子操作详细
export function getEquipmentAtomOperation(eaoId) {
  return request({
    url: '/system/equipmentAtomOperation/' + eaoId,
    method: 'get'
  })
}

// 新增设备原子操作
export function addEquipmentAtomOperation(data) {
  return request({
    url: '/system/equipmentAtomOperation',
    method: 'post',
    data: data
  })
}

// 修改设备原子操作
export function updateEquipmentAtomOperation(data) {
  return request({
    url: '/system/equipmentAtomOperation',
    method: 'put',
    data: data
  })
}

// 删除设备原子操作
export function delEquipmentAtomOperation(eaoId) {
  return request({
    url: '/system/equipmentAtomOperation/' + eaoId,
    method: 'delete'
  })
}
