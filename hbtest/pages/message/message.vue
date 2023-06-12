<template>
  <view>
    <view>
      <view v-if="userInfo != ''" class="my-userinfo-container">
        <!-- 头像昵称区域 -->
        <view class="top-box">
          <image :src="userInfo.avatarUrl" class="avatar"></image>
          <view class="nickname">{{userInfo.nickName}}</view>
        </view>
      </view>
      <view v-else class="top-box">
        <!-- <button class="login-btn" type="primary" @click="getUserInfo">
          微信用户一键登录
        </button> -->
        <image src="../../static/title3.png"></image>
        <view class='content'>
          <view>申请获取以下权限</view>
          <text>获得你的公开信息(昵称，头像等)</text>
        </view>
        <button class='bottom' type='primary' @click="getUserInfo">
          授权登录
        </button>
        
      </view>
    </view>
  
    <!-- <image src="../../static/title3.png"></image> -->
    <view class="buttons">
    <button type="default" @tap="goto('/subpkg/ptgg/ptgg')">
      <view>
        <uni-icons type="sound" size="25" color="blue" class="tb"></uni-icons>
        <text class="sybz-text">平台公告</text>
      </view>
    </button>
    <button type="default" @tap="goto('/subpkg/sybz/sybz')">
      <view>
        <uni-icons type="star" size="25" color="green" class="tb"></uni-icons>
        <text class="sybz-text">使用帮助</text>
      </view>
    </button>
    <button type="default" @tap="goto('/subpkg/lxwm/lxwm')">
      <view>
        <uni-icons type="email" size="25" color="red" class="tb"></uni-icons>
        <text class="sybz-text">联系我们</text>
      </view>
    </button>
    </view>
  </view>
</template>

<script>
  export default {
    data() {
      return {
        userInfo: []
        
      };
    },
    methods: {
      goto(url) {
        uni.navigateTo({
          url:url
        })
      },
      // 用户授权之后，获取用户的基本信息
      async getUserInfo(e) {
        var sessionId = '';
        console.log(e);
        uni.login({
          provider: 'weixin',
          success: async (res) => {
            const code = res.code;
            console.log('登录获取code', res.code);
            if(res.code){
              const res = await uni.$http.get('/weixin/sessionId/' + code);
              console.log(res)
              if(res.statusCode == 200){
                // console.log(res.data);
                sessionId = res.data;
              }
            }
          },
        })
        uni.getUserProfile({
          desc: '登录后可同步数据',
          success: async (res) => {
            var encryptedData = res.encryptedData;
            var iv= res.iv;
            console.log(res.encryptedData);
            console.log(res.iv)
        
            // 携带data的参数去获得token
            const res2 = await uni.request({
              url:'http://localhost:8090/weixin/authLogin',
              method:'POST',
              data: {
                "encryptedData" : encryptedData,
                "iv": iv,
                "sessionId": sessionId
              },
              dataType:'json'
            })
            console.log('获取res2成功');
            if(res2[1].data.code == 0){
              // const userInfo = res2.data;
              // const token = res2.datatoken;
              // console.log(userInfo);
              // console.log(token);
              this.userInfo = res2[1].data.data;
              console.log(this.userInfo.nickName)
              // uni.setStorageSync('userInfo', userInfo);
              // uni.setStorageSync('token', token);
              // uni.navigateBack({
              //   delta:1,
              // })
            }else{
              console.log(res2.errMsg);
              uni.showToast({
                title:'登录失败',
                icon:'error',
              })
            }
          }
        })
      },
    }
  }
</script>

<style lang="scss">
   image{
    width: 100%;
  }
  .content {
    margin-top: 50rpx;
      margin-left: 50rpx;
      margin-bottom: 50rpx;
  }
  .content text {
      display: block;
      color: #9d9d9d;
      margin-top: 20rpx;
  }
  .bottom {
      border-radius: 80rpx;
      margin: 50rpx 50rpx;
      font-size: 35rpx;
  }
  .buttons{
    margin-top: 40px;
    height: 40px;
  }
  .sybz-text{
    height: 40px;
    font-size: 20px;
    margin-left: 10rpx;
  }
    
  .buttons{
    margin-top: 50rpx;
    height: 40px;
  }
  .sybz-text{
    height: 40px;
    font-size: 20px;
    margin-left: 10rpx;
  }
  image{
    width: 100%
  }
  
  .login-btn{
    background-color: #55aa7f;
  }
  
  .my-userinfo-container {
    height: 100%;
    // 为整个组件的结构添加浅灰色的背景
    background-color: #f4f4f4;
  
    .top-box {
      height: 400rpx;
      background-color: #55aa7f;
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;
  
      .avatar {
        display: block;
        width: 90px;
        height: 90px;
        border-radius: 45px;
        border: 2px solid white;
        box-shadow: 0 1px 5px black;
      }
  
      .nickname {
        color: white;
        font-weight: bold;
        font-size: 16px;
        margin-top: 10px;
      }
    }
  }
</style>
