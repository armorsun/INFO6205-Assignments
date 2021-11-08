package edu.neu.coe.info6205.sort.par;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;

/**
 * This code has been fleshed out by Ziyao Qiao. Thanks very much.
 * TODO tidy it up a bit.
 */
public class Main {

    public static void main(String[] args) {
        processArgs(args);

        Random random = new Random();
        final int parallelism = 1;

        final int arraySize = 4000000;
        final int repeatedTimes = 10;

        final int cutoffBase = 1;
        final int cutoffWeight = 1000;
        final int cutoffInterval = 1;
        final int cutoffLimit = 40;
        int[] array = new int[arraySize];

        ArrayList<Long> timeList = new ArrayList<>();

        ParSort.parallelism = parallelism;

        for (int k = cutoffBase; k <= cutoffLimit; k += cutoffInterval) {
            ParSort.cutoff = cutoffWeight * k;

            long startTime = System.currentTimeMillis();

            for (int expTime = 0; expTime < repeatedTimes; expTime++) {
                for (int i = 0; i < array.length; i++) array[i] = random.nextInt(arraySize * repeatedTimes);
                ParSort.sort(array, 0, array.length);
            }

            long endTime = System.currentTimeMillis();

            long time = (endTime - startTime);
            timeList.add(time);

            System.out.println("cutoff：" + (ParSort.cutoff) + "\t\t10 times Time:" + time + "ms");

        }

        try {
            FileOutputStream fis = new FileOutputStream("./assignment_5/data/arr-" + arraySize +
                    "_cutoffStart-" + cutoffWeight * cutoffBase +
                    "_cutoffEnd-" + cutoffLimit +
                    "_cutoffInterval-" + cutoffInterval * cutoffWeight +
                    "_parallelism-" + parallelism + "_result.csv");

            OutputStreamWriter isr = new OutputStreamWriter(fis);
            BufferedWriter bw = new BufferedWriter(isr);
            int j = cutoffBase;
            for (long i : timeList) {
                String content = (double) cutoffWeight * j / arraySize + "," + (double) i / 10 + "\n";
                j += cutoffInterval;
                bw.write(content);
                bw.flush();
            }
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void processArgs(String[] args) {
        String[] xs = args;
        while (xs.length > 0)
            if (xs[0].startsWith("-")) xs = processArg(xs);
    }

    private static String[] processArg(String[] xs) {
        String[] result = new String[0];
        System.arraycopy(xs, 2, result, 0, xs.length - 2);
        processCommand(xs[0], xs[1]);
        return result;
    }

    private static void processCommand(String x, String y) {
        if (x.equalsIgnoreCase("N")) setConfig(x, Integer.parseInt(y));
        else
            // TODO sort this out
            if (x.equalsIgnoreCase("P")) //noinspection ResultOfMethodCallIgnored
                ForkJoinPool.getCommonPoolParallelism();
    }

    private static void setConfig(String x, int i) {
        configuration.put(x, i);
    }

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private static final Map<String, Integer> configuration = new HashMap<>();


}
