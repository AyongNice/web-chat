//import Vue from 'vue'
//import Router from 'vue-router'

const Router = require("vue-router");

Vue.use(Router);

export default new Router({
  routes: [
    {
      path: "/",
      name: "index",
      component: () => import("@/components/layouts/BasicLayout"),
      redirect: "/login",
      children: [
        {
          //首页
          path: "/home",
          name: "home",
          component: () => import("@/views/Home/home"),
          beforeEnter: (to, from, next) => {
            next();
          },
        },
        {
          //联系人列表
          path: "/contactList",
          name: "contactList",
          component: () => import("@/views/Contact/contactList"),
        },
        {
          //朋友圈
          path: "/circle",
          name: "circle",
          component: () => import("@/views/Circle/circle"),
        },
        {
          //用户Tab
          path: "/userInfo_Tab",
          name: "userInfo_Tab",
          component: () => import("@/views/UserInfo/userInfo_Tab"),
        },
        {
          //个人信息
          path: "/userInfo",
          name: "userInfo",
          component: () => import("@/views/UserInfo/userInfo"),
        },
      ],
    },
    {
      //编辑用户昵称
      path: "/update_username",
      name: "update_username",
      component: () => import("@/views/UserInfo/update_username"),
    },
    {
      //群聊操作
      path: "/groupinfo",
      name: "groupinfo",
    //   component: () => import("@/views/group/groupinfo"),
    },
    {
      //编辑用户 个性签名
      path: "/update_signature",
      name: "update_signature",
      component: () => import("@/views/UserInfo/update_signature"),
    },
    {
      //相册
      path: "/photo_home",
      name: "photo_home",
      component: () => import("@/views/UserInfo/photo_album/photo_home"),
    },
    {
      //相册详情
      path: "/photo_details",
      name: "photo_details",
      component: () => import("@/views/UserInfo/photo_album/photo_details"),
    },
    {
      //添加好友
      path: "/add_Friend",
      name: "add_Friend",
      component: () => import("@/views/Friend/add_Friend"),
    },
    {
      //添加好友 搜索的好友个人信息
      path: "/friend_Information",
      name: "friend_Information",
      component: () => import("@/views/Friend/friend_Information"),
    },
    {
      //添加好友 搜索的好友个人信息
      path: "/audit_Friend_apply",
      name: "audit_Friend_apply",
      component: () => import("@/views/Friend/audit_Friend_apply"),
    },
    {
      //添加的好友信息
      path: "/add_Friend_Information",
      name: "add_Friend_Information",
      component: () => import("@/views/Friend/add_Friend_Information"),
    },
    {
      //好友信息
      path: "/Friend_Information_",
      name: "Friend_Information_",
      component: () => import("@/views/Contact/friend_information"),
    },
    {
      //聊天室
      path: "/chat_room",
      name: "chat_room",
      component: () => import("@/views/Home/chat_room"),
      meta: { keepAlive: true },
    },
    {
      //登录
      path: "/login",
      name: "login",
      component: () => import("@/views/user/login"),
    },
    {
      //注册
      path: "/register",
      name: "register",
      component: () => import("@/views/user/register"),
    },
    {
      path: "/test",
      name: "test",
      component: () => import("@/views/Test/test"),
    },
  ],
});
