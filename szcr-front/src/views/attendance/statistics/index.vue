<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="用户ID" prop="userId">
        <el-input
          v-model="queryParams.userId"
          placeholder="请输入用户ID"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="年份" prop="attendYear">
        <el-select v-model="queryParams.attendYear" placeholder="请选择年份" clearable size="small">
          <el-option
            v-for="dict in dict.type.attend_statistics_year"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="月份" prop="attendMonth">
        <el-select v-model="queryParams.attendMonth" placeholder="请选择月份" clearable size="small">
          <el-option
            v-for="dict in dict.type.attend_statistics_month"
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
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="统计编号" align="center" prop="statisticsId" />
      <el-table-column label="用户ID" align="center" prop="userId" />
      <el-table-column label="规则ID" align="center" prop="ruleId" />
      <el-table-column label="年份" align="center" prop="attendYear">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.attend_statistics_year" :value="scope.row.attendYear"/>
        </template>
      </el-table-column>
      <el-table-column label="月份" align="center" prop="attendMonth">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.attend_statistics_month" :value="scope.row.attendMonth"/>
        </template>
      </el-table-column>
      <el-table-column label="应到天数" align="center" prop="shouldDays" />
      <el-table-column label="实到天数" align="center" prop="attendDays" />
      <el-table-column label="迟到次数" align="center" prop="lateTimes" />
      <el-table-column label="早退次数" align="center" prop="earlyTimes" />
      <el-table-column label="缺勤天数" align="center" prop="absentDays" />
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

    <!-- 添加或修改考勤统计对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="用户ID" prop="userId">
          <el-input v-model="form.userId" placeholder="请输入用户ID" />
        </el-form-item>
        <el-form-item label="规则ID" prop="ruleId">
          <el-input v-model="form.ruleId" placeholder="请输入规则ID" />
        </el-form-item>
        <el-form-item label="年份" prop="attendYear">
          <el-select v-model="form.attendYear" placeholder="请选择年份">
            <el-option
              v-for="dict in dict.type.attend_statistics_year"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="月份" prop="attendMonth">
          <el-select v-model="form.attendMonth" placeholder="请选择月份">
            <el-option
              v-for="dict in dict.type.attend_statistics_month"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="应到天数" prop="shouldDays">
          <el-input v-model="form.shouldDays" placeholder="请输入应到天数" />
        </el-form-item>
        <el-form-item label="实到天数" prop="attendDays">
          <el-input v-model="form.attendDays" placeholder="请输入实到天数" />
        </el-form-item>
        <el-form-item label="迟到次数" prop="lateTimes">
          <el-input v-model="form.lateTimes" placeholder="请输入迟到次数" />
        </el-form-item>
        <el-form-item label="早退次数" prop="earlyTimes">
          <el-input v-model="form.earlyTimes" placeholder="请输入早退次数" />
        </el-form-item>
        <el-form-item label="缺勤天数" prop="absentDays">
          <el-input v-model="form.absentDays" placeholder="请输入缺勤天数" />
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
  dicts: ['attend_statistics_year', 'attend_statistics_month'],
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
      // 考勤统计表格数据
      statisticsList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userId: null,
        attendYear: null,
        attendMonth: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        userId: [
          { required: true, message: "用户ID不能为空", trigger: "blur" }
        ],
        ruleId: [
          { required: true, message: "规则ID不能为空", trigger: "blur" }
        ],
        attendYear: [
          { required: true, message: "年份不能为空", trigger: "change" }
        ],
        attendMonth: [
          { required: true, message: "月份不能为空", trigger: "change" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询考勤统计列表 */
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
        userId: null,
        ruleId: null,
        attendYear: null,
        attendMonth: null,
        shouldDays: null,
        attendDays: null,
        lateTimes: null,
        earlyTimes: null,
        absentDays: null
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
      this.title = "添加考勤统计";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const statisticsId = row.statisticsId || this.ids
      getStatistics(statisticsId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改考勤统计";
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
      this.$modal.confirm('是否确认删除考勤统计编号为"' + statisticsIds + '"的数据项？').then(function() {
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
