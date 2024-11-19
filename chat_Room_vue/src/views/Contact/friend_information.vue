<template>
  <div style="background: #FFFFFF;">
    <!--
        	作者：ayongnice@163.com
        	时间：2024-10-05
        	描述：头部导航栏
        -->
    <div
      style="height: 44px;width: 100%; background:#FFFFFF; position: fixed; z-index: 999;"
    >
      <van-row>
        <van-col span="4">
          <van-icon
            style="margin: 10px; "
            size="24px"
            name="arrow-left"
            @click="() => this.$router.go(-1)"
          />
        </van-col>
        <van-col span="16">
          <span
            style="font-size: 16px; display: flex; justify-content: center; line-height: 44px;"
            >好友资料</span
          >
        </van-col>
        <van-col span="4">
          <van-icon
            style="display: flex; justify-content: center; line-height: 44px;"
            size="20"
            name="ellipsis"
            @click="() => (actionSheet_Show = !actionSheet_Show)"
          />
        </van-col>
      </van-row>
    </div>
    <van-action-sheet
      v-model="actionSheet_Show"
      :actions="actions"
      cancel-text="取消"
      close-on-click-action
      @cancel="onCancel"
      @select="selectOption"
    />
    <div style="height: 54px;"></div>

    <div style="width: 100%; height: 100%; margin-bottom: 20px;">
      <!--
            	作者：ayongnice@163.com
            	时间：2020-10-09
            	描述：用户资料头部
            -->
      <div style="width: 100%; background: #FFFFFF; display: flex;">
        <div style="width: 28%; height: 90px; border: 0px solid black;">
          <img
            style="width: 70px; height: 70px; border-radius: 50px; margin-left: 20px;"
            :src="searchUser.avatar"
          />
        </div>
        <div style="width: 100%; height: 90px; border: 0px solid black;">
          <span
            style="display: flex; font-size: 14px; margin-left: 10px;"
            v-if="this.$store.state.Friend_List_item.note != null"
            >备注：{{ this.$store.state.Friend_List_item.note }}</span
          >
          <span
            style="display: flex; font-size: 18px; font-weight: bold; padding: 10px;"
            >{{ searchUser.username }}</span
          >
          <span style="display: flex; font-size: 12px; margin-left: 10px;"
            >ID:{{ searchUser.email }}</span
          >
        </div>
      </div>
      <!--
            	作者：ayongnice@163.com
            	时间：2020-10-09
            	描述：用户资料昵称
            -->
      <div class="userInfo_list_box">
        <van-row>
          <van-col span="6">
            <span class="userInfo_list_title">昵称</span>
          </van-col>
          <van-col span="18">
            <span class="userInfo_list_content">{{ searchUser.username }}</span>
          </van-col>
        </van-row>
      </div>
      <!--
            	作者：ayongnice@163.com
            	时间：2020-10-09
            	描述：用户资料个性签名
            -->
      <div class="userInfo_list_box">
        <van-row>
          <van-col span="6">
            <span class="userInfo_list_title">签名</span>
          </van-col>
          <van-col span="18">
            <span class="userInfo_list_content2">{{
              searchUser.signature
            }}</span>
          </van-col>
        </van-row>
      </div>
      <!--
            	作者：ayongnice@163.com
            	时间：2020-10-09
            	描述：用户资料性别
            -->
      <div class="userInfo_list_box">
        <van-row>
          <van-col span="6">
            <span class="userInfo_list_title">性别</span>
          </van-col>
          <van-col span="18">
            <span class="userInfo_list_content">{{ searchUser.genders }}</span>
          </van-col>
        </van-row>
      </div>

      <!--
            	作者：ayongnice@163.com
            	时间：2020-10-09
            	描述：用户资料生日
            -->
      <div class="userInfo_list_box">
        <van-row>
          <van-col span="6">
            <span class="userInfo_list_title">生日</span>
          </van-col>
          <van-col span="18">
            <span class="userInfo_list_content">{{
              this.$moment(searchUser.birthday).format("YYYY-MM-DD")
            }}</span>
          </van-col>
        </van-row>
      </div>

      <!--
            	作者：ayongnice@163.com
            	时间：2020-10-09
            	描述：用户资料城市
            -->
      <div class="userInfo_list_box">
        <van-row>
          <van-col span="6">
            <span class="userInfo_list_title">城市</span>
          </van-col>
          <van-col span="18">
            <span
              class="userInfo_list_content3"
              v-for="(item, index) in searchUser.city"
              :key="index"
              >{{ item.name }}&nbsp;</span
            >
          </van-col>
        </van-row>
      </div>
    </div>

    <!--
        	作者：ayongnice@163.com
        	时间：2020-10-09
        	描述：精选照片
        -->
    <div
      style="width: 100%; background: #FFFFFF;"
      @click="checkPhoto(0)"
      v-if="searchUser.photo_album != null"
    >
      <div
        style="width:100%; height: 41px; display: flex; justify-content: space-between;"
      >
        <span style="padding: 10px 20px; font-weight: bold;">
          精选照片
        </span>
        <span style="margin-right: 20px;">
          <van-icon style="line-height: 41px;" name="arrow" />
        </span>
      </div>
      <div style="width: 90%; margin-left: 5%;">
        <img
          style="width: 100%; height: 180px;"
          :src="photoArr[0]"
          @click="checkPhoto(0)"
          v-if="photoArr[0] != null"
        />
        <div style="width: 100%; height: 80px; display: flex;">
          <img
            style="width: 50%; height: 120px;"
            :src="photoArr[1]"
            @click="checkPhoto(1)"
            v-if="photoArr[1] != null"
          />
          <img
            style="width: 50%; height: 120px;"
            :src="photoArr[2]"
            @click="checkPhoto(2)"
            v-if="photoArr[2] != null"
          />
        </div>
      </div>
    </div>

    <div
      style="width: 100%; height: 64px; background: #FFFFFF; position: fixed; bottom: 0px;"
    >
      <van-button
        style="width: 90%; margin-left: 5%; margin-top: 10px;"
        type="primary"
        color="#1989FA"
        block
        @click="sendMessage"
        >发送消息</van-button
      >
    </div>

    <div style="height: 110px;"></div>
    <!--
        	作者：ayongnice@163.com
        	时间：2020-10-09
        	描述：精选图片function触发
        -->

    <!--
        	作者：ayongnice@163.com
        	时间：2020-10-16
        	描述：添加好友备注
        -->
    <saveNote ref="Notes" v-on:callBackSaveNote="callBackSaveNote"></saveNote>
  </div>
