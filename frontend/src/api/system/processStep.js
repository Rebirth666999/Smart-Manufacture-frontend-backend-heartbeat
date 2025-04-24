import request from '@/utils/request'

// 查询工艺步骤列表
export function listProcessStep(query) {
  return request({
    url: '/system/processStep/list',
    method: 'get',
    params: query
  })
}

// 查询工艺步骤详细
export function getProcessStep(psId) {
  return request({
    url: '/system/processStep/' + psId,
    method: 'get'
  })
}

// 新增工艺步骤
export function addProcessStep(data) {
  return request({
    url: '/system/processStep',
    method: 'post',
    data: data
  })
}

// 修改工艺步骤
export function updateProcessStep(data) {
  return request({
    url: '/system/processStep',
    method: 'put',
    data: data
  })
}

// 删除工艺步骤
export function delProcessStep(psId) {
  return request({
    url: '/system/processStep/' + psId,
    method: 'delete'
  })
}

// 查询工艺步骤原料需求列表
export function listProcessMaterial(query) {
  return request({
    url: '/system/processMaterial/list',
    method: 'get',
    params: query
  })
}