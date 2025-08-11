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



export function sendimg(imagePath) {
  return new Promise(async (resolve, reject) => {
    try {
      let base64String = '';
      let fileName = 'image.jpg';
      let mimeType = 'image/jpeg';
      
      if (imagePath instanceof File) {
        const base64Data = await fileToBase64(imagePath);
        base64String = base64Data.split(',')[1];
        fileName = imagePath.name;
        mimeType = imagePath.type;
      } else if (typeof imagePath === 'string') {
        let imageUrl = imagePath;
        
        if (imageUrl.startsWith('/')) {
          imageUrl = window.location.origin + imageUrl;
        }
        
        console.log('正在获取图片:', imageUrl);
        
        const response = await fetch(imageUrl);
        if (!response.ok) {
          throw new Error(`图片加载失败: ${response.status} ${response.statusText}`);
        }
        
        const blob = await response.blob();
        const base64Data = await blobToBase64(blob);
        base64String = base64Data.split(',')[1];
        fileName = imagePath.split('/').pop() || 'image.jpg';
        mimeType = blob.type || 'image/jpeg';
      }
      
      console.log('发送base64图片...');
      console.log('Base64字符串:', base64String);
      // 使用原生 fetch
      const Rimageesponse = await fetch("http://192.168.1.127:5000/api/detect", {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({
          image_base64: base64String,
          filename: fileName,
          mimetype: mimeType
        })
      });
      
      if (!Rimageesponse.ok) {
        const errorText = await Rimageesponse.text();
        throw new Error(`HTTP ${Rimageesponse.status}: ${errorText}`);
      }
      
      const result = await Rimageesponse.json();
      resolve(result);
    } catch (error) {
      console.error('图片上传失败:', error);
      reject(error);
    }
  });
}


function fileToBase64(file) {
  return new Promise((resolve, reject) => {
    
    const reader = new FileReader();
    reader.onload = function(e) {
      resolve(e.target.result);
    };
    reader.onerror = function(error) {
      reject(error);
    };
    reader.readAsDataURL(file);
  });
}

// ✅ 添加缺失的辅助函数：将 Blob 转换为 base64
function blobToBase64(blob) {
  return new Promise((resolve, reject) => {
    
    const reader = new FileReader();
    reader.onload = function(e) {
      resolve(e.target.result);
    };
    reader.onerror = function(error) {
      reject(error);
    };
    reader.readAsDataURL(blob);
  });
}