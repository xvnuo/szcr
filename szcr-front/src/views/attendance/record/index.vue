<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="用户名" prop="userName">
        <el-input
          v-model="queryParams.userName"
          placeholder="请输入用户名"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="考勤日期">
        <el-date-picker
          v-model="dateRange"
          size="small"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="出勤类型" prop="attendType">
        <el-select
          v-model="queryParams.attendType"
          placeholder="请选择出勤类型"
          clearable
          size="small"
          style="width: 240px"
        >
          <el-option
            v-for="dict in dict.type.attend_record_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="上班状态" prop="onStatus">
        <el-select
          v-model="queryParams.onStatus"
          placeholder="请选择上班状态"
          clearable
          size="small"
          style="width: 240px"
        >
          <el-option
            v-for="dict in dict.type.attend_record_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="下班状态" prop="offStatus">
        <el-select
          v-model="queryParams.offStatus"
          placeholder="请选择下班状态"
          clearable
          size="small"
          style="width: 240px"
        >
          <el-option
            v-for="dict in dict.type.attend_record_status"
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
          v-hasPermi="['attendance:record:add']"
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
          v-hasPermi="['attendance:record:edit']"
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
          v-hasPermi="['attendance:record:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['attendance:record:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="recordList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="用户ID" align="center" prop="userId" />
      <el-table-column label="用户名" align="center" prop="userName" />
      <el-table-column label="规则名称" align="center" prop="ruleName" />
      <el-table-column label="考勤日期" align="center" prop="attendDate" >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.attendDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="出勤类型" align="center" prop="attendType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.attend_record_type" :value="scope.row.attendType"/>
        </template>
      </el-table-column>
      <!-- el-table-column label="排班序号" align="center" prop="scheduleSort" /-->
      <el-table-column label="上班时间" align="center" prop="onTime" />
      <el-table-column label="上班状态" align="center" prop="onStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.attend_record_status" :value="scope.row.onStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="下班时间" align="center" prop="offTime" />
      <el-table-column label="下班状态" align="center" prop="offStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.attend_record_status" :value="scope.row.offStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="考勤时长" align="center" prop="attendHour" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['attendance:record:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['attendance:record:remove']"
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

    <!-- 添加或修改考勤记录对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="用户ID" prop="userId">
          <el-input v-model="form.userId" placeholder="请输入用户ID" />
        </el-form-item>
        <el-form-item label="用户名" prop="userName">
          <el-input v-model="form.userName" placeholder="请输入用户名" />
        </el-form-item>
        <el-form-item label="考勤日期" prop="attendDate">
          <el-date-picker clearable size="small"
            v-model="form.attendDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择考勤日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="星期几" prop="weekdayNum">
          <el-select v-model="form.weekdayNum" placeholder="请选择星期几">
            <el-option
              v-for="dict in dict.type.attend_work_day"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="是否工作日" prop="isWorkday">
          <el-select v-model="form.isWorkday" placeholder="请选择是否工作日">
            <el-option
              v-for="dict in dict.type.sys_yes_no"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="考勤月份" prop="attendMonth">
          <el-select v-model="form.attendMonth" placeholder="请选择考勤月份">
            <el-option
              v-for="dict in dict.type.attend_statistics_month"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="考勤时长" prop="attendHour">
          <el-input v-model="form.attendHour" placeholder="请输入考勤时长" />
        </el-form-item>
        <el-form-item label="排班序号" prop="scheduleSort">
          <el-input v-model="form.scheduleSort" placeholder="请输入排班序号" />
        </el-form-item>
        <el-form-item label="上班时间" prop="onTime">
          <el-time-picker clearable
                          v-model="form.onTime" format="HH:mm:ss" value-format="HH:mm:ss"
                          :picker-options='{"selectableRange":"00:00:00-23:59:59"}' :style="{width: '100%'}"
                          placeholder="请选择上班时间">
          </el-time-picker>
        </el-form-item>
        <el-form-item label="上班状态" prop="onStatus">
          <el-select v-model="form.onStatus" placeholder="请选择上班状态">
            <el-option
              v-for="dict in dict.type.attend_record_status"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="下班时间" prop="offTime">
          <el-time-picker clearable
                          v-model="form.offTime" format="HH:mm:ss" value-format="HH:mm:ss"
                          :picker-options='{"selectableRange":"00:00:00-23:59:59"}' :style="{width: '100%'}"
                          placeholder="请选择下班时间">
          </el-time-picker>
        </el-form-item>
        <el-form-item label="下班状态" prop="offStatus">
          <el-select v-model="form.offStatus" placeholder="请选择下班状态">
            <el-option
              v-for="dict in dict.type.attend_record_status"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="规则ID" prop="ruleId">
          <el-input v-model="form.ruleId" placeholder="请输入规则ID" />
        </el-form-item>
        <el-form-item label="规则名称" prop="ruleName">
          <el-input v-model="form.ruleName" placeholder="请输入规则名称" />
        </el-form-item>
        <el-form-item label="出勤类型" prop="attendType">
          <el-select v-model="form.attendType" placeholder="请选择出勤类型">
            <el-option
              v-for="dict in dict.type.attend_record_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listRecord, getRecord, delRecord, addRecord, updateRecord } from "@/api/attendance/record";

export default {
  name: "Record",
  dicts: ['attend_work_day', 'sys_yes_no', 'attend_statistics_month', 'attend_record_status', 'attend_record_status', 'attend_record_type'],
  data() {
    return {
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
      // 考勤记录表格数据
      recordList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 日期范围
      dateRange: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userName: null,
        onStatus: null,
        offStatus: null,
        attendType: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        userId: [
          { required: true, message: "用户ID不能为空", trigger: "blur" }
        ],
        attendDate: [
          { required: true, message: "考勤日期不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询考勤记录列表 */
    getList() {
      this.loading = true;
      listRecord(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
        this.recordList = response.rows;
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
        recordId: null,
        userId: null,
        userName: null,
        attendDate: null,
        weekdayNum: null,
        isWorkday: null,
        attendMonth: null,
        attendHour: null,
        scheduleSort: null,
        onTime: null,
        onStatus: "0",
        offTime: null,
        offStatus: "0",
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null,
        ruleId: null,
        ruleName: null,
        attendType: null
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
      this.dateRange = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.recordId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加考勤记录";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const recordId = row.recordId || this.ids
      getRecord(recordId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改考勤记录";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.recordId != null) {
            updateRecord(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addRecord(this.form).then(response => {
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
      const recordIds = row.recordId || this.ids;
      this.$modal.confirm('是否确认删除考勤记录编号为"' + recordIds + '"的数据项？').then(function() {
        return delRecord(recordIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('attendance/record/export', {
        ...this.queryParams
      }, `record_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
