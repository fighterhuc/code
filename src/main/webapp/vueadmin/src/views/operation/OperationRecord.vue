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
					<el-button type="primary" v-on:click="getRecords">查询</el-button>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" @click="handleAdd">新增</el-button>
				</el-form-item>
			</el-form>
		</el-col>

		<!--列表-->
		<el-table :data="records" highlight-current-row v-loading="listLoading" @selection-change="selsChange" style="width: 100%;">
			<el-table-column type="selection" width="55">
			</el-table-column>
			<el-table-column type="index" width="60">
			</el-table-column>
			<el-table-column prop="username" label="主刀医生" width="100" sortable>
			</el-table-column>
			<el-table-column prop="operatingRoom" label="手术室" width="100" :formatter="formatSex" sortable>
			</el-table-column>
			<el-table-column prop="operationDate" label="手术日期" width="180" sortable>
			</el-table-column>
			<el-table-column prop="patientName" label="患者姓名" width="100" sortable>
			</el-table-column>
			<el-table-column prop="inRoomTime" label="入室时间" min-width="180" sortable>
			</el-table-column>
			<el-table-column prop="outRoomTime" label="出室时间" min-width="180" sortable>
			</el-table-column>
			<el-table-column prop="time" label="手术用时" min-width="100" sortable>
			</el-table-column>
			<el-table-column prop="procedure" label="术式" min-width="100" sortable>
			</el-table-column>
			<el-table-column prop="levelName" label="级别" min-width="100" sortable>
			</el-table-column>
			<el-table-column prop="qjNurse" label="器械护士" min-width="100" sortable>
			</el-table-column>
			<el-table-column prop="xhNurse" label="巡回护士" min-width="100" sortable>
			</el-table-column>
		</el-table>

		<!--工具条-->
		<el-col :span="24" class="toolbar">
			<el-pagination layout="prev, pager, next,total" @current-change="handleCurrentChange" :page-size="pageSize" :total="total" style="float:right;">
			</el-pagination>
		</el-col>
		<!--新增界面-->
		<el-dialog title="新增" v-model="addFormVisible" :close-on-click-modal="false">
			<el-form :model="addForm" label-width="80px" :rules="addFormRules" ref="addForm">
				<el-col :span="24">
					<el-col :span="12">
						<el-form-item label="主刀医生">
							<el-select v-model="addForm.userId" placeholder="请选择人员" style="width: 100%;">
								<template v-for="user in users">
									<el-option :label="user.username" :value="user.id"></el-option>
								</template>
							</el-select>
						</el-form-item>
					</el-col>
					<el-col :span="12">
						<el-form-item label="科室">
							<el-input v-model="addForm.department" auto-complete="off" style="width: 100%;"></el-input>
						</el-form-item>
					</el-col>
				</el-col>

				<el-col :span="24">
					<el-col :span="12">
						<el-form-item label="手术室">
							<el-input v-model="addForm.operatingRoom" auto-complete="off" style="width: 100%;"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="12">
						<el-form-item label="手术日期">
							<el-date-picker type="date" placeholder="选择日期" v-model="addForm.operationDate" style="width: 100%;"></el-date-picker>
						</el-form-item>
					</el-col>
				</el-col>


				<el-col :span="24">
					<el-col :span="12">
						<el-form-item label="患者姓名">
							<el-input v-model="addForm.patientName" auto-complete="off" style="width: 100%;"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="12">
						<el-form-item label="入室时间">
							<el-date-picker type="datetime"  v-model="addForm.inRoomTime" placeholder="选择日期" style="width: 100%;"></el-date-picker>
						</el-form-item>
					</el-col>
				</el-col>


				<el-col :span="24">
					<el-col :span="12">
						<el-form-item label="出室时间">
							<el-date-picker type="datetime"  v-model="addForm.outRoomTime" placeholder="选择日期" style="width: 100%;"></el-date-picker>
						</el-form-item>
					</el-col>
					<el-col :span="12">
						<el-form-item label="手术时长">
						<el-input-number v-model="addForm.time" :min="0" :max="200" style="width: 100%;"></el-input-number>
						</el-form-item>
					</el-col>
				</el-col>


				<el-col :span="24">
					<el-col :span="12">
						<el-form-item label="术式">
							<el-input v-model="addForm.procedure" auto-complete="off" style="width: 100%;"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="12">
						<el-form-item label="级别">
							<el-select v-model="addForm.levelId" placeholder="请选择级别" style="width: 100%;">
								<template v-for="level in levels">
									<el-option :label="level.levelName" :value="level.id"></el-option>
								</template>
							</el-select>
						</el-form-item>
					</el-col>
				</el-col>
				<el-col :span="24">
					<el-col :span="12">
						<el-form-item label="器械护士">
							<el-input v-model="addForm.qjNurse" auto-complete="off" style="width: 100%;"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="12">
						<el-form-item label="巡回护士">
							<el-input v-model="addForm.xhNurse" auto-complete="off" style="width: 100%;"></el-input>
						</el-form-item>
					</el-col>
				</el-col>
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click.native="addFormVisible = false">取消</el-button>
				<el-button type="primary" @click.native="addSubmit" :loading="addLoading">提交</el-button>
			</div>
		</el-dialog>
	</section>
