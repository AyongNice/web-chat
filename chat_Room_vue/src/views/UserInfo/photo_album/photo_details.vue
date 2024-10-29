<template>
	<div>
		<!--
        	作者：ayongnice@163.com
        	时间：2020-10-07
        	描述：头部导航栏
        -->
		<div style="height: 44px; width: 100%; background:#FFFFFF; position: fixed; z-index: 999;">
			<van-row>
				<van-col span="4">
					<van-icon style="margin: 10px; " size="24px" name="arrow-left" @click="()=>this.$router.go(-1)" />
				</van-col>
				<van-col span="16">
					<span style="font-size: 16px; display: flex; justify-content: center; line-height: 44px;">
						{{ContextObject.photo_name}}
						<span style="font-size: 12px;">({{ContextObject.jurisdiction=='public'?"完全公开":ContextObject.jurisdiction=='friend'?"仅朋友可见":"仅自己可见"}})</span>
					</span>
				</van-col>
				<!--@click="()=>this.$refs.upload.onShow()"-->
				<van-col span="4" >
					<!--{{ContextObject.jurisdiction=='public'?'公开':ContextObject.jurisdiction=='friend'?'仅朋友可见':'私密'}}-->
					<!--<span style="font-size: 14px; display: flex; justify-content: center; line-height: 44px; font-weight: bold;">上传照片</span>-->
					<span @click="()=> status = !status" style="font-size: 14px; display: flex; justify-content: center; line-height: 44px; font-weight: bold;" v-if="status==false">
						管理
					</span>
					<span @click="deletePhoto" style="font-size: 14px; display: flex; justify-content: center; line-height: 44px; font-weight: bold;" v-if="status">
						删除
					</span>
				</van-col>
			</van-row>
		</div>
		<div style="height: 54px;"></div>
		
		<!--
        	作者：ayongnice@163.com
        	时间：2020-10-07
        	描述：相册详情
        -->
        
		<div style="width: 100%; height: 100%; margin-bottom: 30px;">
			<div class="photo_details_box">
				<div class="photo_item" v-for="(item,index) in ContextObject.imgs" >
					<img :src="item.img" @click="Preview(index)"/>
					<van-checkbox @change="checkIndex(item)" checked-color="red" v-model="item.status" v-if="status" style="position: absolute; margin-top: -130px; margin-left: 4px;"></van-checkbox>
					<span>{{item.photo_name}}</span>
				</div>
			</div>
		</div>
		
		<div style="width: 100%; height: 100%;">
			<div style="margin-left: 5%; margin-bottom: 30px;">
				<van-uploader v-model="fileList" :max-size="1024 * 1024 * 2" :after-read="afterRead"  @oversize="onOversize"/>
			</div>
			
			<div style="width: 90%; margin-left: 5%;">
				<!--<van-button type="primary" block @click="onUpload">上传照片</van-button>-->
			</div>
		</div>
			
		<!--<photoupload ref="upload"></photoupload>-->
	</div>
</template>

