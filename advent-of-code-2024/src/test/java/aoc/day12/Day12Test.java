package aoc.day12;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day12Test {
    @Test
    public void testPart1Input1() {
        String input = "AAAA\n" +
                "BBCD\n" +
                "BBCC\n" +
                "EEEC";
        String result = new Day12().part1(input);
        String expected = "140";
        assertEquals(expected, result);
    }

    @Test
    public void testPart1Input2() {
        String input = "OOOOO\n" +
                "OXOXO\n" +
                "OOOOO\n" +
                "OXOXO\n" +
                "OOOOO";
        String result = new Day12().part1(input);
        String expected = "772";
        assertEquals(expected, result);
    }

    @Test
    public void testPart1Input3() {
        String input = "RRRRIICCFF\n" +
                "RRRRIICCCF\n" +
                "VVRRRCCFFF\n" +
                "VVRCCCJFFF\n" +
                "VVVVCJJCFE\n" +
                "VVIVCCJJEE\n" +
                "VVIIICJJEE\n" +
                "MIIIIIJJEE\n" +
                "MIIISIJEEE\n" +
                "MMMISSJEEE";
        String result = new Day12().part1(input);
        String expected = "1930";
        assertEquals(expected, result);
    }

    @Test
    public void testPart2Input1() {
        String input = "AAAA\n" +
                "BBCD\n" +
                "BBCC\n" +
                "EEEC";
        String result = new Day12().part2(input);
        String expected = "80";
        assertEquals(expected, result);
    }

    @Test
    public void testPart2Input2() {
        String input = "OOOOO\n" +
                "OXOXO\n" +
                "OOOOO\n" +
                "OXOXO\n" +
                "OOOOO";
        String result = new Day12().part2(input);
        String expected = "436";
        assertEquals(expected, result);
    }

    @Test
    public void testPart2Input3() {
        String input = "EEEEE\n" +
                "EXXXX\n" +
                "EEEEE\n" +
                "EXXXX\n" +
                "EEEEE";
        String result = new Day12().part2(input);
        String expected = "236";
        assertEquals(expected, result);
    }

    @Test
    public void testPart2Input4() {
        String input = "RRRRIICCFF\n" +
                "RRRRIICCCF\n" +
                "VVRRRCCFFF\n" +
                "VVRCCCJFFF\n" +
                "VVVVCJJCFE\n" +
                "VVIVCCJJEE\n" +
                "VVIIICJJEE\n" +
                "MIIIIIJJEE\n" +
                "MIIISIJEEE\n" +
                "MMMISSJEEE\n";
        String result = new Day12().part2(input);
        String expected = "1206";
        assertEquals(expected, result);
    }
}
