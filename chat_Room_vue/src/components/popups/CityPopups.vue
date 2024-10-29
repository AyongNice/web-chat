<template>
	<div>
		<van-popup v-model="show" :overlay="true" position="bottom" :style="{ height: '50%' }">
			<van-area title="标题" :area-list="areaList" @confirm="onConfirm" @cancel="onCancel"/>
		</van-popup>
	</div>
</template>

<script>
	import { Popup, Area  } from 'vant';
	import { data } from '@/components/popups/city_json'
	export default{
		name:'CityPopups',
		data(){
			return{
				show:false,
				areaList:data
			}
		},
		mounted(){
			
		},
		methods:{
			onShow(){
				this.show=true
			},
			onConfirm(value){
				this.$emit("callBackCity",value)
				this.show=false
			},
			onCancel(){
				this.show=false
			},

		},
		components:{
			Popup, Area 
		}
	}
</script>

<style lang="scss" scoped>
</style>