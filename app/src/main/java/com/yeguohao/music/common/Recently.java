package com.yeguohao.music.common;

import java.util.Random;

public class Recently {

    private int[] recently = new int[5];
    private int recent;

    public int random(int size) {
        Random random = new Random();
        int n = random.nextInt(size);
        while (in(n)) n = random.nextInt(size);
        recently[recent % 5] = n;
        recent++;
        return n;
    }

    private boolean in(int n) {
        for (int i : recently) {
            if (n == i) return true;
        }
        return false;
    }

}
