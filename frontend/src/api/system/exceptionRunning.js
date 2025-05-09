import request from '@/utils/request'

// 查询运行中的流程列表
export function listProcess() {
  return request({
    url: '/system/exception/running/processList',
    method: 'get',
  })
}

// 查询运行中的流程列表
export function listTodoProcess() {
  return request({
    url: '/system/exception/running/todoList',
    method: 'get',
  })
}

// 获取流程模型
export function getProcessXml(data) {
  return request({
    url: '/system/exception/running/processXML/',
    method: 'post',
    data: data
  })
}

// 获取流程模型
export function getProcessFlowXml(procId) {
  return request({
    url: '/workflow/task/diagram/' + procId,
    method: 'post',
    responseType: 'blob'
  })
}

// 处理任务
export function handleTask(data) {
  return request({
    url: '/system/exception/running/handleTask/',
    method: 'post',
    data: data
  })
}

// 查询已结束的流程列表
export function listFinishProcess() {
  return request({
    url: '/system/exception/running/finishList',
    method: 'get',
  })
}