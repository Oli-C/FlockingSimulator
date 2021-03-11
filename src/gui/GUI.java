package gui;

import drawing.Canvas;
import tools.Utils;
import turtles.FlockTurtle;
import turtles.PredatorTurtle;

import javax.swing.*;
import javax.swing.border.SoftBevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Hashtable;
import java.util.List;

import static tools.Utils.randNom;

public class GUI {

    public boolean isWrapOrFlip() {
        return WrapOrFlip;
    }

    // Boolean for wrap or flip parameter
    private boolean WrapOrFlip = true;

    // Panels
    private JPanel lowerPanel;

    // Buttons
    private JButton addTurtleButton;
    private JButton addTenTurtleButton;
    private JButton removeTurtleButton;
    private JButton removeTenTurtleButton;
    private JButton addPredatorButton;
    private JButton removePredatorButton;

    // Checkboxes
    private JCheckBox toggleRadius;
    private JCheckBox toggleWrap;

    // Sliders
    private JSlider speedSlider;
    private JSlider alignmentSlider;
    private JSlider cohesionSlider;
    private JSlider separationSlider;
    private JSlider radiusSlider;

    // Labels
    private JLabel totalTurtLabel;
    private JLabel radiusTurtLabel;
    private JLabel totalPredLabel;

    // Menu & Frame
    private JMenu dropMenu;
    private JMenuBar upperMenu;
    private JFrame guiFrame;

    private int speedMax = 16000;

    public GUI(JFrame frame) {
        guiFrame = frame;
    }