</template>

<script>
import { ImagePreview, Button, Toast, ActionSheet, Dialog } from "vant";
import saveNote from "@/components/popups/saveNote";
export default {
  name: "friend_information",
  data() {
    return {
      actionSheet_Show: false,
      actions: [
        { name: "添加备注", value: "addNote" },
        { name: "删除好友", value: "deleteFriend" }
      ],
      searchUser: {},
      photoArr: []
    };
  },
  computed: {
    choicenessImg() {
      for (var i = 0; i < this.searchUser.photo_album.length; i++) {
        if (this.searchUser.photo_album[i].jurisdiction == "public") {
          this.photoArr.push(
            this.searchUser.photo_album[i].imgs[
              Math.floor(
                Math.random() * this.searchUser.photo_album[i].imgs.length
              )
            ].img
          );
          return;
        }
      }
    },
    choicenessImg2() {
      for (var i = 0; i < this.searchUser.photo_album.length; i++) {
        if (this.searchUser.photo_album[i].jurisdiction == "public") {
          for (var j = 0; j < this.searchUser.photo_album[i].imgs.length; j++) {
            if (
              this.photoArr.indexOf(
                this.searchUser.photo_album[i].imgs[j].img
              ) == -1
            ) {
              //								console.log(this.photoArr.length)
              this.photoArr.push(this.searchUser.photo_album[i].imgs[j].img);
            }
          }
        }
      }
    }
  },
  mounted() {
    Toast.loading({
      mask: true,
      message: "查询好友信息..."
    });
    var data = {
      openId: this.$store.state.Friend_List_item.friendId
    };
    this.$Request_post(
      this.$AXIOS_URL + "/v1/api/friends/findByFriendInformaton",
      data
    ).then(res => {
      this.searchUser = res.data[0];
      Toast.clear();
      if (this.$store.state.Friend_List_item.note != null) {
        res.data[0].note = this.$store.state.Friend_List_item.note;
      }
      //在vuex中管理状态，使用session，避免页面刷新数据消失，
      sessionStorage.setItem("Friend_Information", JSON.stringify(res.data[0]));
      //commit一次，再次让vuex状态重新获取一次值对象。
      this.$store.commit("setFriend_Information");
    });
  },
  methods: {
    checkPhoto(index) {
      console.log(this.photoArr);
      ImagePreview({
        images: this.photoArr,
        startPosition: index
      });
    },
    selectOption(events) {
      console.log(events);
      if (events.value == "addNote") {
        this.$refs.Notes.onShow();
      } else if (events.value == "deleteFriend") {
        console.log("删除好友?");
        Dialog.confirm({
          title: "删除",
          message:
            "确定要删除好友吗？（删除好友导致对方账户也将删除你的好友信息！）"
        })
          .then(() => {
            // on confirm
            var data = {
              friendId: this.searchUser.openId
            };
            this.$Request_delete(
              this.$AXIOS_URL + "/v1/api/friends/deleteFriend",
              data
            ).then(res => {
              console.log(res);
              if (res.status == 200) {
                Toast.success(res.data.message);
              }
            });
          })
          .catch(() => {
            // on cancel
          });
      }
    },
    sendMessage() {
      console.log(this.$store.state.Friend_List_item);
      //在vuex中管理状态，使用session，避免页面刷新数据消失，
      sessionStorage.setItem(
        "That_Chat",
        JSON.stringify(this.$store.state.Friend_List_item)
      );
      //commit一次，再次让vuex状态重新获取一次值对象。
      this.$store.commit("setThat_Chat");
      this.$router.push({ name: "chat_room" });
    },
    callBackSaveNote(value) {
      console.log(value);
      var data = {
        openId: this.$store.state.Friend_List_item.openId,
        friendId: this.$store.state.Friend_List_item.friendId,
        note: value
      };
      console.log(data);
      this.$Request_put(
        this.$AXIOS_URL + "/v1/api/friends/updateFriendNote",
        data
      ).then(res => {
        console.log(res);
      });
    }
  },
  components: {
    ImagePreview,
    Button,
    Toast,
    ActionSheet,
    saveNote,
    Dialog
  }
};
</script>

<style lang="scss" scoped>
.userInfo_list_box {
  height: 44px;
  background: #ffffff;
  border-bottom: 1px solid #eaeaea;
  .userInfo_list_title {
    font-size: 14px;
    padding: 10px 20px;
    line-height: 44px;
  }
  .userInfo_list_content {
    font-size: 14px;
    padding: 10px 0px;
    line-height: 44px;
  }
  .userInfo_list_content2 {
    font-size: 14px;
    line-height: 44px;
    font-size: 14px;
    overflow: hidden;
    text-overflow: ellipsis;
    display: -webkit-box;
    /*! autoprefixer: off*/
    -webkit-box-orient: vertical;
    /* autoprefixer: on */
    -webkit-line-clamp: 1;
  }
  .userInfo_list_content3 {
    font-size: 14px;
    line-height: 44px;
    font-size: 14px;
    float: left;
  }
  .userInfo_list_arrow {
    display: flex;
    justify-content: center;
    line-height: 44px;
  }
}
</style>
