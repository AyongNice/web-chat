<template>
	<div style="background: #FFFFFF; ">
		<div style="height: 30px;"></div>
		<div style="margin-bottom: 10px;">
			<van-cell-group>
				<van-cell title="新朋友" is-link @click="()=>this.$router.push({name:'audit_Friend_apply'})">
					<template #right-icon>
					    <van-tag round type="danger" v-if="count!=0">{{count}}</van-tag>
					    <van-icon style="margin: auto;" size="16" name="arrow" />
					</template>
				</van-cell>
			</van-cell-group>
		</div>
		<van-empty style="height: 80vh;" v-if="friendList==null" description="暂无好友列表" />  
		<div style="display: flex; width: 92%; margin-left: 4%; height: 44px; margin-bottom: 10px;" v-for="(item,index) in friendList" :key="index" @click="lock_friend_information(item)">
			<div style="height: 44px;">
			  	<img style="height: 40px; width: 40px; margin-top: 2px;" :src="item.avatar"/>
			</div>
			<div style="width: 100%; height: 44px; margin-left: 10px; border-bottom: 0.1px solid darkgray;">
			  	<span style="font-size: 16px; font-weight: bold;">{{item.note==null?item.username:item.note}}</span>
			  	<span style="display: flex; font-size: 12px; color: darkgray; margin-top: 4px;">{{item.signature}}</span>
			</div>
		</div>
		
		
	</div>
</template>

<script>
	import { Cell, Toast, Empty, } from 'vant';
	export default{
		name:"contactList",
		data(){
			return{
				count:0,
				friendList:this.$store.state.Friend_List
			}
		},
		mounted(){
			this.$Request_get(this.$AXIOS_URL+'/v1/api/friends/findAddRecords').then(res=>{
				console.log(res);
				if(res.status==200){
					this.count = res.data.length;
					//在vuex中管理状态，使用session，避免页面刷新数据消失，
					sessionStorage.setItem('Apply_Friend_List',JSON.stringify(res.data));	
					//commit一次，再次让vuex状态重新获取一次值对象。
					this.$store.commit('setApply_Friend_List');
				}
			})
//			Toast.loading({
//			  mask: true,
//			  message: "加载好友列表..."
//			});
//			this.$Request_get(this.$AXIOS_URL+'/v1/api/friends/findByFriendList').then(res=>{
//				Toast.clear();
//				console.log(res);
//				if(res.data.length>=1){
//					this.friendList = res.data;
//					//在vuex中管理状态，使用session，避免页面刷新数据消失，
//					sessionStorage.setItem('Friend_List',JSON.stringify(res.data));	
//					//commit一次，再次让vuex状态重新获取一次值对象。
//					this.$store.commit('setFriend_List');
//				}
//			}) 
		},
		methods:{
			lock_friend_information(item){
				console.log(item.friendId)
				//在vuex中管理状态，使用session，避免页面刷新数据消失，
				sessionStorage.setItem('Friend_List_item',JSON.stringify(item));	
				//commit一次，再次让vuex状态重新获取一次值对象。
				this.$store.commit('setFriend_List_item');
				this.$router.push({name:"Friend_Information_"})
			}
		},
		components:{
			Cell, Toast, Empty 
		}
	}
</script>

<style>
</style>