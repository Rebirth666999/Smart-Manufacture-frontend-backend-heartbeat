import request from '@/utils/request'

// 查询生产任务列表
export function listManufactureTask(query) {
  return request({
    url: '/system/manufactureTask/list',
    method: 'get',
    params: query
  })
}

// 审核端查询生产任务列表
export function listReviewManufactureTask(query) {
  return request({
    url: '/system/manufactureTask/reviewList',
    method: 'get',
    params: query
  })
}

// 查询生产任务详细
export function getManufactureTask(mtId) {
  return request({
    url: '/system/manufactureTask/' + mtId,
    method: 'get'
  })
}

// 新增生产任务
export function addManufactureTask(data) {
  return request({
    url: '/system/manufactureTask',
    method: 'post',
    data: data
  })
}

// 修改生产任务
export function updateManufactureTask(data) {
  return request({
    url: '/system/manufactureTask',
    method: 'put',
    data: data
  })
}

// 删除生产任务
export function delManufactureTask(mtId) {
  return request({
    url: '/system/manufactureTask/' + mtId,
    method: 'delete'
  })
}
