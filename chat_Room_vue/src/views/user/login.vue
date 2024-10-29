<template>
  <div class="container">
    <div style="width: 100%; height: 100%;">
      <div
        style="display: flex; justify-content: center; margin-top: 50px; margin-bottom: 50px;"
      >
        <img
          style="width: 120px; height: 120px;"
          src="../../../build/logo.png"
        />
      </div>
      <div>
        <van-form @submit="onSubmit">
          <van-field
            v-model="username"
            style="background: #FAFAFA;"
            name="username"
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
            name="password"
            label="密码"
            placeholder="密码"
            :rules="[{ required: true, message: '请填写密码' }]"
          />
          <span
            style="font-size: 12px; float: right; margin-right: 6px;padding: 10px;"
            @click="register"
            >没有账户前往注册?</span
          >
          <div style="margin: 16px;">
            <van-button block type="primary" native-type="submit"
              >Login</van-button
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
import { Button, Form, Toast } from "vant";
export default {
  name: "login",
  data() {
    return {
      username: "",
      password: ""
    };
  },
  mounted() {
    console.log("13");
  },
  methods: {
    asyncValidator(val) {
      return true;
      var reg = /^[0-9a-zA-Z_.-]+[@][0-9a-zA-Z_.-]+([.][a-zA-Z]+){1,2}$/;
      if (reg.test(val)) {
        return true;
      } else {
        return false;
      }
    },
    onSubmit(values) {
      console.log("submit", values);
      this.$Request_post(this.$AXIOS_URL + "/api/login", values).then(res => {
        if (res.code == 200) {
          window.sessionStorage.setItem("userInfos", JSON.stringify(res.data));
          Toast.loading({
            message: "登陆成功",
            forbidClick: true,
            type: "success"
          });
          this.$router.push({ name: "home" });
        }
      });
    },
    register() {
      this.$router.push({ name: "register" });
    }
  },
  components: {
    Button,
    Form,
    Toast,
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
  background: #f0f2f5
    url("https://gw.alipayobjects.com/zos/rmsportal/TVYTbAXWheQpRcWDaDMu.svg")
    no-repeat center 110px;
  background-size: 100%;
}
</style>
