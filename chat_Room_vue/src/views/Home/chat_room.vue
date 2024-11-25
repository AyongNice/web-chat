<template>
  <div>
    <!-- <van-pull-refresh
      style="width:100vw;height:80vh"
      v-model="isLoading"
      @refresh="onRefresh"
    >
      <p>刷新次数: {{ count }}</p>
    </van-pull-refresh> -->
    <div
      id="chatBox"
      @keydown.enter="sendMessage()"
      oncontextmenu="return false;"
    >
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
              {{ ChatContent.username }}
            </span>
          </van-col>

          <van-col span="4">
            <van-icon
              style="display: flex; justify-content: center; line-height: 44px;"
              size="20"
              name="ellipsis"
              @click="onInfo"
            />
          </van-col>
        </van-row>
      </div>
      <van-pull-refresh
        style="width:100vw;height:89vh;padding-top: 30px;overflow-y: auto;"
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
              <van-image
                style="width: 35px; height: 35px;"
                :src="
                  $route.query.groupId
                    ? groupUserMap[item.openId] &&
                      groupUserMap[item.openId].avatar
                    : ChatContent.avatar
                "
              >
                <template slot="loading">
                  人已不在
                </template>
              </van-image>

              <div
                style="background: #ffffff; padding: 10px; margin-left: 10px; border-radius: 8px;"
                v-if="item.contentType == 'txt'"
              >
                {{ item.message }}
              </div>
              <div
                style="background: #ffffff; padding: 10px; margin-left: 10px; border-radius: 8px;"
                v-if="item.contentType == 'img'"
              >
                <img
                  :id="index"
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
            </div>
            <div style="display: flex; margin-top: 10px; float: right;" v-else>
              <div
                style="float: right; background: #ffffff; margin-right: 10px; padding: 10px; border-radius: 8px;"
                v-if="item.contentType == 'txt'"
              >
                {{ item.message }}
              </div>
              <div
                style="float: right; background: #ffffff; margin-right: 10px; padding: 10px; border-radius: 8px;"
                v-if="item.contentType == 'img'"
              >
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

      <van-loading
        v-if="recordingLoading"
        vertical
        color="#0094ff"
        size="50px"
        style="position: fixed;bottom: 150px;left: 50%;transform: translateX(-50%);"
      >
        <template #icon>
          <van-icon name="star-o" size="30" />
        </template>
        加载中...
      </van-loading>

      <div class="bottom-box">
        <div style="display: flex;align-items: center;">
          <van-icon
            name="comment-o"
            size="25"
            v-if="chatIconType === 'audio'"
            style="line-height: 37px;margin-right:  8px;"
            @click="() => (chatIconType = 'txt')"
          />
          <van-icon
            v-if="chatIconType === 'txt'"
            name="volume-o"
            size="25"
            @click="() => (chatIconType = 'audio')"
            style="line-height: 37px;margin-right:  8px;"
          />
          <input
            v-if="chatIconType === 'txt'"
            v-model="message"
            v-myfocus
            style=" border: 0px; background: #FFFFFF; height: 28px; font-size: 16px;flex:1"
          />
          <button
            @mousedown="startRecording"
            @mouseup="stopAndSend"
            @touchstart="startRecording"
            @touchend="stopAndSend"
            style=" border: none; background: #eae1e1; height: 28px; font-size: 16px;flex:1;text-align: center;border-radius: 8px;"
            v-if="chatIconType === 'audio'"
          >
            按住说话
          </button>

          <div style=" display: flex;align-items: center;">
            <van-icon
              name="smile-o"
              size="25"
              @click="showMoji = !showMoji"
              style="line-height: 58px;margin: 0  8px;"
            />

            <van-button
              v-if="message"
              @click="sendMessage()"
              style="display: flex; justify-content: center; line-height: 60px;"
              >发送</van-button
            >
            <van-icon
              v-else
              name="add-o"
              size="25"
              @click="onOptions"
              style="line-height: 58px;"
            />
          </div>
        </div>
      </div>
      <MessageBox v-if="options" @afterRead="sendMessage" />
      <MojiPicker
        v-show="showMoji"
        @selectEmoji="selectEmoji"
        @sendMessage="sendMessage"
      />
      <van-action-sheet
        v-model="actionSheet_Show"
        :actions="actions"
        cancel-text="取消"
        close-on-click-action
        @cancel="onCancel"
        @select="selectOption"
      />
    </div>
  </div>
