<template>
	<Global-Layout>
		<transition name="page-transition">
	      <router-view/>
	    </transition>
    </Global-Layout>
</template>

<script>
//	import {get_apply_make_friends_msg} from '@/utils/callbackMsg';
	import GlobalLayout from '@/components/pages/GLOBAL_layout';
	export default{
		name:'BasicLayout',
		data(){
			return{
				keepAlive:this.$route.meta.keepAlive
			}
		},
		mounted(){
//  		console.log(this.$route)
    	},
    	
		components:{
			GlobalLayout
		},
		watch:{
//			'$route' (to, from) {//关于浏览器自带back回退时，导航栏状态不变的解决方式，监听路由，当路由发生改变时重新赋值给选择器
//		      	console.log(this.$ls.get("userInfotoken"))
//		      	if(this.$ls.get("userInfotoken")==null){
//		      		this.$router.push({name:'login'})
//		      	}
//		 	}
		}
	}
</script>

<style>
</style>