<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="目标产品" prop="maCode">
        <el-select
          v-model="queryParams.maCode"
          placeholder="请选择目标产品"
          clearable
        >
          <el-option
            v-for="item in productList"
            :key="item.maCode"
            :label="item.maName"
            :value="item.maCode"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="名称" prop="procName">
        <el-input
          v-model="queryParams.procName"
          placeholder="请输入工艺流程名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="procStat">
        <el-select v-model="queryParams.procStat" placeholder="请选择工艺流程状态" clearable>
          <el-option
            v-for="dict in dict.type.ices_process_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <!-- <el-form-item label="已删除" prop="procDelete">
        <el-input
          v-model="queryParams.procDelete"
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
          v-hasPermi="['system:process:add']"
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
          v-hasPermi="['system:process:edit']"
        >设计</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:process:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:process:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="processList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="工艺流程ID" align="center" prop="procId" v-if="true"/>
      <el-table-column label="工艺流程编码" align="center" prop="procCode" />
      <el-table-column label="目标产品" align="center" prop="maCode">
        <template slot-scope="scope">
          {{ productList.find(ele => ele.maCode === scope.row.maCode).maName || '' }}
        </template>
      </el-table-column>
      <el-table-column label="工艺流程名称" align="center" prop="procName" />
      <el-table-column label="工艺流程状态" align="center" prop="procStat">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.ices_process_status" :value="scope.row.procStat"/>
        </template>
      </el-table-column>
      <!-- <el-table-column label="已删除" align="center" prop="procDelete" /> -->
      <!-- <el-table-column label="描述" align="center" prop="procDesc" /> -->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-brush"
            v-show="scope.row.procStat === '1'"
            v-hasPermi="['system:process:edit']"
            @click="handleDesign(scope.row)"
          >设计</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-search"
            @click="handleViewer(scope.row)"
          >查看</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-finished"
            v-show="scope.row.procStat === '1'"
            @click="handleSubmitReview(scope.row)"
          >提交审核</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-refresh-left"
            v-show="scope.row.procStat === '2' || scope.row.procStat === '7'"
            @click="handleWithdrawReview(scope.row)"
          >撤回审核</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-video-pause"
            v-show="scope.row.procStat === '5'"
            @click="handleDeactivate(scope.row)"
          >停用</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-video-play"
            v-show="scope.row.procStat === '4'"
            @click="handleActivate(scope.row)"
          >激活</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            v-show="scope.row.procStat === '4'"
            @click="handleDepreciateReview(scope.row)"
          >弃用</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:process:remove']"
            v-show="scope.row.procStat === '1'"
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

    <!-- 添加工艺流程对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="目标产品" prop="maCode">
          <el-select
            v-model="form.maCode"
            placeholder="请选择目标产品"
          >
            <el-option
              v-for="item in productList"
              :key="item.maCode"
              :label="item.maName"
              :value="item.maCode"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="名称" prop="procName">
          <el-input v-model="form.procName" placeholder="请输入工艺流程名称" />
        </el-form-item>
        <el-form-item label="描述" prop="procDesc">
          <el-input v-model="form.procDesc" type="textarea" placeholder="请输入内容" />
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
import { listProcess, getProcess, delProcess, addProcess, updateProcess, saveModel, getBpmnXml } from "@/api/system/process";
import { listMaterial } from "@/api/system/material";
import { listEquipmentModel } from "@/api/system/equipmentModel";
import { listModelOperation } from "@/api/system/modelOperation";
import ProcessDesigner from '@/components/ProcessDesigner';
import ProcessViewer from '@/components/ProcessViewerIndustry';


