import request from '@/utils/request'
import{saveModel} from '@/api/system/exceptionLifecycle'
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





   

    // ✅ 批量创建多个用户任务的流程
export async function createComplexUserTaskFlow(exlId,exrId) {
      const processConfig = {
        processId: `Process_${exrId}`,
        processName: "异常处理流程",
      };

      // 定义多个用户任务
      const userTasks = [
        {
          id: "UserTask_Assessment",
          name: "异常评估",
          assignee: "${1}",
          formKey: "assessment_form"
        },
        {
          id: "UserTask_Handle", 
          name: "创建人工处理任务", // 使用自定义工具的名称
          assignee: "${2}",
          formKey: "exception_handle_form"
        },
        {
          id: "UserTask_Confirm",
          name: "结果确认", 
          assignee: "${3}",
          formKey: "confirm_form"
        }
      ];

      // 生成包含多个用户任务的 XML
      const xml = generateMultiUserTaskXML(processConfig, userTasks);
            console.log("复杂用户任务流程 XML:", xml);
  try {
        await saveModel({
            exlId: exlId,
            xml: xml,
        });

        // >>>>>>>>>>>>>>>>> 成功调试信息 <<<<<<<<<<<<<<<<<
        console.log("saveModel 调用成功！");
        // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    } catch (error) {
        // >>>>>>>>>>>>>>>>> 错误调试信息 <<<<<<<<<<<<<<<<<
        console.error("saveModel 调用失败！", error);
        // 如果是Promise reject，error本身可能包含更多信息
        if (error.response) {
            console.error("后端响应数据:", error.response.data);
            console.error("后端响应状态码:", error.response.status);
        }
        // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        throw error; // 重新抛出错误，以便上层调用者也能捕获
    }


    }

    // 生成多用户任务 XML 的辅助方法
/**
 * 生成包含多个用户任务的 XML
 * @param processConfig 流程配置
 * @param userTasks 用户任务列表
 * @returns {string} 生成的XML字符串
 */
