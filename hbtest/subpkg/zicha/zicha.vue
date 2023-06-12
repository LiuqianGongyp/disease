<script>
  export default {
  	data() {
  		return {
  			// 表单数据
			dynamicFormData: {
				num: [],
				address: [],
				time:[],
			}
      }
    },
    
	mounted(){
		this.add();
	},
	methods:{
      onClick(id){
		  //在页面设置缓存
		          try {
		            uni.setStorageSync('ADDindex',id);        
		          } catch (e) {// error
		          }
        const key = 'GMWBZ-Y2PC3-4VD3K-3WTIF-7BWI3-2LB2Q'; //使用在腾讯位置服务申请的key
        const referer = '地点定位'; //调用插件的app的名称
        const location = JSON.stringify({
        					latitude: 39.89631551,
        					longitude: 116.323459711
        				});
        const category = '生活服务,娱乐休闲';
        wx.navigateTo({
          url: 'plugin://chooseLocation/index?key=' + key + '&referer=' + referer + '&location=' + location + '&category=' + category
        });
      },
      onShow() {
      			const chooseLocation = requirePlugin('chooseLocation');
      			const location = chooseLocation.getLocation(); // 如果点击确认选点按钮，则返回选点结果对象，否则返回null
				if(location){
					try {
					  const id = uni.getStorageSync('ADDindex');
					  this.dynamicFormData.address[id].value = location.address;
					} catch (e) {
					  console.log(e); // error
					}
					
				}
      		},
		add() {
			this.dynamicFormData.num.push({})
			this.dynamicFormData.time.push({
				label: '时间',
				value: '',
				rules: [{
					'required': true,
					errorMessage: '时间项必填'
				}],
				id: Date.now()
			})
			this.dynamicFormData.address.push({
				label: '地点',
				value: '',
				rules: [{
					'required': true,
					errorMessage: '地点项必填'
				}],
				id: Date.now()
			})
		},
		
		del() {
			this.dynamicFormData.num.pop()
			this.dynamicFormData.time.pop()
			this.dynamicFormData.address.pop()
		},
		
		submit(ref) {
			uni.showLoading({
			          title: '正在加载中...',
			          mask:true
			        });
			let timeee = new Date().valueOf();
			console.log(timeee);
			let data = [];
			let OriData = [];
			let identification = [];
			for(let index = 0; index <this.dynamicFormData.address.length; index++){
				let starttime0 = this.dynamicFormData.time[index].value[0];
				let endtime0 = this.dynamicFormData.time[index].value[1];
				let information = '在' + this.dynamicFormData.time[index].value[0] + '到' + this.dynamicFormData.time[index].value[1] + '期间您去了' + this.dynamicFormData.address[index].value
				var starttime1 = '';
				var endtime1 = '';
				let order = [0,1,2,3,5,6,8,9,11,12];//,14,15];
				for(let i = 0; i < 10; i++){
					starttime1 = starttime1 + starttime0[order[i]];
					endtime1 = endtime1 + endtime0[order[i]];
				}
				let a = BigInt(starttime1);
				let b = BigInt(endtime1);
				for(let i = a; i<= b; i++){
					if(((i)%100n) == 24n){
						i = (i/100n + 1n) * 100n;
					}
					let month = (i/10000n)%100n;
					if(month == 1n||month ==3n||month == 5n||month ==7n||month ==8n||month ==10n||month == 12n){
						if((i/100n)%100n==32n){
							i = (i/10000n + 1n) *10000n + 100n;
						}
					}else if(month ==2n){
						let year = i/1000000n;
						if(year%100n==0n){
							if(year%400n==0n){
								if((i/100n)%100n==30n){
									i = (i/10000n + 1n) *10000n + 100n;
								}
							}
							else{
								if((i/100n)%100n==29n){
									i = (i/10000n + 1n) *10000n + 100n;
								}
							}
						}else{
							if(year%4n==0n){
								if((i/100n)%100n==30n){
									i = (i/10000n + 1n) *10000n + 100n;
								}
							}
							else{
								if((i/100n)%100n==29n){
									i = (i/10000n + 1n) *10000n + 100n;
								}
							}
						}
					}else{
						if((i/100n)%100n==31n){
							i = (i/10000n + 1n) *10000n+ 100n;
						}
					}
					if((i/10000n)%100n==13n){
						i = (i/1000000n + 1n) * 1000000n+ 10000n +100n;
					}
					let rr = this.dynamicFormData.address[index].value + i.toString();
					data.push(rr);
					OriData.push(information);
					identification.push(index);
				}
			}
			try {
			      uni.setStorageSync('setName',data); 
				  uni.setStorageSync('Oridata',OriData);
				  uni.setStorageSync('indentify',identification);
			    } catch (e) {// error
			    }
			    let q = 683n;//2691853091n;
			    let point = require('../../common/element');
			    let gx = 169n;//542669622n;
			    let gy = 30n;//1778955512n;
			//     order = 19n;
			//     let ordernum = 19;
			//     let g = new point(gx,gy,0,q);
			//     let GTelement = require('../../common/GTElement');
			//     let eggx = 562n;
			//     let eggy = 200n;
			//     let egg = new GTelement(eggx, eggy,q);
			//     let randomZr = require('../../common/KeyGen');
			    let sk = 11n;//私钥
			    let gpowzn = require('../../common/curGpow');
			    let temp = new point(gx,gy,0,q);
			    let pk = gpowzn(temp, sk, q);//公钥
			//     //公钥需要传输这三个部分
			    let pkx = pk.x;
			    let pky = pk.y;
			    let pkinfflag = pk.infFlag;
			//     //加密
			//     let eggpowzn = require('../../common/GTpow');
			//     let bitsize = 10000n;
			    
			//     for(let i=0;i<bitsize;i++){
			//       let rij = randomZr(ordernum);
			//       temp = new point(pkx,pky,pkinfflag,q);
			//       let cipher1 = gpowzn(temp, rij, q);
			//       temp = new GTelement(eggx,eggy,q);
			//       let temp2 = eggpowzn(temp, rij, q);
			//       let cipher2;
			//       if(bl.filter.get(BigInt(i))){
			//         temp = new GTelement(eggx,eggy,q);
			//         cipher2 = eggpowzn(temp, 1n, q).mul(temp2);//对1加密
			//       } else{
			//         temp = new GTelement(eggx,eggy,q);
			//         cipher2 = eggpowzn(temp, 0n, q).mul(temp2);//对0加密
			//       }
				  // //console.log(rij);
			//       shuzu1[i] = cipher1.x;
			//       shuzu2[i] = cipher1.y;
			//       shuzu3[i] = cipher2.x;
			//       shuzu4[i] = cipher2.y;
			//       shuzu5[i] = cipher1.infFlag;
			//     }
			    // console.log(shuzu1)
			    // console.log(shuzu2)
				for(let i = 0; i<data.length;i++){
					console.log(data[i]);
				}
			    this.$refs[ref][0]
			      .validate()
			      .then(res => {
			        // console.log(res)
			        uni.request({
			          url:"http://localhost:8090/info/create",
			          method:'POST',
			          data: {
			            // c11: shuzu1.toString(),
			            // c22: shuzu2.toString(),
			            // c33: shuzu3.toString(),
			            // c44: shuzu4.toString(),
			            // c5: shuzu5,
			            pk111: String(pkx),
			            pk222: String(pky),
			            pk33: pkinfflag
			          },
			          header:{
			            "Content-Type": "application/x-www-form-urlencoded"
			          },
			          dataType:'json'
			        }).then(res => {
			          // 请求是异步的
			          console.log('submit: ', res);
					  
			          uni.navigateTo({
			            url:"/subpkg/result/result",
			          });
			          uni.hideLoading();
			        })
			      })
			    
			  }
			},
      
  }
