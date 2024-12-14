package aoc.day05;

import aoc.Day;
import aoc.Utils;

import java.util.*;

public class Day05 implements Day {

    private static <K, V> void printMap(HashMap<K, V> map) {
        map.forEach((key, value) -> System.out.println("Key " + key + ": " + value));
    }

    private HashMap<Integer, ArrayList<Integer>> pageThatAppearsBeforeMap;

    private HashMap<Integer, ArrayList<Integer>> pageThatAppearsAfterMap;

    private ArrayList<int[]> invalidPageUpdates;

    @Override
    public String part1(String input) {
        List<String> lines = Utils.splitLines(input);

        pageThatAppearsBeforeMap = new HashMap<>();
        pageThatAppearsAfterMap = new HashMap<>();
        invalidPageUpdates = new ArrayList<>();

        int sum = 0;

        for (String line : lines) {

            // parse page ordering rules: if line contains "|"
            if (line.contains("|")) {

                String[] tokens = line.split("\\|");

                int firstPage = Integer.parseInt(tokens[0]);
                int secondPage = Integer.parseInt(tokens[1]);

                ArrayList<Integer> pagesAfter = null;
                ArrayList<Integer> pagesBefore = null;
                pagesAfter = pageThatAppearsBeforeMap.get(firstPage);
                pagesBefore = pageThatAppearsAfterMap.get(secondPage);

                if (pagesAfter != null) {
                    pagesAfter.add(secondPage);
                } else {
                    pagesAfter = new ArrayList<>(Arrays.asList(secondPage));
                    pageThatAppearsBeforeMap.put(firstPage, pagesAfter);
                }

                if (pagesBefore != null) {
                    pagesBefore.add(firstPage);
                } else {
                    pagesBefore = new ArrayList<>(Arrays.asList(firstPage));
                    pageThatAppearsAfterMap.put(secondPage, pagesBefore);
                }

                // printMap(pageThatAppearsBeforeMap);
                // printMap(pageThatAppearsAfterMap);

            } else {

                // parse page updates
                if (!line.isEmpty()) {

                    int[] pageUpdateArray = Arrays.stream(line.split(","))
                            .mapToInt(Integer::parseInt)
                            .toArray();

                    boolean isValidPageUpdate = true;

                    for (int i = 0; i < pageUpdateArray.length; i++) {

                        ArrayList<Integer> pagesAfter = pageThatAppearsBeforeMap.get(pageUpdateArray[i]);
                        ArrayList<Integer> pagesBefore = pageThatAppearsAfterMap.get(pageUpdateArray[i]);

                        for (int j = 0; j < pageUpdateArray.length; j++) {

                            if (i != j) {

                                if (j > i) { // check rules: this number should not appear before value of i

                                    if (pagesBefore != null && pagesBefore.contains(pageUpdateArray[j])) {
                                        isValidPageUpdate = false;
                                    }

                                } else if (j < i) { // check rules: this number should not appear after value of i

                                    if (pagesAfter != null && pagesAfter.contains(pageUpdateArray[i])) {
                                        isValidPageUpdate = false;
                                    }
                                }
                            }
                            if (!isValidPageUpdate) {
                                break;
                            }
                        }
                        if (!isValidPageUpdate) {
                            // add invalid updates for part 2
                            invalidPageUpdates.add(pageUpdateArray);
                            break;
                        }
                    }
                    if (isValidPageUpdate) {
                        // get middle page number
                        int middleIndex = pageUpdateArray.length / 2; // (integer division rounds down)
                        sum += pageUpdateArray[middleIndex];
                    }
                }
            }
        }
        return Integer.toString(sum);
    }


    @Override
    public String part2(String input) {

        // Run part 1 first to populate class variables
        part1(input);

        int sum = 0;

        // bubble sort
        for (int[] pageUpdate : this.invalidPageUpdates) {

            for (int i = 0; i < pageUpdate.length - 1; i++) {

                for (int j = 0; j < pageUpdate.length - i - 1; j++) {

                    ArrayList<Integer> pagesBefore = this.pageThatAppearsAfterMap.get(pageUpdate[j]);

                    if(pagesBefore != null && pagesBefore.contains(pageUpdate[j + 1])) {
                        int temp = pageUpdate[j];
                        pageUpdate[j] = pageUpdate[j + 1];
                        pageUpdate[j + 1] = temp;
                    }
                }
            }

            // get middle page number
            int middleIndex = pageUpdate.length / 2; // (integer division rounds down)
            sum += pageUpdate[middleIndex];

        }
        return Integer.toString(sum);
    }
}
