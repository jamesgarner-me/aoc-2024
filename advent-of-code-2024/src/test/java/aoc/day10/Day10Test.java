package aoc.day10;

import aoc.day01.Day01;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day10Test {
    @Test
    public void testPart1Input1() {
        String input = "2220222\n" +
                "2221222\n" +
                "2222222\n" +
                "6543456\n" +
                "7222227\n" +
                "8222228\n" +
                "9222229";
        String result = new Day10().part1(input);
        String expected = "2";
        assertEquals(expected, result);
    }

    @Test
    public void testPart1Input2() {
        String input = "89010123\n" +
                "78121874\n" +
                "87430965\n" +
                "96549874\n" +
                "45678903\n" +
                "32019012\n" +
                "01329801\n" +
                "10456732";
        String result = new Day10().part1(input);
        String expected = "36";
        assertEquals(expected, result);
    }

    @Test
    public void testPart2Input1() {
        String input = "89010123\n" +
                "78121874\n" +
                "87430965\n" +
                "96549874\n" +
                "45678903\n" +
                "32019012\n" +
                "01329801\n" +
                "10456732";
        String result = new Day10().part2(input);
        String expected = "81";
        assertEquals(expected, result);
    }
}
