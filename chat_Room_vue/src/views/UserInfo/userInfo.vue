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
					<span style="font-size: 16px; display: flex; justify-content: center; line-height: 44px;">编辑资料</span>
				</van-col>
				<van-col span="4">

				</van-col>
			</van-row>
		</div>
		<div style="height: 54px;"></div>

		<div style="width: 100%; height: 100%;">
			<!--
            	作者：ayongnice@163.com
            	时间：2024-10-05
            	描述：头像
            -->
			<div style="border-bottom: 1px solid #eaeaea;">
				<input type="file" id="updateAvatar" style="display: none" multiple @change="handleFile" />
				<van-cell title="头像" @click="uploadAvatar" border>
					<!-- 使用 right-icon 插槽来自定义右侧图标 -->
					<template #right-icon>
						<img :src="userInfo.avatar" style="width: 30px; height: 30px; border-radius: 50px;" />
					</template>
				</van-cell>
			</div>
			<!--
            	作者：ayongnice@163.com
            	时间：2024-10-05
            	描述：昵称
            -->
			<div class="userInfo_list_box" @click="()=>this.$router.push({name:'update_username'})">
				<van-row>
					<van-col span="5">
						<span class="userInfo_list_title">昵称</span>
					</van-col>
					<van-col span="16">
						<span class="userInfo_list_content">{{userInfo.username}}</span>
					</van-col>
					<van-col span="3">
						<van-icon class="userInfo_list_arrow" name="arrow" />
					</van-col>
				</van-row>
			</div>
			<!--
            	作者：ayongnice@163.com
            	时间：2024-10-05
            	描述：个性签名
            -->
			<div class="userInfo_list_box" @click="()=>this.$router.push({name:'update_signature'})">
				<van-row>
					<van-col span="5">
						<span class="userInfo_list_title">签名</span>
					</van-col>
					<van-col span="16">
						<span class="userInfo_list_content2">{{userInfo.signature==null?'':userInfo.signature}}</span>
					</van-col>
					<van-col span="3">
						<van-icon class="userInfo_list_arrow" name="arrow" />
					</van-col>
				</van-row>
			</div>
			<!--
            	作者：ayongnice@163.com
            	时间：2024-10-05
            	描述：性别
            -->
			<div class="userInfo_list_box" @click="Show_Genders_options">
				<van-row>
					<van-col span="5">
						<span class="userInfo_list_title">性别</span>
					</van-col>
					<van-col span="16">
						<span class="userInfo_list_content">{{userInfo.genders}}</span>
					</van-col>
					<van-col span="3">
						<van-icon class="userInfo_list_arrow" name="arrow" />
					</van-col>
				</van-row>
			</div>
			<GendersPopups ref="genders" v-on:callBackGenders="callBackGenders" :GendersText="userInfo.genders"></GendersPopups>
			
			<!--
            	作者：ayongnice@163.com
            	时间：2024-10-05
            	描述：生日
            -->
			<div class="userInfo_list_box" @click="Show_Birthday_options">
				<van-row>
					<van-col span="5">
						<span class="userInfo_list_title">生日</span>
					</van-col>
					<van-col span="16">
						<span class="userInfo_list_content">{{this.$moment(userInfo.birthday).format('YYYY-MM-DD')}}</span>
					</van-col>
					<van-col span="3">
						<van-icon class="userInfo_list_arrow" name="arrow" />
					</van-col>
				</van-row> 
			</div>
			<BirthdayPopups ref="birthday" v-on:callBackBirthday="callBackBirthday" :defaultBirthday="userInfo.birthday"></BirthdayPopups>
			
			<!--
            	作者：ayongnice@163.com
            	时间：2024-10-05
            	描述：城市
            -->
            <div class="userInfo_list_box" @click="Show_City_options">
				<van-row>
					<van-col span="5">
						<span class="userInfo_list_title">城市</span>
					</van-col>
					<van-col span="16">
						<span class="userInfo_list_content3" v-for="(item,index) in userInfo.city" :key="index">{{item.name}}&nbsp;</span>
					</van-col>
					<van-col span="3">
						<van-icon class="userInfo_list_arrow" name="arrow" />
					</van-col>
				</van-row> 
			</div>
			<CityPopups ref="city" v-on:callBackCity="callBackCity"></CityPopups>
			
		</div>
	</div>
</template>

