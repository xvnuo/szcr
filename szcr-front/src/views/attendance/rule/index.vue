<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="名称" prop="ruleName">
        <el-input
          v-model="queryParams.ruleName"
          placeholder="请输入名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="考勤类型" prop="ruleType">
        <el-select v-model="queryParams.ruleType" placeholder="请选择考勤类型" clearable size="small">
          <el-option
            v-for="dict in dict.type.attend_rule_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="上班时间" prop="onTime">
        <el-time-picker clearable
                        v-model="queryParams.onTime" format="HH:mm:ss" value-format="HH:mm:ss"
                        :picker-options='{"selectableRange":"00:00:00-23:59:59"}' :style="{width: '100%'}"
                        placeholder="请选择上班时间">
        </el-time-picker>
      </el-form-item>
      <el-form-item label="下班时间" prop="offTime">
        <el-time-picker clearable
                        v-model="queryParams.offTime" format="HH:mm:ss" value-format="HH:mm:ss"
                        :picker-options='{"selectableRange":"00:00:00-23:59:59"}' :style="{width: '100%'}"
                        placeholder="请选择下班时间">
        </el-time-picker>
      </el-form-item>
      <el-form-item label="每日工时" prop="workHour">
        <el-input
          v-model="queryParams.workHour"
          placeholder="请输入每日工时"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable size="small">
          <el-option
            v-for="dict in dict.type.sys_normal_disable"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
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
          v-hasPermi="['attendance:rule:add']"
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
          v-hasPermi="['attendance:rule:edit']"
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
          v-hasPermi="['attendance:rule:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['attendance:rule:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="ruleList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="规则名称" align="center" prop="ruleName"/>
      <el-table-column label="考勤类型" align="center" prop="ruleType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.attend_rule_type" :value="scope.row.ruleType"/>
        </template>
      </el-table-column>
      <!-- el-table-column label="编号" align="center" prop="ruleId" /-->
      <!-- el-table-column label="上班时间" align="center" prop="onTime" />
      <el-table-column label="下班时间" align="center" prop="offTime" />
      <el-table-column label="每日工时" align="center" prop="workHour" />
      <el-table-column label="工作日" align="center" prop="workDays">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.attend_work_day" :value="scope.row.workDays"/>
        </template>
      </el-table-column !-->
      <el-table-column label="状态" align="center" width="100" key="status" >
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.status"
            active-value="0"
            inactive-value="1"
            @change="handleStatusChange(scope.row)"
          ></el-switch>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['attendance:rule:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['attendance:rule:remove']"
          >删除</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-user"
            @click="handleAuthUser(scope.row)"
            v-hasPermi="['attendance:rule:edit']"
          >分配用户</el-button>
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

    <!-- 添加或修改考勤规则对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1000px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="名称" prop="ruleName">
              <el-input v-model="form.ruleName" placeholder="请输入名称" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="类型">
              <el-radio-group v-model="form.ruleType">
                <el-radio
                  v-for="dict in dict.type.attend_rule_type"
                  :key="dict.value"
                  :label="dict.value"
                >{{dict.label}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="上班时间" prop="onTime">
              <el-time-picker clearable
                              v-model="form.onTime" format="HH:mm:ss" value-format="HH:mm:ss"
                              :picker-options='{"selectableRange":"00:00:00-23:59:59"}' :style="{width: '100%'}"
                              placeholder="请选择上班时间">
              </el-time-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="下班时间" prop="offTime">
              <el-time-picker clearable
                              v-model="form.offTime" format="HH:mm:ss" value-format="HH:mm:ss"
                              :picker-options='{"selectableRange":"00:00:00-23:59:59"}' :style="{width: '100%'}"
                              placeholder="请选择下班时间">
              </el-time-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="每日工时" prop="workHour">
              <el-input v-model="form.workHour" placeholder="请输入每日工时" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="状态">
              <el-radio-group v-model="form.status">
                <el-radio
                  v-for="dict in dict.type.sys_normal_disable"
                  :key="dict.value"
                  :label="dict.value"
                >{{dict.label}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-form-item label="工作日">
            <el-checkbox-group v-model="form.workDays">
              <el-checkbox
                v-for="dict in dict.type.attend_work_day"
                :key="dict.value"
                :label="dict.value">
                {{dict.label}}
              </el-checkbox>
            </el-checkbox-group>
          </el-form-item>
        </el-row>
        <el-row>
          <el-form-item label="备注" prop="remark">
            <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
          </el-form-item>
        </el-row>
        <el-divider content-position="center">排班信息</el-divider>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAddAttendSchedule">添加</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" icon="el-icon-delete" size="mini" @click="handleDeleteAttendSchedule">删除</el-button>
          </el-col>
        </el-row>
        <el-table :data="attendScheduleList" :row-class-name="rowAttendScheduleIndex" @selection-change="handleAttendScheduleSelectionChange" ref="attendSchedule">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column label="序号" align="center" prop="index" width="50"/>
          <el-table-column label="班次名称" prop="scheduleName">
            <template slot-scope="scope">
              <el-input v-model="scope.row.scheduleName" placeholder="请输入班次名称" />
            </template>
          </el-table-column>
          <el-table-column label="工作日" prop="workDay">
            <template slot-scope="scope">
              <el-select v-model="scope.row.workDay" placeholder="请选择工作日" clearable size="small">
                <el-option
                  v-for="dict in dict.type.attend_work_day"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="开始时间" prop="onTime">
            <template slot-scope="scope">
              <el-time-picker clearable
                              v-model="scope.row.onTime" format="HH:mm:ss" value-format="HH:mm:ss"
                              :picker-options='{"selectableRange":"00:00:00-23:59:59"}' :style="{width: '100%'}"
                              placeholder="请选择开始时间">
              </el-time-picker>
            </template>
          </el-table-column>
          <el-table-column label="结束时间" prop="offTime">
            <template slot-scope="scope">
              <el-time-picker clearable
                              v-model="scope.row.offTime" format="HH:mm:ss" value-format="HH:mm:ss"
                              :picker-options='{"selectableRange":"00:00:00-23:59:59"}' :style="{width: '100%'}"
                              placeholder="请选择结束时间">
              </el-time-picker>
            </template>
          </el-table-column>
          <el-table-column label="状态" align="center" prop="status">
            <template slot-scope="scope">
              <dict-tag :options="dict.type.sys_normal_disable" :value="scope.row.status"/>
            </template>
          </el-table-column>
          <el-table-column label="备注" prop="remark">
            <template slot-scope="scope">
              <el-input v-model="scope.row.remark" placeholder="请输入备注" />
            </template>
          </el-table-column>
        </el-table>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listRule, getRule, delRule, addRule, updateRule, changeRuleStatus} from "@/api/attendance/rule";

export default {
  name: "Rule",
  dicts: ['attend_rule_type', 'sys_normal_disable', 'attend_work_day'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 子表选中数据
      checkedAttendSchedule: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 考勤规则表格数据
      ruleList: [],
      // 排班表格数据
      attendScheduleList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        ruleName: null,
        ruleType: null,
        onTime: null,
        offTime: null,
        workHour: null,
        workDays: null,
        status: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        ruleName: [
          { required: true, message: "名称不能为空", trigger: "blur" }
        ],
        ruleType: [
          { required: true, message: "考勤类型不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询考勤规则列表 */
    getList() {
      this.loading = true;
      listRule(this.queryParams).then(response => {
        this.ruleList = response.rows;
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
        ruleId: null,
        ruleName: null,
        ruleType: "0",
        onTime: null,
        offTime: null,
        workHour: null,
        workDays: [],
        status: "0",
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null
      };
      this.attendScheduleList = [];
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
      this.ids = selection.map(item => item.ruleId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    // 更多操作触发
    handleCommand(command, row) {
      switch (command) {
        case "handleDataScope":
          break;
        case "handleAuthUser":
          this.handleAuthUser(row);
          break;
        default:
          break;
      }
    },
    /** 分配用户操作 */
    handleAuthUser: function(row) {
      const ruleId = row.ruleId;
      this.$router.push("/attendance/rule-auth/user/" + ruleId);
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加考勤规则";
    },
    /** 考勤规则状态修改 */
    handleStatusChange(row) {
      let text = row.status === "0" ? "启用" : "停用";
      this.$modal.confirm('确认要"' + text + '""' + row.ruleName + '"吗？').then(function() {
        return changeRuleStatus(row.ruleId, row.status);
      }).then(() => {
        this.$modal.msgSuccess(text + "成功");
      }).catch(function() {
        row.status = row.status === "0" ? "1" : "0";
      });
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const ruleId = row.ruleId || this.ids
      getRule(ruleId).then(response => {
        this.form = response.data;
        if(this.form.workDays) {
          this.form.workDays = this.form.workDays.split(",");
        }
        this.attendScheduleList = response.data.attendScheduleList;
        this.open = true;
        this.title = "修改考勤规则";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.workDays = this.form.workDays.join(",");
          this.form.attendScheduleList = this.attendScheduleList;
          if (this.form.ruleId != null) {
            updateRule(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addRule(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ruleIds = row.ruleId || this.ids;
      this.$modal.confirm('是否确认删除考勤规则编号为"' + ruleIds + '"的数据项？').then(function() {
        return delRule(ruleIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 排班序号 */
    rowAttendScheduleIndex({ row, rowIndex }) {
      row.index = rowIndex + 1;
    },
    /** 排班添加按钮操作 */
    handleAddAttendSchedule() {
      let obj = {};
      obj.scheduleName = "";
      obj.workDay = "";
      obj.onTime = "";
      obj.offTime = "";
      obj.status = "";
      obj.remark = "";
      this.attendScheduleList.push(obj);
    },
    /** 排班删除按钮操作 */
    handleDeleteAttendSchedule() {
      if (this.checkedAttendSchedule.length == 0) {
        this.$modal.msgError("请先选择要删除的排班数据");
      } else {
        const attendScheduleList = this.attendScheduleList;
        const checkedAttendSchedule = this.checkedAttendSchedule;
        this.attendScheduleList = attendScheduleList.filter(function(item) {
          return checkedAttendSchedule.indexOf(item.index) == -1
        });
      }
    },
    /** 复选框选中数据 */
    handleAttendScheduleSelectionChange(selection) {
      this.checkedAttendSchedule = selection.map(item => item.index)
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('attendance/rule/export', {
        ...this.queryParams
      }, `rule_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
