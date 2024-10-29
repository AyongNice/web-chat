
import router from "./router";
import NProgress from "nprogress";
import "nprogress/nprogress.css";
import { Dialog } from "vant";
router.beforeEach((to, from, next) => {
  NProgress.start(); //开启加载进度样式
  if (to.name != "login") {
    //因为进入登录页面也在路由钩子函数中，所以需要判断
    if (!window.sessionStorage.getItem("userInfos")) {
      //当userInfo为空，就让用户再次进入登录页面重新登录，
      //			alert(from.name)
      if (to.name != "register") {
        Dialog.alert({
          title: "身份验证已过期",
          message: "前往重新登录"
        }).then(() => {
          // on confirm
          next("/login");
          NProgress.done(); //加载完成关闭 加载进度样式
        });
      }
    }
  }
  next();
});
router.afterEach(transition => {
  NProgress.done(); //页面渲染完成，关闭加载进度样式
});



// WEBPACK FOOTER //
// src/permission.js