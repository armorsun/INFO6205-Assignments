package edu.neu.coe.info6205.union_find;

import java.util.Random;

public class UF_HWQUPC_experiment {

    public static void main(String[] args) {
        boolean pathCompression = true;
        int[] numberOfSites = {100, 200, 400, 800, 1600, 3200, 6400, 12800, 25600};
        int times = 10;

        for (int i = 0; i < numberOfSites.length; i++) {
            int connections = 0;

            // Do this multiple times and get average
            for (int j = 0; j < times; j++) {
                UF_HWQUPC union = new UF_HWQUPC(numberOfSites[i], pathCompression);
                connections += count(union);
            }
            System.out.println("Average " + connections / times + " connections of " + numberOfSites[i] + " sites");
        }
    }

    private static int count(UF_HWQUPC union) {
        int connections = 0;
        while (union.components() != 1) {
            int[] randomPair = getRandomPair(union.size());
            if (!union.connected(randomPair[0], randomPair[1])) {
                union.connect(randomPair[0], randomPair[1]);

                // Only count the connections built
                connections++;
            }
        }

        return connections;
    }

    private static int[] getRandomPair(int max) {
        Random random = new Random();
        int[] randomPair = {random.nextInt(max), random.nextInt(max)};
        return randomPair;
    }
}
