import request from '@/utils/request'

// 查询订单所需产品关联列表
export function listOrderDemand(query) {
  return request({
    url: '/system/orderDemand/list',
    method: 'get',
    params: query
  })
}

// 查询订单所需产品关联详细
export function getOrderDemand(odId) {
  return request({
    url: '/system/orderDemand/' + odId,
    method: 'get'
  })
}

// 新增订单所需产品关联
export function addOrderDemand(data) {
  return request({
    url: '/system/orderDemand',
    method: 'post',
    data: data
  })
}

// 修改订单所需产品关联
export function updateOrderDemand(data) {
  return request({
    url: '/system/orderDemand',
    method: 'put',
    data: data
  })
}

// 删除订单所需产品关联
export function delOrderDemand(odId) {
  return request({
    url: '/system/orderDemand/' + odId,
    method: 'delete'
  })
}

// 修改订单所需产品关联
export function updateOrderWithDemand(data) {
  return request({
    url: '/system/orderDemand/updateWithOrder',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'text/plain;charset=utf-8'
    },
  })
}