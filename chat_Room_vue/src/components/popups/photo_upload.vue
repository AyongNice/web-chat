<template>
	<div>
		<van-popup v-model="show" :overlay="true"position="bottom" :style="{ height: '90%' }">
			<div style="height: 44px;width: 100%; background:#FFFFFF; position: fixed; z-index: 999;">
				<van-row>
					<van-col span="4">
						<span style="font-size: 16px; display: flex; justify-content: center; line-height: 44px;" @click="()=> show = false">取消</span>
					</van-col>
					<van-col span="16">
						<span style="font-size: 16px; display: flex; justify-content: center; line-height: 44px;">创建相册</span>
					</van-col>
					<van-col span="4">
					</van-col>
				</van-row>
			</div>
			<div style="height: 54px;"></div>
			
			<div style="width: 100%; margin-bottom: 20px;">
				 <div style="padding: 5%;">
				 	<van-uploader v-model="fileList" :after-read="afterRead" multiple />
				 </div>
			</div>
			
			<van-button type="primary" block @click="onUpload">上传照片</van-button>
			
		</van-popup>
	</div>
</template>

<script>
	import { Popup, Uploader, Button } from 'vant';
	export default {
		name:"photo_upload",
		data(){
			return{
				show:false,
				fileList: [],
			}
		},
		mounted(){
			
		},
		methods:{
			onShow(){
				this.show = true
			},
			onUpload(){
				console.log(this.fileList)
			},
			afterRead(file){
				file.status = 'uploading';
      			file.message = '上传中...';
				console.log(file)
			}
		},
		components:{
			 Popup, Uploader, Button  
		}
	}
</script>

<style lang="scss" scoped> 
</style>