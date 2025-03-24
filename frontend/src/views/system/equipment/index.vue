<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="名称" prop="eqName">
        <el-input
          v-model="queryParams.eqName"
          placeholder="请输入名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="车间" prop="arId">
        <el-select
          v-model="queryParams.arId"
          placeholder="请选择车间"
          clearable
        >
          <el-option
            v-for="item in areaList"
            :key="item.arId"
            :label="item.arName"
            :value="item.arId"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="设备模型" prop="emId">
        <el-select v-model="queryParams.emId" placeholder="请选择设备模型" 
        @keyup.enter.native="handleQuery" clearable>
          <el-option
            v-for="item in equipmentModelListFull"
            :key="item.emId"
            :label="item.emName"
            :value="item.emId"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="状态" prop="eqStat">
        <el-select v-model="queryParams.eqStat" placeholder="请选择状态" clearable>
          <el-option
            v-for="dict in dict.type.ices_equipment_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <!-- <el-form-item label="已删除" prop="eqDelete">
        <el-input
          v-model="queryParams.eqDelete"
          placeholder="请输入已删除"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item> -->
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

    <el-table v-loading="loading" :data="equipmentList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="设备ID" align="center" prop="eqId" v-if="true"/>
      <el-table-column label="名称" align="center" prop="eqName" />
      <el-table-column label="所属车间" align="center" prop="arId">
        <template slot-scope="scope">
          {{ areaList.find(ele => ele.arId === scope.row.arId).arName || '' }}
        </template>
      </el-table-column>
      <el-table-column label="所属设备模型" align="center" prop="emId">
        <template slot-scope="scope">
          {{ equipmentModelListFull.find(ele => ele.emId === scope.row.emId).emName || '' }}
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="eqStat">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.ices_equipment_status" :value="scope.row.eqStat"/>
        </template>
      </el-table-column>
      <el-table-column label="上次通讯时间" align="center" prop="eqCommunicateTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.eqCommunicateTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="IP地址" align="center" prop="eqIp" />
      <!-- <el-table-column label="已删除" align="center" prop="eqDelete" /> -->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:equipment:edit']"
            v-show="scope.row.eqStat === '1'"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-bell"
            @click="handleEquipmentRecordView(scope.row)"
          >查看事件</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-position"
            @click="handleAtomOperationView(scope.row)"
          >原子操作</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-set-up"
            @click="handleEquipmentOperationView(scope.row)"
          >设备操作</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-finished"
            v-show="scope.row.eqStat === '1'"
            @click="handleSubmitReview(scope.row)"
          >提交审核</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-refresh-left"
            v-show="scope.row.eqStat === '2' || scope.row.eqStat === 'd' || scope.row.eqStat === 'f' || scope.row.eqStat === 'h'"
            @click="handleWithdrawReview(scope.row)"
          >撤回审核</el-button>
          <!-- <el-button
            size="mini"
            type="text"
            icon="el-icon-document-copy"
            v-show="scope.row.eqStat !== '1' && scope.row.eqStat !== '2' && scope.row.eqStat !== '3'"
          >复制配置</el-button> -->
          <el-button
            size="mini"
            type="text"
            icon="el-icon-video-pause"
            v-show="scope.row.eqStat === '4' || scope.row.eqStat === '5' || scope.row.eqStat === '8' || scope.row.eqStat === '9'"
            @click="handleStopReview(scope.row)"
          >停用</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-video-play"
            v-show="scope.row.eqStat === 'a'"
            @click="handleResumeReview(scope.row)"
          >恢复使用</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-setting"
            v-show="scope.row.eqStat === 'a'"
            @click="handleMaintaince(scope.row)"
          >开始维护</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-setting"
            v-show="scope.row.eqStat === 'b'"
            @click="handleMaintainceComplete(scope.row)"
          >结束维护</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            v-show="scope.row.eqStat === 'a'"
            @click="handleDepreciateReview(scope.row)"
          >报废</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:equipment:remove']"
            v-show="scope.row.eqStat === '1'"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改设备对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="名称" prop="eqName">
          <el-input v-model="form.eqName" placeholder="请输入名称" />
        </el-form-item>
        <el-form-item label="车间" prop="arId">
          <el-select
            v-model="form.arId"
            placeholder="请选择车间"
          >
            <el-option
              v-for="item in areaList"
              :key="item.arId"
              :label="item.arName"
              :value="item.arId"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="设备模型" prop="emId">
          <el-select
            v-model="form.emId"
            placeholder="请选择设备模型"
          >
            <el-option
              v-for="item in equipmentModelList"
              :key="item.emId"
              :label="item.emName"
              :value="item.emId"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="采购时间" prop="eqIntroduceTime">
          <el-date-picker clearable
            v-model="form.eqIntroduceTime"
            type="datetime"
            value-format="yyyy-MM-dd HH:mm:ss"
            placeholder="请选择采购时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="IP地址" prop="eqIp">
          <el-input v-model="form.eqIp" placeholder="请输入IP地址" />
        </el-form-item>
        <el-form-item label="描述" prop="eqDesc">
          <el-input v-model="form.eqDesc" type="textarea" placeholder="请输入内容" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button :loading="buttonLoading" type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listEquipment, getEquipment, delEquipment, addEquipment, updateEquipment } from "@/api/system/equipment";
import { listArea } from "@/api/system/area";
import { listEquipmentModel } from "@/api/system/equipmentModel";

export default {
  name: "Equipment",
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
        arId: undefined,
        emId: undefined,
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
        arId: [
          { required: true, message: "所属车间不能为空", trigger: "change" }
        ],
        emId: [
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
  methods: {
    // 获取车间列表
    getAreaList() {
      listArea().then(response => {
        this.areaList = response.rows;
      });
    },
    // 查询设备模型列表
    getEquipmentModelList() {
      listEquipmentModel().then(response => {
        this.equipmentModelListFull = response.rows;
      });
      listEquipmentModel({ emStat: "4" }).then(response => {
        this.equipmentModelList = response.rows;
      });
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
        arId: undefined,
        emId: undefined,
        eqName: undefined,
        eqStat: undefined,
        eqIntroduceTime: undefined,
        eqCommunicateTime: undefined,
        eqIp: undefined,
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
        this.loading = false;
        this.form = response.data;
        this.open = true;
        this.title = "修改设备";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
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
      this.$router.push(`/equipment/equipmentRecord?eqId=${row.eqId}`)
    },
    // 设备原子操作
    handleAtomOperationView(row) {
      this.$router.push(`/equipment/equipmentAtomOperation?eqId=${row.eqId}`)
    },
    // 设备操作
    handleEquipmentOperationView(row) {
      this.$router.push(`/equipment/equipmentOperation?eqId=${row.eqId}`)
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
</style>