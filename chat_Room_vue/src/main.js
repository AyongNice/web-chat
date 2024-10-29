// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
//import Vue from 'vue'
import App from './App'
import router from './router'
const Vue = require('vue')
const Vant = require('vant')
//require('vant/lib/index.css')
const Axios = require('axios')
const Storage = require('vue-ls')
const moment = require('moment')
import store from './store/index.js';
import VueSocketIO from 'vue-socket.io'
import SocketIO from 'socket.io-client';
/**
 *axios 请求公共方法 
 */
import { Request_get, Request_post, Request_put, Request_delete, Request_patch } from '@/utils/utils.js'; //axios请求工具类
Vue.prototype.$Request_get = Request_get; //全局get请求
Vue.prototype.$Request_post = Request_post; //全局post请求
Vue.prototype.$Request_put = Request_put; //全局Put请求
Vue.prototype.$Request_delete = Request_delete; //全局Delete请求
Vue.prototype.$Request_patch = Request_patch; //全局patch请求
Vue.prototype.$Vue = Vue;
Vue.prototype.$AXIOS_URL = "http://localhost:8080";
Vue.prototype.$moment = moment

Vue.use(Vant);
import { Toast, Notify, } from 'vant';

Axios.defaults.headers.post["Content-Type"] = "application/json";
Axios.interceptors.request.use(
  function(config) {
    //	console.log(config.url)
    if (
      !config.url.includes("login") &&
      !config.url.includes("send-email") &&
      !config.url.includes("sendAuth_codes")
    ) {
      config.headers.Authorization = JSON.parse(
        window.sessionStorage.getItem("userInfos") || {}
      ).token;
    }
    return config;
  },
  function(error) {
    return Promise.reject(error);
  }
);
Axios.interceptors.response.use(
  function(response) {
    console.log(response);
    if (response.data.message != null && response.data.message != "") {
      if (response.status == 200) {
        Toast.loading({
          type: "success",
          message: response.data.message,
          duration: 3000
        });
      }
    }

    return response.data;
  },
  function(error) {
    if (error.toString().includes("401")) {
      router.push("/login");
    }
    return Promise.reject(error);
  }
);

Vue.config.productionTip = false

/**
 * ls本地缓存
 */
var options = {
  namespace: 'vuejs__', // key prefix
  name: 'ls', // name variable Vue.[ls] or this.[$ls],
  storage: 'local', // storage name session, local, memory
};
Vue.use(Storage, options);
import '@/permission.js';


/**
 *socket 连接
 */
//var SetInterval = setInterval(function() {
//	clearInterval(SetInterval)
//},100)

if(Vue.ls.get("userInfos")!=null){
	console.log("socket启动连接")
	Vue.use(new VueSocketIO({
		debug: true,
		connection: "ws://192.168.16.91:3000/Room?Uid="+Vue.ls.get("userInfos").openId
	}))
	console.log("socket连接完成")
}
import Moment from 'moment'
Vue.filter('formatDate', function(value) {
  return Moment(value).format('YYYY-MM-DD HH:mm:ss')
})
//自动获取焦点
Vue.directive('myfocus', {
    inserted: function(el, binding) {
        //console.log(el)
        el.focus()
    }
})

/* eslint-disable no-new */
new Vue({
	el: '#app',
	router,
	store,
	components: {
		App
	},
	sockets:{
		get_apply_make_friends_msg(data){
			Notify({ type: 'primary', message: data, duration: 10000 });
    	},
    	disconnect(){
    		console.log("Socket 断开");
    		Toast("您与聊天服务器断开连接!")
    	}
	},
	template: '<App/>'
})