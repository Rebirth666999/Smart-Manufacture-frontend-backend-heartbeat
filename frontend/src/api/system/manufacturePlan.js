import request from '@/utils/request'

// 查询生产计划列表
export function listManufacturePlan(query) {
  return request({
    url: '/system/manufacturePlan/list',
    method: 'get',
    params: query
  })
}
//审核查询生产计划列表
export function listReviewManufacturePlan(query) {
  return request({
    url: '/system/manufacturePlan/reviewList',
    method: 'get',
    params: query
  })
}
export function getManufacturePlan(mpId) {
  return request({
    url: '/system/manufacturePlan/' + mpId,
    method: 'get'
  })
}

// 新增生产计划
export function addManufacturePlan(data) {
  return request({
    url: '/system/manufacturePlan',
    method: 'post',
    data: data
  })
}

// 修改生产计划
export function updateManufacturePlan(data) {
  return request({
    url: '/system/manufacturePlan',
    method: 'put',
    data: data
  })
}

// 删除生产计划
export function delManufacturePlan(mpId) {
  return request({
    url: '/system/manufacturePlan/' + mpId,
    method: 'delete'
  })
}
