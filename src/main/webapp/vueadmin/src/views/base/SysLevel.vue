<template>
	<section>
		<!--工具条-->
		<el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
			<el-form :inline="true" :model="filters">
				<el-form-item>
					<el-input v-model="filters.levelName" placeholder="级别名称"></el-input>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" v-on:click="getLevels">查询</el-button>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" @click="handleAdd">新增</el-button>
				</el-form-item>
			</el-form>
		</el-col>

		<!--列表-->
		<el-table :data="levels" highlight-current-row v-loading="listLoading" @selection-change="selsChange" style="width: 100%;">
			<el-table-column type="selection" width="55">
			</el-table-column>
			<el-table-column type="index"  width="60">
			</el-table-column>
			<el-table-column prop="levelName" label="级别" width="180" sortable>
			</el-table-column>
			<el-table-column prop="levelScore" label="级别分值" width="180" sortable>
			</el-table-column>
			<el-table-column prop="baseTime" label="基础时间" width="180" sortable>
			</el-table-column>
			<el-table-column prop="addScore" label="超时加分" min-width="180" sortable>
			</el-table-column>
			<el-table-column label="操作" width="150">
				<template scope="scope">
					<el-button type="danger" size="small" @click="handleDel(scope.$index, scope.row)">删除</el-button>
				</template>
			</el-table-column>
		</el-table>

		<!--工具条-->
		<el-col :span="24" class="toolbar">
			<!--<el-button type="danger" @click="batchRemove" :disabled="this.sels.length===0">批量删除</el-button>-->
			<el-pagination layout="prev, pager, next,total" @current-change="handleCurrentChange" :page-size="pageSize" :total="total" style="float:right;">
			</el-pagination>
		</el-col>
		<!--新增界面-->
		<el-dialog title="新增" v-model="addLevelVisible" :close-on-click-modal="false">
			<el-form :model="addLevel" label-width="80px"  ref="addLevel">
				<el-form-item label="级别">
					<el-input v-model="addLevel.levelName" auto-complete="off"></el-input>
				</el-form-item>
				<el-form-item label="级别分值">
					<el-input v-model="addLevel.levelScore" auto-complete="off"></el-input>
				</el-form-item>
				<el-form-item label="基础时间">
					<el-input v-model="addLevel.baseTime" auto-complete="off"></el-input>
				</el-form-item>
				<el-form-item label="超时加分">
					<el-input v-model="addLevel.addScore" auto-complete="off"></el-input>
				</el-form-item>
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click.native="addLevelVisible = false">取消</el-button>
				<el-button type="primary" @click.native="addSubmit" :loading="addLoading">提交</el-button>
			</div>
		</el-dialog>
	</section>
</template>

<script>
	import {httpPost } from '../../api/api';

	export default {
		data() {
			return {
				filters: {
                    levelName: ''
				},
                levels: [],
				total: 0,
				page: 1,
				pageSize:10,
				listLoading: false,
				sels: [],//列表选中列

                addLevelVisible: false,//新增界面是否显示
				addLoading: false,
				//新增界面数据
				addLevel: {
                    levelName: '',
                    levelScore: '',
                    baseTime: '',
                    addScore: ''
				}

			}
		},
		methods: {
			handleCurrentChange(val) {
                this.page=val
				this.getLevels();
			},
			//获取用户列表
            getLevels() {
				let para = {
				    pageSize:this.pageSize,
					page: this.page,
                    levelName: this.filters.levelName
				};
				this.listLoading = true;
				//NProgress.start();
                httpPost("listLevel",para,this).then((data) => {
					this.total = data.totalCount;
					this.levels = data.list;
					this.listLoading = false;
					//NProgress.done();
				});
			},
			//删除
			handleDel: function (index, row) {
				this.$confirm('确认删除该记录吗?', '提示', {
					type: 'warning'
				}).then(() => {
					this.listLoading = true;
					//NProgress.start();
					let para = { id: row.id };
                    httpPost("delLevel",para,this).then((res) => {
						this.listLoading = false;
						//NProgress.done();
						this.$message({
							message: '删除成功',
							type: 'success'
						});
						this.getLevels();
					});
				}).catch(() => {

				});
			},
			//显示新增界面
			handleAdd: function () {
				this.addLevelVisible = true;
				this.addLevel ={
                    levelName: '',
                    levelScore: '',
                    baseTime: '',
                    addScore: ''
                };
			},
			//新增
			addSubmit: function () {
				this.$refs.addLevel.validate((valid) => {
					if (valid) {
						this.$confirm('确认提交吗？', '提示', {}).then(() => {
							this.addLoading = true;
							//NProgress.start();
							let para = Object.assign({}, this.addLevel);
                            httpPost("addLevel",para,this).then((res) => {
								this.addLoading = false;
								if(res.success){
                                    this.$message({
                                        message: '提交成功',
                                        type: 'success'
                                    });
                                    this.$refs['addLevel'].resetFields();
                                    this.addLevelVisible = false;
                                    this.page=1;
                                    this.getLevels();
								}else{
                                    this.$message({
                                        message: res.data,
                                        type: 'error'
                                    });
								}
							});
						});
					}
				});
			}
		},
		mounted() {
			this.getLevels();
		}
	}

</script>

<style scoped>

</style>