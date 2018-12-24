<template>
	<section>
		<!--工具条-->
		<el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
			<el-form :inline="true" :model="filters">
				<el-form-item label="开始时间">
					<el-date-picker type="datetime" placeholder="选择日期" v-model="filters.beginDate"></el-date-picker>
				</el-form-item>
				<el-form-item label="结束时间">
					<el-date-picker type="datetime" placeholder="选择日期" v-model="filters.endDate"></el-date-picker>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" v-on:click="listPmsReportList">查询</el-button>
				</el-form-item>
			</el-form>
		</el-col>

		<!--列表-->
		<el-table :data="reportInfo" highlight-current-row v-loading="listLoading" @selection-change="selsChange" style="width: 100%;">
			<el-table-column type="selection" width="55">
			</el-table-column>
			<el-table-column type="index" width="60">
			</el-table-column>
			<el-table-column prop="username" label="姓名" width="180" sortable>
			</el-table-column>
			<el-table-column prop="sumTime" label="总手术时间" width="180" :formatter="formatSex" sortable>
			</el-table-column>
			<el-table-column prop="sumScore" label="总绩效分数" width="180" sortable>
			</el-table-column>
		</el-table>
		<!--工具条-->
		<el-col :span="24" class="toolbar">
			<el-pagination layout="prev, pager, next,total" @current-change="handleCurrentChange" :page-size="pageSize" :total="total" style="float:right;">
			</el-pagination>
		</el-col>
	</section>
</template>

<script>
    import {httpPost } from '../../api/api';
    export default {
		data() {
			return {
				filters: {
					beginDate: '',
					endDate:'',
				},
				reportInfo: [],
				total: 0,
				page: 1,
				pageSize:10,
				listLoading: false
			}
		},
		methods: {
			handleCurrentChange(val) {
				this.page = val;
				this.listPmsReportList();
			},
			//获取用户列表
            listPmsReportList() {
                let para = Object.assign({}, this.filters);
                para.beginDate = (!para.beginDate || para.beginDate == '') ? '' : util.formatDate.format(new Date(para.beginDate), 'yyyy-MM-dd HH:mm:ss');
                para.endDate = (!para.endDate || para.endDate == '') ? '' : util.formatDate.format(new Date(para.endDate), 'yyyy-MM-dd HH:mm:ss');
                para.page = this.page;
                para.pageSize = this.pageSize;
				this.listLoading = true;
				//NProgress.start();
				httpPost("listPmsReportList",para,this).then((res) => {
					this.total = res.total;
					this.reportInfo = res.list;
					this.listLoading = false;
				});
			}
		},
		mounted() {
            this.listPmsReportList();
		}
	}

</script>

<style scoped>

</style>