    // Create frame and add all JElements to the frame (Buttons, Checkboxes, Sliders, Labels, Menu)
    public void setup() {

        // Set windows look and feel
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }

        } catch (Exception e) {
            // If synth look and feel is not available, set the GUI to defualt look and feel...
        }

        // init frame
        guiFrame.setTitle("University of York - Flocking Simulator - Y3859110");
        guiFrame.setSize(1280, 720);
        guiFrame.setDefaultCloseOperation(guiFrame.EXIT_ON_CLOSE);
        guiFrame.setVisible(true);

        FlowLayout flowlayout = new FlowLayout(); // Configure layout to Flow Layout

        // init labels
        totalTurtLabel = new JLabel("|  NO. OF TURTLES: 0");
        radiusTurtLabel = new JLabel("|  RADIUS: 70");
        totalPredLabel = new JLabel("|  NO. OF PREDATORS: 0");

        // Set label colors
        totalTurtLabel.setForeground(Color.white);
        radiusTurtLabel.setForeground(Color.white);
        totalPredLabel.setForeground(Color.white);

        // init all J-Elements
        addTurtleButton = new JButton("Add Turtle");
        removeTurtleButton = new JButton("Remove Turtle");
        removeTenTurtleButton = new JButton("Remove 10 Turtles");
        addTenTurtleButton = new JButton("Add 10 Turtles");
        toggleRadius = new JCheckBox(" Enable Radius ");
        toggleWrap = new JCheckBox(" Enable Wrapping ");
        addPredatorButton = new JButton(" Add Predator ");
        removePredatorButton = new JButton(" Remove Predator ");

        // init Menu
        upperMenu = new JMenuBar();
        dropMenu = new JMenu("Turtle Settings");
        upperMenu.add(dropMenu);

        // Add Elements to Menu
        dropMenu.add(toggleRadius);
        dropMenu.add(toggleWrap);
        toggleWrap.setSelected(true);

        // init Speed slider
        speedSlider = new JSlider(JSlider.HORIZONTAL, 0, speedMax, 100);
        speedSlider.setMajorTickSpacing(speedMax / 4);
        speedSlider.setMinorTickSpacing(speedMax / 4);
        speedSlider.setPaintTicks(true);
        speedSlider.setPaintLabels(true);

        // Add positioned labels for speed slider
        Hashtable SpeedHashposition = new Hashtable();
        SpeedHashposition.put(0, new JLabel("stop"));
        SpeedHashposition.put(speedMax / 4, new JLabel("slow"));
        SpeedHashposition.put(speedMax / 2, new JLabel("SPEED"));
        SpeedHashposition.put(speedMax, new JLabel("fast"));

        // Set the speed label to be drawn
        speedSlider.setLabelTable(SpeedHashposition);

        // init Alignment slider
        alignmentSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
        alignmentSlider.setMajorTickSpacing(25);
        alignmentSlider.setMinorTickSpacing(0); // Double only as wanted float for alignment.
        alignmentSlider.setPaintLabels(true);   //    have used /100 and double.
        alignmentSlider.setPaintTicks(true);

        // Add positioned labels for alignment slider
        Hashtable AlignmentHashposition = new Hashtable();
        AlignmentHashposition.put(50, new JLabel(" ALIGNMENT "));
        AlignmentHashposition.put(0, new JLabel(" 0 "));
        AlignmentHashposition.put(100, new JLabel(" 1 "));
        alignmentSlider.setLabelTable(AlignmentHashposition);

        // init Radius size slider
        radiusSlider = new JSlider(JSlider.HORIZONTAL, 0, 200, 70);
        radiusSlider.setMajorTickSpacing(20);
        radiusSlider.setMinorTickSpacing(0);
        radiusSlider.setPaintLabels(true);
        radiusSlider.setPaintTicks(true);

        // Add positioned labels for radius slider
        Hashtable RadiusHashposition = new Hashtable();
        RadiusHashposition.put(100, new JLabel(" RADIUS "));
        RadiusHashposition.put(0, new JLabel(" 0 "));
        RadiusHashposition.put(200, new JLabel(" 200 "));
        RadiusHashposition.put(speedMax, new JLabel(" 0 "));
        radiusSlider.setLabelTable(RadiusHashposition);

        // init Cohesion slider
        cohesionSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
        cohesionSlider.setMajorTickSpacing(25);
        cohesionSlider.setMinorTickSpacing(0);
        cohesionSlider.setPaintLabels(true);
        cohesionSlider.setPaintTicks(true);

        // Add positioned labels for cohesion slider
        Hashtable CohesionHashposition = new Hashtable();
        CohesionHashposition.put(50, new JLabel(" COHESION "));
        CohesionHashposition.put(0, new JLabel(" 0 "));
        CohesionHashposition.put(100, new JLabel(" 1 "));
        cohesionSlider.setLabelTable(CohesionHashposition);

        // init Seperation slider
        separationSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
        separationSlider.setMajorTickSpacing(25);
        separationSlider.setMinorTickSpacing(0);
        separationSlider.setPaintLabels(true);
        separationSlider.setPaintTicks(true);

        // Add positioned labels for cohesion slider
        Hashtable SeparationHashposition = new Hashtable();
        SeparationHashposition.put(50, new JLabel(" SEPERATION "));
        SeparationHashposition.put(0, new JLabel(" 0 "));
        SeparationHashposition.put(100, new JLabel(" 1 "));
        separationSlider.setLabelTable(SeparationHashposition);

        // init lower panel
        lowerPanel = new JPanel();
        lowerPanel.setBackground(new Color(19, 78, 95, 255));
        lowerPanel.add(cohesionSlider);


        // Add Buttons to drop down menu
        dropMenu.add(addTurtleButton);
        dropMenu.add(addTenTurtleButton);
        dropMenu.add(removeTurtleButton);
        dropMenu.add(removeTenTurtleButton);
        dropMenu.add(addPredatorButton);
        dropMenu.add(removePredatorButton);

        upperMenu.add(speedSlider);
        upperMenu.add(alignmentSlider);
        upperMenu.add(radiusSlider);
        upperMenu.add(cohesionSlider);
        upperMenu.add(separationSlider);


        // Set layout of lower panel and add elements
        lowerPanel.setLayout(flowlayout);
        lowerPanel.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
        lowerPanel.add(totalTurtLabel);
        lowerPanel.add(radiusTurtLabel);
        lowerPanel.add(totalPredLabel);

        guiFrame.add(upperMenu, BorderLayout.NORTH);
        guiFrame.add(lowerPanel, BorderLayout.SOUTH);
        guiFrame.setBackground(new Color(0, 0, 0, 255));
        guiFrame.revalidate();

    }

    // Create all event listeners for the JElements
    public void programEvents(Canvas canvas, Canvas predatorCanvas, List<FlockTurtle> turtles, List<PredatorTurtle> predatorTurtles) {

        /* Synchronized added to each listener when iterating over array to avoid crashes when
        adding additional turts.
         */

        // Add turtles listener
        addTurtleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                synchronized (turtles) {
                    turtles.add(new FlockTurtle(canvas, randNom(0, canvas.getWidth()), randNom(0, canvas.getHeight())));
                    turtles.get(turtles.size() - 1).setVelocity(speedSlider.getValue());
                    addTurtleButton.setText("Add Turtle");
                    turtles.get((turtles.size() - 1)).setDrawingRadius(toggleRadius.isSelected());
                    turtles.get((turtles.size() - 1)).setRadius(radiusSlider.getValue());
                    turtles.get((turtles.size() - 1)).setCohesion((double)cohesionSlider.getValue());
                    turtles.get((turtles.size() - 1)).setAlignment((double)alignmentSlider.getValue());
                    turtles.get((turtles.size() - 1)).setAlignment((double)separationSlider.getValue());

                    if (turtles.size() > 1) {
                        removeTurtleButton.setEnabled(true);
                    }

                    totalTurtLabel.setText("|  NO. OF TURTLES: " + turtles.size());
                }
            }
        });

        // Add 10-turtles listener
        addTenTurtleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                synchronized (turtles) {
                    for (int i = 0; i <= 9; i++) {

                        turtles.add(new FlockTurtle(canvas, randNom(0, canvas.getWidth()), randNom(0, canvas.getHeight())));

                        // Get most recently added turtles and set it's parameters to current settings.
                        turtles.get(turtles.size() - 1).setVelocity(speedSlider.getValue());
                        turtles.get(turtles.size() - 1).setAngle(randNom(-180, 180));
                        turtles.get((turtles.size() - 1)).setDrawingRadius(toggleRadius.isSelected());
                        turtles.get((turtles.size() - 1)).setRadius(radiusSlider.getValue());
                        turtles.get(i).setCohesion((double)cohesionSlider.getValue());
                        turtles.get(i).setAlignment((double)alignmentSlider.getValue());
                        turtles.get(i).setAlignment((double)separationSlider.getValue());
                    }

                    totalTurtLabel.setText("|  NO. OF TURTLES: " + turtles.size());
                }
            }
        });

        // Remove turtles listener
        removeTurtleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                synchronized (turtles) {
                    if (turtles.size() < 10) {
                        removeTenTurtleButton.setEnabled(false);
                    }

                    if (turtles.size() < 2) {
                        removeTurtleButton.setEnabled(false);
                    } else

                        // removes random turtles
                        removeTurtleButton.setEnabled(true);
                    removeTenTurtleButton.setEnabled(true);

                    turtles.remove(turtles.get(randNom(0, turtles.size() - 1)));
                }
                totalTurtLabel.setText("|  NO. OF TURTLES: " + turtles.size());
            }
        });

        // Remove turtles listener
        removeTenTurtleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                synchronized (turtles) {
                    if (turtles.size() < 10) {
                        removeTenTurtleButton.setEnabled(false);
                    } else
                        // removes random turtles
                        removeTurtleButton.setEnabled(true);
                    removeTenTurtleButton.setEnabled(true);

                    for (int i = 0; i <= 9; i++) {
                        turtles.remove(turtles.get(randNom(0, turtles.size() - 1)));
                    }
                }
                totalTurtLabel.setText("|  NO. OF TURTLES: " + turtles.size());
            }
        });

        // Toggle radius listener
        toggleRadius.addItemListener(new ItemListener() {
            @Override

            public void itemStateChanged(ItemEvent e) {

                if (e.getStateChange() == ItemEvent.SELECTED) {
                    synchronized (turtles) {
                        for (int i = 0; i < turtles.size(); i++) {

                            turtles.get(i).setDrawingRadius(true);
                        }

                        for (int i = 0; i < predatorTurtles.size(); i++) {

                            predatorTurtles.get(i).setDrawingRadius(true);
                        }
                    }
                } else {

                    synchronized (turtles) {
                        for (int i = 0; i < turtles.size(); i++) {

                            turtles.get(i).setDrawingRadius(false);
                        }
                        for (int i = 0; i < predatorTurtles.size(); i++) {

                            predatorTurtles.get(i).setDrawingRadius(false);
                        }
                    }
                }
            }
        });

        // Toggle Wrapping listener
        toggleWrap.addItemListener(new ItemListener() {
            @Override

            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {

                    WrapOrFlip = true;
                } else {

                    WrapOrFlip = false;
                }
            }
        });

        // Speed slider listener
        speedSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {

                synchronized (turtles) {
                    for (int i = 0; i < turtles.size(); i++) {

                        turtles.get(i).setVelocity(speedSlider.getValue());
                    }
                }
            }
        });

        // Alignment slider listener
        alignmentSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {

                synchronized (turtles) {
                    for (int i = 0; i < turtles.size(); i++) {
                        turtles.get(i).setAlignment((double)alignmentSlider.getValue());
                    }
                }
            }
        });

        // Cohesion slider listener
        cohesionSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {

                synchronized (turtles) {
                    for (int i = 0; i < turtles.size(); i++) {

                        turtles.get(i).setCohesion((double)cohesionSlider.getValue());
                    }
                }
            }
        });

        // Separation slider listener
        separationSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {

                synchronized (turtles) {
                    for (int i = 0; i < turtles.size(); i++) {

                        turtles.get(i).setSeperation((double)separationSlider.getValue());
                    }
                }
            }
        });

        // Radius slider listener
        radiusSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {

                int radius = radiusSlider.getValue();

                synchronized (turtles) {
                    for (int i = 0; i < turtles.size(); i++) {
                        turtles.get(i).setRadius(radius);
                    }

                    radiusTurtLabel.setText("|  RADIUS: " + radius);
                }
            }
        });

        // Add predatorTurtle listener
        addPredatorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                synchronized (predatorTurtles) {
                    predatorTurtles.add(new PredatorTurtle(predatorCanvas, randNom(0, canvas.getWidth()), randNom(0, canvas.getHeight())));

                    predatorTurtles.get(predatorTurtles.size() - 1).setVelocity(2000);
                    predatorTurtles.get(predatorTurtles.size() - 1).setAngle(Utils.randNom(-180, 180));
                    predatorTurtles.get(predatorTurtles.size() - 1).setRadius(50);
                    predatorTurtles.get((predatorTurtles.size() - 1)).setCohesion(0.8);

                    totalPredLabel.setText("|  NO. OF PREDATORS: " + predatorTurtles.size());
                }
            }
        });

        // Remove predatorTurtle listener
        removePredatorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                synchronized (predatorTurtles) {
                    if (predatorTurtles.size() <= 1) {
                        predatorTurtles.remove(predatorTurtles.get(randNom(0, predatorTurtles.size() - 1)));

                        totalPredLabel.setText("|  NO. OF PREDATORS: " + predatorTurtles.size());
                    }
                }
            }
        });

    }

    // Method to update labels
    public void updateLabels(List<FlockTurtle> turtles) {
        totalTurtLabel.setText("|  NO. OF TURTLES: " + turtles.size());
    }
}