export function generateMultiUserTaskXML(processConfig, userTasks) {
    let userTasksXML = '';
    let sequenceFlowsXML = '';
    let shapesXML = '';
    let edgesXML = '';

    const xOffset = 200; // 流程图起始x坐标
    const yOffset = 250; // 流程图起始y坐标
    const taskWidth = 100;
    const taskHeight = 80;
    const hGap = 150; // 任务之间的水平间距
    // 定义开始事件和结束事件的ID
    const startEventId = "StartEvent_1";
    const endEventId = "EndEvent_1";

    userTasks.forEach((task, index) => {
        const currentX = xOffset + (index * (taskWidth + hGap));
        const currentY = yOffset;

        // 重新添加 'bpmn2:' 前缀
        userTasksXML += `
    <bpmn2:userTask id="${task.id}" name="${task.name}" flowable:assignee="${task.assignee.replace('${', '').replace('}', '')}" flowable:formKey="${task.formKey}">
      <bpmn2:incoming>Flow_${index === 0 ? startEventId : userTasks[index - 1].id}_${task.id}</bpmn2:incoming>
      <bpmn2:outgoing>Flow_${task.id}_${index === userTasks.length - 1 ? endEventId : userTasks[index + 1].id}</bpmn2:outgoing>
    </bpmn2:userTask>`;

        // 生成连接线 XML：重新添加 'bpmn2:' 前缀
        const flowIdIn = `Flow_${index === 0 ? startEventId : userTasks[index - 1].id}_${task.id}`;
        const flowIdOut = `Flow_${task.id}_${index === userTasks.length - 1 ? endEventId : userTasks[index + 1].id}`;

        if (index === 0) {
            sequenceFlowsXML += `
    <bpmn2:sequenceFlow id="${flowIdIn}" sourceRef="${startEventId}" targetRef="${task.id}" />`;
        } else {
            sequenceFlowsXML += `
    <bpmn2:sequenceFlow id="${flowIdIn}" sourceRef="${userTasks[index - 1].id}" targetRef="${task.id}" />`;
        }

        // 最后一个任务连接到结束事件
        if (index === userTasks.length - 1) {
            sequenceFlowsXML += `
    <bpmn2:sequenceFlow id="${flowIdOut}" sourceRef="${task.id}" targetRef="${endEventId}" />`;
        }


        // 生成图形信息（bpmndi, dc, di 前缀保持不变）
        shapesXML += `
      <bpmndi:BPMNShape id="${task.id}_di" bpmnElement="${task.id}">
        <dc:Bounds x="${currentX}" y="${currentY}" width="${taskWidth}" height="${taskHeight}" />
      </bpmndi:BPMNShape>`;

        edgesXML += `
      <bpmndi:BPMNEdge id="${flowIdIn}_di" bpmnElement="${flowIdIn}">
        <di:waypoint x="${index === 0 ? (xOffset - 36/2) : (currentX - hGap/2 + 36/2 + 20)}" y="${index === 0 ? (yOffset + taskHeight/2) : (currentY + taskHeight/2)}" />
        <di:waypoint x="${currentX}" y="${currentY + taskHeight/2}" />
      </bpmndi:BPMNEdge>`;

        if (index === userTasks.length - 1) {
            edgesXML += `
      <bpmndi:BPMNEdge id="${flowIdOut}_di" bpmnElement="${flowIdOut}">
        <di:waypoint x="${currentX + taskWidth}" y="${currentY + taskHeight/2}" />
        <di:waypoint x="${currentX + taskWidth + hGap/2 + 36/2 + 20}" y="${currentY + taskHeight/2}" />
      </bpmndi:BPMNEdge>`;
        }
    });

    // 计算结束事件的X坐标
    const endEventX = xOffset + (userTasks.length * (taskWidth + hGap));
    const endEventY = yOffset + (taskHeight/2) - (36/2);

    // 重新添加 'bpmn2:' 前缀到根元素和核心流程元素
    return `<?xml version="1.0" encoding="UTF-8"?>
<bpmn2:definitions xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
                  xmlns:bpmn2="http://www.omg.org/spec/BPMN/20100524/MODEL" 
                  xmlns:bpmndi="http://www.omg.org/spec/BPMN/20100524/DI" 
                  xmlns:dc="http://www.omg.org/spec/DD/20100524/DC" 
                  xmlns:di="http://www.omg.org/spec/DD/20100524/DI" 
                  xmlns:flowable="http://flowable.org/bpmn"              
                  id="diagram_${processConfig.processId}"               
                  targetNamespace="http://flowable.org/bpmn"                         
                  xsi:schemaLocation="http://www.omg.org/spec/BPMN/20100524/MODEL BPMN20.xsd">
  <bpmn2:process id="${processConfig.processId}" name="${processConfig.processName}" isExecutable="true">
    
    <bpmn2:startEvent id="${startEventId}" >
      <bpmn2:outgoing>Flow_${startEventId}_${userTasks[0].id}</bpmn2:outgoing>
    </bpmn2:startEvent>
    ${userTasksXML}
    <bpmn2:endEvent id="${endEventId}" name="处理完成">
      <bpmn2:incoming>Flow_${userTasks[userTasks.length - 1].id}_${endEventId}</bpmn2:incoming>
    </bpmn2:endEvent>
    ${sequenceFlowsXML}
    
  </bpmn2:process>
  
  <bpmndi:BPMNDiagram id="BPMNDiagram_${processConfig.processId}">
    <bpmndi:BPMNPlane id="BPMNPlane_1" bpmnElement="${processConfig.processId}">
      <bpmndi:BPMNShape id="${startEventId}_di" bpmnElement="${startEventId}">
        <dc:Bounds x="${xOffset - 36/2}" y="${yOffset + taskHeight/2 - 36/2}" width="36" height="36" />
      </bpmndi:BPMNShape>
      ${shapesXML}
      <bpmndi:BPMNShape id="${endEventId}_di" bpmnElement="${endEventId}">
        <dc:Bounds x="${endEventX}" y="${endEventY}" width="36" height="36" />
      </bpmndi:BPMNShape>
      ${edgesXML}
    </bpmndi:BPMNPlane>
  </bpmndi:BPMNDiagram>
</bpmn2:definitions>`;
}