<template>
	<div>
		<!--<img style="width: 80px; height: 80px;" v-for="(item,index) in 5" :key="index" :src="getImageDom(index,'123')"/>-->
		<!--{{getImageDom(1,'123')}}-->
		<!--{{getImgs(10)}}-->
		<img :src="getImageDom(1,'123')" />
		<div>
			<input id="message" v-model="msg" />
		</div>
		
		<button @click="but">lock</button>
	</div>
</template>

<script>
	import {msg} from "@/utils/utils"
	export default{
		name:"test",
		data(){
			return{
//				websock:{},
//				status:false,
//				msg:""
			}
		},
		computed:{
			getImageDom(){
				return function(id,data){
					console.log("触发")
					console.log(id)
					console.log(data)
				}
			}
		},
		mounted(){
			console.log()
//			this.$socket.emit('login', JSON.stringify({openId:"1", nick_Name:"nick_Chen"}))
		},
		methods:{
			aaa(data){
				console.log(data)
			},
			getImgs(id){
				console.log("执行")
				console.log(id)
			},
			but(){
				console.log(this.msg)
				this.msg=""
				var dom =document.getElementById('message')
				dom.value=""
			}
		},
		sockets:{
			users(data){//服务器反馈在线人数
				console.log(data)
			},
			// 连接
			connect(){
		        console.log('socket connected from Page')
		    },
		   	customEmit: function (data) {
            	console.log('this method was fired by the socket server. eg: io.emit("customEmit", data)')
            }
		}
	}
</script>

<style lang="scss" scoped>
</style>