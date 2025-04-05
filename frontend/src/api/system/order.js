import request from '@/utils/request'

// 查询订单列表
export function listOrder(query) {
  return request({
    url: '/system/order/list',
    method: 'get',
    params: query
  })
}
//审核查询订单列表
export function listReviewOrder(query) {
  return request({
    url: '/system/order/reviewList',
    method: 'get',
    params: query
  })
}
// 查询订单详细
export function getOrder(orId) {
  return request({
    url: '/system/order/' + orId,
    method: 'get'
  })
}

// 新增订单
export function addOrder(data) {
  return request({
    url: '/system/order',
    method: 'post',
    data: data
  })
}

// 修改订单
export function updateOrder(data) {
  return request({
    url: '/system/order',
    method: 'put',
    data: data
  })
}

// 删除订单
export function delOrder(orId) {
  return request({
    url: '/system/order/' + orId,
    method: 'delete'
  })
}
