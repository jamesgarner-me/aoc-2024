package aoc.day01;

import aoc.Day;
import aoc.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day01 implements Day {

    @Override
    public String part1(String input) {
        List<String> lines = Utils.splitLines(input);
        List<Integer> locationIds1 = new ArrayList<>();
        List<Integer> locationIds2 = new ArrayList<>();

        for (String line : lines) {
            String[] tokens = line.split("\\s+");
            locationIds1.add(Integer.parseInt(tokens[0]));
            locationIds2.add(Integer.parseInt(tokens[1]));
        }
        Collections.sort(locationIds1);
        Collections.sort(locationIds2);

        int sum = 0;
        for (int i = 0; i < locationIds1.size(); i++) {
            sum += Math.abs(locationIds1.get(i) - locationIds2.get(i));
        }
        return Integer.toString(sum);
    }

    @Override
    public String part2(String input) {
        List<String> lines = Utils.splitLines(input);
        List<Integer> locationIds1 = new ArrayList<>();
        List<Integer> locationIds2 = new ArrayList<>();

        for (String line : lines) {
            String[] tokens = line.split("\\s+");
            locationIds1.add(Integer.parseInt(tokens[0]));
            locationIds2.add(Integer.parseInt(tokens[1]));
        }

        int similarityScore = 0;
        for (Integer value : locationIds1) {
            int count = 0;
            int currentVal = value;
            for (Integer integer : locationIds2) {
                if (currentVal == integer) {
                    count++;
                }
            }
            similarityScore += currentVal * count;
        }
        return Integer.toString(similarityScore);
    }

}
