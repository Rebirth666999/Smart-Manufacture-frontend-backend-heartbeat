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
    data: data,
    timeout: 90000
  })
}

// 查询设备任务参数列表
export function listDeviceTaskParam(query) {
  return request({
    url: '/system/deviceTaskParam/list',
    method: 'get',
    params: query
  })
}

// 查询设备任务前序列表
export function listDeviceTaskPrev(query) {
  return request({
    url: '/system/deviceTaskPrev/list',
    method: 'get',
    params: query
  })
}

// 下发设备任务
export function executeDeviceTask(url, data) {
  return request({
    baseURL: '',
    url: url,
    method: 'post',
    data: data
  })
}

// 查询设备任务前序列表
export function findRemainByManufactureTask(data) {
  return request({
    url: '/system/deviceTask/findRemainByManufactureTask',
    method: 'post',
    data: data
  })
}
