import javax.swing.*;
import drawing.Canvas;
import turtles.FlockTurtle;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import gui.GUI;
import turtles.PredatorTurtle;


public class FlockingSim {

    private static List<FlockTurtle> flockTurtles;
    private static List<PredatorTurtle> predatorTurtles;
    private static JFrame frame;
    private static Canvas canvas;
    private static Canvas predatorCanvas;
    private static GUI gui;

    public static void main(String[] args) { new FlockingSim().runFlockingProgram(); // init program and then run program loop;
    }

    private FlockingSim() {

        // Make frame
        frame = new JFrame();
        frame.setResizable(true);

        // Make canvas for drawing turtles
        canvas = new Canvas();
        canvas.setForeground(new Color(255, 149, 0, 255)); // Set foreground of turtle canvas to orange

        // Make canvas for drawing predators
        predatorCanvas = new Canvas();
        predatorCanvas.setForeground(new Color(255, 59, 48, 255));  // Set foreground of predator turtle canvas to red

        // Add background image
        ImageIcon backgroundImage = new ImageIcon("UnderWater.png");
        JLabel backgroundLabel = new JLabel(backgroundImage);

        JPanel canvasPanel = new JPanel();
        OverlayLayout overlay = new OverlayLayout(canvasPanel);

        canvasPanel.setLayout(overlay); // Sets the panel for background and two panels to

        canvas.setOpaque(false);
        predatorCanvas.setOpaque(false);

        canvas.setDoubleBuffered(true);
        predatorCanvas.setDoubleBuffered(true);

        canvasPanel.add(canvas, BorderLayout.CENTER);
        canvasPanel.add(predatorCanvas, BorderLayout.CENTER);
        canvasPanel.add(backgroundLabel, BorderLayout.CENTER);

        frame.add(canvasPanel); // Add background image and canvas' to the frame

        flockTurtles = Collections.synchronizedList(new ArrayList<FlockTurtle>()); // List of flocking turts

        predatorTurtles = Collections.synchronizedList(new ArrayList<PredatorTurtle>()); // List of predator turts

        // init GUI elements and create GUI
        gui = new GUI(frame);
        gui.setup(); // init all buttons and GUI layout
        gui.programEvents(canvas, predatorCanvas, flockTurtles, predatorTurtles); // Activate listener events

        // Have two seperate canvas strutures for turtles and predator
    }

    private void runFlockingProgram() {

        boolean continueRunning = true;

        long lastLoopTime = System.nanoTime();
        final int TARGET_FPS = 30; // Lower value reduces flashing as canvas drawing library is very slow.
        final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;

        while (continueRunning) {

            // work out how long its been since the last update
            long now = System.nanoTime();
            long updateLength = now - lastLoopTime;
            lastLoopTime = now;
            double delta = updateLength / ((double) OPTIMAL_TIME);

            // UPDATE TURTLES

           // update flocking turtles
            synchronized (flockTurtles) {
                for (FlockTurtle T : flockTurtles) {
                    T.Flock(flockTurtles);
                    T.update(delta, gui.isWrapOrFlip());
                }
            }

            // update predator turtles
            synchronized (predatorTurtles) {
                for (PredatorTurtle pT : predatorTurtles) {
                    pT.Hunt(flockTurtles);
                    pT.update(delta, gui.isWrapOrFlip());
                }
            }

            gui.updateLabels(flockTurtles);

            // CLEAR CANVAS
            canvas.clear();
            predatorCanvas.clear();

            // DRAW

            // draw flocking turtles
            synchronized (flockTurtles) {
                for (FlockTurtle T : flockTurtles) {
                    T.draw();
                }
            }

            // draw predator turtles
            synchronized (predatorTurtles) {
                for (PredatorTurtle pT : predatorTurtles){
                    pT.draw();
                }
            }

            // Waits, leaving drawn elements on the screen (thread sleep is not always accurate and can slip by + or - 10 ms)
            try {
                Thread.sleep(Math.max(0, (lastLoopTime - System.nanoTime() + OPTIMAL_TIME) / 1000000));
            } catch (InterruptedException e) {
                e.printStackTrace();

                // https://stackoverflow.com/questions/18283199/java-main-game-loop
            }
        }
    }
}