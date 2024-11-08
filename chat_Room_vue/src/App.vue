<template>
  <div id="app">
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: "App"
};
</script>

<style>
.header {
  height: 44px;
  width: 100%;
  position: fixed;
  z-index: 999;
  background: #ebebeb;
}
#app {
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  /*text-align: center;*/
  color: #2c3e50;
  /*margin-top: 60px;*/
}
.action_sheet_box {
  width: 100%;
  padding: 0 20px;
  box-sizing: border-box;
  position: fixed;
  bottom: 57px;
  display: flex;
  justify-content: space-between;
}

.add-group {
  width: 100%;
  position: fixed;
  bottom: 70px;
  display: flex;
  justify-content: space-around;
}
</style>
