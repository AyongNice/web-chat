<template>
	<div>
		<div style="width: 100%; height: 50px; background: #FFFFFF; position: fixed; bottom: 0px;">
			<div style="display: flex; text-align: center;">
				<div style="width: 25%;" @click="goRouter('home')">
					<van-icon :name="home.name" size="30" style="line-height: 50px;"/>
				</div>
				<div style="width: 25%;" @click="goRouter('contactList')">
					<van-icon :name="linkman.name" size="30" style="line-height: 50px;"/>
				</div>
				<div style="width: 25%;" @click="goRouter('circle')">
					<van-icon :name="circle.name" size="30" style="line-height: 50px;"/>
				</div>
				<div style="width: 25%;" @click="goRouter('userInfo_Tab')">
					<van-icon :name="user.name" size="30" style="line-height: 50px;"/>
				</div>
			</div>
		</div>
		<div style="height: 50px;"></div>
	</div>
</template>

<script>
	import { Icon } from 'vant';
	export default{
		name:"GLOBAL_navigation_bar",
		data(){
			return{
				home:{name:'chat-o'},
				linkman:{name:'friends-o'},
				circle:{name:'fire-o'},
				user:{name:'manager-o'}
			}
		},
		mounted(){
			if(this.$route.name=="home"){
				this.home.name="chat"
			}
			if(this.$route.name=="contactList"){
				this.linkman.name="friends"
			}
			if(this.$route.name=="circle"){
				this.circle.name="fire"
			}
			if(this.$route.name=="userInfo_Tab"){
				this.user.name="manager"
			}
		},
		methods:{
			goRouter(routers){
				if(this.$route.name!=routers){
					this.$router.push({name:routers})
				}
			}
		},
		watch:{
			'$route' (to, from) {//关于浏览器自带back回退时，导航栏状态不变的解决方式，监听路由，当路由发生改变时重新赋值给选择器
//		      	console.log(to)
		      	if(to.name=="home"){
					this.home.name="chat"
					this.linkman.name="friends-o"
					this.circle.name="fire-o"
					this.user.name="manager-o"
				}
				if(to.name=="contactList"){
					this.home.name="chat-o"
					this.linkman.name="friends"
					this.circle.name="fire-o"
					this.user.name="manager-o"
				}
				if(to.name=="circle"){
					this.home.name="chat-o"
					this.linkman.name="friends-o"
					this.circle.name="fire"
					this.user.name="manager-o"
				}
				if(to.name=="userInfo_Tab"){
					this.home.name="chat-o"
					this.linkman.name="friends-o"
					this.circle.name="fire-o"
					this.user.name="manager"
				}
		 	}
		},
		components:{
			Icon
		}
	}
</script>

<style>
</style>