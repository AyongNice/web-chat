<template>
  <div>
    <div class="user_content">
      <div>
        <img :src="userInfos.avatar" />
        <van-icon
          class="user_content_add_icon"
          name="add-o"
          size="20"
          @click="() => (actionSheet_Show = !actionSheet_Show)"
        />
      </div>
    </div>

    <div class="van_swipe_box" v-if="friendList.length >= 1">
      <van-swipe-cell v-for="(item, index) in friendList" :key="index">
        <div
          class="van_swipe_msg_box"
          stop-propagation="true"
          v-if="JSON.parse(item.msgs).length >= 1"
          @click.stop="sendMessage(item)"
        >
          <div style="display: flex;">
            <div>
              <van-image
                round
                width="40"
                height="40"
                style="padding: 10px;"
                :src="item.avatar"
              />
            </div>
            <div>
              <span class="msg_box_username">{{
                item.note == null ? item.username : item.note
              }}</span>
              <span class="msg_box_msg">
                {{
                  item.msgs[item.msgs.length - 1].type == "String"
                    ? item.msgs[item.msgs.length - 1].msg
                    : "[图片]"
                }}
                <!--{{verify_msg(this.$ls.get('userInfos').avatar)==true?test:'[图片]'}}-->
              </span>
            </div>
          </div>
        </div>
      </van-swipe-cell>
    </div>
    <van-action-sheet
      v-model="actionSheet_Show"
      :actions="actions"
      cancel-text="取消"
      close-on-click-action
      @cancel="onCancel"
      @select="selectOption"
    />
  </div>
</template>

<script>
import {
  Icon,
  Toast,
  SwipeCell,
  Image as VanImage,
  Popup,
  ActionSheet
} from "vant";
export default {
  name: "home",
  data() {
    return {
      popup_Show: false,
      actionSheet_Show: false,
      actions: [
        { name: "添加好友", value: "add" },
        { name: "匹配聊天", value: "matching" }
      ],
      friendList: [],
      userInfos: {}
    };
  },
  computed: {
    verify_msg() {
      return function(val) {
        if (val.substring(0, 4) == "http") {
          return false;
        } else {
          return true;
        }
      };
    }
  },
  sockets: {
    get_sendMessage_msg(data) {
      console.log(data);
      //				this.ChatContent.msgs.push(data)
      console.log(
        this.friendList[
          this.friendList.map(x => x.friendId).indexOf(data.openId)
        ]
      );
      this.friendList[
        this.friendList.map(x => x.friendId).indexOf(data.openId)
      ].msgs.push(data);
    }
  },
  mounted() {
    this.userInfos = JSON.parse(
      window.sessionStorage.getItem("userInfos") || "{}"
    );
    //			console.log(this.$ls.get("userInfos").avatar)
    Toast.loading({
      mask: true,
      message: "加载好友列表..."
    });
    this.$Request_get(this.$AXIOS_URL + "/api/friends/findByFriendList").then(
      res => {
        Toast.clear();
        if (res.data.length >= 1) {
          this.friendList = res.data;
          //在vuex中管理状态，使用session，避免页面刷新数据消失，
          sessionStorage.setItem("Friend_List", JSON.stringify(res.data));
          //commit一次，再次让vuex状态重新获取一次值对象。
          this.$store.commit("setFriend_List");
        }
      }
    );
  },
  methods: {
    selectOption(events) {
      console.log(events);
      if (events.value == "add") {
        this.$router.push({ name: "add_Friend" });
      } else if (events.value == "matching") {
        Toast("懒得写了，没啥意思有空有时间在弄!");
      }
    },
    sendMessage(item) {
      console.log(item);
      //在vuex中管理状态，使用session，避免页面刷新数据消失，
      sessionStorage.setItem("That_Chat", JSON.stringify(item));
      //commit一次，再次让vuex状态重新获取一次值对象。
      this.$store.commit("setThat_Chat");
      this.$router.push({ name: "chat_room" });
    },
    onDeleteChat(index, item) {
      //				console.log("删除聊天记录")
      //				console.log(this.friendList[this.friendList.map(x=>x.friendId).indexOf(item.friendId)])
      var data = {
        friendId: item.friendId
      };
      this.$Request_put(
        this.$AXIOS_URL + "/api/chat/clearChathistory",
        data
      ).then(res => {
        console.log(res);
        if (res.status == 200) {
          item.msgs = [];
          //						this.friendList.splice(index,1)
        }
      });
    }
  },
  components: {
    Icon,
    Toast,
    SwipeCell,
    VanImage,
    Popup,
    ActionSheet
  }
};
</script>

<style>
.van-image__img {
  width: 40px;
  height: 40px;
}
</style>

<style lang="scss" scoped>
.user_content {
  width: 100%;
  height: 80px;
  background: #ffffff;
  border-bottom: 0.5px solid #eaeaea;
  img {
    padding: 18px;
    width: 40px;
    height: 40px;
    border-radius: 50px;
  }
  div {
    line-height: 80px;
    display: flex;
    justify-content: space-between;

    .user_content_add_icon {
      line-height: 80px;
      margin-right: 10px;
    }
  }
}
.van_swipe_box {
  .van_swipe_msg_box {
    width: 100%;
    height: 60px;
    background: #ffffff;
    border-bottom: 1px solid #eaeaea;
    div > .msg_box_username {
      display: flex;
      margin-top: 12px;
      font-size: 14px;
      font-weight: bold;
    }
    div > .msg_box_msg {
      font-size: 14px;
      margin-top: 2px;
      overflow: hidden;
      text-overflow: ellipsis;
      display: -webkit-box;
      -webkit-box-orient: vertical;
      -webkit-line-clamp: 1;
    }
  }
}
</style>
