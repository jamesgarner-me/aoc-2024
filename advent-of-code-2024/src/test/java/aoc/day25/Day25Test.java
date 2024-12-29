package aoc.day25;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day25Test {
    @Test
    public void testPart1Input() {
        String input = "#####\n" +
                ".####\n" +
                ".####\n" +
                ".####\n" +
                ".#.#.\n" +
                ".#...\n" +
                ".....\n" +
                "\n" +
                "#####\n" +
                "##.##\n" +
                ".#.##\n" +
                "...##\n" +
                "...#.\n" +
                "...#.\n" +
                ".....\n" +
                "\n" +
                ".....\n" +
                "#....\n" +
                "#....\n" +
                "#...#\n" +
                "#.#.#\n" +
                "#.###\n" +
                "#####\n" +
                "\n" +
                ".....\n" +
                ".....\n" +
                "#.#..\n" +
                "###..\n" +
                "###.#\n" +
                "###.#\n" +
                "#####\n" +
                "\n" +
                ".....\n" +
                ".....\n" +
                ".....\n" +
                "#....\n" +
                "#.#..\n" +
                "#.#.#\n" +
                "#####\n";
        String result = new Day25().part1(input);
        String expected = "3";
        assertEquals(expected, result);
    }
}
