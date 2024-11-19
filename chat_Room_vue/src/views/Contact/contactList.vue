<template>
  <div style="background: #FFFFFF; ">
    <div style="height: 30px;"></div>
    <div style="margin-bottom: 10px;">
      <van-cell-group>
        <van-cell
          title="新朋友"
          is-link
          @click="() => this.$router.push({ name: 'audit_Friend_apply' })"
        >
          <template #right-icon>
            <van-tag round type="danger" v-if="count != 0">{{ count }}</van-tag>
            <van-icon style="margin: auto;" size="16" name="arrow" />
          </template>
        </van-cell>
      </van-cell-group>
    </div>
    <van-empty
      style="height: 80vh;"
      v-if="friendList == null"
      description="暂无好友列表"
    />
    <van-checkbox-group v-model="result">
      <div
        style="display: flex; width: 92%; margin-left:10px; height: 44px; margin-bottom: 10px;"
        v-for="(item, index) in friendList"
        :key="index"
      >
        <van-checkbox
          v-if="
            addGroupUserFlag &&
              !item.leader &&
              !groupUserList.includes(item.friendId)
          "
          :name="item.friendId"
          style="width: 25px; margin-right: 10px;"
        />

        <div @click="lock_friend_information(item)" style="height: 44px;">
          <img
            style="height: 40px; width: 40px; margin-top: 2px;"
            :src="item.avatar"
          />
        </div>
        <div
          @click="lock_friend_information(item)"
          style="width: 100%; height: 44px; margin-left: 10px; border-bottom: 0.1px solid darkgray;"
        >
          <span style="font-size: 16px; font-weight: bold;">{{
            item.note == null ? item.username : item.note
          }}</span>
          <span
            style="display: flex; font-size: 12px; color: darkgray; margin-top: 4px;"
            >{{ item.signature }}</span
          >
        </div>
      </div>
    </van-checkbox-group>

    <div v-if="addGroupUserFlag" class="add-group">
      <van-button @click="addGroupUserFlag = false">取消</van-button>
      <van-button @click="addGroupUser" type="primary">添加群友</van-button>
    </div>
  </div>
</template>

<script>
import { Cell, Toast, Empty } from "vant";
export default {
  name: "contactList",
  data() {
    return {
      count: 0,
      result: [],
      addGroupUserFlag: false,
      friendList: this.$store.state.Friend_List,
      groupUserList: []
    };
  },
  mounted() {
    this.addGroupUserFlag = this.$route.query.groupId;
    if (this.$route.query.groupUserList) {
      this.groupUserList = JSON.parse(this.$route.query.groupUserList).map(
        _ => _.openId
      );
    }
  },
  methods: {
    lock_friend_information(item) {
      console.log(item.friendId);
      //在vuex中管理状态，使用session，避免页面刷新数据消失，
      sessionStorage.setItem("Friend_List_item", JSON.stringify(item));
      //commit一次，再次让vuex状态重新获取一次值对象。
      this.$store.commit("setFriend_List_item");
      // this.$router.push({ name: "Friend_Information_" });
    },
    addGroupUser() {
      this.$Request_post(this.$AXIOS_URL + "/api/group/addGroupUser", {
        groupId: this.$route.query.groupId,
        openIds: this.result
      }).then(() => {
        Toast("添加成功");
        this.$router.push({
          name: "chat_room",
          query: {
            groupId: this.$route.query.groupId
          }
        });
      });
    }
  },
  components: {
    Cell,
    Toast,
    Empty
  }
};
</script>

<style></style>
