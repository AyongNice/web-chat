<template>
	<div>
		<!--
        	作者：ayongnice@163.com
        	时间：2020-10-13
        	描述：头部导航栏
        -->
		<div style="height: 44px; width: 100%; background:#FFFFFF; position: fixed; z-index: 999;">
			<van-row>
				<van-col span="3">
					<van-icon style="margin: 10px; " size="24px" name="arrow-left" @click="()=>this.$router.go(-1)"/>
				</van-col>
				<van-col span="16">
					<span style="font-size: 16px; display: flex; justify-content: center; line-height: 44px;">新好友</span>
				</van-col>
				<van-col span="5">
					<span style="font-size: 14px; display: flex; justify-content: center; line-height: 44px;" @click="()=>this.$router.push({name:'add_Friend'})">
						添加朋友
					</span>
				</van-col>
			</van-row>
		</div>
		<div style="height: 54px;"></div>
		
		<div style="width: 100%; height: 100%; background: #FFFFFF;">
			<div style="width: 92%; height: 46px; margin-left: 4%; border-bottom: 0.1px solid #8C99A0; padding: 4px;" v-for="(item,index) in applyList" @click="lock_Friend_details(item)" :key="index">
				<van-row>
					<van-col span="4">
						<img style="width: 38px; height: 38px; margin-top: 4px; border-radius: 4px;" :src="item.avatar" />
					</van-col>
					<van-col span="16">
						<span style="display: flex; font-weight: bold; margin-top: 4px;">{{item.username}}</span>
						<span style="display: flex; font-size: 12px; color: #8C99A0;">请求添加你为好友</span>
					</van-col>
					<van-col span="4">
						<span style="font-size: 14px; line-height: 44px; color: #8C99A0;">{{item.status==0?'待同意':item.status==1?'已同意':'已拒绝'}}</span>
					</van-col>
				</van-row>
			</div>
		</div>
		<!--<van-divider />-->
		
	</div>
</template>

<script>
	import { Divider } from 'vant';

	export default{
		name:"",
		data(){
			return{
				applyList:this.$store.state.Apply_Friend_List
			}
		},
		mounted(){
			console.log(111,this.$store.state.Apply_Friend_List)
		},
		methods:{
			lock_Friend_details(item){
				console.log(item)
				//在vuex中管理状态，使用session，避免页面刷新数据消失，
				sessionStorage.setItem('Add_Friend_Information',JSON.stringify(item));	
				//commit一次，再次让vuex状态重新获取一次值对象。
				this.$store.commit('setAdd_Friend_Information');
				this.$router.push({name:"add_Friend_Information"})
			}
		},
		components:{Divider}
	}
</script>

<style lang="scss" scoped>

</style>