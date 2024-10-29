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
					<span style="font-size: 16px; display: flex; justify-content: center; line-height: 44px;">添加好友</span>
				</van-col>
				<van-col span="4">

				</van-col>
			</van-row>
		</div>
		<div style="height: 54px;"></div>
		
		<!--
        	作者：ayongnice@163.com
        	时间：2020-10-09
        	描述：搜索栏
        -->
		<van-search v-model="searchText" placeholder="请输入搜索关键词" show-action @search="onSearch"> 
			<template #action>
		    	<div @click="onSearch">搜索</div>
			</template>
		</van-search>
		
		<div style="width: 100%; height: 100%;">
			<span style="display: flex;justify-content: center; font-size: 12px; font-weight: bold; margin-top: 30px;">
				我的Room号：{{this.$ls.get('userInfos').email}}
			</span>
		</div>
	</div>
</template>

<script>
	import { Search, Toast  } from 'vant';
	export default{
		name:"add_Friend",
		data(){
			return{
				searchText:""	
			}
		},
		mounted(){
			
		},
		methods:{
			onSearch(values){
				if(typeof values == "object"){
					console.log(this.searchText)
					if(this.$ls.get('userInfos').email!=this.searchText){
						var data = {
							email:this.searchText
						}
						this.$Request_post(this.$AXIOS_URL + "/v1/api/friends/searchEmail", data).then(res => {
							console.log(res)
							if(res.data.message==null){
								//在vuex中管理状态，使用session，避免页面刷新数据消失，
								sessionStorage.setItem('SearchUser',JSON.stringify(res.data));
								//commit一次，再次让vuex状态重新获取一次值对象。
								this.$store.commit('setSearchUser',res.data)
								this.$router.push({name:"friend_Information"})
							}else{
								Toast('无此ID用户');
							}
						})
					}else{
						Toast('不可添加自己为好友');
					}
				}else{
					console.log(values)
					if(this.$ls.get('userInfos').email!=values){
						var data = {
							email:values
						}
						this.$Request_post(this.$AXIOS_URL + "/v1/api/friends/searchEmail", data).then(res => {
							console.log(res)
							if(res.data.message==null){
								//在vuex中管理状态，使用session，避免页面刷新数据消失，
								sessionStorage.setItem('SearchUser',JSON.stringify(res.data));
								//commit一次，再次让vuex状态重新获取一次值对象。
								this.$store.commit('setSearchUser',res.data)
								this.$router.push({name:"friend_Information"})
							}else{
								Toast('无此ID用户');
							}
						})
					}else{
						Toast('不可添加自己为好友');
					}
				}
			},
		},
		components:{
			Search, Toast 
		}
	}
</script>

<style lang="scss" scoped>

</style>