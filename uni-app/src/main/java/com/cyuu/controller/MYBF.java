package com.cyuu.controller;

import java.math.BigInteger;
import java.util.BitSet;
import java.util.concurrent.atomic.AtomicInteger;

public class MYBF {
    private static final String ENCODING = "UTF-8";
    /**
     * 位数组的大小
     */
    public int BITSIZE;
    /**
     * 哈希函数的个数
     */
    private int NUMHASH;
    /**
     * 处理的数据规模
     */
    private long DATASIZE;
    /**
     * 位数组。数组中的元素只能是 0 或者 1
     */
    public BitSet bits;
    /**
     * 假阳率
     */
    private double FPP;


    /**
     * 使用数量
     */
    private final AtomicInteger useCount = new AtomicInteger(0);


    /**
     *
     * @param FPP 假阳率
     * @param DATASIZE 预期处理的数据规模
     */
    public MYBF(double FPP, long DATASIZE){
        this.DATASIZE = DATASIZE;
        this.FPP = FPP;
        Double temp = this.DATASIZE * Math.ceil(- Math.log(this.FPP) / (Math.log(2) * Math.log(2)));
        this.BITSIZE = temp.intValue();
        if (this.BITSIZE < 0 || this.BITSIZE > Integer.MAX_VALUE) {
            throw new RuntimeException("位数太大溢出了，请提高假阳率或者降低数据大小");
        }
        bits = new BitSet(this.BITSIZE);
//        bits.set(0,this.BITSIZE);
        temp= Math.ceil(-Math.log(this.FPP) / (Math.log(2)));
        this.NUMHASH = temp.intValue();
    }



    /**
     * 添加元素到位数组
     */
    public void add(Object value) {
        String temp = SM3Utils.encrypt(value.toString());
        BigInteger hash1 = new BigInteger( temp.substring(0,32), 16);
        BigInteger hash2 = new BigInteger( temp.substring(32,64), 16);
        for(int i = 1; i <= NUMHASH; ++i) {
            int index = hash2.mod(BigInteger.valueOf(BITSIZE)).intValue();
            bits.set(index);
            hash2 = hash1.add(hash2).mod(BigInteger.valueOf(BITSIZE));
        }
        useCount.getAndIncrement();

    }

    /**
     * 判断指定元素是否存在于位数组
     */
    public boolean contains(Object value) {
        boolean ret = true;
        String temp = SM3Utils.encrypt(value.toString());
        BigInteger hash1 = new BigInteger( temp.substring(0,32), 16);
        BigInteger hash2 = new BigInteger( temp.substring(32,64), 16);
        for(int i = 1; i <= NUMHASH; ++i) {
            int index = hash2.mod(BigInteger.valueOf(BITSIZE)).intValue();
            if (!bits.get(index)) {
                ret = false;
                break;
            }
            hash2 = hash1.add(hash2).mod(BigInteger.valueOf(BITSIZE));
        }
        return ret;
    }

    public static void main(String[] args) {
        String value1 = "abc";
        String value2 = "ddbgbfgbfbbgfb";
        MYBF filter = new MYBF(1e-3,100);
        System.out.println(filter.contains(value1));
        System.out.println(filter.contains(value2));
        filter.add(value1);
        filter.add(value2);
        System.out.println(filter.contains(value1));
        System.out.println(filter.contains(value2));
        Integer value11 = 13423;
        Integer value21 = 22131;
        System.out.println(filter.contains(value11));
        System.out.println(filter.contains(value21));
        filter.add(value11);
        filter.add(value21);
        System.out.println(filter.contains(value11));
        System.out.println(filter.contains(value21));
    }

}
