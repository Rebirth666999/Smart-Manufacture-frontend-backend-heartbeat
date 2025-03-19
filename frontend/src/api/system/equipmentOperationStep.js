import request from '@/utils/request'

// 查询设备操作步骤列表
export function listEquipmentOperationStep(query) {
  return request({
    url: '/system/equipmentOperationStep/list',
    method: 'get',
    params: query
  })
}
