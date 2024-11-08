<template>
  <div style="position: fixed;bottom: 52px;width: 100%;">
    <v-emoji-picker
      @changeCategory="onCategoryChange"
      style="width: 100%;"
      @select="onSelectEmoji"
    />
    <div v-show="mojiPackage" class="warp">
      <img
        :src="baseFileUrl + item.fileName"
        v-for="item in listMojiPackage"
        :key="item.fileName"
        @click="sendMessage(item.fileName)"
      />
    </div>
  </div>
</template>

<script>
import { baseFileUrl } from "@/utils/utils";

export default {
  props: {
    /**
     * 是否显示表情包
     */
    showListMojiPackage: {
      type: Boolean,
      default: true
    }
  },
  data() {
    return {
      selectedEmoji: "",
      mojiPackage: false,
      listMojiPackage: [],
      baseFileUrl
    };
  },

  mounted() {
    if (!this.showListMojiPackage) return;
    this.$Request_post(this.$AXIOS_URL + "/api/menes/getMenesList").then(
      ({ data }) => {
        this.listMojiPackage = data;
      }
    );

    //获取 ID 为Categories 的 dom 给他子元素前 追加一个元素
    this.$nextTick(() => {
      var dom = document.getElementById("Categories");
      var newDiv = document.createElement("div");
      newDiv.innerHTML = `<i style="line-height: 27px; font-size: 25px;" class="van-icon van-icon-star"></i>`;
      dom.insertBefore(newDiv, dom.firstChild);
      //添加点击事件监听
      newDiv.onclick = () => {
        this.mojiPackage = true;
      };
    });
  },
  methods: {
    onSelectEmoji(emoji) {
      this.$emit("selectEmoji", emoji.data);
    },
    onCategoryChange() {
      this.mojiPackage = false;
    },
    sendMessage(fileName) {
      this.$emit("sendMessage", fileName, "img");
    }
  }
};
</script>

<style lang="scss" scoped>
/* 你可以在这里添加一些样式 */

.warp {
  width: 100%;
  height: 93%;
  background: #fff;
  position: absolute;
  top: 37px;
  left: 0;
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
  padding: 0 10px;
  box-sizing: border-box;

  img {
    width: 90px;
    height: 90px;
    margin: 10px 10px 0 0;
    &:nth-child(4) {
      margin-right: 0;
    }
  }
}
</style>