</template>

<script>
import { message, baseFileUrl } from "@/utils/utils";
import SockJS from "sockjs-client";
import * as Stomp from "stompjs";
import MessageBox from "./components/message-box.vue";
import MojiPicker from "./components/moji-picker.vue";
import { Toast, Dialog } from "vant";
export default {
  name: "chat_room",
  components: {
    // Uploader,
    Toast,
    MessageBox,
    MojiPicker
    // PullRefresh: aa.PullRefresh
  },
  data() {
    return {
      count: 0,
      isLoading: false,
      message: "",
      avatar: "",
      userInfos: {},
      page: 1,
      size: 10,
      ChatContent: this.$store.state.That_Chat,
      list: [],
      stompClient: {},
      loading: true,
      options: false,
      chatIconType: "txt",
      isRecording: false, // 是否正在录制
      mediaRecorder: null, // MediaRecorder 实例
      audioChunks: [], // 存储录制的音频片段
      audioBlob: null, // 录制完成后的音频 Blob 对象
      audioUrl: null, // 预览音频的 URL
      recordingLoading: false,
      showMoji: false,
      roomId: "", //监听id
      groupInfo: {}, //群聊信息
      groupUserMap: {},
      actions: [{ name: "删除好友", value: "remove" }],
      actionSheet_Show: false,
      groupUserList: [],
      total: 0
    };
  },
  mounted() {
    this.userInfos = JSON.parse(
      window.sessionStorage.getItem("userInfos") || "{}"
    );
    this.avatar = this.userInfos.avatar;
    this.roomId = this.$route.query.groupId || this.ChatContent.friendId;

    const broadcastId = this.$route.query.groupId || this.ChatContent.openId;

    this.init();
    this.getGroupInfo();
    this.scrollToBottom();

    const socket = new SockJS("http://localhost:8066/room");
    this.stompClient = Stomp.over(socket);

    this.stompClient.connect({}, () => {
      this.stompClient.subscribe(
        `/broadcast/${broadcastId}/queue/messages`,
        message => {
          const body = JSON.parse(message.body);
          if (body.messageType === "remove") {
            if (body.message === this.userInfos.openId) {
              this.removeFlag = true;
            }
            return;
          }

          if (this.$route.query.groupId && body.openId == this.userInfos.openId)
            return;

          this.list.push(body);
        }
      );
    });
  },

  methods: {
    getImageDom(data) {
      return baseFileUrl + data.message;
    },
    onOptions() {
      this.options = !this.options;
    },
    init() {
      this.loading = true;

      if (this.$route.query.groupId) {
        //获取群聊聊天记录
        this.$Request_post(this.$AXIOS_URL + "/api/recording/groupRecords", {
          page: this.page,
          size: this.size,
          friendId: this.roomId
        }).then(({ data }) => {
          this.total = data.total;
          if (data.total <= this.list.length) return (this.loading = false);
          this.page++;
          this.list = [...data.list, ...this.list];
          this.loading = false;
        });
      } else {
        //获取聊天记录
        this.$Request_post(this.$AXIOS_URL + "/api/recording/findrecording", {
          page: this.page,
          size: this.size,
          friendId: this.roomId
        }).then(({ data }) => {
          this.total = data.total;
          if (data.total <= this.list.length) return (this.loading = false);
          this.page++;
          this.list = [...data.list, ...this.list];
          this.loading = false;
        });
      }
    },
    getGroupInfo() {
      //获取群 信息
      this.$Request_get(this.$AXIOS_URL + "/api/group/getGroupInfo", {
        groupId: this.roomId
      }).then(({ data }) => {
        this.groupInfo = data;
      });

      this.$Request_get(this.$AXIOS_URL + "/api/group/getGroupMember", {
        groupId: this.roomId
      }).then(({ data }) => {
        this.groupUserList = data;
        data.forEach(element => {
          this.groupUserMap[element.openId] = element;
        });
      });
    },
    sendMessage(message = this.message, contentType = "txt") {
      if (message != "") {
        if (this.removeFlag) {
          return this.$route.query.groupId
            ? Toast("你已被提走")
            : Toast("你已被删除");
        }
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
          friendId: this.roomId,
          contentType: contentType,
          contentStatus: "unread",
          message: message,
          timestamp: new Date().getTime(),
          messageType: this.$route.query.groupId ? "group" : "single"
        };
        this.stompClient.send("/app/sendMessage", {}, JSON.stringify(data));
        this.message = "";
        this.list.push(data);
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
    onInfo() {
      if (this.$route.query.groupId) {
        this.$router.push({
          name: "groupinfo",
          query: {
            groupUserList: JSON.stringify(this.groupUserList),
            groupInfo: JSON.stringify(this.groupInfo)
          }
        });
      } else {
        this.actionSheet_Show = !this.actionSheet_Show;
      }
    },
    /**
     * 表情包selectEmoji
     */
    selectEmoji(data) {
      this.message += data;
    },

    selectImage() {
      var data = document.getElementById("uploads").click();
    },
    handleFile(file) {
      console.log(file);
    },

    onRefresh() {
      this.init();
    },
    selectOption(events) {
      if (events.value == "remove") {
        Dialog.confirm({
          title: "删除",
          message:
            "确定要删除好友吗？（删除好友导致对方账户也将删除你的好友信息！）"
        }).then(() => {
          this.$Request_post(this.$AXIOS_URL + "/api/friends/deleteFriend", {
            friendId: this.roomId
          }).then(() => {
            this.$router.push({ name: "home" });
          });
        });
      }
    },
    async startRecording() {
      console.log("开始录音");
      this.recordingLoading = true;
      try {
        // 请求麦克风权限
        const stream = await navigator.mediaDevices.getUserMedia({
          audio: true
        });

        // 创建 MediaRecorder 实例
        this.mediaRecorder = new MediaRecorder(stream);

        // 当有数据可用时，将数据片段添加到 audioChunks 数组中
        this.mediaRecorder.ondataavailable = event => {
          if (event.data.size > 0) {
            this.audioChunks.push(event.data);
          }
        };

        // 当录制停止时，生成 Blob 对象并创建预览 URL
        this.mediaRecorder.onstop = () => {
          this.audioBlob = new Blob(this.audioChunks, { type: "audio/webm" });
          this.audioUrl = URL.createObjectURL(this.audioBlob);
          this.audioChunks = []; // 清空音频片段数组
        };

        // 开始录制
        this.mediaRecorder.start();
        this.isRecording = true; // 更新录制状态
      } catch (error) {
        console.error("无法获取麦克风权限", error);
      }
    },
    stopAndSend() {
      this.mediaRecorder.stop();
      this.recordingLoading = false;

      if (this.mediaRecorder) {
        // 停止录制

        this.isRecording = false; // 更新录制状态

        // 如果有录制的音频，发送到服务器
        if (this.audioBlob) {
          this.sendAudio();
        }
      }
    },
    async sendAudio() {
      if (this.audioBlob) {


        // 创建 FormData 对象，用于发送音频文件
        const formData = new FormData();
        formData.append("file", this.audioBlob, `${+new Date()}recording.webm`);

        try {
          // 发送 POST 请求到服务器
          this.$Request_post(
            this.$AXIOS_URL + "/api/recording/upload",
            formData
          ).then(res => {
            this.sendMessage(res.data, "audio");
          });
        } catch (error) {
          console.error("录音上传失败", error);
        }
      }
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

.bottom-box {
  width: 100%;
  position: fixed;
  bottom: 0px;
  height: 60px;
  background: #eef0f4;
  padding: 0 8px;
  box-sizing: border-box;
}
</style>
