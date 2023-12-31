[![Build Status](https://travis-ci.org/Khalilw1/bloomf.svg?branch=master)](https://travis-ci.org/Khalilw1/bloomf)
[![Coverage Status](https://coveralls.io/repos/github/Khalilw1/bloomf/badge.svg?branch=master)](https://coveralls.io/github/Khalilw1/bloomf?branch=master)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

# bloomf

This package implements a bloom filter for a general type of usage. It uses FNV and a simple trick to compute the k hashes required.

# features

- Initializing Bloom Filter requires only the size of the filter and the number of hashing functions.
- Usage of Uint8Array TypedArray to ensure minimal memory footprint.
- Usage of bit operation to operate on our bit set implies better performance (to be test thouroughtly).
- FNV hash and simple linear hash used as hashing functions.
- Possibility to insert different element types (Number, String) by converting them into strings.

# Usage

```js

const BloomFilter = require('bloomf');

const filterSize = 10;
const kHashes = 3;

const bl = new BloomFilter(filterSize, kHashes);

bl.insert(3);
bl.insert("bloblo");

bl.test(3) // returns true
bl.test("bloblo") // returns true

```

# Improvements
- Use better hashing functions than the linear hashing function
- offer multiple constructors
- Check how feasable it is to transform any object into bytes in JavaScript