</script>

<template>
	<view class="container">
		<uni-section title="自查自检" type="line">
				<view class="example">
					<!-- 动态表单校验 -->
					<uni-forms ref="dynamicForm" :modelValue="dynamicFormData" v-for ="(item,index) in dynamicFormData.num" :model="dynamicFormData" labelWidth="80px">
						<uni-forms-item label="时间" required name="time">
							<uni-datetime-picker v-model="dynamicFormData.time[index].value" type="datetimerange"></uni-datetime-picker>
						</uni-forms-item>
						<uni-forms-item label="地点" required name="address">
							<uni-easyinput suffixIcon="search" v-model="dynamicFormData.address[index].value" placeholder="请点击图标选择查询地点" @iconClick="onClick(index)"/>
						</uni-forms-item>
						
						
					</uni-forms>
					<view class="button-group">
						<button type="primary" style="width: 45%; margin-top: 10px;" size="mini" @click="add">添加一组</button>
						<button type="default" style="width: 45%; margin-top: 10px;" size="mini" @click="del">删除一组</button>
					</view>
					<button type="primary" style="width: 95%; margin-top: 20px; margin-left: 8px;" @click="submit('dynamicForm')">提交</button>
				</view>
		</uni-section>
	</view>
</template>
			 
<style lang="scss" scoped>
 .example {
 	padding: 15px;
 	background-color: #fff;
 }
 
 .segmented-control {
 	margin-bottom: 15px;
 }
 
 .button-group {
 	margin-top: 15px;
 	display: flex;
 	justify-content: space-around;
 }
 
 .form-item {
 	display: flex;
 	align-items: center;
 	flex: 1;
 }
 
 .button {
 	display: flex;
 	align-items: center;
 	height: 35px;
 	line-height: 35px;
 	margin-left: 10px;
 }
</style>

