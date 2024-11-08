<template>
  <div>
    <div
      style="height: 44px;width: 100%; background:#FFFFFF; position: fixed; z-index: 999;"
    >
      <van-row>
        <van-col span="4">
          <van-icon
            style="margin: 10px; "
            size="24px"
            name="arrow-left"
            @click="$router.go(-1)"
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
          <!-- <van-icon
          style="display: flex; justify-content: center; line-height: 44px;"
          size="20"
          name="ellipsis"
          @click="onInfo"
        /> -->
        </van-col>
      </van-row>
    </div>
    <div style="width: 100%;height: 44px;"></div>
    <van-checkbox-group style="display: flex;" v-model="result">
      <div
        v-for="(item, index) in groupUserList"
        :key="index"
        class="van_swipe_box"
      >
        <van-checkbox
          class="van-checkbox"
          v-if="removeFlag"
          :name="item.openId"
        />

        <img
          :src="item.avatar"
          style="width: 45px;height: 45px;margin-right: 15px;"
        />
      </div>
    </van-checkbox-group>

    <template v-if="groupInfo.leader === userInfos.openId">
      <van-icon size="40" name="plus" @click="addGroupUser" />
      <van-icon size="40" name="cross" @click="removeFlag = !removeFlag" />
    </template>

    <div style="margin-top: 20px;">群公告:暂无公告</div>

    <div v-if="removeFlag" class="add-group">
      <van-button @click="removeFlag = false">取消</van-button>
      <van-button @click="removeGroppUser" type="primary">删除好友</van-button>
    </div>
  </div>
</template>
<script>
import { Toast } from "vant";
import stompService from "@/utils/callbackMsg.js";
export default {
  name: "group_info",
  data() {
    return {
      ChatContent: this.$store.state.That_Chat,
      groupUserList: [],
      groupInfo: {},
      userInfos: {},
      result: [], //删除好友列表id
      removeFlag: false
    };
  },

  mounted() {
    this.userInfos = JSON.parse(
      window.sessionStorage.getItem("userInfos") || "{}"
    );
    this.groupInfo = JSON.parse(this.$route.query.groupInfo);
    this.groupUserList = JSON.parse(this.$route.query.groupUserList);
  },
  methods: {
    onInfo() {
      this.$emit("onInfo");
    },
    addGroupUser() {
      this.$router.push({
        name: "contactList",
        query: {
          groupUserList: JSON.stringify(this.groupUserList),
          groupId: this.groupInfo.id
        }
      });
    },
    removeGroppUser() {
      this.$Request_post(this.$AXIOS_URL + "/api/group/removeFriends", {
        groupId: this.groupInfo.id,
        openIds: this.result
      }).then(() => {
        Toast("删除成功");
        this.groupUserList = this.groupUserList.filter(item => {
          return !this.result.includes(Number(item.openId));
        });
        this.result.forEach(item => {
          stompService.sendMessage({
            openId: this.userInfos.openId,
            roomId: this.groupInfo.id,
            messageType: "remove",
            message:item
          });
        });
      });
    }
  }
};
</script>

<style lang="scss" scoped>
.van_swipe_box {
  position: relative;
}
.van-checkbox {
  position: absolute;
  left: -2px;
  top: 1px;
  border: 2px solid yellow;
  border-radius: 50px;
}
</style>
