<template>
	<div style="background: #F0F2F5; text-align: center; left: 0;right: 0;" >
		<!--<span style="font-size: 13px; color: #1989FA;" @click="goSkip('privacy')">&lt;&lt;PetsFree软件许可及服务协议&gt;&gt;</span><br />
		<span style="font-size: 13px; color: #1989FA;" @click="goSkip('privacy')">&lt;&lt;PetsFree隐私保护指引&gt;&gt;</span><br />-->
		<span style="font-size: 12px; color: #666;">© mychen.vip 版权所有</span><br />
		<span style="font-size: 12px; color: #666;">注：使用此项目时如造成各种损失与作者无关、请知悉~</span><br />
		<span style="font-size: 12px; color: #666;">如有侵权或者软件著作问题等请与我取得联系</span><br />
		<a style="font-size: 12px; color: #666;text-decoration:none;" href="#">邮箱：ayongnice@163.com</a>
	</div>
</template>

<script>
	export default{
		name:'copyright',
		data(){
			return{
				
			}
		},
		methods:{
			/**
			 * 跳转
			 * 1.需携带路由name参数
			 * 2.次级页面需要params参数则添加第二参数（次级页面不需要params也可不传）
			 */
			goSkip(routersName, paramsVal) {
				console.log("跳转到" + routersName + "页,携带" + paramsVal + "参数")
				this.$router.push({
					name: routersName,
					params: {
						name: paramsVal
					}
				})
			},
		},
		components:{
			
		}
	}
</script>

<style>
	
</style>