<script>
	import { Col, Row, Cell, CellGroup, Toast, Field } from 'vant';
	import GendersPopups from '@/components/popups/GendersPopups'
	import BirthdayPopups from '@/components/popups/BirthdayPopups'
	import CityPopups from '@/components/popups/CityPopups'
	export default {
		name: "userInfo",
		data() {
			return {
				userInfo: this.$ls.get('userInfos'),
			}
		},
		mounted() {
			console.log(this.$ls.get('userInfos'));
		},
		methods: {
			uploadAvatar() { 
				var data = document.getElementById("updateAvatar").click();
			},
			handleFile(e) {
				console.log(e)
				const isJpgOrPng = e.target.files[0].type === "image/jpeg" || e.target.files[0].type === "image/png" || e.target.files[0].type === "image/jpg"
				if(isJpgOrPng) {
					//					Toast('上传图片类型错误!仅支持PNG，JPG图片');
					if(e.target.files[0].size < 2 * 1024 * 1024) {
						var file1 = e.target.files[0]
						var formData = new FormData();
						formData.append('file', file1)
						Toast.loading({
							mask: true,
							message: '图片上传中！',
							duration: 50000
						});
						this.$Request_post(this.$AXIOS_URL + "/v1/api/files/fileUpload?command=Avatar&email=" + this.userInfo.email, formData).then(res => {
							console.log(res)
							if(res.data!=null){
								Toast.success("图片上传成功！")
								Toast.clear();
								var data = {
									avatar: res.data.fileurl
								}
								this.$Request_put(this.$AXIOS_URL + "/v1/api/user/users", data).then(res => {
									console.log(res)
									this.$ls.set("userInfos", res.data, 60 * 60 * 1000 * 6)
									var that = this
									setTimeout(()=>{
										that.$router.go(-1)
									},800)
								})
							}
						})
					} else {
						Toast('图片大小超过2M!');
					}
				} else {
					Toast('上传文件类型错误!');
				}
			},
			callBackGenders(value){
				console.log(value)
				var data = {
					genders: value
				}
				this.$Request_put(this.$AXIOS_URL + "/v1/api/user/users", data).then(res => {
					console.log(res)
					this.userInfo.genders=value
					this.$ls.set("userInfos", res.data, 60 * 60 * 1000 * 6)
					this.$router.go(-1)
				})
			},
			Show_Genders_options(){
				this.$refs.genders.onShow()
			},
			callBackBirthday(value){
				var data = {
					birthday: value
				}
				this.$Request_put(this.$AXIOS_URL + "/v1/api/user/users", data).then(res => {
					console.log(res)
					this.userInfo.birthday = this.$moment(value).format('YYYY-MM-DD')
					this.$ls.set("userInfos", res.data, 60 * 60 * 1000 * 6)
				})
			},
			Show_Birthday_options(){
				this.$refs.birthday.onShow()
			},
			callBackCity(value){
				console.log(value)
				var data = {
					city: value
				}
				this.$Request_put(this.$AXIOS_URL + "/v1/api/user/users", data).then(res => {
					console.log(res)
					this.userInfo.city = value
					this.$ls.set("userInfos", res.data, 60 * 60 * 1000 * 6)
					this.$router.go(-1)
				})
			},
			Show_City_options(){
				this.$refs.city.onShow()
			}
			
		},
		components: {
			Col,
			Row,
			Cell,
			CellGroup,
			Toast,
			Field,
			GendersPopups,
			BirthdayPopups,
			CityPopups
		}
	}
</script>

<style lang="scss" scoped>
.userInfo_list_box{
	height: 44px; background: #ffffff; border-bottom: 1px solid #eaeaea;
	.userInfo_list_title{
		font-size: 14px; padding: 10px 16px; line-height: 44px;
	}
	.userInfo_list_content{
		font-size: 14px; padding: 10px 0px; line-height: 44px;
	}
	.userInfo_list_content2{
		font-size: 14px; 
		line-height: 44px; 
		font-size: 14px; 
		overflow: hidden; 
		text-overflow: ellipsis;
		display: -webkit-box;
		/*! autoprefixer: off*/
		-webkit-box-orient: vertical;
 		/* autoprefixer: on */
		-webkit-line-clamp: 1;
	}
	.userInfo_list_content3{
		font-size: 14px; 
		line-height: 44px; 
		font-size: 14px; 
		float: left;
	}
	.userInfo_list_arrow{
		display: flex; justify-content: center; line-height: 44px;
	}
}
</style>