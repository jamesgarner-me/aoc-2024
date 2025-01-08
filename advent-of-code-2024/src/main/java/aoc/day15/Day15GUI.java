package aoc.day15;

import aoc.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class Day15GUI extends JFrame {
    private static final int CELL_SIZE = 40;
    private static final int DELAY = 100;
    private Day15 day15;
    private WarehousePanel warehousePanel;
    private javax.swing.Timer animationTimer;
    private int currentMoveIndex;

    public Day15GUI() {
        day15 = new Day15();
        setTitle("Warehouse Robot Simulator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initializeComponents();
        pack();
        setLocationRelativeTo(null);
    }

    private void initializeComponents() {
        warehousePanel = new WarehousePanel();
        JScrollPane scrollPane = new JScrollPane(warehousePanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        // Initialize with a default size, will be updated when warehouse is loaded
        scrollPane.setPreferredSize(new Dimension(800, 600));
        add(scrollPane, BorderLayout.CENTER);

        JButton startButton = new JButton("Start Simulation");
        startButton.addActionListener(e -> startSimulation());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(startButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void startSimulation() {
        if (animationTimer != null && animationTimer.isRunning()) {
            animationTimer.stop();
        }

        // Initialize the simulation
        currentMoveIndex = 0;
        String input = getTestInput();
        List<String> lines = Utils.splitLines(input);
        day15.initialise(lines);

        // Update scroll pane size based on warehouse dimensions
        char[][] warehouse = day15.getWarehouse();
        if (warehouse != null) {
            int width = warehouse[0].length * CELL_SIZE;
            int height = warehouse.length * CELL_SIZE;

            // Add some padding and account for scrollbars
            width = Math.min(width + 50, 1200);  // Max width of 1200
            height = Math.min(height + 100, 800); // Max height of 800

            ((JScrollPane) warehousePanel.getParent().getParent()).setPreferredSize(new Dimension(width, height));
            pack();
            setLocationRelativeTo(null);
        }

        animationTimer = new javax.swing.Timer(DELAY, e -> {
            if (currentMoveIndex < day15.getMoves().size()) {
                char move = day15.getMoves().get(currentMoveIndex++);
                switch (move) {
                    case '<' -> day15.moveRobotCheck(0, -1);
                    case '>' -> day15.moveRobotCheck(0, 1);
                    case '^' -> day15.moveRobotCheck(-1, 0);
                    case 'v' -> day15.moveRobotCheck(1, 0);
                }
                warehousePanel.repaint();
            } else {
                ((javax.swing.Timer) e.getSource()).stop();
            }
        });
        animationTimer.start();
    }

    private String getTestInput() {
        try {
            // Load the resource file from the classpath
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("day15.txt");
            if (inputStream == null) {
                throw new IOException("Could not find day15.txt in resources");
            }
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to read day15.txt", e);
        }
    }

    private class WarehousePanel extends JPanel {
        @Override
        public Dimension getPreferredSize() {
            char[][] warehouse = day15.getWarehouse();
            if (warehouse == null) return new Dimension(400, 400);
            int width = warehouse[0].length * CELL_SIZE;
            int height = warehouse.length * CELL_SIZE;
            // Add padding to center the warehouse
            return new Dimension(width + 40, height + 40);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            char[][] warehouse = day15.getWarehouse();
            if (warehouse == null) return;

            // Enable anti-aliasing for smoother graphics
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            // Calculate centering offsets
            int xOffset = (getWidth() - warehouse[0].length * CELL_SIZE) / 2;
            int yOffset = (getHeight() - warehouse.length * CELL_SIZE) / 2;

            // Draw background
            g2d.setColor(new Color(240, 240, 240));  // Light gray background
            g2d.fillRect(0, 0, getWidth(), getHeight());

            for (int row = 0; row < warehouse.length; row++) {
                for (int col = 0; col < warehouse[row].length; col++) {
                    int x = xOffset + col * CELL_SIZE;
                    int y = yOffset + row * CELL_SIZE;

                    // Draw cell background
                    g2d.setColor(Color.WHITE);
                    g2d.fillRect(x, y, CELL_SIZE, CELL_SIZE);
                    g2d.setColor(Color.BLACK);
                    g2d.drawRect(x, y, CELL_SIZE, CELL_SIZE);

                    // Draw cell content
                    char cell = warehouse[row][col];
                    switch (cell) {
                        case '#' -> {
                            g2d.setColor(Color.DARK_GRAY);
                            g2d.fillRect(x, y, CELL_SIZE, CELL_SIZE);
                        }
                        case '@', 'O' -> {
                            String emoji = (cell == '@') ? "🤖" : "📦";
                            g2d.setColor((cell == '@') ? Color.BLUE : Color.RED);
                            g2d.setFont(new Font("Segoe UI Emoji", Font.PLAIN, CELL_SIZE - 10));

                            // Center the emoji in the cell
                            FontMetrics fm = g2d.getFontMetrics();
                            int emojiWidth = fm.stringWidth(emoji);
                            int emojiHeight = fm.getHeight();
                            int emojiX = x + (CELL_SIZE - emojiWidth) / 2;
                            int emojiY = y + ((CELL_SIZE + emojiHeight) / 2) - fm.getDescent();

                            g2d.drawString(emoji, emojiX, emojiY);
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Day15GUI().setVisible(true);
        });
    }
}
