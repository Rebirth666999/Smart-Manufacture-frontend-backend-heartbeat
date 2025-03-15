import request from '@/utils/request'

// 查询工艺流程原料需求列表
export function listProcessMaterial(query) {
  return request({
    url: '/system/processMaterial/list',
    method: 'get',
    params: query
  })
}

// 查询工艺流程原料需求详细
export function getProcessMaterial(pmId) {
  return request({
    url: '/system/processMaterial/' + pmId,
    method: 'get'
  })
}

// 新增工艺流程原料需求
export function addProcessMaterial(data) {
  return request({
    url: '/system/processMaterial',
    method: 'post',
    data: data
  })
}

// 修改工艺流程原料需求
export function updateProcessMaterial(data) {
  return request({
    url: '/system/processMaterial',
    method: 'put',
    data: data
  })
}

// 删除工艺流程原料需求
export function delProcessMaterial(pmId) {
  return request({
    url: '/system/processMaterial/' + pmId,
    method: 'delete'
  })
}
