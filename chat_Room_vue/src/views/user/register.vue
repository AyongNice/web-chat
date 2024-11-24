<template>
  <div class="container">
    <div style="width: 100%; height: 100%;">
      <div
        style="display: flex; justify-content: center; margin-top: 50px; margin-bottom: 50px;"
      >
        <img style="height: 120px;" src="@/assets/weixin.png"/>
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
            v-model="userName"
            style="background: #FAFAFA;"
            name="用户名"
            label="用户名"
            placeholder="用户名"
            :rules="[{ required: true, message: '请填写用户名' }]"
          />
          <van-field
            v-model="randomNum"
            center
            clearable
            label="邮箱验证码"
            placeholder="请输入邮箱验证码"
          >

            <template #button>
              <van-button
                :disabled="button_auth.button_show"
                size="small"
                type="primary"
                @click.stop.prevent="sendEmail($event)"
              >{{ button_auth.textdesc }}
              </van-button
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
            >注册
            </van-button
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
import {Button, Form, Toast} from "vant";

export default {
  name: "login",
  data() {
    return {
      email: "1540018883@qq.com",
      password: "",
      randomNum: '',
      userName: '',
      button_auth: {
        button_show: false,
        textdesc: "发送验证码"
      }
    };
  },

  methods: {
    asyncValidator(val) {
      const reg = /^[0-9a-zA-Z_.-]+[@][0-9a-zA-Z_.-]+([.][a-zA-Z]+){1,2}$/;
      if (reg.test(val)) {
        return true;
      } else {
        return false;
      }
    },
    onSubmit() {

      this.$Request_post(this.$AXIOS_URL + "/api/register", {
        email: this.email,
        passWord: this.password,
        userName: this.userName,
        randomNum: this.randomNum
      }).then(
        () => {
          this.login()
        }
      );
    },
    login() {
      this.$router.push({
        name: "login"
      });
    },
    sendEmail(e) {
      this.$Request_get(this.$AXIOS_URL + "/api/email", {
        to: this.email
      }).then(res => {
        Toast(typeof res === "object" ? res.msg : res)
      }).catch(res => {
        console.log(res)
      })

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
