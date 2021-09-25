package edu.neu.coe.info6205.util;

import edu.neu.coe.info6205.sort.elementary.InsertionSort;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class InsertionSortBenchmarks {

    private final int[] elementNumbers = new int[]{50, 100, 200, 400, 800, 1600, 3200, 6400, 12800, 25600, 51200};
    private final int min = 1;
    private List<int[]> randomArrays = new ArrayList<int[]>(elementNumbers.length);
    private List<int[]> orderedArrays = new ArrayList<int[]>(elementNumbers.length);
    private List<int[]> partiallyOrderedArrays = new ArrayList<int[]>(elementNumbers.length);
    private List<int[]> reverseOrderedArrays = new ArrayList<int[]>(elementNumbers.length);
    private static DecimalFormat df = new DecimalFormat("0.00");

    @Before
    public void setUpRandom() throws IOException {
        for (int i = 0; i < elementNumbers.length; i++) {
            List<Integer> orderedList = IntStream.rangeClosed(min, elementNumbers[i]).boxed().collect(Collectors.toList());
            Collections.shuffle(orderedList); // shuffle the ordered list
            randomArrays.add(orderedList.stream().mapToInt(Integer::intValue).toArray());
        }
    }

    @Before
    public void setUpOrdered() throws IOException {
        for (int i = 0; i < elementNumbers.length; i++) {
            orderedArrays.add(IntStream.rangeClosed(min, elementNumbers[i]).toArray());
        }
    }

    @Before
    public void setUpPartiallyOrdered() throws IOException {
        for (int i = 0; i < elementNumbers.length; i++) {
            List<Integer> orderedList = IntStream.rangeClosed(min, elementNumbers[i]).boxed().collect(Collectors.toList());
            Collections.shuffle(orderedList.subList(min, elementNumbers[i] / 2)); // shuffle half of the array
            partiallyOrderedArrays.add(orderedList.stream().mapToInt(Integer::intValue).toArray());
        }
    }

    @Before
    public void setUpReverseOrdered() throws IOException {
        for (int i = 0; i < elementNumbers.length; i++) {
            List<Integer> orderedList = IntStream.rangeClosed(min, elementNumbers[i]).boxed().collect(Collectors.toList());
            Collections.reverse(orderedList); //reverse the ordered list
            reverseOrderedArrays.add(orderedList.stream().mapToInt(Integer::intValue).toArray());
        }
    }

    @Test
    public void benchmarkRandom() {
        for (int m = 0; m < randomArrays.size(); m++){
            final Timer timer = new Timer();
            int[] target = randomArrays.get(m);

            double mean = timer.repeat(30, () -> target, (t) -> {
                Integer[] I = new Integer[t.length];
                Arrays.setAll(I, i -> t[i]);
                InsertionSort.sort(I);
                return null;
            });
            System.out.println("random array with " + target.length +
                    " elements, sorting time mean: " +  df.format(mean));
        }
    }

    @Test
    public void benchmarkOrdered() {
        for (int m = 0; m < orderedArrays.size(); m++){
            final Timer timer = new Timer();
            int[] target = orderedArrays.get(m);

            double mean = timer.repeat(30, () -> target, (t) -> {
                Integer[] I = new Integer[t.length];
                Arrays.setAll(I, i -> t[i]);
                InsertionSort.sort(I);
                return null;
            });
            System.out.println("ordered array with " + target.length +
                    " elements, sorting time mean: " +  df.format(mean));
        }
    }

    @Test
    public void benchmarkPartiallyOrdered() {
        for (int m = 0; m < partiallyOrderedArrays.size(); m++){
            final Timer timer = new Timer();
            int[] target = partiallyOrderedArrays.get(m);

            double mean = timer.repeat(30, () -> target, (t) -> {
                Integer[] I = new Integer[t.length];
                Arrays.setAll(I, i -> t[i]);
                InsertionSort.sort(I);
                return null;
            });
            System.out.println("partially ordered array with " + target.length +
                    " elements, sorting time mean: " +  df.format(mean));
        }
    }

    @Test
    public void benchmarkReverseOrdered() {
        for (int m = 0; m < reverseOrderedArrays.size(); m++){
            final Timer timer = new Timer();
            int[] target = reverseOrderedArrays.get(m);

            double mean = timer.repeat(30, () -> target, (t) -> {
                Integer[] I = new Integer[t.length];
                Arrays.setAll(I, i -> t[i]);
                InsertionSort.sort(I);
                return null;
            });
            System.out.println("reverse ordered array with " + target.length +
                    " elements, sorting time mean: " + df.format(mean));
        }
    }
}
