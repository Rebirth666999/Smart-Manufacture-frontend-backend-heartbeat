<!-- Equipment.vue -->
<template>
  <div class="app-container">
    <!-- 搜索栏 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="名称" prop="eqName">
        <el-input v-model="queryParams.eqName" placeholder="请输入名称" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="车间" prop="arCode">
        <el-select v-model="queryParams.arCode" placeholder="请选择车间" clearable>
          <el-option v-for="item in areaList" :key="item.arCode" :label="item.arName" :value="item.arCode"/>
        </el-select>
      </el-form-item>
      <el-form-item label="设备模型" prop="emCode">
        <el-select v-model="queryParams.emCode" placeholder="请选择设备模型" clearable>
          <el-option v-for="item in equipmentModelList" :key="item.emCode" :label="item.emName" :value="item.emCode"/>
        </el-select>
      </el-form-item>
      <el-form-item label="状态" prop="eqStat">
        <el-select v-model="queryParams.eqStat" placeholder="请选择状态" clearable>
          <el-option v-for="dict in dict.type.ices_equipment_status" :key="dict.value" :label="dict.label" :value="dict.value"/>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:equipment:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:equipment:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:equipment:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:equipment:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <!-- 图标式卡片网格（已修复缺失标签） -->
    <el-row :gutter="20">
      <el-col
        v-for="item in equipmentList"
        :key="item.eqId"
        :xs="24"
        :sm="12"
        :md="8"
        :lg="6"
      >
        <el-card
          class="equipment-card"
          shadow="hover"
          :body-style="{ padding: '0' }"
        >
          <!-- 上半：名称 + 编码 + 状态 + 查看详情 -->
          <div class="card-top">
            <div class="left-info">
              <div class="card-title">{{ item.eqName }}</div>
              <div class="card-code">{{ item.eqCode }}</div>
            </div>
            <div class="right-status">
              <!-- 情况1：绿灯单独出现 -->
              <div
                v-if="item.eqStat === 'a'"
                class="status-light green"
              ></div>

              <!-- 情况2：红灯 + 按钮一起出现 -->
              <template v-else>
                <div class="status-light red"></div>
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-view"
                  @click="handleViewDetail(item)"
                >查看详情</el-button>
              </template>
            </div>
          </div>

          <!-- 下半：所有其它按钮 -->
          <div class="card-bottom">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleUpdate(item)"
              v-show="item.eqStat === '1'"
            >修改</el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-bell"
              @click="handleEquipmentRecordView(item)"
            >查看事件</el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-position"
              @click="handleAtomOperationView(item)"
            >原子操作</el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-set-up"
              @click="handleEquipmentOperationView(item)"
            >设备操作</el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-finished"
              v-show="item.eqStat === '1'"
              @click="handleSubmitReview(item)"
            >提交审核</el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-refresh-left"
              v-show="item.eqStat === '2' || item.eqStat === 'd' || item.eqStat === 'f' || item.eqStat === 'h'"
              @click="handleWithdrawReview(item)"
            >撤回审核</el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-video-pause"
              v-show="item.eqStat === '4' || item.eqStat === '5' || item.eqStat === '8' || item.eqStat === '9'"
              @click="handleStopReview(item)"
            >停用</el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-video-play"
              v-show="item.eqStat === 'a'"
              @click="handleResumeReview(item)"
            >恢复使用</el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-setting"
              v-show="item.eqStat === 'a'"
              @click="handleMaintaince(item)"
            >开始维护</el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-setting"
              v-show="item.eqStat === 'b'"
              @click="handleMaintainceComplete(item)"
            >结束维护</el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-delete"
              v-show="item.eqStat === 'a'"
              @click="handleDepreciateReview(item)"
            >报废</el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-delete"
              @click="handleDelete(item)"
              v-hasPermi="['system:equipment:remove']"
              v-show="item.eqStat === '1'"
            >删除</el-button>

          </div>
        </el-card>

      </el-col>
    </el-row>

    <!-- 分页与弹窗保持原样 -->
    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="85px">
        <!-- 表单内容与原文件一致 -->
        <el-form-item label="名称" prop="eqName">
          <el-input v-model="form.eqName" placeholder="请输入名称" />
        </el-form-item>
        <el-form-item label="车间" prop="arCode">
          <el-select v-model="form.arCode" placeholder="请选择车间">
            <el-option v-for="a in areaList" :key="a.arCode" :label="a.arName" :value="a.arCode"/>
          </el-select>
        </el-form-item>
        <el-form-item label="设备模型" prop="emCode">
          <el-select v-model="form.emCode" placeholder="请选择设备模型">
            <el-option v-for="m in equipmentModelList" :key="m.emCode" :label="m.emName" :value="m.emCode"/>
          </el-select>
        </el-form-item>
        <el-form-item label="采购时间" prop="eqIntroduceTime">
          <el-date-picker v-model="form.eqIntroduceTime" type="datetime" value-format="yyyy-MM-dd HH:mm:ss" placeholder="请选择采购时间"/>
        </el-form-item>
        <el-form-item prop="eqIp">
          <span slot="label">
            <el-tooltip placement="top">
              <div slot="content">
                <div>如为ROS2连接，请输入'ROS2'并保持端口号为空。</div>
                <div>如为HTTP连接，请正确输入IP地址和端口号。</div>
              </div>
              <i class="el-icon-question"></i>
            </el-tooltip>
            IP地址
          </span>
          <el-input v-model="form.eqIp" placeholder="请输入IP地址" style="width: 50%"/>
          <el-input v-model="form.eqPort" style="width: 50%">
            <template slot="prepend">端口号</template>
          </el-input>
        </el-form-item>
        <el-form-item label="描述" prop="eqDesc">
          <el-input v-model="form.eqDesc" type="textarea" placeholder="请输入内容"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button :loading="buttonLoading" type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />



