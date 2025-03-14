import request from '@/utils/request'

// 查询工艺流程列表
export function listProcess(query) {
  return request({
    url: '/system/process/list',
    method: 'get',
    params: query
  })
}

// 查询工艺流程详细
export function getProcess(procId) {
  return request({
    url: '/system/process/' + procId,
    method: 'get'
  })
}

// 新增工艺流程
export function addProcess(data) {
  return request({
    url: '/system/process',
    method: 'post',
    data: data
  })
}

// 修改工艺流程
export function updateProcess(data) {
  return request({
    url: '/system/process',
    method: 'put',
    data: data
  })
}

// 删除工艺流程
export function delProcess(procId) {
  return request({
    url: '/system/process/' + procId,
    method: 'delete'
  })
}

// 保存流程模型
export function saveModel(data) {
  return request({
    url: '/system/process/model/save',
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
    url: '/system/process/model/bpmnXml/' + eoModel,
    method: 'get'
  })
}