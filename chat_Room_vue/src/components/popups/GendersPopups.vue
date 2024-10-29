<template>
	<div>
		<van-popup v-model="show" :overlay="true"position="bottom" :style="{ height: '30%' }">
			<van-picker
			  title="选择性别"
			  show-toolbar
			  :columns="columns"
			  @confirm="onConfirm"
			  @cancel="onCancel"
			  :default-index="columns.indexOf(GendersText)"
			/>
		</van-popup>
	</div>
</template>

<script>
	import { Popup, Picker } from 'vant';
	export default{
		name:"GendersPopups",
		data(){
			return {
				show: false,
				columns: ['男', '女'],
			}
		},
		mounted(){
		},
		methods:{
			onShow(){
				this.show = true
			},
			onConfirm(value, index) {
		      	this.show = false
		      	this.$emit("callBackGenders",value)
		    },
		    onCancel() {
		      this.show=false
		    },
		},
		props:{
			GendersText:{
				type:String
			}
		},
		components:{Popup, Picker}
	}
</script>

<style lang="scss" scoped>
</style>