<script>
import { listEquipment, getEquipment, delEquipment, addEquipment, updateEquipment } from "@/api/system/equipment";
import { listArea } from "@/api/system/area";
import { listEquipmentModel } from "@/api/system/equipmentModel";

export default {
  name: "ManageEquipment",
  dicts: ['ices_equipment_status'],
  data() {
    return {
      // 按钮loading
      buttonLoading: false,
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 设备表格数据
      equipmentList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        arCode: undefined,
        emCode: undefined,
        eqName: undefined,
        eqStat: undefined,
        eqDelete: 0,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        eqId: [
          { required: true, message: "设备ID不能为空", trigger: "blur" }
        ],
        arCode: [
          { required: true, message: "所属车间不能为空", trigger: "change" }
        ],
        emCode: [
          { required: true, message: "所属设备模型不能为空", trigger: "change" }
        ],
        eqName: [
          { required: true, message: "名称不能为空", trigger: "blur" }
        ],
        eqStat: [
          { required: true, message: "状态代码不能为空", trigger: "change" }
        ],
        eqIp: [
          { required: true, message: "IP地址不能为空", trigger: "blur" }
        ],
      },
      // 车间列表
      areaList: [],
      // 已发布的设备模型列表
      equipmentModelList: [],
      // 设备模型列表
      equipmentModelListFull: [],
    };
  },
  async created() {
    await this.getAreaList();
    await this.getEquipmentModelList();
    this.getList();
  },
  async activated() {
    await this.getAreaList();
    await this.getEquipmentModelList();
    this.getList();
  },
  methods: {
    // 获取车间列表
    getAreaList() {
      return new Promise((resolve, reject) => {
        this.loading = true;
        listArea().then(response => {
          this.areaList = response.rows
          resolve()
        }).catch(() => {
          reject()
        }).finally(() => {
          this.loading = false
        })
      })
    },
    // 查询设备模型列表
    getEquipmentModelList() {
      return new Promise((resolve, reject) => {
        this.loading = true;
        listEquipmentModel().then(response => {
          this.equipmentModelListFull = response.rows
          this.equipmentModelList = response.rows.filter(ele => ele.emStat === "4")
          resolve()
        }).catch(() => {
          reject()
        }).finally(() => {
          this.loading = false
        })
      })
    },
    /** 查询设备列表 */
    getList() {
      this.loading = true;
      listEquipment(this.queryParams).then(response => {
        this.equipmentList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        eqId: undefined,
        arCode: undefined,
        emCode: undefined,
        eqName: undefined,
        eqStat: undefined,
        eqIntroduceTime: undefined,
        eqCommunicateTime: undefined,
        eqIp: undefined,
        eqPort: undefined,
        eqDelete: undefined,
        eqDesc: undefined,
        createBy: undefined,
        updateBy: undefined,
        createTime: undefined,
        updateTime: undefined
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.eqId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加设备";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true;
      this.reset();
      const eqId = row.eqId || this.ids
      getEquipment(eqId).then(response => {
        this.form = response.data;
        // 处理IP和端口号
        if (this.form.eqIp) {
          if (this.form.eqIp.length > 0 && this.form.eqIp.indexOf(":") !== -1) {
            let url = this.form.eqIp.split("http://")[1].split(":")
            this.form.eqIp = url[0]
            this.form.eqPort = url[1]
          }
        }
        this.loading = false;
        this.open = true;
        this.title = "修改设备";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.eqIp !== 'ROS2') {
            // 拼接端口号
            if (this.form.eqPort && this.form.eqPort.length > 0) {
              this.form.eqIp = "http://" + this.form.eqIp
              this.form.eqIp += ":"
              this.form.eqIp += this.form.eqPort
            } else {
              this.$modal.msgWarning("请输入设备IP地址和端口号")
              return
            }
          }
          this.buttonLoading = true;
          if (this.form.eqId != null) {
            updateEquipment(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            // 添加设备的默认状态：未发布
            this.form.eqStat = "1"
            addEquipment(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const eqIds = row.eqId || this.ids;
      this.$modal.confirm('是否确认删除设备编号为"' + eqIds + '"的数据项？').then(() => {
        this.loading = true;
        return delEquipment(eqIds);
      }).then(() => {
        this.loading = false;
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      }).finally(() => {
        this.loading = false;
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/equipment/export', {
        ...this.queryParams
      }, `equipment_${new Date().getTime()}.xlsx`)
    },
    // 提交审核
    handleSubmitReview(row) {
      const eqId = row.eqId;
      this.$modal.confirm('是否要提交审核？审核在开始之前可以撤回。').then(() => {
        this.loading = true;
        getEquipment(eqId).then(response => {
          this.form = response.data;
          this.form.eqStat = "2";
          updateEquipment(this.form).then(response => {
            this.$modal.msgSuccess("已提交审核");
            this.getList();
          })
        });
      }).catch(() => {
      }).finally(() => {
        this.loading = false;
      });
    },
    // 撤回审核
    handleWithdrawReview(row) {
      const eqId = row.eqId;
      this.$modal.confirm('是否要撤回审核？若审核已开始即无法撤回。').then(() => {
        this.loading = true;
        getEquipment(eqId).then(response => {
          this.form = response.data;
          if (this.form.eqStat === '2') this.form.eqStat = '1'
          else if (this.form.eqStat === 'd') this.form.eqStat = '4'
          else if (this.form.eqStat === 'f') this.form.eqStat = 'a'
          else if (this.form.eqStat === 'h') this.form.eqStat = 'a'
          updateEquipment(this.form).then(response => {
            this.$modal.msgSuccess("已撤回审核");
            this.getList();
          })
        });
      }).catch(() => {
      }).finally(() => {
        this.loading = false;
      });
    },
    // 停用设备
    handleStopReview(row) {
      const eqId = row.eqId;
      this.$modal.confirm('是否停用此设备？停用设备需要进行停用审核。').then(() => {
        this.loading = true;
        getEquipment(eqId).then(response => {
          this.form = response.data;
          this.form.eqStat = 'd';
          updateEquipment(this.form).then(response => {
            this.$modal.msgSuccess("已提交停用审核");
            this.getList();
          })
        });
      }).catch(() => {
      }).finally(() => {
        this.loading = false;
      });
    },
    // 设备报废
    handleDepreciateReview(row) {
      const eqId = row.eqId;
      this.$modal.confirm('是否要将此设备提交报废审核？请注意您仍需进行固定资产报废的流程。').then(() => {
        this.loading = true;
        getEquipment(eqId).then(response => {
          this.form = response.data;
          this.form.eqStat = 'f';
          updateEquipment(this.form).then(response => {
            this.$modal.msgSuccess("已提交设备报废审核");
            this.getList();
          })
        });
      }).catch(() => {
      }).finally(() => {
        this.loading = false;
      });
    },
    // 设备维护
    handleMaintaince(row) {
      const eqId = row.eqId;
      this.$modal.confirm('是否要开始设备维护？').then(() => {
        this.loading = true;
        getEquipment(eqId).then(response => {
          this.form = response.data;
          this.form.eqStat = 'b';
          updateEquipment(this.form).then(response => {
            this.$modal.msgSuccess("设备维护已提交");
            this.getList();
          })
        });
      }).catch(() => {
      }).finally(() => {
        this.loading = false;
      });
    },
    // 设备维护结束
    handleMaintainceComplete(row) {
      const eqId = row.eqId;
      this.$modal.confirm('是否要结束设备维护？').then(() => {
        this.loading = true;
        getEquipment(eqId).then(response => {
          this.form = response.data;
          this.form.eqStat = 'a';
          updateEquipment(this.form).then(response => {
            this.$modal.msgSuccess("结束设备维护已提交");
            this.getList();
          })
        });
      }).catch(() => {
      }).finally(() => {
        this.loading = false;
      });
    },
    // 恢复使用审核
    handleResumeReview(row) {
      const eqId = row.eqId;
      this.$modal.confirm('是否要恢复设备的使用？恢复使用需要先进行审核。').then(() => {
        this.loading = true;
        getEquipment(eqId).then(response => {
          this.form = response.data;
          this.form.eqStat = "h";
          updateEquipment(this.form).then(response => {
            this.$modal.msgSuccess("已提交恢复使用审核");
            this.getList();
          })
        });
      }).catch(() => {
      }).finally(() => {
        this.loading = false;
      });
    },
    // 查看事件
    handleEquipmentRecordView(row) {
      this.$router.push(`/equipment/equipmentRecord?eqCode=${row.eqCode}`)
    },
    // 设备原子操作
    handleAtomOperationView(row) {
      this.$router.push(`/equipment/equipmentAtomOperation?eqCode=${row.eqCode}`)
    },
    // 设备操作
    handleEquipmentOperationView(row) {
      this.$router.push(`/equipment/equipmentOperation?eqCode=${row.eqCode}`)
    }
  }
};
</script>
<style scoped>
.el-select {
  width: 100%;
}
.el-date-editor{
  width: 100%;
}

.card-title {
  font-weight: 600;
  font-size: 16px;
  margin-bottom: 8px;
}
.card-code {
  font-weight: 600;
  font-size: 16px;
  margin-bottom: 8px;
}
.equipment-card {
  display: flex;
  flex-direction: column;
  min-height: 220px;
  margin-bottom:20px;
}
.card-top {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px 10px 0 10px;
}
.left-info {
  display: flex;
  flex-direction: column;
}
.right-status {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 4px;
}
.status-light {
  width: 16px;
  height: 16px;
  border-radius: 50%;
}
.status-light.green { background-color: #67C23A; }
.status-light.red   { background-color: #F56C6C; }
.card-bottom {
  padding: 8px 10px 10px;
  border-top: 1px solid #ebeef5;
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  gap: 6px;
}

</style>
