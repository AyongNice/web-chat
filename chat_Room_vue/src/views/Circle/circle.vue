<template>
  <div style="">
    <!--
        	作者：ayongnice@163.com
        	时间：2020-10-19
        	描述：头部导航栏
        -->
    <div
      style="height: 44px;width: 100%; background:#ffffff; position: fixed; z-index: 999;"
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
          >
            胖友圈
          </span>
        </van-col>
        <van-col span="4">
          <van-icon
            style="display: flex; justify-content: center; line-height: 44px;"
            size="26"
            name="photo-o"
            @click="() => this.$refs.redact.onShow()"
          />
        </van-col>
      </van-row>
    </div>
    <div style="height: 44px;"></div>

    <div style="width: 100%; height: 100%;">
      <div
        style="width: 92%; height: 100%; margin-left: 4%; padding: 0px 0px 20px 0px; margin-bottom: 10px; border-bottom:1px solid #8C99A0;"
        v-for="(item, index) in circleList"
        :key="index"
      >
        <!--        朋友圈信息内容-->
        <div style="display: flex;">
          <img
            style="margin-top: 10px; width: 35px; height: 35px;"
            :src="item.avatar"
          />
          <div class="counant">
            <span class="userName">{{ item.userName }}</span>
            <span>{{ item.commentMessage }}</span>
            <div>
              <img
                :style="
                  `${getImgSize(item.imageList.length)};margin-right: 5px;`
                "
                :src="getImageDom(image)"
                @click="lockCircleImg(image)"
                :key="imageIndex"
                v-for="(image, imageIndex) in item.imageList"
              />
            </div>
          </div>
        </div>

        <!--        点赞/评论 popover-->
        <div class="bottom-bar">
          <span class="time" style="font-size: 12px; margin-left: 45px;">{{
              item.createdAt
            }}</span>
          <van-popover
            v-model:show="item.showPopover"
            :actions="actions"
            @select="onSelect"
          >
            <template #reference>
              <van-icon size="30" @click="onPopover(item)" name="more-o"/>
            </template>
          </van-popover>
        </div>

        <!--        点赞信息内容-->
        <div v-if="item.linkeList && item.linkeList.length" class="link-box">
          <img style="width: 35px;height: 35px" v-for="(link, linkIndex) in item.linkeList"
               :key="linkIndex" :src="link.avatar"/>

        </div>

        <!--        评论信息内容-->

        <div v-if="item.commentList && item.commentList.length" class="link-box">
          <dd v-for="(comment, commentIndex) in item.commentList"
              :key="commentIndex"
              @click="showPopup($event,comment,commentIndex,item.commentList)"
              style="margin-top: 15px"
          >
            <van-popover v-model="comment.showPopover" :actions="actions" @select="onSelect">
              <template #reference>
                <div>
              <span @click="onUserNameMessage(item, comment.userId, comment.userName)">{{
                  comment.userName
                }} </span>
                  回复
                  <span @click="onUserNameMessage(item,comment.receiverId, comment.receiverName)">{{
                      comment.receiverName
                    }}  </span>
                  : {{ comment.commentMessage }}
                </div>
              </template>
            </van-popover>


          </dd>

        </div>


        <!--评论input弹框-->
        <div style="position: relative" v-if="item.showMessage">
          <van-field
            v-model="commentMessage"
            rows="2"
            autosize
            label="评论"
            type="textarea"
            maxlength="50"
            placeholder="请输入留言"
            show-word-limit
          >

          </van-field>
          <van-button class="comment-btu" @click="onCommentSend" size="small" type="primary">发送</van-button>
        </div>

      </div>
    </div>

    <!--
        	作者：ayongnice@163.com
        	时间：2020-10-19
        	描述：发布朋友圈
        -->
    <redactCircle ref="redact"></redactCircle>
    <div v-if="show" @click="onRemove" :style="{ position: 'absolute', left: x + 'px', top: y + 'px' }" class="popup">
      删除
    </div>
  </div>
</template>

<script>
import redactCircle from "@/components/popups/redactCircle";
import {Divider, ImagePreview, Toast} from "vant";
import {baseFileUrl} from "@/utils/utils";

