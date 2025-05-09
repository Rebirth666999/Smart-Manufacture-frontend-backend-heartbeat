import request from '@/utils/request'

// 查询运行中的流程列表
export function listProcess() {
  return request({
    url: '/system/exception/running/processList',
    method: 'get',
  })
}