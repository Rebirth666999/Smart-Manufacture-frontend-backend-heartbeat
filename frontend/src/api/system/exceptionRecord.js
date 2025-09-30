import request from '@/utils/request'
import{saveModel} from '@/api/system/exceptionLifecycle'
import{listDept} from '@/api/system/dept'
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


export function getKG(exrDesc, exName) {
  if (!exName || !exName.trim()) {
    // 增加更严格的校验
    return Promise.reject(new Error('异常名称不能为空'));
  }
  const query = encodeURIComponent(`${exName}怎么处理`);
  return fetch(`http://192.168.1.101:8080/query?q=${query}`,
    {
      method:"GET",
      headers:{
        "Authorization": "d146d1c992f636039717e561d551af67e00db123",
        "content-type": "application/json",
      }
    }
  )
  
}


    // ✅ 批量创建多个用户任务的流程
export async function createComplexUserTaskFlow(exlId,exrCode,exrPro) {
      const processConfig = {
        processId: `Process_${exrCode}`,
        processName: "异常处理流程_"+exlId,
      };
      console.log(typeof(exrPro))
let solutionsArray = [];
  
  try {
      // 如果是字符串，尝试解析为 JSON
      solutionsArray = JSON.parse(exrPro);
      console.log('JSON 解析成功:', solutionsArray);
    // 验证解析后的数据
    if (!Array.isArray(solutionsArray)) {
      throw new Error('解析后的数据不是数组');
    }
  } catch (parseError) {
    console.error('解析 exrPro 失败:', parseError);
    console.log('原始数据:', exrPro);
      throw new Error('无法解析 exrPro 数据');
    
  }
      let userTasks = [];
    for (let i = 0; i <solutionsArray.length; i++) {//获取每个异常处理方案的涉及到部门ID
      const solution = solutionsArray[i];
    const deptNames = solution.涉及到部门.split(/[、,，]/).map(name => name.trim()).filter(name => name);//获取单个处理方案涉及到的部门名称
    for (let j = 0; j < deptNames.length; j++) {
      await listDept({deptName : deptNames[j]}).then(response => {
        console.log("查询部门返回:", response);
      if (response.data[0]) {
      solutionsArray[i].涉及到部门 = ""
      solutionsArray[i].涉及到部门+=(response.data[0].deptId) // 如果找到部门，使用其ID; 
      solutionsArray[i].涉及到部门+=(',')
    } else {
      solutionsArray[i].涉及到部门+=( "无"); // 如果没有找到部门，使用默认值
    }

  })}

}

      for (let i = 0; i <=solutionsArray.length-1; i++) {
        userTasks[i]={
          id: `UserTask_${i+1}`,// 任务ID
          name: solutionsArray[i].异常处理方案 ? solutionsArray[i].异常处理方案 : `任务${i}`,// 任务名称

          assignee: solutionsArray[i].涉及到部门 ? solutionsArray[i].涉及到部门: " ",//分配的部门（）部门ID）
          formKey:  solutionsArray[i].异常处理方案描述 ? solutionsArray[i].异常处理方案描述 :  "无",//任务描述
        }
      }

      // 生成包含多个用户任务的 XML
      const xml = generateMultiUserTaskXML(processConfig, userTasks);
            console.log("复杂用户任务流程 XML:", xml,"exlId:", exlId);
  try {
        await saveModel({
            exlId: exlId,
            xml: xml,
        });
        console.log("saveModel 调用成功！");
    } catch (error) {
        console.error("saveModel 调用失败！", error);
        throw error; // 重新抛出错误
    }}

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
        const currentX = xOffset + 50+( index * (taskWidth + hGap)); 
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
    const endEventX = xOffset + ((taskWidth)+ userTasks.length * (taskWidth + hGap)) + taskWidth + 50; // 在最后一个任务右侧50px
    const endEventY = yOffset + (taskHeight/2) - (36/2);

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

