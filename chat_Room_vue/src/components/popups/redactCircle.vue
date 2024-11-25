<template>
  <div>
    <van-popup v-model="show" :overlay="true" position="bottom" :style="{ height: '100%' }">
      <!--
            作者：ayongnice@163.com
            时间：2020-10-19
            描述：头部导航栏
          -->
      <div style="height: 44px;width: 100%; background:#ffffff; position: fixed; z-index: 999;">
        <van-row>
          <van-col span="4">
            <van-icon style="margin: 10px; " size="24px" name="arrow-left" @click="circleClose"/>
          </van-col>
          <van-col span="16">
						<span style="font-size: 16px; display: flex; justify-content: center; line-height: 44px;">
							<!--胖友圈-->
						</span>
          </van-col>
          <van-col span="4">
            <!--<van-icon style="display: flex; justify-content: center; line-height: 44px;" size="26" name="photo-o" @click="()=> this.$refs.redact.onShow()"/>-->
            <div
              style="width: 50px; height: 30px; background: #07C160; border-radius: 4px; margin-top: 6px; margin-left: 6px;"
              @click="onSubmit">
              <span
                style="display: flex; line-height: 30px; justify-content: center; font-size: 14px; font-weight: bold; color: #ffffff;">发布</span>
            </div>
          </van-col>
        </van-row>
      </div>
      <div style="height: 44px; margin-bottom: 20px;"></div>

      <div style="width: 92%; margin-left: 4%;">
        <div style="width: 100%; height: 100%;">
          <textarea v-model="text" maxlength="500"
                    style="width: 100%; height:120px; font-size: 14px; font-weight: bold; border: 0px;"
                    placeholder="这一刻的想法..."></textarea>
        </div>

        <div style="width: 100%; height: 100%;">
          <van-uploader v-model="fileList" :max-size="1024 * 1024 * 2" :after-read="afterRead" @oversize="onOversize"
                        @delete="deleteFile"/>
        </div>
      </div>
    </van-popup>
  </div>
</template>

<script>
import {Popup, Uploader, Toast} from 'vant'

export default {
  name: "redactCircle",
  data() {
    return {
      show: false,
      text: "",
      fileList: [],
      uploadList: [],
      userInfos: {},

    }
  },
  mounted() {
    this.userInfos = JSON.parse(
      window.sessionStorage.getItem("userInfos") || "{}"
    );
  },
  methods: {
    onShow() {
      this.show = true
    },
    afterRead(file) {
      var formData = new FormData();
      formData.append('file', file.file)
      this.$Request_post(this.$AXIOS_URL + "/api/recording/upload", formData).then(res => {
        this.uploadList.push(res.data)
        if (res.data) {
          file.status = 'success'
          file.message = '上传成功'
          console.log(this.fileList)
        }
      })
    },
    onOversize(file) {
      Toast('文件大小不能超过 2mb');
    },
    deleteFile(file) {
      //				console.log(file)
      //				console.log(this.uploadList)
      //console.log(this.uploadList.indexOf(file.file.name))
      for (var i = 0; i < this.uploadList.length; i++) {
        //					console.log(this.uploadList[i])
        //					console.log(file.file.name)
        var a = this.uploadList[i].split(file.file.name)
        if (a.length == 2) {
          this.uploadList.splice(i, 1)
          //						console.log(this.uploadList)
          return
        }
      }
    },
    onSubmit() {
      var data = {
        message: this.text,
        uploadList: this.uploadList.join(","),
        userName: this.userInfos.username,
        avatar: this.userInfos.avatar,
        userId: this.userInfos.openId
      }
      this.$Request_post(this.$AXIOS_URL + "/api/circle/addCircle", data).then(res => {
        if (res.code === 200) {
          Toast.success('发布成功')
          this.$emit('onSuccess')
          this.circleClose()
        }
      })
    },
    circleClose() {
      this.text = ""
      this.fileList = []
      this.uploadList = []
      this.show = false
    }
  },
  components: {
    Popup,
    Uploader,
    Toast
  }
}
</script>

<style lang="scss" scoped>

</style>
