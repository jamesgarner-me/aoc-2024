package aoc.day01;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day01Test {

    @Test
    public void testPart1(){
        String input = "3   4\n" +
                "4   3\n" +
                "2   5\n" +
                "1   3\n" +
                "3   9\n" +
                "3   3";
        String result = new Day01().part1(input);
        String expected = "11";
        assertEquals(expected, result);
    }

    @Test
    public void testPart2(){
        String input = "3   4\n" +
                "4   3\n" +
                "2   5\n" +
                "1   3\n" +
                "3   9\n" +
                "3   3";
        String result = new Day01().part2(input);
        String expected = "31";
        assertEquals(expected, result);
    }
}
