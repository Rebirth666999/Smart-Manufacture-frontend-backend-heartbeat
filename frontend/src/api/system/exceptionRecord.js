import request from '@/utils/request'

// 查询异常记录列表
export function listExceptionRecord(query) {
  return request({
    url: '/system/exceptionRecordNew/list',
    method: 'get',
    params: query
  })
}

// 查询异常记录详细
export function getExceptionRecord(exrId) {
  return request({
    url: '/system/exceptionRecordNew/' + exrId,
    method: 'get'
  })
}

// 新增异常记录
export function addExceptionRecord(data) {
  return request({
    url: '/system/exceptionRecordNew',
    method: 'post',
    data: data
  })
}

// 修改异常记录
export function updateExceptionRecord(data) {
  return request({
    url: '/system/exceptionRecordNew',
    method: 'put',
    data: data
  })
}




export function saveDescToKnowledge(descObj, exrCode) {
  return fetch('https://api.coze.cn/v3/chat', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': 'Bearer pat_W9JsgUzbDxK6NGon0SAkRx9O109gX14PepCRuS43XQgz9CzP2ugXvqAjya1vNlil',
    },
    body: JSON.stringify({
      bot_id: '7532762700867272739',
      user_id: exrCode,
      stream: false,
      auto_save_history: true,
      additional_messages: [
        {
          role: 'user',
          content: `请分析这个异常记录：${JSON.stringify(descObj)}`,
          content_type: 'text'
        }
      ]
    })
  })
}
export function getdetail(conversation_id, chat_id) {
  // 使用 URLSearchParams 构建查询字符串
  const params = new URLSearchParams();
  params.append('conversation_id', conversation_id);
  params.append('chat_id', chat_id);

  const url = `https://api.coze.cn/v3/chat/message/list?${params.toString()}`;

  return fetch(url, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': 'Bearer pat_W9JsgUzbDxK6NGon0SAkRx9O109gX14PepCRuS43XQgz9CzP2ugXvqAjya1vNlil', // ⚠ 安全警告：硬编码的API密钥
    },
    // GET 请求不应该有 body
  });
}

export function checkdetail(conversation_id, chat_id) {
  // 使用 URLSearchParams 构建查询字符串
  const params = new URLSearchParams();
  params.append('conversation_id', conversation_id);
  params.append('chat_id', chat_id);

  const url = `https://api.coze.cn/v3/chat/retrieve?${params.toString()}`;

  return fetch(url, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': 'Bearer pat_W9JsgUzbDxK6NGon0SAkRx9O109gX14PepCRuS43XQgz9CzP2ugXvqAjya1vNlil', // ⚠ 安全警告：硬编码的API密钥
    },
    // GET 请求不应该有 body
  });
}




    // url :'http://localhost:5000/get_info',
    // method:'get',
    // headers:{
    //   'Content-Type': 'application/json',
    // }

export function saveKnowledgeToBackend(params) {
  return request({
    url:  `/system/exceptionRecordNew/extractAndSave?exrId=${params.exrId}` , // 后端接收数据的接口地址
    method: 'post',
    data: params // 参数包含知识库完整数据及关联的业务信息
  })
}


