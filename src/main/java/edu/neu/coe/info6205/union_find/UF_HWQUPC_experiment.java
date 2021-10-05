package edu.neu.coe.info6205.union_find;

import java.util.Random;

public class UF_HWQUPC_experiment {

    public static void main(String[] args) {
        boolean pathCompression = true;
        int[] numberOfSites = {10000, 20000, 40000, 80000, 160000, 320000, 640000, 1280000, 2560000};
        int times = 10;

        for (int i = 0; i < numberOfSites.length; i++) {
            int generated = 0;
            int connections = 0;

            // Do this multiple times and get average
            for (int j = 0; j < times; j++) {
                UF_HWQUPC union = new UF_HWQUPC(numberOfSites[i], pathCompression);
                int[] count = count(union);
                connections += count[0];
                generated += count[1];

            }
            System.out.println("Average " + generated / times + " paris generated with " + connections / times + " connections of " + numberOfSites[i] + " sites");
        }
    }

    private static int[] count(UF_HWQUPC union) {
        int connections = 0;
        int generated = 0;
        while (union.components() != 1) {
            int[] randomPair = getRandomPair(union.size());
            generated++;

            if (!union.connected(randomPair[0], randomPair[1])) {
                union.connect(randomPair[0], randomPair[1]);

                // Only count the connections built
                connections++;
            }
        }

        return new int[]{connections, generated};
    }

    private static int[] getRandomPair(int max) {
        Random random = new Random();
        int[] randomPair = {random.nextInt(max), random.nextInt(max)};
        return randomPair;
    }
}
