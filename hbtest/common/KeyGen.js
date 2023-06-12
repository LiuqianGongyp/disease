


module.exports = function getRndInteger(r) {
	//返回2到r-1的整数
	let sk = BigInt(Math.floor(Math.random() * (r - 1) ) + 2);
    return sk;
}