<template>
	<div>
		<!--
        	作者：ayongnice@163.com
        	时间：2024-10-05
        	描述：头部导航栏
        -->
		<div style="height: 44px;width: 100%; background:#FFFFFF; position: fixed; z-index: 999;">
			<van-row>
				<van-col span="4">
					<van-icon style="margin: 10px; " size="24px" name="arrow-left" @click="()=>this.$router.go(-1)" />
				</van-col>
				<van-col span="16">
					<span style="font-size: 16px; display: flex; justify-content: center; line-height: 44px;">设置个性签名</span>
				</van-col>
				<van-col span="4">
					<span style="font-size: 16px; display: flex; justify-content: center; line-height: 44px;" @click="updateSignature">保存</span>
				</van-col>
			</van-row>
		</div>
		<div style="height: 54px;"></div>
		
		<!--
        	作者：ayongnice@163.com
        	时间：2024-10-05
        	描述：编辑个性签名
        -->
		<div style="width: 100%; height: 100%;">
			<div style="height: 100%; background: #ffffff; border-bottom: 1px solid #eaeaea;">
				<van-row>
					<van-col span="24">
						<textarea v-model="signature" maxlength="80" autofocus="autofocus" style="height: 100px; font-size: 14px; width: 88%; padding: 10px 20px; border:1px;" />
						<span :style="textLength==0?'float: right; top: -20px; margin: 15px; font-size: 12px; color: red;':'float: right; top: -20px; margin: 15px; font-size: 12px; color: darkgray;'">{{textLength}}/80字</span>
					</van-col>
				</van-row>
			</div>
		</div>
	</div>
</template>

<script>
	import { Toast } from 'vant';
	export default{
		name:"update_signature",
		data(){
			return{
				signature:this.$ls.get('userInfos').signature,
				textLength:this.$ls.get('userInfos').signature==null?80:this.$ls.get('userInfos').signature.length==0?80:80-this.$ls.get('userInfos').signature.length
			}
		},
		mounted(){
			
		},
		methods:{
			updateSignature(){
				var data = {
					signature: this.signature
				}
				this.$Request_put(this.$AXIOS_URL + "/v1/api/user/users", data).then(res => {
					console.log(res)
					if(res.data!=null){
						Toast.success('修改成功');
						this.$ls.set("userInfos", res.data, 60 * 60 * 1000 * 6)
					}
				})
			}
		},
		components:{
			Toast
		},
		watch:{
			signature(val){
				this.textLength = 80-val.length
			}
		}
	}
</script>

<style lang="scss" scoped>
</style>