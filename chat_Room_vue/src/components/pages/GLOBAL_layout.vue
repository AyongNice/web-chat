<template>
	<div>
		<!--子页-->
		<router-view/>
		<!--导航栏-->
		<div>
			<layou></layou>
		</div>
	</div>
</template>

<script>
	import layou from '@/components/pages/GLOBAL_navigation_bar';
	export default{
		name:"GLOBAL_layou",
		data(){
			return{
				
			}
		},
		mounted(){
			
		},
		methods:{
			
		},
		components:{
			layou
		}
	}
</script>

<style>
</style>