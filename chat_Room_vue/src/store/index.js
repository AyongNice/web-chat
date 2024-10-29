import Vue from "vue";
// import Vuex from 'vuex';
// const Vue = require('vue')
const Vuex = require("vuex");
Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    Photo_Item_Details: JSON.parse(
      sessionStorage.getItem("Photo_Item_Details")
    ), //相册详情
    SearchUser: JSON.parse(sessionStorage.getItem("SearchUser")), //搜索用户ID 信息
    Apply_Friend_List: JSON.parse(sessionStorage.getItem("Apply_Friend_List")), //申请添加好友列表
    Add_Friend_Information: JSON.parse(
      sessionStorage.getItem("Add_Friend_Information")
    ), //添加好友的详情信息
    Friend_List: JSON.parse(sessionStorage.getItem("Friend_List")), //查询好友列表
    Friend_List_item: JSON.parse(sessionStorage.getItem("Friend_List_item")), //好友openId
    Friend_Information: JSON.parse(
      sessionStorage.getItem("Friend_Information")
    ), //好友信息
    That_Chat: JSON.parse(sessionStorage.getItem("That_Chat")), //当前聊天
    userinfo: JSON.parse(sessionStorage.getItem("userInfos"))
  },
  mutations: {
    setPhoto_Item_Details(state) {
      state.Photo_Item_Details = JSON.parse(
        sessionStorage.getItem("Photo_Item_Details")
      );
    },
    setSearchUser(state) {
      state.SearchUser = JSON.parse(sessionStorage.getItem("SearchUser"));
    },
    setApply_Friend_List(state) {
      state.Apply_Friend_List = JSON.parse(
        sessionStorage.getItem("Apply_Friend_List")
      );
    },
    setAdd_Friend_Information(state) {
      state.Add_Friend_Information = JSON.parse(
        sessionStorage.getItem("Add_Friend_Information")
      );
    },
    setFriend_List(state) {
      state.Friend_List = JSON.parse(sessionStorage.getItem("Friend_List"));
    },
    setFriend_List_item(state) {
      state.Friend_List_item = JSON.parse(
        sessionStorage.getItem("Friend_List_item")
      );
    },
    setFriend_Information(state) {
      state.Friend_Information = JSON.parse(
        sessionStorage.getItem("Friend_Information")
      );
    },
    setThat_Chat(state) {
      state.That_Chat = JSON.parse(sessionStorage.getItem("That_Chat")); //当前聊天
    }
  },
  getters: {
    getUserInfo(state) {
      return state.userinfo;
    }
  }
});



// WEBPACK FOOTER //
// src/store/index.js