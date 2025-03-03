import request from '@/utils/request'

// 查询设备模型操作列表
export function listModelOperation(query) {
  return request({
    url: '/system/modelOperation/list',
    method: 'get',
    params: query
  })
}

// 查询设备模型操作详细
export function getModelOperation(moId) {
  return request({
    url: '/system/modelOperation/' + moId,
    method: 'get'
  })
}

// 新增设备模型操作
export function addModelOperation(data) {
  return request({
    url: '/system/modelOperation',
    method: 'post',
    data: data
  })
}

// 修改设备模型操作
export function updateModelOperation(data) {
  return request({
    url: '/system/modelOperation',
    method: 'put',
    data: data
  })
}

// 删除设备模型操作
export function delModelOperation(moId) {
  return request({
    url: '/system/modelOperation/' + moId,
    method: 'delete'
  })
}
