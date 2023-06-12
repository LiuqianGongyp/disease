module.exports = class BitSet {
  constructor(n) {
    this.buffer = new Uint8Array(Math.ceil(n / 8));
	// for(let i = 0;i < Math.ceil(n/8); i++){
	// 	this.buffer[i] = 255;
	// }
	
  }

  set(index) {
    const i = index / 8n;
    const bit = index % 8n; 
	this.buffer[Number(i)] |= (1 << Number(bit));
  }

  get(index) {
    const i = index / 8n;
    const bit = index % 8n;
	return (this.buffer[Number(i)] >> Number(bit)) & 1;
  }
}