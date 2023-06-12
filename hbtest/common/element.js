//会改变原来的数
module.exports = class Element {
  constructor(x, y, infFlag, q) {
	  //椭圆曲线群中的点
    this.x = x;
    this.y = y;
    this.infFlag = infFlag;
	this.q = q;
	this.xx = 1n;
	this.yy = 0n;
	this.a = 1n;
  }
  //设置
  set(ele){
	  if (ele.infFlag != 0) {
		  this.infFlag = 1;
	      return this;
		  } 
	  else {
			  this.x = ele.x;
			  this.y = ele.y;
			  this.infFlag = 0;
			  return this;
			}
   }
   gcd(a,b) {
   	if(b==0n)
   	{
   		this.xx = 1n;
   		this.yy = 0n;
   		return a;
   	}
   	let d = this.gcd(b, (a + b) % b);
   	let t = this.xx;
	this.xx = this.yy;
	this.yy = t - a / b * this.yy;
   	return d;
   }

  invert(a,b){
	  this.gcd(a,b);
	  return (this.xx + b) % b;
  }
  // 乘法
  mul(ele) {
     if (this.infFlag != 0) {
		 this.set(ele);
        return this;
		}
	else {
		if (ele.infFlag != 0) {
            return this;
        }
		else if (this.x == ele.x) {
			if (this.y == ele.y) {
                if (this.y == 0n) {
                    this.infFlag = 1;
                    return this;
                } 
				else {
					let lambda = ((((this.x * this.x * 3n + this.q)% this.q) + this.a + this.q) % this.q) * this.invert((this.y+this.y)%this.q,this.q)%this.q;
                    let x3 = ((lambda * lambda + this.q) % this.q - this.x - ele.x + this.q)%this.q;
                    let y3 = ((this.x - x3) * lambda % this.q- this.y) % this.q;
					this.x = (x3 + this.q) % this.q;
					this.y = (y3 + this.q) % this.q;
					this.infFlag = 0;
                    return this;}
                    } 
			else {
                this.infFlag = 1;
                return this;
            }
        } 
		else {
            let lambda = (((ele.y - this.y + this.q) % this.q) * this.invert((ele.x-this.x + this.q)%this.q,this.q))%this.q;
            let x3 = ((lambda*lambda + this.q)%this.q - this.x - ele.x)%this.q;
			let y3 = ((this.x - x3 + this.q) * lambda % this.q - this.y) % this.q;
            this.x = (x3 + this.q) % this.q;
            this.y = (y3 + this.q) % this.q;
            this.infFlag = 0;
            return this;
        }
    }
  }
};