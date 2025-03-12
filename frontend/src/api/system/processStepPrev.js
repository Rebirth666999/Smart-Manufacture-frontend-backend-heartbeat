import request from '@/utils/request'

// 查询工艺步骤的前序步骤列表
export function listProcessStepPrev(query) {
  return request({
    url: '/system/processStepPrev/list',
    method: 'get',
    params: query
  })
}

// 查询工艺步骤的前序步骤详细
export function getProcessStepPrev(pspId) {
  return request({
    url: '/system/processStepPrev/' + pspId,
    method: 'get'
  })
}

// 新增工艺步骤的前序步骤
export function addProcessStepPrev(data) {
  return request({
    url: '/system/processStepPrev',
    method: 'post',
    data: data
  })
}

// 修改工艺步骤的前序步骤
export function updateProcessStepPrev(data) {
  return request({
    url: '/system/processStepPrev',
    method: 'put',
    data: data
  })
}

// 删除工艺步骤的前序步骤
export function delProcessStepPrev(pspId) {
  return request({
    url: '/system/processStepPrev/' + pspId,
    method: 'delete'
  })
}
