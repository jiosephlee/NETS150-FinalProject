import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

/**
 * This class instantiates a ConnectFive object, which is the model for the
 * game. As the user clicks the game board, the model is updated. Whenever the
 * model is updated, the game board repaints itself and updates its status
 * JLabel to reflect the current state of the model.
 * 
 * This game adheres to a Model-View-Controller design framework. This framework
 * is very effective for turn-based games. We STRONGLY recommend you review
 * these lecture slides, starting at slide 8, for more details on
 * Model-View-Controller:
 * https://www.seas.upenn.edu/~cis120/current/files/slides/lec37.pdf
 * 
 * In a Model-View-Controller framework, GameBoard stores the model as a field
 * and acts as both the controller (with a MouseListener) and the view (with its
 * paintComponent method and the status JLabel).
 */
@SuppressWarnings("serial")
public class Backend extends JPanel{

    private static final String[] FOOD_CATEGORIES = {"Thai", "Mexican"};
    private static final String[] ACTIVITY_CATEGORIES = {"Arcade", "Taking Photos"};
    private ArrayList<Integer> userFood;
    private int userBudget;
    private ArrayList<Integer> userActivity;
    
    public Backend (){
        userFood = new ArrayList<Integer>();
        userActivity = new ArrayList<Integer>();
    }
    
    public void setUserFood (int foodCategory) {
        userFood.add(foodCategory);
    }
    
    public void setUserActivity (int activityCategory) {
        userActivity.add(activityCategory);
    }
    
    public void setUserBudget (int budget) {
        userBudget = budget;
    }
}
