package com.cyuu.controller;

import it.unisa.dia.gas.jpbc.Element;

public class party {
    Element sk,pk;
    long v;//集合大小
    Element[] cipher1;
    Element[] cipher2;
    String pathname;
    public party(Element sk,Element pk,int index){
        this.sk = sk;
        this.pk = pk;
        v = 10000;
        pathname = new String();
        pathname = "" + index;
        pathname = "src\\main\\resources\\X" + pathname + ".txt";
    }
    public party(Element pk){
        this.pk = pk;
        v = 1000;
    }
    public party(Element sk,Element pk,long v){
        this.sk = sk;
        this.pk = pk;
        this.v = v;
    }
    public void cipher(int m){
        cipher1 = new Element[m];
        cipher2 = new Element[m];
    }
}
