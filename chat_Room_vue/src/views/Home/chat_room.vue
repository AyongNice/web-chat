<template>
  <div id="chatBox">
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
            @click="() => this.$router.push({ name: 'home' })"
          />
        </van-col>
        <van-col span="16">
          <span
            style="font-size: 16px; display: flex; justify-content: center; line-height: 44px;"
          >
            {{
              ChatContent.note == null ? ChatContent.username : ChatContent.note
            }}
          </span>
        </van-col>
        <van-col span="4">
          <van-icon
            style="display: flex; justify-content: center; line-height: 44px;"
            size="20"
            name="ellipsis"
          />
        </van-col>
      </van-row>
    </div>
    <div style="height: 44px;"></div>

    <!--
        	作者：ayongnice@163.com
        	时间：2020-10-17
        	描述：聊天记录
        -->

    <van-pull-refresh
      style="height: 80vh;"
      v-model="loading"
      @refresh="onRefresh"
    >
      <div
        style="width: 92%; margin-left: 4%; display: flex;justify-content: space-between;"
        v-for="(item, index) in list"
        :key="index"
      >
        <div style="width: 100%; margin-top: 14px;">
          <div style="display: flex;" v-if="userInfos.openId != item.openId">
            <img style="width: 35px; height: 35px;" :src="ChatContent.avatar" />
            <div
              style="background: #ffffff; padding: 10px; margin-left: 10px; border-radius: 8px;"
              v-if="item.contentType == 'txt'"
            >
              {{ item.message }}
            </div>
            <!--图片-->
            <div
              style="background: #ffffff; padding: 10px; margin-left: 10px; border-radius: 8px;"
              v-if="item.contentType == 'img'"
            >
              <img
                :id="index"
                :stop.src="getImageDom(item)"
                style="width: 140px; height: auto;"
              />
              <!--<img :id="index"  :src="$Request_get('http://192.168.16.95:3000/v1/api/chat/findFiles/'+item.message).then(res=>{window.document.getElementById(index).src = res.data})" style="width: 140px; height: auto;"/>-->
            </div>
            <div
              style=" margin-left: 10px; border-radius: 8px;"
              v-if="item.contentType == 'audio'"
            >
              <audio controls controlsList="nodownload">
                <source :src="getImageDom(item)" type="audio/mpeg" />
                您的浏览器不支持 audio 元素。
              </audio>
            </div>
          </div>
          <div style="display: flex; margin-top: 10px; float: right;" v-else>
            <div
              style="float: right; background: #ffffff; margin-right: 10px; padding: 10px; border-radius: 8px;"
              v-if="item.contentType == 'txt'"
            >
              {{ item.message }}
            </div>
            <!--图片-->
            <div
              style="float: right; background: #ffffff; margin-right: 10px; padding: 10px; border-radius: 8px;"
              v-if="item.contentType == 'img'"
            >
              <!--<img :id="index" :stop.src="getImageDom(index,item.message)" style="width: 140px; height: auto;"/>-->
              <img
                :src="getImageDom(item)"
                style="width: 140px; height: auto;"
              />
            </div>
            <div
              style=" margin-left: 10px; border-radius: 8px;"
              v-if="item.contentType == 'audio'"
            >
              <audio controls controlsList="nodownload">
                <source :src="getImageDom(item)" type="audio/mpeg" />
                您的浏览器不支持 audio 元素。
              </audio>
            </div>
            <div style="float: right;">
              <img
                style="float: right; width: 35px; height: 35px;"
                :src="avatar"
              />
            </div>
          </div>
        </div>
      </div>
    </van-pull-refresh>
    <!--
        	作者：ayongnice@163.com
        	时间：2020-10-16
        	描述：底部发送消息部分
        -->
    <div
      style="width: 100%;position: fixed; bottom: 0px; height: 60px; background: #EEF0F4;"
    >
      <div style="display: flex;align-items: center;">
        <div style="width: 15%;">
          <van-icon name="smile-o" size="25" style="line-height: 37px;" />
        </div>
        <input
          v-model="message"
          v-myfocus
          style="width: 100%; border: 0px; background: #FFFFFF; height: 28px; font-size: 16px;"
        />
        <div style="width: 15%;" @click="sendmessage()">
          <span
            style="display: flex; justify-content: center; line-height: 60px;"
            >发送</span
          >
        </div>
        <div style="width: 15%;">
          <van-icon
            name="add-o"
            size="25"
            @click="onOptions"
            style="line-height: 37px;"
          />
        </div>
      </div>
    </div>
    <MessageBox v-if="options" @afterRead="sendmessage" />
  </div>
</template>

