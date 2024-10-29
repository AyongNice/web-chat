<template>
	<div id="chatBox">
		<!--
        	作者：ayongnice@163.com
        	时间：2024-10-05
        	描述：头部导航栏
        -->
		<div style="height: 44px;width: 100%; background:#FFFFFF; position: fixed; z-index: 999;">
			<van-row>
				<van-col span="4">
					<van-icon style="margin: 10px; " size="24px" name="arrow-left" @click="()=>this.$router.push({name:'home'})" />
				</van-col>
				<van-col span="16">
					<span style="font-size: 16px; display: flex; justify-content: center; line-height: 44px;">
						{{ChatContent.note==null?ChatContent.username:ChatContent.note}}
					</span>
				</van-col>
				<van-col span="4">
					<van-icon style="display: flex; justify-content: center; line-height: 44px;" size="20" name="ellipsis" />
				</van-col>
			</van-row>
		</div>
		<div style="height: 44px;"></div>
		
		
		<!--
        	作者：ayongnice@163.com
        	时间：2020-10-17
        	描述：聊天记录
        -->
		<div style="width: 92%; margin-left: 4%; display: flex;justify-content: space-between;" v-for="(item,index) in ChatContent.msgs" :key="index">
			<div style="width: 100%; margin-top: 14px;" >
				<div style="display: flex;" v-if="ChatContent.openId!=item.openId">
					<img style="width: 35px; height: 35px;" :src="ChatContent.avatar" />
					<div style="background: #ffffff; padding: 10px; margin-left: 10px; border-radius: 8px;" v-if="item.type=='String'">
						{{item.msg}}
					</div>
					<!--图片-->
					<div style="background: #ffffff; padding: 10px; margin-left: 10px; border-radius: 8px;" v-if="item.type=='Images'">
						<img :id="index"  :stop.src="getImageDom(index,item.msg)" style="width: 140px; height: auto;"/>
						<!--<img :id="index"  :src="$Request_get('http://192.168.16.95:3000/v1/api/chat/findFiles/'+item.msg).then(res=>{window.document.getElementById(index).src = res.data})" style="width: 140px; height: auto;"/>-->
					</div>
				</div>
				<div style="display: flex; margin-top: 10px; float: right;"  v-if="ChatContent.openId==item.openId">
					<div style="float: right; background: #ffffff; margin-right: 10px; padding: 10px; border-radius: 8px;" v-if="item.type=='String'">
						{{item.msg}} 
					</div>
					<!--图片-->
					<div style="float: right; background: #ffffff; margin-right: 10px; padding: 10px; border-radius: 8px;" v-if="item.type=='Images'">
						<!--<img :id="index" :stop.src="getImageDom(index,item.msg)" style="width: 140px; height: auto;"/>-->
						<img :id="index" :stop.src="getImageDom(index,item.msg)" style="width: 140px; height: auto;"/>
					</div>
					<div style="float: right;">
						<img style="float: right; width: 35px; height: 35px;" :src="avatar" />
					</div>
				</div>
			</div>
		</div>
		
		<!--
        	作者：ayongnice@163.com
        	时间：2020-10-16
        	描述：底部发送消息部分
        -->
        <div style="height: 60px;"></div>
		<div style="width: 100%;position: fixed; bottom: 0px; height: 60px; background: #EEF0F4;">
			<div style="display: flex;">
				<div style="width: 15%;">
					<!--<van-icon style="display: flex;justify-content: center; line-height: 60px;" size="30" name="photo-o" @click="selectImage"/>
   					<input type="file" id="uploads" style="display: none" multiple @change="handleFile"/>-->
   					<van-uploader :after-read="afterRead" :max-size="1024 * 1024 * 1" @oversize="onOversize" style="padding: 8px;" preview-size="40"/>
				</div>
				<div style="width: 70%;">
					<input v-model="msg" v-myfocus style="width: 100%; border: 0px; background: #FFFFFF; height: 28px; font-size: 16px; margin-top: 15px;"/>
				</div>
				<div style="width: 15%;" @click="sendMsg">
					<span style="display: flex; justify-content: center; line-height: 60px;">发送</span>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
	import { msg } from "@/utils/utils"
	import { Uploader, Toast } from 'vant';
	export default {
		name: "chat_room",
		data() {
			this.msg = msg;
			return {
				avatar:this.$ls.get('userInfos').avatar,
				ChatContent:this.$store.state.That_Chat,
				
			}
		},
		computed:{
			getImageDom(){
				return function(index,data){
					this.$Request_get(this.$AXIOS_URL + "/v1/api/chat/findFiles/"+data).then(res=>{
//						console.log(res)
						document.getElementById(index).src = res.data
						return 
					})
				}
			}
		},
		mounted() {
			console.log(this.$store.state.That_Chat)
			this.scrollToBottom();
			
//			this.$Request_get(this.$AXIOS_URL + "/v1/api/chat/findFiles/156320201007161738_134020201022140707.png").then(res => {
//				console.log(res)
//			})
		},
		sockets:{
			cllback_sendMessage(data){
				console.log(data)
				this.ChatContent.msgs.push(data.data)
			},
			get_sendMessage_msg(data){
				console.log(data)
				this.ChatContent.msgs.push(data)
			}
		},
		updated(){
			this.scrollToBottom();
		},
		methods: {
			sendMsg(types){
				if(this.msg!=""){
					var data = {
						openId: this.ChatContent.openId, 
						correlationId: this.ChatContent.friendId,
						command: "sendMessage",
						msgs:{ openId:this.ChatContent.openId, msg:this.msg, type:"String" }
					}
					this.$socket.emit('sendMessage', JSON.stringify(data));
					this.msg=""
					this.scrollToBottom();
				}else{
					Toast('发送消息不可为空');
				}
			},
			scrollToBottom() {
		     	this.$nextTick(() => {
		     		// Chrome
					document.body.scrollTop = document.getElementById("chatBox").offsetTop + document.documentElement.scrollHeight || document.body.scrollHeight
//					console.log(document.getElementById("chatBox").offsetTop)
					// Firefox
					document.documentElement.scrollTop = document.getElementById("chatBox").offsetTop + document.documentElement.scrollHeight || document.body.scrollHeight
					// Safari
					window.pageYOffset = document.getElementById("chatBox").offsetTop +document.documentElement.scrollHeight || document.body.scrollHeight
					
		     	})
		   	},
		   	selectImage(){
		   		var data = document.getElementById("uploads").click();
		   	},
		   	handleFile(file){
		   		console.log(file)
		   	},
		   	onOversize(file){
		   		Toast('文件大小不能超过 100kb');
		   	},
		   	afterRead(file){
//		   		console.log(file.content)
				var formData = new FormData();
				formData.append('file', file.file)
				this.$Request_post(this.$AXIOS_URL + "/v1/api/chat/uploads", formData).then(res => {
					console.log(res)
					var data = {
						openId: this.ChatContent.openId, 
						correlationId: this.ChatContent.friendId,
						command: "sendMessage",
						msgs:{ openId:this.ChatContent.openId, msg:res.data.filename, type:"Images" }
					}
					console.log(data);
					this.$socket.emit('sendMessage', JSON.stringify(data));
					this.scrollToBottom()
				})
		   	}
		},
		components: {
			Uploader, Toast
		}
	}
</script>

<style lang="scss" scoped>
	.van-uploader__upload{
		background-color:#EEF0F4;
	}

</style>