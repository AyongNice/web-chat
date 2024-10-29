<template>
	<div style="">
		<!--
        	作者：ayongnice@163.com
        	时间：2020-10-19
        	描述：头部导航栏
        -->
		<div style="height: 44px;width: 100%; background:#ffffff; position: fixed; z-index: 999;">
			<van-row>
				<van-col span="4">
					<van-icon style="margin: 10px; " size="24px" name="arrow-left" @click="()=>this.$router.go(-1)" />
				</van-col>
				<van-col span="16">
					<span style="font-size: 16px; display: flex; justify-content: center; line-height: 44px;">
						胖友圈
					</span>
				</van-col>
				<van-col span="4">
					<van-icon style="display: flex; justify-content: center; line-height: 44px;" size="26" name="photo-o" @click="()=> this.$refs.redact.onShow()"/>
				</van-col>
			</van-row>
		</div>
		<div style="height: 44px;"></div>
		
		
		<div style="width: 100%; height: 100%;">
			 <div style="width: 92%; height: 100%; margin-left: 4%; padding: 0px 0px 20px 0px; margin-bottom: 10px; border-bottom:1px solid #8C99A0;" v-for="(item,index) in circleList" :key="index">
			 	<div style="display: flex;">
			 		<div style="height: 100%;">
			 			<img style="margin-top: 10px; width: 35px; height: 35px;" :src="item.circleDetails==null?item.avatar:item.circleDetails.avatar" />
				 	</div>
				 	<div style="height: 100%;">
				 		<span style="padding: 10px 10px 2px 10px; display: flex; font-size: 16px; font-weight: bold;" v-if="item.note!=null">{{item.note}}</span>
				 		<span style="padding: 10px 10px 2px 10px; display: flex; font-size: 16px; font-weight: bold;" v-if="item.username">{{item.username}}</span>
				 		<span style="padding: 10px 10px 2px 10px; display: flex; font-size: 16px; font-weight: bold;" v-if="item.note==null&&item.circleDetails!=null">{{item.circleDetails.username}}</span>
				 		<span style="margin-left: 10px; display: flex; font-size: 14px;">{{item.circleDetails==null?item.message:item.circleDetails.message}}</span>
				 	</div>
			 	</div>
			 	<div style="display: flex; margin-bottom: 10px;">
			 		<div style="height: 100%;">
			 			<div style="width: 35px;"></div>
			 		</div>
			 		<div style="float: left;">
			 			<img style="margin-left: 10px; margin-top: 6px; width: 80px; height: 80px;" :src="item2" v-for="(item2,index) in item.circleDetails==null?item.illustration:item.circleDetails.illustration" @click="lockCircleImg(index,item.circleDetails==null?item.illustration:item.circleDetails.illustration)"/>
			 		</div>
			 	</div>
			 	<span class="time" style="font-size: 12px; margin-left: 45px;">{{item.circleDetails==null?item.createTime:item.circleDetails.createTime|formatDate}}</span>
			 
			 	
			 </div>
		</div>
		
		<!--
        	作者：ayongnice@163.com
        	时间：2020-10-19
        	描述：发布朋友圈
        -->
		<redactCircle ref='redact'></redactCircle>
	</div>
</template>

<script>
	import redactCircle from '@/components/popups/redactCircle'
	import { Divider, ImagePreview } from 'vant';
	export default{
		name:'circle',
		data(){
			return {
				circleList:[]
			}
		},
		mounted(){//item.circleDetails==null?item.createTime:item.circleDetails.createTime
			
//			window.addEventListener('scroll', this.listenerFunction, true)
			console.log(this.$store.state.Friend_List)
			this.$Request_get(this.$AXIOS_URL + "/api/circle/findByfriendCircle").then(res => {
				console.log(res);
				if(this.circleList.length<=0){
					this.circleList = res.data
				}else{
					this.circleList = this.circleList.concat(res.data)
				}
				console.log(this.circleList);
			})
			this.$Request_get(this.$AXIOS_URL + "/api/circle/findByMeCircle").then(res => {
				console.log(res);
				if(this.circleList.length<=0){
					this.circleList = res.data
				}else{
					this.circleList = this.circleList.concat(res.data)
				}
				console.log(this.circleList);
			})
			var that = this;
			setTimeout(function(){
				console.log("heihh")
				that.circleList.sort(function(a,b){return a.createTime!=null?a.createTime:a.circleDetails.createTime<b.createTime!=null?b.createTime:b.circleDetails.createTime?1:-1})
			},500)
			
		},
		methods:{
			listenerFunction(e) {
   				var scr = document.documentElement.scrollTop || document.body.scrollTop; // 向上滚动的那一部分高度
				var clientHeight = document.documentElement.clientHeight; // 屏幕高度也就是当前设备静态下你所看到的视觉高度
				var scrHeight = document.documentElement.scrollHeight || document.body.scrollHeight; // 整个网页的实际高度，兼容Pc端
//				console.log(scr+clientHeight)
//				console.log(scrHeight)
				if((scr + clientHeight) >= scrHeight){
					console.log("到底了")
				}
			},
			lockCircleImg(index,arr){
				console.log(index)
				console.log(arr)
				ImagePreview({
				    images: arr,
				    startPosition: index,
				});
			}
		},
		components:{
			redactCircle, Divider, ImagePreview
		}
	}
</script>

<style>
</style>