<script>
import { message, baseFileUrl } from "@/utils/utils";
import { Uploader, Toast } from "vant";
import SockJS from "sockjs-client";
import * as Stomp from "stompjs";
import MessageBox from "./components/message-box.vue";
export default {
  name: "chat_room",
  components: {
    Uploader,
    Toast,
    MessageBox
  },
  data() {
    return {
      message: "",
      avatar: "",
      userInfos: {},
      page: 1,
      size: 10,
      ChatContent: this.$store.state.That_Chat,
      list: [],
      stompClient: {},
      loading: false,
      options: false
    };
  },
  computed: {},
  mounted() {
    this.userInfos = JSON.parse(
      window.sessionStorage.getItem("userInfos") || "{}"
    );
    this.avatar = this.userInfos.avatar;
    this.scrollToBottom();

    this.init();

    const socket = new SockJS("http://localhost:8066/room");
    this.stompClient = Stomp.over(socket);

    this.stompClient.connect({}, frame => {
      this.stompClient.subscribe(
        `/broadcast/${this.ChatContent.openId}/queue/messages`,
        message => {
          console.log("Received: " + message.body);

          const body = JSON.parse(message.body);
          if (body.messageType === "remove") {
            this.removeFlag = true;
            return;
          }

          this.list.push(body);
        }
      );
    });
  },
  sockets: {
    // cllback_sendMessage(data) {
    //   console.log(data);
    //   this.ChatContent.messages.push(data.data);
    // },
    // get_sendMessage_message(data) {
    //   console.log(data);
    //   this.ChatContent.messages.push(data);
    // }
  },
  updated() {
    this.scrollToBottom();
  },
  methods: {
    getImageDom(data) {
      console.log(data);
      return baseFileUrl + data.message;
    },
    onOptions() {
      this.options = !this.options;
    },
    init() {
      //获取聊天记录
      this.$Request_post(this.$AXIOS_URL + "/api/recording/findrecording", {
        page: this.page,
        size: this.size,
        friendId: this.ChatContent.friendId
      }).then(({ data }) => {
        this.list = data.list;
        this.loading = false;
      });
    },
    sendmessage(message = this.message, contentType = "txt") {
      if (message != "") {
        if (this.removeFlag) {
          return this.$route.query.groupId
            ? Toast("你已被提走")
            : Toast("你已被删除");
        }
        debugger;
        /**
         *     private String openId;
    private String friendId;
    private String contentType;
    private String contentStatus;
    private String message;
    private String timestamp;
    private String messageType;
         */
        var data = {
          openId: this.userInfos.openId,
          friendId: this.ChatContent.friendId,
          contentType: contentType,
          contentStatus: "unread",
          message: message,
          timestamp: new Date().getTime(),
          messageType: "single"
        };
        this.stompClient.send("/app/sendMessage", {}, JSON.stringify(data));
        this.message = "";
        this.list.push(data);
        console.log(this.list);
        this.scrollToBottom();
      } else {
        Toast("发送消息不可为空");
      }
    },
    scrollToBottom() {
      this.$nextTick(() => {
        // Chrome
        document.body.scrollTop =
          document.getElementById("chatBox").offsetTop +
            document.documentElement.scrollHeight || document.body.scrollHeight;
        //					console.log(document.getElementById("chatBox").offsetTop)
        // Firefox
        document.documentElement.scrollTop =
          document.getElementById("chatBox").offsetTop +
            document.documentElement.scrollHeight || document.body.scrollHeight;
        // Safari
        window.pageYOffset =
          document.getElementById("chatBox").offsetTop +
            document.documentElement.scrollHeight || document.body.scrollHeight;
      });
    },
    selectImage() {
      var data = document.getElementById("uploads").click();
    },
    handleFile(file) {
      console.log(file);
    },

    onRefresh() {
      this.loading = true;
      this.page++;
      this.init();
    }
  }
};
</script>

<style lang="scss" scoped>
.van-uploader__upload {
  background-color: #eef0f4;
}
</style>

<style>
.custom-audio {
  width: 100%;
  max-width: 600px;
  background-color: #e4e4e4;
  border: 1px solid #ced4da;
  border-radius: 8px;
  padding: 10px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

audio {
  width: 230px !important;
  height: 35px;
  border: none;
  outline: none;
}

.custom-audio audio::-webkit-media-controls-panel {
  background-color: #fff;
  border-radius: 8px;
}

.custom-audio audio::-webkit-media-controls-play-button {
  background-color: #007bff;
  color: #fff;
}

.custom-audio audio::-webkit-media-controls-timeline {
  height: 4px;
  background-color: #e9ecef;
}

.custom-audio audio::-webkit-media-controls-current-time-display,
.custom-audio audio::-webkit-media-controls-time-remaining-display {
  font-size: 14px;
  color: #6c757d;
}
</style>