<script>
	import { Uploader, Checkbox, Dialog, Toast, Button, ImagePreview } from 'vant';
	import photoupload from '@/components/popups/photo_upload'
	export default{
		name:"photo_details",
		data(){
			return{
				status:false,
				checked:false,
				fileList: [],
				ContextObject:this.$store.state.Photo_Item_Details,
				deleteArrImgs:[]
			}
		},
		mounted(){
			console.log(this.$store.state.Photo_Item_Details)
		},
		methods:{
			afterRead(file) {
		        // 此时可以自行将文件上传至服务器
		        console.log(file);
		        file.status = 'uploading';
      		    file.message = '上传中...';
      		    var formData = new FormData();
				formData.append('file', file.file)
			    this.$Request_post(this.$AXIOS_URL + "/v1/api/files/fileUpload?command=Photo&email=" + this.$ls.get('userInfos').email, formData).then(res => {
					console.log(res)
					if(res.data.fileurl!=null){
						file.status = 'success'
						file.message = '上传成功'
						this.ContextObject.imgs.push({img:res.data.fileurl,status:false})
						var data = {
							photo_name:this.ContextObject.photo_name,
							fileurl:res.data.fileurl
						}
						this.$Request_put(this.$AXIOS_URL + "/v1/api/user/photo", data).then(res=>{
							console.log(res)
							this.$ls.set("userInfos", res.data, 60 * 60 * 1000 * 6)
							//在vuex中管理状态，使用session，避免页面刷新数据消失，
							sessionStorage.setItem('Photo_Item_Details',JSON.stringify(this.ContextObject));
							//commit一次，再次让vuex状态重新获取一次值对象。
							this.$store.commit('setPhoto_Item_Details',this.ContextObject)
						})
					}else{
						file.status = 'failed'
		          		file.message = '上传失败'
					}
				})
		      setTimeout(() => {
		        file.status = 'fail';
		        file.message = '上传成功';
		      }, 1000);
		   	},
		   	onOversize(file){
		   		Toast('文件大小不能超过 2mb');
		   	},
		   	onUpload(){
		   		console.log(this.fileList);	
		   	},
		   	Preview(index){
		   		console.log(index)
//		   		console.log(this.ContextObject.imgs)
				var arr = []
				for (var i=0;i<this.ContextObject.imgs.length;i++) {
					arr.push(this.ContextObject.imgs[i].img)
				}
				ImagePreview({
				    images: arr,
				    startPosition: index,
				    closeable: true,
				    onClose() {
				  	   console.log("删除吗")
				    }
				});
		   	},
		   	checkIndex(item){
		   		console.log(item)
		   		if(item.status){
		   			this.deleteArrImgs.push(item.img)
		   		}else{
		   			this.deleteArrImgs.splice(this.deleteArrImgs.map(x=>x).indexOf(item.img),1)
		   		}
		   	},
		   	deletePhoto(){
		    	if(this.deleteArrImgs.length>=1){
		   			Dialog.confirm({
					  message: '确认要删除此照片吗？',
					})
					.then(() => {
					  	var data = {
			   				photo_name:this.ContextObject.photo_name,
			   				photoArr:this.deleteArrImgs
			   			}
			   			this.$Request_post(this.$AXIOS_URL + "/v1/api/user/deleteDhoto", data).then(res=>{
			   				console.log(res)
			   				if(res.data!=null){
			   					this.$ls.set("userInfos", res.data, 60 * 60 * 1000 * 6)
			   					for(var i=0;i<this.deleteArrImgs.length;i++){
			   						this.ContextObject.imgs.splice(this.ContextObject.imgs.map(x=>x.img).indexOf(this.deleteArrImgs[i]),1)
			   					}
			   					//在vuex中管理状态，使用session，避免页面刷新数据消失，
								sessionStorage.setItem('Photo_Item_Details',JSON.stringify(this.ContextObject));
								//commit一次，再次让vuex状态重新获取一次值对象。
								this.$store.commit('setPhoto_Item_Details',this.ContextObject)
								this.fileList = []
			   					Toast.success('删除成功');
			   				}else{
			   					Toast.fail('删除失败');
			   				}
			   			})
					})
					.catch(() => {
					    // on cancel
					});
		   		}
		    	this.status = false
		   	},
		},
		components:{
			Uploader, Checkbox, Dialog, photoupload, Toast, Button, ImagePreview 
		}
	}
</script>

<style lang="scss" scoped>
.photo_details_box{
	width: 92%; margin-left: 5%;
	.photo_item{
		margin: 0% 1.5% 1.5% 0%; width: 48%; height: 130px; border: 0px solid black; float: left; border-radius: 8px;
		img{
			width: 100%; height: 100%; border-radius: 8px; display: flex;
		}
	}
}

</style>