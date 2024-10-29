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
					<span style="font-size: 16px; display: flex; justify-content: center; line-height: 44px;">我的相册</span>
				</van-col>
				<van-col span="4">

				</van-col>
			</van-row>
		</div>
		<div style="height: 54px;"></div>
		
		<div style="width: 100%; height: 100%;">
			<div class="create_photo_box">
				<div class="photo_item" v-for="(item,index) in photo_album" @click="openPhoto(item)">
					<img :src="photo_album[index].imgs[0] == null ? imageList : photo_album[index].imgs[0].img"/>
					<span>{{item.photo_name}}</span>
				</div>
				<div class="create_photo_item" @click="createPhoto">
					<van-icon class="create_photo_item_icon" size="30" name="photo" color="darkgray"/>
					<span>创建相册</span>
				</div>
			</div>
		</div>
		<createPhoto ref="photo"></createPhoto>
	</div>
</template>

<script>
	
	import createPhoto from '@/components/popups/CreatePhoto'
	export default{
		name:"photo_home",
		data(){
			return{
				photo_album:this.$ls.get('userInfos').photo_album,
				imageList:require('../../../assets/18806809-14BF-485f-9DCC-5254CE6D917D.png'),
			}
		},
		mounted(){
			console.log(this.$ls.get('userInfos'))
			console.log(this.$ls.get('userInfos').photo_album)
		},
		methods:{
			createPhoto(){
				this.$refs.photo.onShow()
			},
			openPhoto(item){
//				console.log(item)
				//在vuex中管理状态，使用session，避免页面刷新数据消失，
				sessionStorage.setItem('Photo_Item_Details',JSON.stringify(item));
				//commit一次，再次让vuex状态重新获取一次值对象。
				this.$store.commit('setPhoto_Item_Details',item)
				this.$router.push({name:"photo_details"})
			}
		},
		components:{
			createPhoto,
		}
	}
</script>

<style lang="scss" scoped>
.create_photo_box{
	width: 92%; margin-left: 5%;
	.photo_item{
		margin: 0% 1.5% 1.5% 0%; width: 48%; height: 130px; border: 0px solid black; float: left; border-radius: 8px;
		img{
			width: 100%; height: 100%; border-radius: 8px;
		}
		span{
			display: flex; justify-content: center; font-size: 14px; color: darkgray; margin-top: -30px;
		}
	}
	.create_photo_item{
		margin: 0% 1.5% 1.5% 0%; width: 48%; height: 130px; background:lemonchiffon; border: 0px solid black; float: left; border-radius: 8px;
		.create_photo_item_icon{
			display: flex; justify-content: center; margin-top: 40px;
		}
		span{
			display: flex; justify-content: center; font-size: 14px; color: darkgray;
		}
	}
}
</style>
