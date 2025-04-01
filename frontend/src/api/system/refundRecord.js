import request from '@/utils/request'

// 查询退货记录列表
export function listRefundRecord(query) {
  return request({
    url: '/system/refundRecord/list',
    method: 'get',
    params: query
  })
}

// 查询退货记录详细
export function getRefundRecord(rrId) {
  return request({
    url: '/system/refundRecord/' + rrId,
    method: 'get'
  })
}

// 新增退货记录
export function addRefundRecord(data) {
  return request({
    url: '/system/refundRecord',
    method: 'post',
    data: data
  })
}

// 修改退货记录
export function updateRefundRecord(data) {
  return request({
    url: '/system/refundRecord',
    method: 'put',
    data: data
  })
}

// 删除退货记录
export function delRefundRecord(rrId) {
  return request({
    url: '/system/refundRecord/' + rrId,
    method: 'delete'
  })
}
