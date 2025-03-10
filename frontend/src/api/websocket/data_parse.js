function listOp(info,target,key) {
    let ret=[];
    for(let i=0;i<target.length;i++){
        const loc=info.findIndex((item)=>item[key]===target[i]);
        if(loc!=-1)
        ret.push(info[loc]);
    }
    return ret;
}
export function message_parse(data) {
    // 任务执行进度统计
    const task_undo_num = data["tasks_status"]["task_undo"].length;
    const task_done_num = data["tasks_status"]["task_done"].length;
    const task_doing_num = data["tasks_status"]["task_doning"].length;
    const task_info=data["task_info"].map((item)=>item.productionTask);
    const task_done_info=listOp(task_info,data["tasks_status"]["task_done"],"ptId");
    const task_doing_info=listOp(task_info,data["tasks_status"]["task_doning"],"ptId");
    const task_undo_info=listOp(task_info,data["tasks_status"]["task_undo"],"ptId");
    // for(let i=0;i<task_done_num;i++){
    //     const loc=task_info.findIndex((item)=>item.ptId===data["tasks_status"]["task_done"][i]);
    //     if(loc!=-1)
    //     task_done_info.push(task_info[loc]);
    // }
    // 当前任务中操作执行进度统计
    const op_undo_num = data["now_task_status"]["op_undo"].length;
    const op_done_num = data["now_task_status"]["op_done"].length;
    const op_doing_num = data["now_task_status"]["op_doing"].length;
    // 找到当前任务在datap["task_info"]中的位置
    const index=data["task_info"].findIndex((item)=>item["productionTask"]["ptId"]===data["now_task_status"]["task_id"]);
    const op_info = data["task_info"][index]["processRoute"];
    const op_undo_info = listOp(op_info,data["now_task_status"]["op_undo"],"prdId");
    const op_doing_info = listOp(op_info,data["now_task_status"]["op_doing"],"prdId");
    const op_done_info = listOp(op_info,data["now_task_status"]["op_done"],"prdId");
    for(let i=0;i<op_done_info.length;i++){
        const index=data["op_finish_msg"].findIndex((item)=>item["op_id"]===op_done_info[i]["opId"]);
        console.log(index,data["op_finish_msg"],op_done_info[i]["opId"]);
        if(index!=-1){
            op_done_info[i]["opFinishMsg"]=data["op_finish_msg"][index];
        }
    }
    console.log(op_done_info);
    const ret = {
      tasks_status: {
        process: 0,
        task_undo_num: task_undo_num,
        task_done_num: task_done_num,
        task_doing_num: task_doing_num,
        task_undo: data["tasks_status"]["task_undo"],
        task_doning: data["tasks_status"]["task_doning"],
        task_done: data["tasks_status"]["task_done"],
        task_done_info: task_done_info,
        task_doning_info: task_doing_info,
        task_undo_info: task_undo_info,
      },
      now_task_status: {
        task_id: 0,
        op_undo_num: op_undo_num,
        op_done_num: op_done_num,
        op_doing_num: op_doing_num,
        op_done: data["now_task_status"]["op_done"],
        op_doing: data["now_task_status"]["op_doing"],
        op_undo: data["now_task_status"]["op_undo"],
        op_done_info: op_done_info,
        op_doing_info: op_doing_info,
        op_undo_info: op_undo_info,
      },
      heartbeat: data["heartbeat"],
      op_finish_msg: data["op_finish_msg"],
      error: data["error"],
      task_info: data["task_info"],
    };
    return ret;
  };
