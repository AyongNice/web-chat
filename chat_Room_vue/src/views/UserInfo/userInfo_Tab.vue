<template>
  <div>
    <div style="width: 100%; height: 200px; background: #FFFFFF;">
      <div style="display: flex; margin-top: 0px;">
        <div style="display: flex; margin-top: 30px;">
          <div style="padding: 15px;">
            <img
              style="width: 60px; height: 60px; border-radius: 8px;"
              :src="userInfos.avatar"
            />
          </div>
          <div style="padding: 15px 0px 0px 0px;">
            <span style="display: flex; font-size: 16px; font-weight: bold;">{{
              userInfos.username
            }}</span>
            <span
              style="display: flex; font-size: 14px; color: darkgray; margin-top: 10px;"
              >ID：{{ userInfos.email }}</span
            >
          </div>
        </div>
      </div>
    </div>

    <van-cell title="个人信息" is-link to="userInfo" />
    <van-cell title="我的相册" is-link to="photo_home" />
    <!--功能待定-->
    <van-cell title="功能待定" is-link />
    <van-cell title="功能待定" is-link />
  </div>
</template>

<script>
import { Cell, CellGroup } from "vant";
export default {
  name: "userInfo_Tab",
  data() {
    return {
      userInfos: {}
    };
  },
  mounted() {
    this.userInfos = JSON.parse(
      window.sessionStorage.getItem("userInfos") || "{}"
    );
  },
  methods: {},
  components: {
    Cell,
    CellGroup
  }
};
</script>

<style lang="scss" scoped></style>
