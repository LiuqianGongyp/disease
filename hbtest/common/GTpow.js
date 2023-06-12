let GTelement = require('../common/GTElement');


module.exports = function (a, n, q) {//求a的n次方
   let ans = new GTelement(1n,0n,q);
   while(n != 0n){
  		  if((n&1n) == 1n) {       //如果n的当前末位为1
  	          ans.mul(a);  //ans乘上当前的a
  	      }
  	    a.mul(a);        //a自乘
  	    n = n >> 1n;       //n往右移一位
  	}
  	return ans;
}
	