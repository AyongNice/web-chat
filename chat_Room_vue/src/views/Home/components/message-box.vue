<template>
  <van-uploader
    :after-read="afterRead"
    :max-size="1024 * 1024 * 1"
    @oversize="onOversize"
    style="padding: 8px;"
    preview-size="40"
  />
</template>

<script>
export default {
  methods: {
    afterRead(file) {
      //		   		console.log(file.content)
      var formData = new FormData();
      formData.append("file", file.file);
      this.$Request_post(
        this.$AXIOS_URL + "/api/recording/upload",
        formData
      ).then(res => {
        this.$emit("afterRead", res.data, "img");
        this.scrollToBottom();
      });
    },
    onOversize(file) {
      Toast("文件大小不能超过 1MB");
    }
  }
};
</script>
