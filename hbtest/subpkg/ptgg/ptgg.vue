<template>
	<view>
    <text class="pbiaoti">敏感人群追踪自查小程序平台公告\n</text>
		<text class="pletter">敏感人群追踪自查小程序提供查询的数据范围是截止至当前查询日期的近7天内和传染病患者及密接人员的相关流调信息数据，但由于相关权限限制，该小程序目前无法获得全国范围内完整相关数据，暂时仍处于测试阶段。我们通过随机地址生成器随机生成大量测试数据用来检测该功能的查询情况，测试结果准确无误且运行时间合理。同时我们引用高效密码算法，保证流调的人员隐私信息被加密，保证了人民群众的个人信息安全，实现了在保证数据隐私安全的同时实现高效便捷的敏感人群查询功能，相信在未来科学防控工作方面将会发挥巨大作用。\n</text>
    <text class="pletter">由于各地防疫机构上传数据存在不等时性，不排除查询时存在数据局限等因素，可能查询结果存在误差。请及时关注自身健康，做好防护，查询结果仅供参考。</text>
	</view>
</template>
			 

<script>
  export default {
  	data() {
  		return {
        
  		}
  	},
  	methods: {
      
  	}
  }

</script>

<style lang="scss">
  .pbiaoti{
    font-size: 45rpx;
    font-weight:bold;
    display: flex;
    align-items:center;
    justify-content:center;
    margin-top: 25px;
  }
  .pletter{
    margin-top: 60rpx;
    display:flex;
    align-items:center;
    justify-content:center;
    margin-left: 50rpx;
    margin-right: 50rpx;
  }
</style>
