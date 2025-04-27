import request from '@/utils/request'

// 查询生产计划(主)列表
export function listManufacturePlanMain(query) {
  return request({
    url: '/system/manufacturePlanMain/list',
    method: 'get',
    params: query
  })
}

// 查询生产计划(主)详细
export function getManufacturePlanMain(mpmId) {
  return request({
    url: '/system/manufacturePlanMain/' + mpmId,
    method: 'get'
  })
}

// 新增生产计划(主)
export function addManufacturePlanMain(data) {
  return request({
    url: '/system/manufacturePlanMain',
    method: 'post',
    data: data
  })
}

// 修改生产计划(主)
export function updateManufacturePlanMain(data) {
  return request({
    url: '/system/manufacturePlanMain',
    method: 'put',
    data: data
  })
}

// 删除生产计划(主)
export function delManufacturePlanMain(mpmId) {
  return request({
    url: '/system/manufacturePlanMain/' + mpmId,
    method: 'delete'
  })
}
