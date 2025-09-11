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

})}

export function sendimg(imagePath) {
  // 创建 FormData 对象
  const formData = new FormData();
  
  // 如果是文件对象
  if (imagePath instanceof File) {
    formData.append('image', imagePath);
  } 
  // // 如果是本地路径，需要先转换为 File 对象
  // else if (typeof imagePath === 'string') {
  //   // 对于前端，无法直接读取本地文件系统路径
  //   // 需要通过 input[type=file] 或者拖拽获取 File 对象
  //   console.error('无法直接使用文件路径，请使用 File 对象');
  //   return Promise.reject('无法直接使用文件路径');
  // }

  return request ({
    url: "http://192.168.1.100:5000/api/detect",
    method: 'post',
    headers: {
      // 不要手动设置 Content-Type，让浏览器自动设置 multipart/form-data
      // 'Content-Type': 'multipart/form-data' 
    },
    data: formData // 使用 data 而不是 body
  });
}
