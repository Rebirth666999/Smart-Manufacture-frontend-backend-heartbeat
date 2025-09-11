import request from '@/utils/request'

// 查询设备列表
export function listEquipment(query) {
  return request({
    url: '/system/equipment/list',
    method: 'get',
    params: query
  })
}

// 查询审核用设备列表
export function listReviewEquipment(query) {
  return request({
    url: '/system/equipment/reviewList',
    method: 'get',
    params: query
  })
}

// 查询设备详细
export function getEquipment(eqId) {
  return request({
    url: '/system/equipment/' + eqId,
    method: 'get'
  })
}

// 新增设备
export function addEquipment(data) {
  return request({
    url: '/system/equipment',
    method: 'post',
    data: data
  })
}

// 修改设备
export function updateEquipment(data) {
  return request({
    url: '/system/equipment',
    method: 'put',
    data: data
  })
}

// 删除设备
export function delEquipment(eqId) {
  return request({
    url: '/system/equipment/' + eqId,
    method: 'delete'
  })
}


//设备测试
export function testEquipment(data,eqFlaskIp) {
console.log(eqFlaskIp);
return fetch(`${eqFlaskIp}`, { // 使用模板字符串
  method: 'POST',
  headers: {
    'Content-Type': 'application/json'
  },
  body: JSON.stringify(data) // 将JavaScript对象转换为JSON字符串发送
})

}