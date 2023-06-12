package com.cyuu.controller;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Field;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReferenceArray;

public class VolatileArray {

    AtomicReferenceArray<Element> atomicReferenceArray = new AtomicReferenceArray<>(1);

    /*public static void main(String[] args) throws InterruptedException {

        VolatileArray example = new VolatileArray();
        Pairing bp = PairingFactory.getPairing("ourmap.properties");
        Field G1 = bp.getG1();
        new Thread(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(20000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            example.atomicReferenceArray.set(0, G1.newRandomElement());
            System.out.println(example.atomicReferenceArray.get(0));
            System.out.println('a');
            try {
                TimeUnit.MILLISECONDS.sleep(20000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println('b');
        }).start();

        new Thread(() -> {
            while (true) {
                if (example.atomicReferenceArray.get(0) != null) {
                    System.out.println("index updated");
                    System.out.println(example.atomicReferenceArray.get(0));
                    break;
                }
            }
        }).start();
    }*/

}