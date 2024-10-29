<template>
	<div class="container">
	  <div style="width: 100%; height: 100%;">
		<div
		  style="display: flex; justify-content: center; margin-top: 50px; margin-bottom: 50px;"
		>
		  <img style="height: 120px;" src="@/assets/weixin.png" />
		</div>
		<div>
		  <van-form @submit="onSubmit">
			<van-field
			  v-model="email"
			  style="background: #FAFAFA;"
			  name="邮箱"
			  label="邮箱"
			  placeholder="邮箱"
			  :rules="[
				{
				  required: true,
				  validator: asyncValidator,
				  message: '请填写正确的邮箱地址'
				}
			  ]"
			/>
			<van-field
			  v-model="password"
			  style="background: #FAFAFA;"
			  type="password"
			  name="密码"
			  label="密码"
			  placeholder="密码"
			  :rules="[{ required: true, message: '请填写密码' }]"
			/>
			<van-field
			  v-model="auth_code"
			  center
			  clearable
			  label="邮箱验证码"
			  placeholder="请输入邮箱验证码"
			>
			  <!--placeholder="请输入邮箱验证码" name="验证码"
						  :rules="[{ required: true, message: '请填写验证码' }]"-->
			  <template #button>
				<van-button
				  :disabled="button_auth.button_show"
				  size="small"
				  type="primary"
				  @click.stop.prevent="sendEmail($event)"
				  >{{ button_auth.textdesc }}</van-button
				>
			  </template>
			</van-field>
			<span
			  style="font-size: 12px; float: right; margin-right: 6px;padding: 10px;"
			  @click="login"
			  >使用已有账号登陆</span
			>
			<div style="margin: 16px;">
			  <van-button block type="primary" native-type="submit"
				>注册</van-button
			  >
			</div>
		  </van-form>
		</div>
	  </div>
	  <copyright></copyright>
	</div>
  </template>
  
  <script>
  import copyright from "@/components/pages/copyright";
  import { Button, Form } from "vant";
  export default {
	name: "login",
	data() {
	  return {
		email: "",
		password: "",
		auth_code: "",
		button_auth: {
		  button_show: false,
		  textdesc: "发送验证码"
		}
	  };
	},
	mounted() {
	  console.log("13");
	},
	methods: {
	  asyncValidator(val) {
		var reg = /^[0-9a-zA-Z_.-]+[@][0-9a-zA-Z_.-]+([.][a-zA-Z]+){1,2}$/;
		if (reg.test(val)) {
		  return true;
		} else {
		  return false;
		}
	  },
	  onSubmit(values) {
		console.log("submit", values);
		var data = {
		  email: this.email,
		  password: this.password,
		  auth_code: this.auth_code
		};
		this.$Request_post(this.$AXIOS_URL + "/api/user/register", data).then(
		  res => {
			console.log(res);
			var that = this;
			setTimeout(() => {
			  that.$router.push({
				name: "login"
			  });
			}, 3000);
		  }
		);
	  },
	  login() {
		this.$router.push({
		  name: "login"
		});
	  },
	  sendEmail(e) {
		console.log(e);
		this.$Request_post(this.$AXIOS_URL + "/api/send-email", {
		  email: this.email
		}).then(res => {
		  console.log(res);
		  if (res.status == 200) {
			this.button_auth.button_show = true;
			this.button_auth.textdesc = 60;
			var that = this;
			var SetInterval = setInterval(function() {
			  if (that.button_auth.textdesc == 0) {
				that.button_auth.textdesc = "发送验证码";
				that.button_auth.button_show = false;
				clearInterval(SetInterval);
				return;
			  } else {
				that.button_auth.textdesc = that.button_auth.textdesc - 1;
			  }
			}, 1000);
		  }
		});
	  }
	},
	components: {
	  Button,
	  Form,
	  copyright
	}
  };
  </script>
  
  <style>
  .container {
	display: flex;
	flex-direction: column;
	height: 100vh;
	overflow: auto;
  
	background-size: 100%;
  }
  </style>
  
  
  
  // WEBPACK FOOTER //
  // register.vue