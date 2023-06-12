<template>
  <view>
    <!--无交集，安全页面 0-->
    <block v-if="flag[0]==0">
      <view class="result">
        <uni-icons type="checkbox" size="60" color="green"></uni-icons>
        <text class="result.word" style="color: seagreen;">安全!</text>
      </view>
      <view class="time">
        <uni-dateformat :date="now - 7200000"></uni-dateformat>
      </view>
      <view class="title">
        <text>恭喜！数据库中未查询到您与该传染病确诊人员有轨迹交集，请您继续做好有效防护，保持身体健康！</text>
      </view>
      <view><image src="../../static/picture1.png"></image></view>
    </block>
    <!--有交集，警告页面 1-->
    <block v-if="flag[0]==1">
      <view class="result">
        <uni-icons type="close" size="60" color="red"></uni-icons>
        <text class="result.word" style="color: red;">危险!</text>
      </view>
      <view class="time">
        <uni-dateformat :date="now - 7200000"></uni-dateformat>
      </view>
      <view class="showresult">可能存在风险轨迹：</view>
      <view class="showresult" v-for="(item,index) in showword" :key="item.id">{{item}}</view>
      <view class="title">
        <text>请注意！根据数据库查询，您近期可能与该传染病确诊人员有轨迹交集，但暂不能确认您的健康状况。请您主动上报，积极配合防治工作，如有疑问，请咨询当地防疫办电话或致电政务服务热线，感谢您的配合！</text>
      </view>
      <view><image src="../../static/picture2.png"></image></view>
    </block>
  </view>
</template>


<script>
  export default {
    data() {
      return {
        flag: [],
		showword: [],
        now: Date.now()
      };
    },
    onLoad() {
      this.getFlag()
    },
    methods: {
      async getFlag() {
        uni.showLoading({
          title: '正在加载中...',
          mask: true
        });
        await uni.$http.get('/info/get').then(res => {
          //console.log(res.data);
          uni.hideLoading();
          // let page = getCurrentPages().pop();
          // if(!page) return;
          // page.onLoad();//页面存在重新刷新页面

          // console.log(res.data)
          //console.log(res);
		  var bitsize = 10000;
		  //验签
		  const sm2 = require('../../common/miniprogram-sm-crypto/src/index').sm2
		  let msg = "";
		  for (i = 0; i < bitsize; i++) {
		    msg = msg + res.data[0][i] + res.data[1][i] + res.data[2][i] + res.data[3][i];
		  }
		  let publicKey = '04bd2644b35e1263be9491221913d71256597fed4a61a6a94be06bafc1806919fc33ebe6928797b20fc480ee91b218c16ecc82f0eff0e5dd556aea18cd8addad8e';//keypair.publicKey // 公钥
		  let verifyResult1 = sm2.doVerifySignature(msg, res.data[4][0], publicKey, {
			  hash: true,
			  userId: '1234567812345678',
			  der:true,
		  })
		  let verifyResult2 = sm2.doVerifySignature('feedback', res.data[4][1], publicKey, {
			  hash: true,
			  userId: '1234567812345678',
			  der:true,
		  })
		  if(verifyResult1&verifyResult2){
			  //解密
			  let q = 683n;//2691853091n;
			  let order = 19n;//61441n;
			  let sk = 11n; //对应修订
			  let gx = 169n//542669622n;
			  let gy = 30n//1778955512n;
			  let point = require('../../common/element');
			  let usetoinv = new point(gx, gy, 0, q);
			  let skinv = usetoinv.invert(sk, order);
			  let flag = 0;
			  let GTelement = require('../../common/GTElement');
			  let eggpowzn = require('../../common/GTpow');
			  var i;
			  let zero = new GTelement(1n, 0n, q);
			  const BloomFilter = require('../../common/bloomf-master/lib/bloomf');
			  const Fpp = 0.01;
			  const Maxsize = 1000;
			  const bl = new BloomFilter(Fpp, Maxsize);
			  for (i = 0; i < bitsize; i++) {
				let c11 = new GTelement(BigInt(res.data[0][i]), BigInt(res.data[1][i]), q);
				let c22 = new GTelement(BigInt(res.data[2][i]), BigInt(res.data[3][i]), q);
				let rho = eggpowzn(c11, skinv, q);
				let m = c22.mul(rho.inv());
				if (m.x == zero.x && m.y == zero.y) {
				  bl.filter.set(BigInt(i));
				}
			  }
			  try {
				const value = uni.getStorageSync('setName');
				if (value) {
				  let L = [];
				  L = value;
				  let Ori = [];
				  let identifacation = [];
				  let have = [];
				  try {
				  		const Orignaldata = uni.getStorageSync('Oridata');
						if(Orignaldata){
							Ori = Orignaldata;
						}
					}catch(e){
						console.log(e); // error
					}
				try {
						const ident = uni.getStorageSync('indentify');
						if(ident){
							identifacation = ident;
						}
					}catch(e){
						console.log(e); // error
					}
				  for (var i = 0; i < L.length; i++) {
					if (bl.test(value[i])) {
					  flag = 1;
					  if(have.includes(identifacation[i])){
						  continue
					  }
					  this.showword.push(Ori[i]);
					  have.push(identifacation[i]);
					}
				  }
				}
			  } catch (e) {
				console.log(e); // error
			  }
			  if (flag == 0) {
				let timeee = new Date().valueOf();
				console.log(timeee);
				console.log("安全");
			  } else {
				let timeee = new Date().valueOf();
				console.log(timeee);
				console.log("有交集");
			  }
			  this.flag.push(flag);
		  }
		  else{
			  console.log('验签失败')
		  }
		  console.log(this.showword)
        })
         // this.searchList = res.data;
      }
    }
  }
</script>

<style lang="scss">
  image{
    margin-top: 20px;
    width: 100%
  }
  .result{
    display: flex;
    align-items: center;
    uni-icons{
      margin-top: 100px;
      margin-left:125px;
    };
    text{
      margin-top: 99px;
      font-size: 60rpx;
      color: seagreen;
      margin-left: 5px;
    };
  }
  .time{
    display: flex;
    align-items: center;
    margin-left:125px;
    margin-top: 10px;
  }
  .title{
    margin-top: 10px;
    font-size: 15px;
    color:#909399;
    margin-left: 50px;
    margin-right: 50px;
  }
  .showresult{
      margin-top: 10px;
      font-size: 15px;
      color:#D87093;
      margin-left: 50px;
      margin-right: 50px;
      font-weight: 900;
    }
</style>