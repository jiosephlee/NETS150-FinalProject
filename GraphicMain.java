import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.FileWriter;

import javax.swing.*;

/**
 * This class sets up the top-level frame and widgets for the GUI.
 *
 * This game adheres to a Model-View-Controller design framework. This framework
 * is very effective for turn-based games. We STRONGLY recommend you review
 * these lecture slides, starting at slide 8, for more details on
 * Model-View-Controller:
 * https://www.seas.upenn.edu/~cis120/current/files/slides/lec37.pdf
 *
 * In a Model-View-Controller framework, Game initializes the view, implements a
 * bit of controller functionality through the reset button, and then
 * instantiates a GameBoard. The GameBoard will handle the rest of the game's
 * view and controller functionality, and it will instantiate a TicTacToe object
 * to serve as the game's model.
 */
public class GraphicMain implements Runnable {

    public void run() {
        // NOTE: the 'final' keyword denotes immutability even for local variables.

        // Top-level frame in which game components live
        final JFrame frame = new JFrame("Nets Eats!");
        frame.setLocation(300, 300);
        BoxLayout boxlayout = new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS);
        frame.setLayout(boxlayout);

        // Instructions
        JOptionPane.showMessageDialog(frame,
                "Hi! Welcome to our App: Perfect Night in NYC\n"
                + "Let us first ask some of your preferences!",
                "Instructions", JOptionPane.INFORMATION_MESSAGE);

        // Instructions
        final JPanel instructions_panel = new JPanel();
        frame.add(instructions_panel);
        final JLabel instructions = new JLabel("Please select your favorites for each category!");
        instructions_panel.add(instructions);

        // Food Categories Panel
        final JPanel status_panel = new JPanel();
        frame.add(status_panel);
        final JLabel status = new JLabel("Food Categories: ");
        status_panel.add(status);

        final Backend engine = new Backend();
        frame.add(engine, BorderLayout.CENTER);

        //First Food Category
        final JButton thai = new JButton("Thai");
        thai.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                engine.setUserFood(0);
                thai.setEnabled(false);
            }
        });
        status_panel.add(thai);
        //Second Food Category
        final JButton mexican = new JButton("Mexican");
        mexican.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                engine.setUserFood(1);
                mexican.setEnabled(false);
            }
        });
        status_panel.add(mexican);
        //Third Food Category
        final JButton italian = new JButton("Italian");
        italian.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                engine.setUserFood(2);
                italian.setEnabled(false);
            }
        });
        status_panel.add(italian);

        //Fourth Food Category
        final JButton korean = new JButton("Korean");
        korean.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                engine.setUserFood(3);
                korean.setEnabled(false);
            }
        });
        status_panel.add(korean);

        //Fifth Food Category
        final JButton chinese = new JButton("Chinese");
        chinese.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                engine.setUserFood(4);
                chinese.setEnabled(false);
            }
        });
        status_panel.add(chinese);

        //Sixth Food Category
        final JButton american = new JButton("American");
        american.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                engine.setUserFood(5);
                american.setEnabled(false);
            }
        });
        status_panel.add(american);

        // Activities Panel
        final JPanel activities_panel = new JPanel();
        frame.add(activities_panel);
        final JLabel labelTwo = new JLabel("Activities Categories: ");
        activities_panel.add(labelTwo);

        //First Activity Category
        final JButton arcade = new JButton("Arcade");
        arcade.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                engine.setUserActivity(0);
                arcade.setEnabled(false);
            }
        });
        activities_panel.add(arcade);
        //Second Activity Category
        final JButton karaoke = new JButton("Karaoke");
        karaoke.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                engine.setUserActivity(1);
                karaoke.setEnabled(false);
            }
        });
        activities_panel.add(karaoke);
        //Third Activity Category
        final JButton takingPhotos = new JButton("Taking Photos");
        takingPhotos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                engine.setUserActivity(2);
                takingPhotos.setEnabled(false);
            }
        });
        activities_panel.add(takingPhotos);
        //Fourth Activity Category
        final JButton bowling = new JButton("Bowling");
        bowling.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                engine.setUserActivity(3);
                bowling.setEnabled(false);
            }
        });
        activities_panel.add(bowling);
        //Budget Panel
        final JPanel text_panel = new JPanel();
        frame.add(text_panel);
        final JLabel labelThree = new JLabel("Please set your budget: ");
        text_panel.add(labelThree);
        JTextField textField = new JTextField("", 20);
        text_panel.add(textField);
        final JPanel last_panel = new JPanel();
        frame.add(last_panel);
        final JButton submit = new JButton("Submit");
        submit.setEnabled(false);
     // adds event listener which listens to Enter key event
        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                JOptionPane.showMessageDialog(frame,
                        "You entered text:\n" + textField.getText());
            }
        });
        // adds key event listener
        textField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent event) {
                String content = textField.getText();
                if (!content.equals("")) {
                    submit.setEnabled(true);
                } else {
                    submit.setEnabled(false);
                }
            }
        });

        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                engine.calculate();
                System.out.println(engine.getItinerary());
            }
        });
        last_panel.add(submit);
        // Note here that when we add an action listener to the reset button, we define
        // it as an
        // anonymous inner class that is an instance of ActionListener with its
        // actionPerformed()
        // method overridden. When the button is pressed, actionPerformed() will be
        // called.
//        final JButton reset = new JButton("Two Player Mode");
//        reset.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                board.reset();
//            }
//        });
//        control_panel.add(reset);
//        // play against ai
//        final JButton ai = new JButton("Singleplayer Mode");
//        ai.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                board.reset();
//                board.turnOnAi();
//            }
//        });
//        control_panel.add(ai);

        //Submission Button

        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Start the game
    }

    /**
     * Main method run to start and run the game. Initializes the GUI elements
     * specified in Game and runs it. IMPORTANT: Do NOT delete! You MUST include
     * this in your final submission.
     */
    public static void main(String[] args) {
//        Backend engine = new Backend();
//        Double[] coo = engine.getCoordinates("261J Signs Rd, 10314 NY");
//        System.out.println(coo[0]);
        SwingUtilities.invokeLater(new GraphicMain());
    }
}