//
export function startLifeCycle(form){
  return request({
    url: '/system/exceptionRecordNew/startLifecycle',
    method: 'post',
    data: form
  })

}





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
      // 验证输入参数
  
        // 如果不是数组，使用默认的5张图片
        let imagePaths = [
          "/frame_000150.jpg",
          "/frame_000151.jpg", 
          "/frame_000152.jpg",
          "/frame_000153.jpg",
          "/frame_000154.jpg"
        ];
        console.log('使用默认图片序列');
     
      
      if (imagePaths.length !== 5) {
        throw new Error(`需要提供5张图片，当前提供了${imagePaths.length}张`);
      }

      const sessionId = `session_${Date.now()}_${Math.random().toString(36).slice(2, 11)}`;
      const results = [];
      
      console.log('开始发送5张连续图片...');
      
      // 依次发送每张图片
      for (let i = 0; i < imagePaths.length; i++) {
        const imagePath = imagePaths[i];
        let base64String = '';
        let fileName = `frame_${i + 1}.jpg`;
        let mimeType = 'image/jpeg';
        
        try {
          if (imagePath instanceof File) {
            // 处理File对象
            const base64Data = await fileToBase64(imagePath);
            base64String = base64Data.split(',')[1];
            fileName = imagePath.name || `frame_${i + 1}.jpg`;
            mimeType = imagePath.type;
          } else if (typeof imagePath === 'string') {
            // 处理字符串路径
            let imageUrl = imagePath;
            
            if (imageUrl.startsWith('/')) {
              imageUrl = window.location.origin + imageUrl;
            }
            
            console.log(`正在获取第${i + 1}/5张图片:`, imageUrl);
            
            const response = await fetch(imageUrl);
            if (!response.ok) {
              throw new Error(`第${i + 1}张图片加载失败: ${response.status} ${response.statusText}`);
            }
          
            const blob = await response.blob();
               if (blob.size === 0) {
          throw new Error(`第${i + 1}张图片获取成功，但内容为空 (0 bytes)`);
          }
            const base64Data = await blobToBase64(blob);
            base64String = base64Data.split(',')[1];
            fileName = imagePath.split('/').pop() || `frame_${i + 1}.jpg`;
            mimeType = blob.type || 'image/jpeg';
          }
          
         
                  // ✅ 验证Base64数据
          if (!base64String || base64String.length === 0) {
            throw new Error('Base64数据为空');
          }
           try {
            atob(base64String.substring(0, 100)); // 测试解码前100个字符
          } catch (decodeError) {
            throw new Error('Base64数据格式错误');
          }
          
          console.log(`发送第${i + 1}/5张图片...`);
          
          // 发送单张图片到后端API
          const imageResponse = await fetch("http://192.168.1.101:5000/api/detect", {
            method: 'POST',
            headers: {
              "Authorization": "d146d1c992f636039717e561d551af67e00db123",
              'Content-Type': 'application/json'
            },
            body: JSON.stringify({
              image_base64: base64String,
              filename: fileName,
              mimetype: mimeType,
              frame_index: i + 1
            })
          });
          
          if (!imageResponse.ok) {
            const errorText = await imageResponse.text();
            throw new Error(`第${i + 1}张图片上传失败: HTTP ${imageResponse.status}: ${errorText}`);
          }
          
          const result = await imageResponse.json();
          results.push({
            frame_index: i + 1,
            frame_path: imagePath,
            ...result
          });
          
          console.log(`第${i + 1}张图片响应:`, result.status);
          
          // 检查响应状态
          if (result.status === "buffering") {
            console.log(`第${i + 1}张图片: ${result.message}`);
          } else if (result.status === "success") {
            console.log(`🎉 序列检测完成！共处理 ${i + 1} 帧`);
            
            // 返回完整的检测结果
            resolve({
              status: "success",
              session_id: sessionId,
              total_frames: i + 1,
              frames_processed: results,
              final_result: result.result,
              processing_time: result.processing_time_seconds,
              detection_summary: {
                is_anomaly: result.result?.is_anomaly || false,
                anomaly_type: result.result?.anomaly_type || 0,
                anomaly_description: result.result?.type_description || "正常",
                combined_score: result.result?.combined_score || 0
              }
            });
            return;
          }
          
        } catch (frameError) {
          console.error(`处理第${i + 1}张图片时出错:`, frameError);
          throw new Error(`第${i + 1}张图片处理失败: ${frameError.message}`);
        }
      }
      
      // 如果循环结束还没有得到最终结果
      resolve({
        status: "buffering_complete",
        session_id: sessionId,
        total_frames: imagePaths.length,
        frames_processed: results,
        message: "已发送所有5帧图片，等待最终检测结果"
      });
      
    } catch (error) {
      console.error('连续帧图片检测失败:', error);
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





   

