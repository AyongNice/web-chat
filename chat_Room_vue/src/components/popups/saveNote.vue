<template>
	<div>
		<van-popup v-model="show" :overlay="true"position="bottom" :style="{ height: '60%' }">
			<div style="height: 44px;width: 100%; background:#FFFFFF; position: fixed; z-index: 999;">
				<van-row>
					<van-col span="4">
						<span style="display: flex; justify-content: center; line-height: 44px;" @click="()=> show = false">取消</span>
					</van-col>
					<van-col span="16">
						<span style="font-size: 16px; display: flex; justify-content: center; line-height: 44px;">添加备注</span>
					</van-col>
					<van-col span="4">
						<span style="display: flex; justify-content: center; line-height: 44px;" @click="save">保存</span>
					</van-col>
				</van-row>
			</div>
			
			<div style="height: 54px;"></div>
			
			<van-cell-group>
			  <van-field v-model="noteName" maxlength="15" label="备注名" placeholder="请输入备注名" />
			</van-cell-group>
		</van-popup>
	</div>
</template>

<script>
	import { Popup, Field } from 'vant'
	export default {
		name: "saveNote",
		data() {
			return {
				show:false,
				noteName:this.$store.state.Friend_Information.note!=null?this.$store.state.Friend_Information.note:this.$store.state.Friend_Information.username
			}
		},
		mounted(){
			console.log(this.$store.state.Friend_Information)
		},
		methods:{
			onShow(){
				this.show = true
			},
			save(){
				this.$emit("callBackSaveNote",this.noteName);
				this.show = false
			}
		},
		components:{
			Popup, Field
		}
	}
</script>

<style>

</style>