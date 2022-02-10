<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="考勤月份" prop="attendMonth">
        <el-select v-model="queryParams.attendMonth" placeholder="请选择考勤月份" clearable size="small">
          <el-option
            v-for="dict in dict.type.attend_year_month"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="用户名" prop="userName">
        <el-input
          v-model="queryParams.userName"
          placeholder="请输入用户名"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="规则名称" prop="ruleName">
        <el-input
          v-model="queryParams.ruleName"
          placeholder="请输入规则名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
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
          v-hasPermi="['attendance:statistics:add']"
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
          v-hasPermi="['attendance:statistics:edit']"
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
          v-hasPermi="['attendance:statistics:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['attendance:statistics:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="statisticsList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" align="center" />
      <el-table-column label="考勤月份" align="center" prop="attendMonth">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.attend_year_month" :value="scope.row.attendMonth"/>
        </template>
      </el-table-column>
      <!-- el-table-column label="用户编号" align="center" prop="userId" /-->
      <el-table-column label="用户名" align="center" prop="userName" />
      <el-table-column label="规则名称" align="center" prop="ruleName" />
      <el-table-column label="实到天数" align="center" prop="attendDays" />
      <el-table-column label="正常天数" align="center" prop="normalDays" />
      <el-table-column label="外勤天数" align="center" prop="outsideDays" />
      <el-table-column label="缺勤天数" align="center" prop="absenceDays" />
      <el-table-column label="请假天数" align="center" prop="leaveDays" />
      <el-table-column label="迟到次数" align="center" prop="lateTimes" />
      <el-table-column label="早退次数" align="center" prop="earlyTimes" />
      <el-table-column label="加班时长" align="center" prop="overHours" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['attendance:statistics:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['attendance:statistics:remove']"
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

    <!-- 添加或修改月度考勤统计对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="考勤月份" prop="attendMonth">
          <el-select v-model="form.attendMonth" placeholder="请选择考勤月份">
            <el-option
              v-for="dict in dict.type.attend_year_month"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="用户编号" prop="userId">
          <el-input v-model="form.userId" placeholder="请输入用户编号" />
        </el-form-item>
        <el-form-item label="用户名" prop="userName">
          <el-input v-model="form.userName" placeholder="请输入用户名" />
        </el-form-item>
        <el-form-item label="规则编号" prop="ruleId">
          <el-input v-model="form.ruleId" placeholder="请输入规则编号" />
        </el-form-item>
        <el-form-item label="规则名称" prop="ruleName">
          <el-input v-model="form.ruleName" placeholder="请输入规则名称" />
        </el-form-item>
        <el-form-item label="实到天数" prop="attendDays">
          <el-input v-model="form.attendDays" placeholder="请输入实到天数" />
        </el-form-item>
        <el-form-item label="正常天数" prop="normalDays">
          <el-input v-model="form.normalDays" placeholder="请输入正常天数" />
        </el-form-item>
        <el-form-item label="外勤天数" prop="outsideDays">
          <el-input v-model="form.outsideDays" placeholder="请输入外勤天数" />
        </el-form-item>
        <el-form-item label="缺勤天数" prop="absenceDays">
          <el-input v-model="form.absenceDays" placeholder="请输入缺勤天数" />
        </el-form-item>
        <el-form-item label="请假天数" prop="leaveDays">
          <el-input v-model="form.leaveDays" placeholder="请输入请假天数" />
        </el-form-item>
        <el-form-item label="迟到次数" prop="lateTimes">
          <el-input v-model="form.lateTimes" placeholder="请输入迟到次数" />
        </el-form-item>
        <el-form-item label="早退次数" prop="earlyTimes">
          <el-input v-model="form.earlyTimes" placeholder="请输入早退次数" />
        </el-form-item>
        <el-form-item label="加班时长" prop="overHours">
          <el-input v-model="form.overHours" placeholder="请输入加班时长" />
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
import { listStatistics, getStatistics, delStatistics, addStatistics, updateStatistics } from "@/api/attendance/statistics";

export default {
  name: "Statistics",
  dicts: ['attend_year_month'],
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
      // 月度考勤统计表格数据
      statisticsList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        attendMonth: null,
        userName: null,
        ruleName: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        attendMonth: [
          { required: true, message: "考勤月份不能为空", trigger: "change" }
        ],
        userId: [
          { required: true, message: "用户编号不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询月度考勤统计列表 */
    getList() {
      this.loading = true;
      listStatistics(this.queryParams).then(response => {
        this.statisticsList = response.rows;
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
        statisticsId: null,
        attendMonth: null,
        userId: null,
        userName: null,
        ruleId: null,
        ruleName: null,
        attendDays: null,
        normalDays: null,
        outsideDays: null,
        absenceDays: null,
        leaveDays: null,
        lateTimes: null,
        earlyTimes: null,
        overHours: null
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
      this.ids = selection.map(item => item.statisticsId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加月度考勤统计";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const statisticsId = row.statisticsId || this.ids
      getStatistics(statisticsId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改月度考勤统计";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.statisticsId != null) {
            updateStatistics(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addStatistics(this.form).then(response => {
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
      const statisticsIds = row.statisticsId || this.ids;
      this.$modal.confirm('是否确认删除月度考勤统计编号为"' + statisticsIds + '"的数据项？').then(function() {
        return delStatistics(statisticsIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('attendance/statistics/export', {
        ...this.queryParams
      }, `statistics_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
