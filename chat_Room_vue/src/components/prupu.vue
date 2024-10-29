<template>
	<div class="home">
		<!-- 首先将要过渡的元素用transition包裹，并设置过渡的name -->
		<transition name="mybox">
			<div class="box" v-show="boxShow" style="display: flex;">123</div>
		</transition>
	</div>
</template>

<script>
	export default {
		name: "propu",
		data() {
			return {

			}
		},
		mounted(){
			console.log(this.boxShow)
		},
		props: {
			boxShow:{
				type:Boolean
			}
		}
	}
</script>

<style lang="scss" scoped>
	.box {
		height: 500px;
		background-color: rgb(245, 224, 224);
		overflow: hidden;
	}
	/* 给过渡的name加样式 */
	
	.mybox-leave-active {
		transition: all 1s ease;
	}
	.mybox-enter-active {
		transition: all 1s ease;
	}
	
	.mybox-leave-active,
	.mybox-enter {
		/*opacity: 0;*/
		height: 0px !important;
	}
	.mybox-enter-active {
		height: 500px;
	}
</style>