// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from "vue";
import App from "./App";
import router from "./router";

const Vant = require("vant");
//require('vant/lib/index.css')
const Axios = require("axios");
const Storage = require("vue-ls");
const moment = require("moment");

// import Vue from 'vue';
// import Vant from 'vant';
// import 'vant/lib/index.css';
// import Storage from 'vue-ls';
// import Axios from 'axios';
// import moment from 'moment';
// import 'moment/locale/zh-cn';
// import io from 'socket.io-client';

import store from "./store/index.js";
// import SocketIO from 'socket.io-client';


import VEmojiPicker from "v-emoji-picker";

Vue.config.productionTip = false;

Vue.use(VEmojiPicker);

/**
 *axios 请求公共方法
 */
import {
  Request_get,
  Request_post,
  Request_put,
  Request_delete,
  Request_patch
} from "@/utils/utils.js"; //axios请求工具类
Vue.prototype.$Request_get = Request_get; //全局get请求
Vue.prototype.$Request_post = Request_post; //全局post请求
Vue.prototype.$Request_put = Request_put; //全局Put请求
Vue.prototype.$Request_delete = Request_delete; //全局Delete请求
Vue.prototype.$Request_patch = Request_patch; //全局patch请求
Vue.prototype.$AXIOS_URL = "";
Vue.prototype.$moment = moment;

Vue.use(Vant);
import {Toast, Notify} from "vant";

/**
 *axios拦截器
 */
Axios.defaults.headers.post["Content-Type"] = "application/json";
Axios.interceptors.request.use(
  function (config) {
    //	console.log(config.url)
    if (
      !config.url.includes("login") &&
      !config.url.includes("email") &&
      !config.url.includes("sendAuth_codes") &&
      !config.url.includes("register")

    ) {
      config.headers.Authorization = JSON.parse(
        window.sessionStorage.getItem("userInfos") || {}
      ).token;
    }
    return config;
  },
  function (error) {
    console.log(111, error)
    return Promise.resolve(error);
  }
);
Axios.interceptors.response.use(
  function (response) {
    return response.data;
  },
  function (error) {
    console.log(error)
    if (error.toString().includes("401")) {
      router.push("/login");
    }
    return Promise.resolve(error);
  }
);

Vue.config.productionTip = false;

/**
 * ls本地缓存
 */
var options = {
  namespace: "vuejs__", // key prefix
  name: "ls", // name variable Vue.[ls] or this.[$ls],
  storage: "local" // storage name session, local, memory
};
Vue.use(Storage, options);
import "@/permission.js";

console.log("vue-ls", Vue.$ls);

/**
 *socket 连接
 */
//var SetInterval = setInterval(function() {
//	clearInterval(SetInterval)
//},100)

console.log("socket启动连接");

import Moment from "moment";

Vue.filter("formatDate", function (value) {
  return Moment(value).format("YYYY-MM-DD HH:mm:ss");
});
//自动获取焦点
Vue.directive("myfocus", {
  inserted: function (el, binding) {
    //console.log(el)
    el.focus();
  }
});

/* eslint-disable no-new */
new Vue({
  el: "#app",
  router,
  store,
  components: {
    App
  },
  //   sockets: {
  //     get_apply_make_friends_msg(data) {
  //       Notify({ type: "primary", message: data, duration: 10000 });
  //     },
  //     disconnect() {
  //       console.log("Socket 断开");
  //       Toast("您与聊天服务器断开连接!");
  //     }
  //   },
  template: "<App/>"
});
