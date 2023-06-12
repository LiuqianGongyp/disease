//会改变原来的数
module.exports = class GTElement {
  constructor(x, y, q) {
	  //F_q[i]的子群
    this.x = x;
    this.y = y;
	this.q = q;
	this.xx = 1n;
	this.yy = 0n;
  }

  // 乘法
  mul(ele) {
	  let a,b;
	  a = (this.x * ele.x + this.q) % this.q - (this.y * ele.y + this.q) % this.q;
	  b = (this.x * ele.y + this.q) % this.q + (ele.x * this.y + this.q) % this.q;
	  this.x = (a + this.q) % this.q;
	  this.y = (b + this.q) % this.q;
	  return this;
     }
  gcd(a,b) {
   	if(b==0n)
   	{
   		this.xx=1n;
   		this.yy=0n;
   		return a;
   	}
   	let d = this.gcd(b,a%b);
   	let t = this.xx;
	this.xx = this.yy;
	this.yy = t - a/b*this.yy;
   	return d;
   }
  invert(a,b){//a关于b的逆
	  this.gcd(a,b);
	  return (this.xx+b)%b;
  }
  inv(){
	  let c,d;
	  let temp = ((this.x * this.x + this.q) % this.q + (this.y * this.y + this.q) % this.q + this.q) % this.q;
	  c = (this.x * this.invert(temp, this.q) + this.q) % this.q;
	  d = ((-this.y + this.q) % this.q * this.invert(temp, this.q) + this.q) % this.q;
	  this.x = (c + this.q) % this.q;
	  this.y = (d + this.q) % this.q;
	  return this;
  }
  
};