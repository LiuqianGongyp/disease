<template>
  <view>
    <text class="biaoti">敏感人群追踪自查小程序使用说明\n</text>
    <text class="zbiaoti">欢迎使用小程序进行敏感人群轨迹查询！\n</text>
    <text class="first">一、小程序介绍\n</text>
    <text class="letter">该小程序向用户提供查询是否与感染病患者或密接人员存在轨迹交集的功能。用户通过输入时间区间以及所经过的地点来查询是否与敏感人群存在时空交集的情况，查询结果直接通过跳转页面返回给用户，从而实现更快速、准确和科学地进行防控工作，最大程度限制传染病传播，维护公共卫生安全体系建设。\n</text>
    <text class="first">二、用户须知\n</text>
    <text class="letter">本小程序保证，该项目方案基于隐私保护集合求交的高效密码协议，用户所提交的查询信息非公开，系统不会获得任何用户敏感信息，请放心使用。\n</text>
    <text class="first">三、使用帮助\n</text>
    <text class="letter">用户首次使用时，可以直接点击首页“自查”按钮进行轨迹追踪自查操作。点击时间选择器，选择要查询的时间日期以及时间区间；点击地点输入框右侧的图标选择待查询地点。该步骤使用地图选点功能，便于用户更加快速准确输入位置信息，保证查询结果的准确性。本小程序还提供查询多次的功能，用户点击“添加”按钮，增添输入项可以实现多结果查询操作。若想删除输入项，只需要点击“删除”按钮即可。信息填写完毕后，点击“提交”即可跳转查询结果页面，获得最终查询结果。</text>
  </view>
</template>

<script>
  export default {
    data() {
      return {
        
      };
    }
  }
</script>

<style lang="scss">
  .biaoti{
    font-size: 45rpx;
    font-weight:bold;
    display: flex;
    align-items:center;
    justify-content:center;
    margin-top: 25px;
  }
  .zbiaoti{
    display: flex;
    align-items:center;
    justify-content:center;
    margin-top: 15px;
  }
  .first{
    font-weight:bold;
    display: flex;
    align-items:center;
    justify-content:center;
    margin-top: 15px;
    margin-left: 50rpx;
    margin-right: 50rpx;
  }
  .letter{
    display: flex;
    align-items:center;
    justify-content:center;
    margin-top: 15px;
    margin-left: 50rpx;
    margin-right: 50rpx;
  }
</style>
