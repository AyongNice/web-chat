<template>
	<div>
		<van-popup v-model="show" :overlay="true"position="bottom" :style="{ height: '50%' }">
			<van-datetime-picker
			  v-model="defaultBirthday"
			  type="date"
			  title="选择年月日"
			  :min-date="minDate"
			  :max-date="maxDate"
			  @confirm="onConfirm"
			  @cancel="onCancel"
			/>
		</van-popup>
	</div>
</template>

<script>
	import { Popup, DatetimePicker } from 'vant';
	export default{
		name:"birthdayPopups",
		data(){
			return{
				show: false,
				minDate: new Date(1970, 0, 1),
			    maxDate: new Date(2021, 10, 1),
			    defaultBirthday: new Date()
			}
		},
		mounted(){
			console.log(this.defaultBirthday)
			this.defaultBirthday= new Date(this.defaultBirthday)
		},
		methods:{
			onShow() {
				this.show = true
			},
			onConfirm(value) {
		      	this.show = false
		      	console.log(this.$moment(value).format('YYYY-MM-DD'))
		      	this.$emit("callBackBirthday",value)
		    },
		    onCancel() {
		      this.show=false
		    },
		},
		props:{
			defaultBirthday:{
				type:"String"
			}
		},
		components:{
			Popup, DatetimePicker 
		}
	}
</script>

<style lang="scss" scoped>
</style>