import request from '@/utils/request'

// 查询设备任务列表
export function listDeviceTask(query) {
  return request({
    url: '/system/deviceTask/list',
    method: 'get',
    params: query
  })
}

// 查询设备任务详细
export function getDeviceTask(dtId) {
  return request({
    url: '/system/deviceTask/' + dtId,
    method: 'get'
  })
}

// 新增设备任务
export function addDeviceTask(data) {
  return request({
    url: '/system/deviceTask',
    method: 'post',
    data: data
  })
}

// 修改设备任务
export function updateDeviceTask(data) {
  return request({
    url: '/system/deviceTask',
    method: 'put',
    data: data
  })
}

// 删除设备任务
export function delDeviceTask(dtId) {
  return request({
    url: '/system/deviceTask/' + dtId,
    method: 'delete'
  })
}

// 生成设备任务
export function saveDeviceTasks(data) {
  return request({
    url: '/system/deviceTask/saveTasks',
    method: 'post',
    data: data
  })
}