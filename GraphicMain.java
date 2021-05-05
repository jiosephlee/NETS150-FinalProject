import java.awt.*;
import java.awt.event.*;

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

    boolean foodSelected;
    boolean activitySelected;

    public void run() {
        // NOTE: the 'final' keyword denotes immutability even for local variables.

        // Top-level frame in which game components live
        final JFrame frame = new JFrame("Nets Eats!");
        frame.setLocation(300, 300);
        BoxLayout boxlayout = new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS);
        frame.setLayout(boxlayout);
        final Backend engine = new Backend();
        frame.add(engine, BorderLayout.CENTER);
        foodSelected = false;
        activitySelected = false;

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

        //Submit Panel
        final JPanel last_panel = new JPanel();

        final JButton submit = new JButton("Submit");
        submit.setEnabled(false);
        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                engine.calculate();
            }
        });
        last_panel.add(submit);

        // Food Categories Panel
        final JPanel status_panel = new JPanel();
        frame.add(status_panel);
        final JLabel status = new JLabel("Food Categories: ");
        status_panel.add(status);

        //First Food Category
        final JButton thai = new JButton("Thai");
        thai.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                engine.setUserFood(0);
                foodSelected = true;
                if (activitySelected) {
                    submit.setEnabled(true);
                }
                thai.setEnabled(false);
            }
        });
        status_panel.add(thai);
        //Second Food Category
        final JButton mexican = new JButton("Mexican");
        mexican.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                engine.setUserFood(1);
                foodSelected = true;
                if (activitySelected) {
                    submit.setEnabled(true);
                }
                mexican.setEnabled(false);
            }
        });
        status_panel.add(mexican);
        //Third Food Category
        final JButton italian = new JButton("Italian");
        italian.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                engine.setUserFood(2);
                foodSelected = true;
                if (activitySelected) {
                    submit.setEnabled(true);
                }
                italian.setEnabled(false);
            }
        });
        status_panel.add(italian);

        //Fourth Food Category
        final JButton korean = new JButton("Korean");
        korean.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                engine.setUserFood(3);
                foodSelected = true;
                if (activitySelected) {
                    submit.setEnabled(true);
                }
                korean.setEnabled(false);
            }
        });
        status_panel.add(korean);

        //Fifth Food Category
        final JButton chinese = new JButton("Chinese");
        chinese.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                engine.setUserFood(4);
                foodSelected = true;
                if (activitySelected) {
                    submit.setEnabled(true);
                }
                chinese.setEnabled(false);
            }
        });
        status_panel.add(chinese);

        //Sixth Food Category
        final JButton american = new JButton("American");
        american.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                engine.setUserFood(5);
                foodSelected = true;
                if (activitySelected) {
                    submit.setEnabled(true);
                }
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
                activitySelected = true;
                if (foodSelected) {
                    submit.setEnabled(true);
                }
                arcade.setEnabled(false);
            }
        });
        activities_panel.add(arcade);
        //Second Activity Category
        final JButton karaoke = new JButton("Karaoke");
        karaoke.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                engine.setUserActivity(1);
                activitySelected = true;
                if (foodSelected) {
                    submit.setEnabled(true);
                }
                karaoke.setEnabled(false);
            }
        });
        activities_panel.add(karaoke);
        //Third Activity Category
        final JButton takingPhotos = new JButton("Taking Photos");
        takingPhotos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                engine.setUserActivity(2);
                activitySelected = true;
                if (foodSelected) {
                    submit.setEnabled(true);
                }
                takingPhotos.setEnabled(false);
            }
        });
        activities_panel.add(takingPhotos);

        //Fourth Activity Category
        final JButton bowling = new JButton("Bowling");
        bowling.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                engine.setUserActivity(3);
                activitySelected = true;
                if (foodSelected) {
                    submit.setEnabled(true);
                }
                bowling.setEnabled(false);
            }
        });
        activities_panel.add(bowling);

        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                engine.calculate();
                System.out.println(engine.getItinerary());
            }
        });
        frame.add(last_panel);

        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    /**
     * Main method run to start and run the game. Initializes the GUI elements
     * specified in Game and runs it. IMPORTANT: Do NOT delete! You MUST include
     * this in your final submission.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new GraphicMain());
    }
}
