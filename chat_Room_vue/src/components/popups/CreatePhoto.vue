<template>
	<div>
		<van-popup v-model="show" :overlay="true" position="bottom" :style="{ height: '100%' }">
			<!--
	        	作者：ayongnice@163.com
	        	时间：2024-10-05
	        	描述：头部导航栏
	        -->
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

			<div style="width: 100%; height: 200px; border: 0px solid black;">
				<input style="padding: 6px 16px; width: 90%; height: 50px; font-size: 18px; font-weight: bold; border: 0px;" v-model="photo_name" placeholder="填写相册名称" maxlength="15" autofocus="autofocus" />
				<div style="display: flex; justify-content: space-between;">
					<span style="padding: 6px 16px; font-size: 14px;">权限</span>
					<!--<span><van-switch v-model="checked" /></span>-->
					<select style="width: 100px; float: right; margin-right: 10px; border-radius: 2px;" v-model="jurisdiction">
						<option :value="item.value" v-for="(item,index) in options">{{item.text}}</option>
					</select>
				</div>
				<div style="width: 90%; margin-left: 5%; margin-top: 60px;">
					<van-button type="primary" block @click="Finish">完成</van-button>
				</div>
			</div>
		</van-popup>
	</div>
</template>

<script>
	import { randomVariable } from '@/utils/utils'
	import { Button } from 'vant';
	export default {
		name: "CreatePhoto",
		data() {
			return {
				show: false,
				checked: false,
				photo_name: "",
				options: [{
					text: "完全公开",
					value: "public"
				}, {
					text: "仅朋友可见",
					value: "friend"
				}, {
					text: "私密",
					value: "private"
				}],
				jurisdiction: 'public'
			}
		},
		mounted() {

		},
		methods: {
			onShow() {
				this.show = true
			},
			Finish() {
				var data = {
					createPhoto:{
						jurisdiction: this.jurisdiction, photo_name: this.photo_name, imgs: []
					}
				}
				this.$Request_post(this.$AXIOS_URL + "/v1/api/user/photo", data).then(res => {
					console.log(res)
					this.$ls.set("userInfos", res.data, 60 * 60 * 1000 * 6)
					this.photo_name=""
					this.show = false
				})
			}
		},
		components: {
			Button
		}
	}
</script>

<style lang="scss" scoped>

</style>