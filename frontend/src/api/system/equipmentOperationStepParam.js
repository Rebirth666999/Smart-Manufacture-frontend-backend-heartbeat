import request from '@/utils/request'

// 查询设备操作步骤列表
export function listEquipmentOperationStepParam(query) {
  return request({
    url: '/system/equipmentOperationStepParam/list',
    method: 'get',
    params: query
  })
}
