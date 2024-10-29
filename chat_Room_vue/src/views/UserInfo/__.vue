<template>
	<div style="width: 100%; height: 90vh;; background: rgb(245, 224, 224);">
		<div style="width: 100%;">
			<div style="width: 80%; float: left; margin-top:50px; height: 300px; margin-left: 10%; border:0px solid black;">
				<div v-for="(item,i) in award" :key="i" :class="item.id==index?'indexBox':'box'" @click="item.id==5?go():to()">
					{{item.name}}
				</div>
			</div>
		</div>
		
		<!--<button @click="()=>index = index==9?index=1:index+=1 ">确定</button>-->
	</div>
</template>

<script>
	export default{
		name:"userInfo",
		data(){
			return{
				boxShow:false,
				award:[
				    { id: 1, name: "谢谢惠顾", min: 70, max: 100 },
				    { id: 2, name: "5欢乐豆", min: 50, max: 70 },
				    { id: 3, name: "10欢乐豆", min: 25, max: 40 },
				    { id: 4, name: "15欢乐豆", min: 15, max: 25 },
				    { id: 5, name: "立即开始", min: 0, max: 0 },
				    { id: 6, name: "35欢乐豆", min: 10, max: 15 },
				    { id: 7, name: "50欢乐豆", min: 3, max: 10 },
				    { id: 8, name: "200欢乐豆", min: 0, max: 3 },
				    { id: 9, name: "谢谢惠顾", min: 40, max: 50 }
				],
				index:0
			}
		},
		mounted(){
			
		},
		methods:{
			go(){
				var that = this
				var SetInterval = setInterval(function() {
					if(that.index==4){
						that.index = 6
					}else{
						if(that.index==9){
							that.index = 1
						}else{
							that.index += 1
						}
					}
				}, 200)
				setTimeout(()=>{
					var data = that.mathData(that.award)
					console.log(data)
					var len = data.id
					clearInterval(SetInterval)
					var SetInterval2 = setInterval(function() {
						if(len==that.index){
							clearInterval(SetInterval2)
							setTimeout(()=>{
								alert(that.award[that.award.map(x => x.id).indexOf(that.index)].name!="谢谢惠顾"?"恭喜获得：" + that.award[that.award.map(x => x.id).indexOf(that.index)].name:"谢谢惠顾")
							},500)
						}else{
							if(that.index==4){
								that.index = 6
							}else{
								if(that.index==9){
									that.index = 1
								}else{
									that.index += 1
								}
							}
						}
					}, 200)
				},6000)
			},
			mathData(data){
				var rag = Math.ceil(Math.random() * 100);
				console.log(rag)
				for (var i = 0; i < data.length; i++) {
					if (rag >= data[i].min && rag < data[i].max) {
					    return data[i]
					}
				}
			},
			to(){
				console.log("不是摇奖区域")
			}
		},
		components:{
			
		}
	}
</script>

<style lang="scss" scoped>
	.box{
		float: left; width: 32%; margin: 0px 4px 4px 0px; height: 100px; border: 0px solid black; text-align: center; line-height: 100px;background: #FFFFFF;
	}
	.indexBox{
		float: left; width: 32%; margin: 0px 4px 4px 0px; height: 100px; border: 0px solid black; text-align: center; line-height: 100px; background: yellow;
	}
</style>