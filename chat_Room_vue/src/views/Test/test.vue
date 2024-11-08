<template>
	<div>
	  <el-button @click="addRow">添加行</el-button>
	  <el-button @click="addColumn">添加列</el-button>
	  <el-table :data="list" border style="width: 100%">
		<el-table-column v-for="(column, index) in columns" :key="index" :label="column.label" :prop="column.prop">
		  <template slot-scope="scope">
			<el-input v-model="list[scope.$index][column.prop]" placeholder="请输入内容"></el-input>
		  </template>
		</el-table-column>
	  </el-table>
	</div>
  </template>
  
  <script>
  export default {
	data() {
	  return {
		list: [
		  { name: '', age: '' },
		],
		columns: [
		  { label: '姓名', prop: 'name' },
		  { label: '年龄', prop: 'age' },
		],
	  };
	},
	methods: {
	  addRow() {
		this.list.push({});
	  },
	  addColumn() {
		const newColumnIndex = this.columns.length;
		this.columns.push({ label: `新列${newColumnIndex + 1}`, prop: `newProp${newColumnIndex + 1}` });
		this.list.forEach((row) => {
		  row[`newProp${newColumnIndex + 1}`] = '';
		});
	  },
	},
  };
  </script>
  
  <style scoped>
  .el-table {
	margin-top: 20px;
  }
  </style>