export default {
  name: "circle",
  data() {
    return {
      pagePamras: {
        page: 1,
        size: 10
      },
      show: false,//删除popu
      x: 0,
      y: 0,
      actions: [{text: "点赞"}, {text: "评论"}],
      circleList: [],
      imgSizeMap: {
        1: `width: 160px; height: 160px;`,
        2: `width: 100px; height: 100px;`,
        3: `width: 70px; height: 70px;`
      },
      selectItem: {},//当前选择项
      commentMessage: '',//评论
      userInfos: {},
      commentId: '',//评论id
      commentIndex: '',
      commentList: [],
    };
  },
  computed: {},
  async mounted() {
    console.log(this.$store.state.Friend_List);
    this.userInfos = JSON.parse(
      window.sessionStorage.getItem("userInfos") || "{}"
    );

    //朋友圈
    const {data} = await this.$Request_get(
      this.$AXIOS_URL + "/api/circle/findByMeCircle",
      this.pagePamras
    )
    data.list.forEach(_ => {
      _.showPopover = false
    })
    const ids = data.list.map(_ => _.id).join(',');

    //点赞信息查询
    const lineListMap = {}
    this.$Request_get(this.$AXIOS_URL + "/api/circle/findLike", {ids}).then(res => {
      res.data.forEach(_ => {
        if (lineListMap[_.circleId]) {
          lineListMap[_.circleId].push({userId: _.userId, avatar: _.avatar})
        } else {
          lineListMap[_.circleId] = []
          lineListMap[_.circleId].push({userId: _.userId, avatar: _.avatar})

        }
      })
      //点赞 朋友圈信息合并
      data.list.forEach(_ => _.linkeList = lineListMap[_.id] || [])
      this.circleList = data.list;
    }).catch(() => {
      this.circleList = data.list;
    })


    //评论查询
    const commentListMap = {}

    function setComment(_) {
      commentListMap[_.circleId].push({
        "userId": _.userId,
        "userName": _.userName,
        "commentMessage": _.commentMessage,
        "receiverId": _.receiverId,
        "receiverName": _.receiverName,
        id: _.id
      })
    }

    this.$Request_get(this.$AXIOS_URL + "/api/circle/findComment", {ids}).then(res => {
      res.data.forEach(_ => {
        if (commentListMap[_.circleId]) {
          setComment(_)
        } else {
          commentListMap[_.circleId] = []
          setComment(_)

        }
      })
      //点赞 朋友圈信息合并
      data.list.forEach(_ => _.commentList = commentListMap[_.id] || [])
      this.circleList = data.list;
      this.$forceUpdate()
      console.log(this.circleList);
    }).catch(() => {
      this.circleList = data.list;
    })


  },
  methods: {
    /**
     * 评论弹框
     * @param event
     * @param comment
     * @param commentIndex
     * @param list
     */
    showPopup(event, comment, commentIndex, list) {
      if (comment.userId == this.userInfos.openId) {
        this.x = event.clientX;
        this.y = event.clientY + window.scrollY; // 考虑滚动条的位置
        this.show = true;
        this.commentId = comment.id;
        this.commentIndex = commentIndex;
        this.commentList = list;
      } else {
        this.show = false;
      }

    },
    /**
     * 关闭评论内容删除弹框
     */
    onRemove() {
      this.show = false;
      this.$Request_delete(this.$AXIOS_URL + `/api/circle/deleteComment/${this.commentId}`, {
        circleId: this.selectItem.id,
        userId: this.userInfos.openId,
      }).then(() => {
        this.commentList.splice(this.commentIndex, 1);
        this.$forceUpdate();
        Toast("删除成功")
      })
    },
    /**
     * 点击评论按钮
     * @param item
     */
    onPopover(item) {
      this.selectItem = item
      this.$nextTick(() => {
        item.showPopover = !item.showPopover
        console.log(item);
      });
    }
    ,
    /**
     * 朋友圈选择
     * @param e
     */
    onSelect(e) {
      console.log(e);

      if (e.text === "点赞") {
        console.log(this.selectItem)
        this.$Request_post(this.$AXIOS_URL + "/api/circle/like", {
          circleId: this.selectItem.id,
          userId: this.userInfos.openId,
        }).then(() => {
          if (!this.selectItem.linkeList) {
            this.selectItem.linkeList = []
          }
          //有赞就取消
          if (this.selectItem.linkeList.some(_ => _.userId === this.userInfos.openId)) {
            this.selectItem.linkeList = this.selectItem.linkeList.filter(_ => _.userId !== this.userInfos.openId)
            return
          }
          //无赞添加
          this.selectItem.linkeList.push({
            userId: this.userInfos.openId,
            avatar: this.userInfos.avatar
          })
        });
      }
      if (e.text === "评论") {
        this.selectItem.showMessage = !this.selectItem.showMessage
      }
    },

    /**
     * 点击评论用户名
     * @param item
     * @param receiverId
     * @param receiverName
     */
    onUserNameMessage(item, receiverId, receiverName) {
      item.showMessage = true;
      this.selectItem = item;
      this.selectItem.userId = receiverId;
      this.selectItem.userName = receiverName;
      this.$forceUpdate()
    },

    /**
     * 发送评论
     */
    onCommentSend() {
      console.log(this.userInfos)
      this.$Request_post(this.$AXIOS_URL + "/api/circle/comment", {
        circleId: this.selectItem.id,
        userId: this.userInfos.openId,
        userName: this.userInfos.username,
        commentMessage: this.commentMessage,
        receiverId: this.selectItem.userId,
        receiverName: this.selectItem.userName,
      }).then(() => {
        if (!this.selectItem.commentList) {
          this.selectItem.commentList = []
        }
        this.selectItem.commentList.push({
          userId: this.userInfos.openId,
          userName: this.userInfos.username,
          commentMessage: this.commentMessage,
          receiverId: this.selectItem.userId,
          receiverName: this.selectItem.userName,
        })
        this.$forceUpdate()

      });
    },
    /**
     * 获取图片样式
     * @param length
     * @returns {*|string}
     */
    getImgSize(length) {
      if (!this.imgSizeMap[length]) {
        return `width: 70px; height: 70px;`;
      }
      return this.imgSizeMap[length];
    }
    ,
    /**
     * 获取图片地址
     * @param data
     * @returns {*}
     */
    getImageDom(data) {
      return baseFileUrl + data;
    }
    ,
    listenerFunction(e) {
      var scr = document.documentElement.scrollTop || document.body.scrollTop; // 向上滚动的那一部分高度
      var clientHeight = document.documentElement.clientHeight; // 屏幕高度也就是当前设备静态下你所看到的视觉高度
      var scrHeight =
        document.documentElement.scrollHeight || document.body.scrollHeight; // 整个网页的实际高度，兼容Pc端
      //				console.log(scr+clientHeight)
      //				console.log(scrHeight)
      if (scr + clientHeight >= scrHeight) {
        console.log("到底了");
      }
    }
    ,
    lockCircleImg(url, index = 0) {
      ImagePreview({
        images: [baseFileUrl + url],
        startPosition: index
      });
    }
  }
  ,
  components: {
    redactCircle,
    Divider,
    ImagePreview
  }
};
</script>

<style>
.van-field__label {
  width: max-content !important;
}

.van-field__word-limit {
  text-align: left !important;
}
</style>

<style scoped lang="scss">
.userName {
  font-size: 17px;
  font-weight: bold;
  margin-bottom: 10px;
}

.counant {
  display: flex;
  flex-direction: column;
  margin-left: 10px;
}

.bottom-bar {
  display: flex;
  justify-content: space-between;
}

.comment-btu {
  position: absolute;
  right: 7px;
  bottom: 13px;
}

.link-box {
  background: rgba(108, 117, 125, 0.2);
  padding: 5px;
  position: relative;
}

.popup {
  position: absolute;
  background-color: white;
  padding: 10px 15px;
  border-radius: 7px;
  box-shadow: 0 0 5px rgba(0, 0, 0, 0.3);
}
</style>
