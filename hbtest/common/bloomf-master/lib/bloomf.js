const BitSet = require('./bitset');

module.exports = class BloomF {
  constructor(errorRate, maxKeys) {
    this.n = maxKeys * Math.ceil((-Math.log(errorRate) / (Math.log(2) * Math.log(2))) );
    this.kHashes = Math.ceil(Math.log(2) * (this.n / maxKeys));
    this.filter = new BitSet(this.n);
  }


  // this generates all the necessary hashes for an element to be inserted in our set.
  generateHashes(element) {
    let hashes = [];
	const sm3 = require('../../miniprogram-sm-crypto/src/index').sm3;
	let hashData = sm3(element); // 杂凑
	let hash1 = BigInt("0x" + hashData.substr(0,32));
	let hash2 = BigInt("0x" + hashData.substr(32,32));
  
    for (let i = 0; i < this.kHashes; i++) {
      hashes.push(hash2 % BigInt(this.n));
	  hash2 = ( hash2 + hash1 ) % BigInt(this.n);
    }
    return hashes;
  }

  // insert sets the bits of the indexes from the hashes
  insert(element){
    const indexes = this.generateHashes(element);
	for(let index = 0; index < this.kHashes; index++){
		this.filter.set(indexes[index]);
	}
     
  }

  // test checks whether the given element exists in the bloom filter.
  // it sums over all the target hashes in the filter and all the bits have to be one.
  // the number of ones should be equal to the number of hashing functions.
  test(element) {
    const indexes = this.generateHashes(element);
	for(let index = 0; index < this.kHashes; index++){
		if(this.filter.get(indexes[index])){
			return false;
		}
	}
    return true;
  }
};