</template>

<script>
	import util from "../../common/js/util";
    import {httpPost } from '../../api/api';
    export default {
		data() {
			return {
				filters: {
					beginDate: '',
					endDate:'',
				},
				records: [],
				levels:[],
				users:[],
				total: 0,
				page: 1,
				pageSize:10,
				listLoading: false,
				addFormVisible: false,//新增界面是否显示
				addLoading: false,
				//新增界面数据
				addForm: {
                    id: '',
                    username: '',
                    userId: '',
                    department: '',
                    operatingRoom: '',
                    operationDate: '',
                    patientName: '',
                    inRoomTime: '',
                    outRoomTime: '',
                    time: '',
                    procedure: '',
                    levelId: '',
                    levelName: '',
                    qjNurse: '',
                    xhNurse: ''
				}

			}
		},
		methods: {
            initData() {
                //NProgress.start();
				var para = {};
				//加载级别信息
                httpPost("getLevelList",para,this).then((res) => {
                    this.levels = res;
                });
                //加载用户信息
                httpPost("listUsers",para,this).then((res) => {
                    this.users = res;
                });
            },
			handleCurrentChange(val) {
				this.page = val;
				this.getRecords();
			},
			//获取用户列表
			getRecords() {
                let para = Object.assign({}, this.filters);
                para.beginDate = (!para.beginDate || para.beginDate == '') ? '' : util.formatDate.format(new Date(para.beginDate), 'yyyy-MM-dd HH:mm:ss');
                para.endDate = (!para.endDate || para.endDate == '') ? '' : util.formatDate.format(new Date(para.endDate), 'yyyy-MM-dd HH:mm:ss');
                para.page = this.page;
                para.pageSize = this.pageSize;
				this.listLoading = true;
				//NProgress.start();
				httpPost("listOperationRecord",para,this).then((res) => {
					this.total = res.totalCount;
					this.records = res.list;
					this.listLoading = false;
					//NProgress.done();
				});
			},
			//显示新增界面
			handleAdd: function () {
				this.addFormVisible = true;
				this.addForm = {
                    id: '',
                    username: '',
                    userId:'',
                    department: '',
                    operatingRoom: '',
                    operationDate: '',
                    patientName: '',
                    inRoomTime: '',
                    outRoomTime: '',
                    time: '',
                    procedure: '',
                    levelId: '',
                    levelName: '',
                    qjNurse: '',
                    xhNurse: ''
                };
			},
			//新增
			addSubmit: function () {
				this.$refs.addForm.validate((valid) => {
					if (valid) {
						this.$confirm('确认提交吗？', '提示', {}).then(() => {
							this.addLoading = true;
							//NProgress.start();
							let para = Object.assign({}, this.addForm);
                            para.operationDate = (!para.operationDate || para.operationDate == '') ? '' : util.formatDate.format(new Date(para.operationDate), 'yyyy-MM-dd');
                            para.inRoomTime = (!para.inRoomTime || para.inRoomTime == '') ? '' : util.formatDate.format(new Date(para.inRoomTime), 'yyyy-MM-dd hh:mm:ss');
                            para.outRoomTime = (!para.outRoomTime || para.outRoomTime == '') ? '' : util.formatDate.format(new Date(para.outRoomTime), 'yyyy-MM-dd hh:mm:ss');

							httpPost("addOperationRecord",para,this).then((res) => {
								this.addLoading = false;
								//NProgress.done();
								this.$message({
									message: '提交成功',
									type: 'success'
								});
								this.$refs['addForm'].resetFields();
								this.addFormVisible = false;
                                this.getRecords();
							});
						});
					}
				});
			},
		},
		mounted() {
            this.initData();
            this.getRecords();
		}
	}

</script>

<style scoped>

</style>