export default {
  name: "Process",
  components: {
    ProcessDesigner,
    ProcessViewer,
  },
  dicts: ['ices_process_status'],
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
      // 工艺流程表格数据
      processList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        maCode: undefined,
        procName: undefined,
        procStat: undefined,
        procDelete: 0,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        procId: [
          { required: true, message: "工艺流程ID不能为空", trigger: "blur" }
        ],
        maCode: [
          { required: true, message: "目标产品不能为空", trigger: "blur" }
        ],
        procName: [
          { required: true, message: "工艺流程名称不能为空", trigger: "blur" }
        ],
      },
      // 产品列表
      productList: [],
      // 设备模型列表
      equipmentModelList: [],
      // 模型操作列表
      modelOperationList: [],
    };
  },
  async created() {
    await this.getProductList();
    await this.getEquipmentModelList();
    await this.getModelOperationList();
    this.getList();
  },
  async activated() {
    await this.getProductList();
    await this.getEquipmentModelList();
    await this.getModelOperationList();
    this.getList();
  },
  methods: {
    // 查询产品列表
    getProductList() {
      return new Promise((resolve, reject) => {
        this.loading = true;
        listMaterial({ maType: '2' }).then(response => {
          this.productList = response.rows
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
          this.equipmentModelList = response.rows
          resolve()
        }).catch(() => {
          reject()
        }).finally(() => {
          this.loading = false
        })
      })
    },
    // 查询模型操作列表
    getModelOperationList() {
      return new Promise((resolve, reject) => {
        this.loading = true;
        listModelOperation().then(response => {
          this.modelOperationList = response.rows
          resolve()
        }).catch(() => {
          reject()
        }).finally(() => {
          this.loading = false
        })
      })
    },
    /** 查询工艺流程列表 */
    getList() {
      this.loading = true;
      listProcess(this.queryParams).then(response => {
        this.processList = response.rows;
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
        procId: undefined,
        maCode: undefined,
        procName: undefined,
        procStat: undefined,
        procDelete: undefined,
        procDesc: undefined,
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
      this.ids = selection.map(item => item.procId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加工艺流程";
    },
    /** 顶部设计按钮操作 */
    handleUpdate(event) {
      const row = this.processList.find(ele => ele.procId === this.ids[0])
      if (row) {
        this.handleDesign(row)
      }
    },
    /** 行内设计按钮操作 */
    handleDesign(row) {
      this.$router.push(`/process/design?procId=${row.procId}&procCode=${row.procCode}`)
    },
    /** 查看按钮操作 */
    handleViewer(row) {
      this.$router.push(`/process/view?procId=${row.procId}&procCode=${row.procCode}`)
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          if (this.form.procId != null) {
            updateProcess(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            // 新增状态自动转为未发布
            this.form.procStat = '1'
            addProcess(this.form).then(response => {
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
      const procIds = row.procId || this.ids;
      this.$modal.confirm('是否确认删除工艺流程编号为"' + procIds + '"的数据项？').then(() => {
        this.loading = true;
        return delProcess(procIds);
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
      this.download('system/process/export', {
        ...this.queryParams
      }, `process_${new Date().getTime()}.xlsx`)
    },
    // 提交审核
    handleSubmitReview(row) {
      const procId = row.procId;
      this.$modal.confirm('是否要提交审核？审核在开始之前可以撤回。').then(() => {
        this.loading = true;
        getProcess(procId).then(response => {
          this.form = response.data;
          this.form.procStat = "2";
          updateProcess(this.form).then(response => {
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
      const procId = row.procId;
      this.$modal.confirm('是否要撤回审核？若审核已开始即无法撤回。').then(() => {
        this.loading = true;
        getProcess(procId).then(response => {
          this.form = response.data;
          if (this.form.procStat === '2') this.form.procStat = '1'
          else this.form.procStat = '4';
          updateProcess(this.form).then(response => {
            this.$modal.msgSuccess("已撤回审核");
            this.getList();
          })
        });
      }).catch(() => {
      }).finally(() => {
        this.loading = false;
      });
    },
    // 弃用工艺流程
    handleDepreciateReview(row) {
      const procId = row.procId;
      this.$modal.confirm('是否弃用此工艺流程？弃用工艺流程需要进行工艺流程弃用审核。').then(() => {
        this.loading = true;
        getProcess(procId).then(response => {
          this.form = response.data;
          this.form.procStat = '7';
          updateProcess(this.form).then(response => {
            this.$modal.msgSuccess("已提交工艺流程弃用审核");
            this.getList();
          })
        });
      }).catch(() => {
      }).finally(() => {
        this.loading = false;
      });
    },
    // 激活工艺流程
    handleActivate(row) {
      const procId = row.procId;
      this.$modal.confirm('是否激活此工艺流程？').then(() => {
        this.loading = true;
        getProcess(procId).then(response => {
          this.form = response.data;
          this.form.procStat = '5';
          updateProcess(this.form).then(response => {
            this.$modal.msgSuccess("已激活工艺流程");
            this.getList();
          })
        });
      }).catch(() => {
      }).finally(() => {
        this.loading = false;
      });
    },
    // 取消激活工艺流程
    handleDeactivate(row) {
      const procId = row.procId;
      this.$modal.confirm('是否停用此工艺流程？').then(() => {
        this.loading = true;
        getProcess(procId).then(response => {
          this.form = response.data;
          this.form.procStat = '4';
          updateProcess(this.form).then(response => {
            this.$modal.msgSuccess("已停用工艺流程");
            this.getList();
          })
        });
      }).catch(() => {
      }).finally(() => {
        this.loading = false;
      });
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