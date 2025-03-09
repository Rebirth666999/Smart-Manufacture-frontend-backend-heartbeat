import request from '@/utils/request'

// 查询关联-工艺步骤的跨轮次前序步骤列表
export function listProcessStepPrevRound(query) {
  return request({
    url: '/system/processStepPrevRound/list',
    method: 'get',
    params: query
  })
}

// 查询关联-工艺步骤的跨轮次前序步骤详细
export function getProcessStepPrevRound(psprId) {
  return request({
    url: '/system/processStepPrevRound/' + psprId,
    method: 'get'
  })
}

// 新增关联-工艺步骤的跨轮次前序步骤
export function addProcessStepPrevRound(data) {
  return request({
    url: '/system/processStepPrevRound',
    method: 'post',
    data: data
  })
}

// 修改关联-工艺步骤的跨轮次前序步骤
export function updateProcessStepPrevRound(data) {
  return request({
    url: '/system/processStepPrevRound',
    method: 'put',
    data: data
  })
}

// 删除关联-工艺步骤的跨轮次前序步骤
export function delProcessStepPrevRound(psprId) {
  return request({
    url: '/system/processStepPrevRound/' + psprId,
    method: 'delete'
  })
}
