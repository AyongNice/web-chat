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
					<span style="font-size: 16px; display: flex; justify-content: center; line-height: 44px;">设置昵称</span>
				</van-col>
				<van-col span="4">
					<span style="font-size: 16px; display: flex; justify-content: center; line-height: 44px;" @click="updateUsername">保存</span>
				</van-col>
			</van-row>
		</div>
		<div style="height: 54px;"></div>
		<!--
        	作者：ayongnice@163.com
        	时间：2024-10-05
        	描述：编辑用户昵称
        -->
		<div style="width: 100%; height: 100%;">
			<div style="height: 44px; background: #ffffff; border-bottom: 1px solid #eaeaea;">
				<van-row>
					<van-col span="24">
						<input v-model="username" maxlength="15" autofocus="autofocus" style="height: 40px; width: 100%; padding: 10px 20px; border: 0px;" />
					</van-col>
				</van-row>
			</div>
		</div>
	</div>
</template>

<script>
	import { Col, Row, Toast } from 'vant';
	export default {
		name: "update_username",
		data() {
			return {
				username:this.$ls.get('userInfos').username
			}
		},
		mounted() {

		},
		methods: {
			updateUsername(){
				var data = {
					username: this.username
				}
				this.$Request_put(this.$AXIOS_URL + "/v1/api/user/users", data).then(res => {
					console.log(res)
					if(res.data!=null){
						this.$ls.set("userInfos", res.data, 60 * 60 * 1000 * 6)
						Toast.success("修改成功")
					}
				})
			}
		},
		components: {
			Col,
			Row,
			Toast
		}
	}
</script>

<style lang="scss" scoped>
	
</style>