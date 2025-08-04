import request from '@/utils/request'

// 查询异常记录（新）列表
export function listExceptionRecordNew(query) {
  return request({
    url: '/system/exceptionRecordNew/list',
    method: 'get',
    params: query
  })
}

// 查询异常记录（新）详细
export function getExceptionRecordNew(exrId) {
  return request({
    url: '/system/exceptionRecordNew/' + exrId,
    method: 'get'
  })
}

// 新增异常记录（新）
export function addExceptionRecordNew(data) {
  return request({
    url: '/system/exceptionRecordNew',
    method: 'post',
    data: data
  })
}

// 修改异常记录（新）
export function updateExceptionRecordNew(data) {
  return request({
    url: '/system/exceptionRecordNew',
    method: 'put',
    data: data
  })
}

// 删除异常记录（新）
export function delExceptionRecordNew(exrId) {
  return request({
    url: '/system/exceptionRecordNew/' + exrId,
    method: 'delete'
  })
}

export function saveDescToKnowledge(descObj,exrCode) {
  return request({
    url: ' https://api.coze.cn/v3/chat',
    method: 'post',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': 'Bearer pat_NKqGsF5f7T0iABkNsQqq1Fanw4oZ6Lk72vuRhRRBC52JUTWc1EpEsxLqms5wPgqr',
    },
    body:{
      bot_id:'7532762700867272739',
      user_id:exrCode,
      stream:true,
      content_type:'object_string',
      content:descObj,
    }

    // url :'http://localhost:5000/get_info',
    // method:'get',
    // headers:{
    //   'Content-Type': 'application/json',
    // }

})
}
