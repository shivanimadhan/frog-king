
/*Shivani Madhan
 * April 22, 2019
 * FrogKing.java
 */
 
import javax.swing.*; //imports
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.imageio.*;
import java.util.Scanner;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class FrogKing extends JFrame
{
    public static FKPHolder fkph; //declares static FKPHolder object
    
    public FrogKing() //FrogKing constructor; empty
    {
    }
    
    public static void main(String [] args)
    {
        FrogKing fk =  new FrogKing(); //Instantiates FrogKing class
        fk.run(); //Calls the run method using the instance of the FrogKing class
    }
    
    public void run() //where JFrame is created
    {
        JFrame frame = new JFrame("Frog King"); //creates JFrame and titles it "Frog King"
        
        frame.setSize(740, 740); //sets JFrame's size
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //sets JFrame's close operation
        frame.setLocation(300,0); //sets JFrame's location
        frame.setResizable(false); //sets JFrame to not resizable
        
        fkph = new FKPHolder(frame); //creates instance of FKPHolder (StartPanel)

        frame.getContentPane().add(fkph); //Instance of FKPHolder is added onto the JFrame
        frame.setVisible(true); //JFrame is set visible
        frame.addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                File of = new File("Score.txt");
            	try
            	{
        			if (of.exists()) //deletes file Score.txt after one round of gameplay
                	{
                    	of.delete();                     
                 	}
                 }
                 catch(Exception io)
                 {
                	System.out.println("Exception occurred:");
                	io.printStackTrace();
            	}
            	
                int i = JOptionPane.showConfirmDialog (null, "Are you sure you want to quit?");
                
                if(i == 0)
                     System.exit(1); //closing file
            }
        });
    }
} //end of FrogKing class

class FKPHolder extends JPanel //FKPHolder class header
{
    StartPanel card1; //declares a card for StartPanel
    InstructionsPanel card2; //declares a card for InstructionsPanel
    SettingsPanel card3; //declares a card for SettingsPanel
    GamePanel card4; //declares a card for GamePanel
    PausePanel card5;
    EndPanel card6;
    CardLayout cl; //declares a CardLayout
    public String username; //declaring String username to document entered username
    public int flies, frog, loop; //declares the int flies to store the number of flies to catch and the int frog to keep track of which frog was selected for gameplay
    public int lives, score, stage; //public integers: hold the number of lives left, the user's score, and what stage is being played.
    public Timer t1, t2, t3, t4, t5, t6, t7; //declares a Timer called t
    public int pos, x, fy, by1, by2, by3, bush1, bush2, bush3, enemy1, enemy2, ey1, ey2, chestx, cy; //variables for position of the frog, the fly, the enemies, and the bushes
    public boolean insect, bush_1, bush_2, bush_3, enemy_1, enemy_2, chest; //booleans to check whether or not the position of the frog, the fly, the bushes, and the enemies has been initialized
    
    FKPHolder(JFrame frameIn) //FKPHolder constructor header
    {    
        cl = new CardLayout(); //initializes cl
        setLayout(cl); //sets to CardLayout
        
        username = new String("Frog"); //sets the default for String username to "Frog"
        
        if (card1 == null) //initializes the cards declared above (if they don't already exist)
            card1 = new StartPanel();
        if (card2 == null)
            card2 = new InstructionsPanel();
        if (card3 == null)
            card3 = new SettingsPanel();
        if (card4 == null)
            card4 = new GamePanel();
        if (card5 == null)
            card5 = new PausePanel();
        if (card6 == null)
            card6 = new EndPanel(frameIn);
        
        add(card1, "Start"); //adds the cards to the container
        add(card2, "Instructions");
        add(card3, "Settings");
        add(card4, "Game");
        add(card5, "Pause");
        add(card6, "End");
        
        cl.show(this, "Start"); //shows card1 (StartPanel)
    }
    
    class StartPanel extends JPanel implements ActionListener //StartPanel class header, implements ActionListener
    {
        JButton game, instructions, settings; //declaration of game, instructions, and settings buttons
    
        StartPanel() //StartPanel constructor header
        {
            setLayout(null); //sets layout to null
        
            setBackground(Color.WHITE); //sets background to white
        
            game = new JButton("Start Game"); //initializes game button
            game.setBounds(300, 300, 125, 30); //sets size, location, and color
            game.setBackground(new Color(0, 185, 0));
            game.addActionListener(this); //adds ActionListener for game button
            add(game); //adds button to panel
    
            instructions = new JButton("Instructions"); //initializes instructions button
            instructions.setBounds(300, 363, 125, 30); //sets size, location, and color
            instructions.setBackground(new Color(0, 185, 0));
            instructions.addActionListener(this); //adds ActionListener for instructions button
            add(instructions); //adds button to panel
        
            settings = new JButton("Settings"); //initializes settings button
            settings.setBounds(300, 426, 125, 30); //sets size, location, and color
            settings.setBackground(new Color(0, 185, 0));
            settings.addActionListener(this); //adds ActionListener for settings button
            add(settings); //adds button to panel
        }
    
        public void paintComponent(Graphics g) //where game title is drawn
        {
            super.paintComponent(g); //calls parent constructor
    
            Font f = new Font("Serif", Font.BOLD, 75); //creates Font f
            setFont(f); //sets font to f

            g.setColor(new Color(255, 185, 0)); //sets drawing color to color created with parameters given
        
            g.drawString("Frog King", 180, 225); //draws String "Frog King" at given location
        }
    
        public void actionPerformed(ActionEvent evt) //checks which button was pressed and performs corresponding action
        {
            String cmd = evt.getActionCommand(); //creates String cmd and sets it equal to the text of the button pressed
        
            if (cmd.equals("Start Game")) //checks if Start Game button was pressed
            {
                cl.show(FrogKing.fkph, "Game"); //changes card to Game card (GamePanel)
            }
        
            if (cmd.equals("Settings")) //checks if Settings button was pressed
            {
                cl.show(FrogKing.fkph, "Settings"); //changes card to Settings card (SettingsPanel)
            }
        
            if (cmd.equals("Instructions")) //checks if Instructions button was pressed
            {
                cl.show(FrogKing.fkph, "Instructions"); //changes card to Instructions card (InstructionsPanel)
            }
        }
    } //end of StartPanel class
    
    class InstructionsPanel extends JPanel implements ActionListener
    {
        Font f; //declares Font f, JButton b (to return to start panel), and JTextArea ta (to hold instructions)
        JButton b;
        JTextArea ta;
        
        InstructionsPanel()
        {
            setBackground(Color.WHITE); //sets background to white
            setLayout(null); //sets layout to null
            
            f = new Font("Serif", Font.BOLD, 75); //initializes Fonts f
            
            b = new JButton("Return"); //initializes b
            b.setBounds(25, 25, 100, 30); //sets location and size
            b.addActionListener(this); //adds ActionListener for b
            add(b); //adds b to panel
            
            //initializes String info which holds the instructions
           
            String info = new String(username + ", the prince of the kingdom of Cethen, has lost his way back home from a quest. Dangerous monsters lurk in the forests around, so username must find return to his kingdom as soon as possible.");
            info += "\n\nTo survive on the long journey back, " + username + " must catch a set amount of flies for food per level. To move right, press down on the right arrow key and to move left, press the left key. To catch the fly while moving, hold down on the space bar to extend " + username + "'s tongue.";
            info += "Otherwise, moving username on top of the fly has the same effect. To eat the fly, the user must answer a physics question right. Their score will then increase by 10 points. If the player answers the question wrong, there will be no change to their score or life count.";
            info += "\n\nSo that the user knows how to answer the question, the player will learn concepts by finding the treasure chests. There are several treasure chests per level, and each will educate the user on the concept for that stage.";
            info += "\n\nThere are different enemies in every stage that " + username + " must avoid. If " + username + " accidentally touches an enemy, they must answer a physics question correctly. If the answer is wrong, the player loses 10 points and their life count decreases by one. Else, there is no change to score or life count.";
            info += "\n\nThere are also crystal powerups, which increase the points gained per question (20 points per correct answer). Crystals are caught in the same manner as flies.\n\nThe game ends when " + username + " loses all three lives or the player completes all five levels by reaching the goals set.";
        
            ta = new JTextArea(info); //initializes b with String info
            ta.setBounds(42, 155, 653, 350); //sets size and location for ta
            ta.setLineWrap(true); //sets format
            ta.setWrapStyleWord(true);
            ta.setEditable(false); //makes ta not editable
            ta.setBorder(BorderFactory.createEtchedBorder()); //adds broder to ta
            add(ta); //adds text area
        }
        
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g); //calls paintComponent's parent constructor
            
            g.setFont(f); //sets font to f
            g.setColor(new Color(255, 185, 0)); //sets drawing color to color created with parameters given
            g.drawString("Instructions", 144, 125); //draws String "Settings" at given location
        }
        
        public void actionPerformed(ActionEvent evt)
        {
            String cmd = evt.getActionCommand(); //cmd is initialized to the text of the button pressed
            
            if(cmd.equals("Return")) //checks if return button was pressed
            {
                cl.show(FrogKing.fkph, "Start"); //returns to start panel
            }
        }
    } //end of InstructionsPanel class
    
    class SettingsPanel extends JPanel implements MouseListener, ActionListener
    {
        Image img1, img2, img3; //declaring images for different frogs
        String picName1, picName2, picName3; //declaring Strings for the image names
        JTextField name; //declaring a textfield for user to enter the username
        Font f, f2; //declaring fonts used
        JRadioButton b1, b2, b3; //declaring radio buttons to select gameplay length
        ButtonGroup bg; //declaring a button group to add the radio buttons to
        JButton b; //declaring a button to return to the start panel
        
        SettingsPanel()
        {
            setBackground(Color.WHITE); //sets background to white
            setLayout(null); //sets layout to null
            
            img1 = null; //initializes img1 as null and sets picName1 to Frog1.png
            picName1 = new String("Frog1.png");
            
            img2 = null; //initializes img2 as null and sets picName2 to Frog2.png
            picName2 = new String("Frog2.png");
            
            img3 = null; //initializes img3 as null and sets picName3 to Frog3.png
            picName3 = new String("Frog3.png");
            
            frog = 0; //initializes String frog (declared in FKPHolder)
            username = new String("Frog"); //initializes username and length as empty Strings
            
            addMouseListener(this); //adds a MouseListener to track which frog was selected for gameplay
            
            name = new JTextField("", 25); //initializes the text field name
            name.setBounds(270, 210, 200, 25); //sets bounds for name
            name.addActionListener(this); //adds ActionListener so actions relative to name can be listened for
            add(name); //adds name to the panel
            
            f = new Font("Serif", Font.BOLD, 75); //initializes Fonts f and f2
            f2 = new Font("Serif", Font.PLAIN, 25);
            
            b = new JButton("Enter"); //initializes button b with name Return
            b.setBounds(25, 25, 100, 30); //sets bounds for b
            b.addActionListener(this); //adds ActionListener so actions relative to b can be listened for
            add(b); //adds b to the panel
            
            b1 = new JRadioButton("Brief"); //initializes radio button b2
            b1.setBounds(225, 530, 65, 20); //sets bounds
            b1.setBackground(Color.WHITE); //changes b1's color to white
            b1.addActionListener(this); //adds ActionListener so actions relative to b1 can be listened for
            
            b2 = new JRadioButton("Intermediate"); //initializes radio button b1
            b2.setBounds(310, 530, 125, 20); //sets bounds
            b2.setBackground(Color.WHITE); //changes b2's color to white
            b2.addActionListener(this); //adds ActionListener so actions relative to b2 can be listened for
            
            b3 = new JRadioButton("Thorough"); //initializes radio button b3
            b3.setBounds(440, 530, 100, 20); //sets bounds
            b3.setBackground(Color.WHITE); //changes b3's color to white
            b3.addActionListener(this); //adds ActionListener so actions relative to b3 can be listened for
            
            bg = new ButtonGroup(); //initializes button group bg
            
            bg.add(b1); //adds buttons b1, b2, and b3 to the button group bg
            bg.add(b2);
            bg.add(b3);
            
            add(b1); //adds buttons b1, b2, and b3 to the panel
            add(b2);
            add(b3);
            
            getMyImage(); //calls the getMyImage() method to load images (for drawing later)
            
        }
        
        public void getMyImage()
        {
            File f1 = new File(picName1); //creates 3 files to add images to; uses the picName Strings
            File f2 = new File(picName2);
            File f3 = new File(picName3);
            
            try //try-catch block to scan for errors
            {
                img1 = ImageIO.read(f1); //adds the images to their corresponding file
                img2 = ImageIO.read(f2);
                img3 = ImageIO.read(f3);
            }
            
            catch (IOException io)
            {
                System.out.println("Cannot load image(s)."); //error message printed if an image cannot be loaded
                io.printStackTrace();
            }    
        }
        
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g); //calls paintComponent's parent constructor
            
            g.setFont(f); //sets font to f
            g.setColor(new Color(255, 185, 0)); //sets drawing color to color created with parameters given
            g.drawString("Settings", 225, 125); //draws String "Settings" at given location
            
            g.setFont(f2); //sets font to f2
            g.setColor(Color.BLACK); //sets drawing color to black
            g.drawString("Enter an username:", 125, 185); //draws the Strings listed at their given locations
            g.drawString("Pick a player:", 125, 295);
            g.drawString("Gameplay length:", 125, 500);
            
            g.drawImage(img1, 127, 325, 144, 112, this); //resizes and then draws the images at their given locations
            g.drawImage(img3, 296, 325, 144, 112, this);
            g.drawImage(img2, 465, 325, 144, 112, this);
        }
        
        public void mousePressed(MouseEvent evt)
        {
            int x = evt.getX(); //declares and initializes variables x and y to the location of the mouse when pressed
            int y = evt.getY();
            
            if ((x < 271 && x > 127) && (y < 437 && y > 325)) //checks if the mouse was pressed in the box around image 1
            {
                frog = 1; //sets int frog to 1 for later use
            }
            
            if ((x < 440 && x > 296) && (y < 437 && y > 325)) //checks if the mouse was pressed in the box around image 2
            {
                frog = 2; //sets int frog to 2 for later use
            }
            
            if ((x < 609 && x > 465) && (y < 437 && y > 325)) //checks if the mouse was pressed in the box around image 3
            {
                frog = 3; //sets int frog to 3 for later use
            }
        }
        
        public void mouseReleased(MouseEvent evt) {}; //rest of MouseEvent methods are empty
        public void mouseClicked(MouseEvent evt) {};
        public void mouseEntered(MouseEvent evt) {};
        public void mouseExited(MouseEvent evt) {};
        
        public void actionPerformed(ActionEvent evt)
        {
            username = name.getText(); //initializes String username to the text entered into the text field
            
            String pressed = evt.getActionCommand(); //creates String pressed and sets it to the text from the button pressed
            
            if (pressed.equals("Brief")) //checks if radio button "Brief" was pressed
            {
                flies = (int)(Math.random()*6 + 5); //sets amount of flies to a random number between 5 and 10
            }
            
            if (pressed.equals("Intermediate"))  //checks if radio button "Intermediate" was pressed
            {
                flies = (int)(Math.random()*6 + 10); //sets amount of flies to a random number between 10 and 15
            }
            
            if (pressed.equals("Thorough"))  //checks if radio button "Thorough" was pressed
            {
                 flies = (int)(Math.random()*6+ 15); //sets amount of flies to a random number between 15 and 20
            }
            
            if (pressed.equals("Enter"))  //checks if button "Return" was pressed
            {
                cl.show(FrogKing.fkph, "Start"); //changes card displayed to the Start Panel
            }
        }
        
    } //end of SettingsPanel class
     
    class GamePanel extends JPanel implements ActionListener, KeyListener
    {
        JButton b1, b2; //declares buttons b1, b2 to start level
        boolean start, add; //declares a boolean start that will clear the screen if true and add that will re-add b1 to screen
        public boolean tongue, flyTouched, chestTouched, enemyTouched1, enemyTouched2, newLevel, fly; //declares variables to check whether the fly, the bushes, or the enemies have been touched
        Image img1, frogimg, img2, flyimg, bushimg, eimg, cimg; //declares an Image variables to hold all the images used
        String img1name, img2name, frogname, flyname, bushname, enemyname, chestname; //declares a String for the Images' name
        QuestionFrame qf; //declares an instane of Question Frame 
        JFrame f; //declares the JFrame that will be passed into QuestionFrame
        PrintWriter pw;
        Scanner in; 
        int chestNumber, begin; //declares integers chestNumber and begin to check save how many times Chest Frame has been called and whether or not to start a new level
        
        GamePanel()  //GamePanel's constructor
        {
            setBackground(new Color(0, 204, 0)); //sets background to green
            setLayout(null); //sets Layout to null
                
            b1 = new JButton("Start"); //initializes button b1 
            b1.setBounds(320, 415, 100, 20); //sets b1's location and size
            b1.addActionListener(this); //adds ActionListener to b1
            add(b1); //adds b to panel
            
            b2 = new JButton("Pause"); //initializes button b1 
            b2.setBounds(605, 25, 100, 20); //sets b1's location and size
            b2.addActionListener(this); //adds ActionListener to b1
            
            start = false; //initializes all booleans to default values
            add = false; 
            insect = false;
            tongue = false;
            chestTouched = false;
            flyTouched = false;
            fly = false;
            enemyTouched1 = false;
            enemyTouched2 = false;
            newLevel = true; 

            x = 298; //initializes all ints to default values
            fy = -50;
            pos = 0;
            bush1 = 0;
            bush2 = 0;
            bush3 = 0;
            by1 = -100;
            by2 = -100;
            by3 = -100;
            ey1 = -75;
            ey2 = -75;
            cy = -75;
            chestNumber = 0;
            begin = 0;
            
            f = null; //initializes JFrame f and QuestionFrame qf to null
            qf = null;
            pw = null; //initializes PrintWriter and Scanner to null
            in = null;
            
            img1 = null; //image is set to null
            img1name = "heartf.png"; //image name initialized as heartf.png
            
            img2 = null; //image is set to null
            img2name = "hearte.png"; //image name initialized as heartf.png
            
            if (frog == 1) //assigns frogname its corresponding name based on which frog was selected for gameplay; has an error
                frogname = "Frog1.png";
            if (frog == 2)
                frogname = "Frog2.png";
            if (frog == 3)
                frogname = "Frog3.png";
            if (frog == 0)
                frogname = "Frog1.png";
                
            frogimg = null; //initializes all images to null
            flyimg = null;
            bushimg = null;
            eimg = null;
            cimg = null;
            
            flyname = "Fly.png"; //initializes Strings to the name of their corresponding images
            bushname = "Bush.png";
            enemyname = "Ivy.png";
            chestname = "Chest.png";
            
            addKeyListener(this); //KeyListener is added to the panel
             
            tryCatch(); //calls tryCatch() method
            
            t1 = new Timer(50, new ActionListener() //initializes Timers t1, t2, t3, t4, t5, t6, t7; has its own actionPerformed method()

            {
                public void actionPerformed(ActionEvent evt) //if timer is started, drawInsect() and paintComponent() methods are called
                {
                    drawInsect();
                    repaint();
                }
            } );
            
            t2 = new Timer(50, new ActionListener()
            
            {
                public void actionPerformed(ActionEvent evt) //if timer is started, moveBackground1() and paintComponent() methods are called
                {
                    moveBackground1();
                    repaint();
                }
            } );
            
            t3 = new Timer(50, new ActionListener()
            
            {
                public void actionPerformed(ActionEvent evt) //if timer is started, moveBackground2() and paintComponent() methods are called
                {
                    moveBackground2();
                    repaint();
                }
            } );
            
            t4 = new Timer(50, new ActionListener()
            
            {
                public void actionPerformed(ActionEvent evt) //if timer is started, moveBackground3() and paintComponent() methods are called
                {
                    moveBackground3();
                    repaint();
                }
            } );
            
            t5 = new Timer(50, new ActionListener() 
            {
                public void actionPerformed(ActionEvent evt) //if timer is started, drawEnemy1() and paintComponent() methods are called
                {
                    drawEnemy1();
                    repaint();
                }
            } );
            
            t6 = new Timer(50, new ActionListener()
            {
                public void actionPerformed(ActionEvent evt) //if timer is started, drawEnemy2() and paintComponent() methods are called
                {
                    drawEnemy2();
                    repaint();
                }
            } );
            
            t7 = new Timer(50, new ActionListener()
            {
                public void actionPerformed(ActionEvent evt) //if timer is started, drawChest() and paintComponent() methods are called
                {
                    drawChest();
                    repaint();
                }
            } );
            
        } //end of GamePanel's constructor
        
        public void drawInsect()
        {
            if (insect == false) //checks if boolean insect is false; fly image is not yet drawn onto screen
            {
                pos = (int)(Math.random()*690); //generates a random number for the x-coordinate of the fly image; saved to pos
                
                while (pos <= 440 && pos >= 250)
                {
                    pos = (int)(Math.random()*690);
                }
                
                insect = true; //sets insect to true because fly is now ready to be drawn to screen
            }
            
            else //checks if boolean insect is true
            {
                fy += 5; //increments y by 4; this controls the y-coordinate of the fly image and moves it down with time
            }
            
            if ((x >= pos && x <= (pos+50) || ((x+144) >= pos && (x+144) <= (pos+50)) || ((x+72) >= pos && (x+72) <= (pos+50))) && (fy <= 602 && fy >= 590)) //checks if frog and fly are overlapping
            {
                if ((chestNumber == 0 || chestNumber == 2) && flyTouched == false) //checks if first chest of the level hasn't been touched yet
                {
                    //increments score and calls the paintComponent() method; resets the fly's position
                    pw.println("10 points");
                    System.out.println("increment by 10");
                    fy = -50;
                    repaint();
                    flyTouched = true;
                }
                
                else if (chestNumber == 1 || chestNumber == 4) //checks if the first chest of the level has already been called
                {
                  
                    loop++;
                
                    t1.stop(); //stops all the timers
                    t2.stop();
                    t3.stop();
                    t4.stop();
                    t5.stop();
                    t6.stop();
                    t7.stop();
                    fy = -50; //resets the fly's position
                    
                    if (qf == null) //checks whether or not qf has been initialized yet
                    {
                        f = new JFrame("Question");
                        fly = true; //sets boolean fly to true; this means score will be incremented if question is answered right
                        qf = new QuestionFrame(f, flies, lives, fly, t1, t2, t3, t4, t5, t6, t7, by1, by2, by3, ey1, ey2, cy); //passes in parameters for Question Frame
                    }
                    else
                    {
                    	fly = true; 
                        this.handleQF(); //calls the handleQF() method
                    }
                }
            }
            
            if (fy >= 740) //checks if the fly has moved off the screen
            {
                t1.restart(); //restarts Timer t1 
                fy = -50; //resets the fly's position
                insect = false; 
                t1.setInitialDelay(1750);
                t1.start(); //starts Timer t1
            }
            
            if (fy == -50) //checks if the fly's position has been reset
            {
                flyTouched = false;
                
                pos = (int)(Math.random()*690); //generates a random number for the x-coordinate of the fly image; saved to pos
                
                while (pos <= 440 && pos >= 250)
                {
                    pos = (int)(Math.random()*690);
                }
            }
        }
        
        public void drawEnemy1()
    	{
            if (enemy_1 == false) //checks if boolean insect is false; fly image is not yet drawn onto screen
            {
                enemy1 = (int)(Math.random()*665); //generates a random number for the x-coordinate of the fly image; saved to pos
                
                while (enemy1 <= 440 && enemy1 >= 225)
                {
                    enemy1 = (int)(Math.random()*665);
                }
                
                enemy_1 = true; //sets insect to true because fly is now ready to be drawn to screen
            }
            
            else //checks if boolean insect is true
            {
                ey1 += 5; //increments y by 4; this controls the y-coordinate of the fly image and moves it down with time
            }
            
            if ((x >= enemy1 && x <= (enemy1+75) || ((x+144) >= enemy1 && (x+144) <= (enemy1+75)) || ((x+72) >= enemy1 && (x+72) <= (enemy1+75))) && (ey1 <= 602 && ey1 >= 590)) //checks if frog and enemy1 are overlapping
            {
                if ((chestNumber == 0 || chestNumber == 2)) //checks if the first chest of the level hasn't been touched yet
                {
                    pw.println("-10 points");
                    System.out.println("decrement by 10");
                    ey1 = -75; //resets enemy1's position
                    repaint(); //calls paintComponent()
                    enemyTouched1 = true;
                }
                
                else if (chestNumber == 1 || chestNumber == 4) //checks if the first chest of the level has been called already
                {
                    loop++;
                
                    t1.stop(); //stops all the timers
                    t2.stop();
                    t3.stop();
                    t4.stop();
                    t5.stop();
                    t6.stop();
                    t7.stop();
                    ey1 = -75;
                    
                    if (qf == null) //checks whether or not qf has been initialized yet
                    {
                        f = new JFrame("Question");
                        fly = false; //sets boolean fly to true; this means score will be incremented if question is answered right
                        qf = new QuestionFrame(f, flies, lives, fly, t1, t2, t3, t4, t5, t6, t7, by1, by2, by3, ey1, ey2, cy); //passes in parameters for Question Frame
                    }
                    else
                    {
                    	fly = false; 
                        this.handleQF(); //calls the handleQF() method
                    }
                }
            }
            
            if (ey1 >= 740) //checks if enemy1's position is off the screen
            {
                t5.restart(); //restarts Timer t5
                ey1 = -75; //sets enemy 1 back to its original position
                enemy_1 = false;
                t5.setInitialDelay(3000);
                t5.start(); //starts t5
            }
            
            if (ey1 == -75) //checks if enemy1 is at its default position
            {
                enemyTouched1 = false;
                
                enemy1 = (int)(Math.random()*665); //generates a random number for the x-coordinate of the fly image; saved to pos
                
                while (enemy1 <= 440 && enemy1 >= 225)
                {
                    enemy1 = (int)(Math.random()*665);
                }
            }
        }
        
         public void drawEnemy2()
    	{
            if (enemy_2 == false) //checks if boolean insect is false; fly image is not yet drawn onto screen
            {
                enemy2 = (int)(Math.random()*665); //generates a random number for the x-coordinate of the fly image; saved to pos
                
                while (enemy2 <= 440 && enemy2 >= 225)
                {
                    enemy2 = (int)(Math.random()*665);
                }
                
                enemy_2 = true; //sets insect to true because fly is now ready to be drawn to screen
            }
            
            else //checks if boolean insect is true
            {
                ey2 += 5; //increments y by 4; this controls the y-coordinate of the fly image and moves it down with time
            }
            
            if ((x >= enemy2 && x <= (enemy2+75) || ((x+144) >= enemy2 && (x+144) <= (enemy2+75)) || ((x+72) >= enemy2 && (x+72) <= (enemy2+75))) && (ey2 <= 602 && ey2 >= 590)) //checks if frog and enemy1 are overlapping
            {
                if ((chestNumber == 0 || chestNumber == 2)) //checks if the first chest of the level hasn't been touched yet
                {
                    pw.println("-10 points");
                    System.out.println("decrement by 10");
                    ey2 = -75; //resets enemy1's position
                    repaint(); //calls paintComponent()
                    enemyTouched2 = true;
                }
                
                else if (chestNumber == 1 || chestNumber == 4) //checks if the first chest of the level has been called already
                {
                    loop++;
                
                    t1.stop(); //stops all the timers
                    t2.stop();
                    t3.stop();
                    t4.stop();
                    t5.stop();
                    t6.stop();
                    t7.stop();
                    ey2 = -75;
                    
                    if (qf == null) //checks whether or not qf has been initialized yet
                    {
                        f = new JFrame("Question");
                        fly = false; //sets boolean fly to true; this means score will be incremented if question is answered right
                        qf = new QuestionFrame(f, flies, lives, fly, t1, t2, t3, t4, t5, t6, t7, by1, by2, by3, ey1, ey2, cy); //passes in parameters for Question Frame
                    }
                    else
                    {
                    	fly = false; 
                        this.handleQF(); //calls the handleQF() method
                    }
                }
            }
            
            if (ey2 >= 740) //checks if enemy1's position is off the screen
            {
                t6.restart(); //restarts Timer t5
                ey2 = -75; //sets enemy 1 back to its original position
                enemy_2 = false;
                t6.setInitialDelay(3000);
                t6.start(); //starts t5
            }
            
            if (ey2 == -75) //checks if enemy1 is at its default position
            {
                enemyTouched2 = false;
                
                enemy2 = (int)(Math.random()*665); //generates a random number for the x-coordinate of the fly image; saved to pos
                
                while (enemy2 <= 440 && enemy2 >= 225)
                {
                    enemy2 = (int)(Math.random()*665);
                }
            }
        }
        
        public void drawChest() 
        {            
            if (chest == false) //checks if treasure chest has a x-position yet
            {
                chestx = (int)(Math.random()*665);
                
                while ((chestx <= 440 && chestx >= 225) || chestx == pos) //generates a random number within range for the chest's position
                {
                    chestx = (int)(Math.random()*665);
                }
                    
                chest = true;
            }
            
            else
            {
                cy += 5; //moves the chest down 5 units
            }
            
            if (cy >= 740 && chestTouched == true) //checks if the the chest's position is off the screen
            { 
                t7.stop(); //stops Timer t7
                cy = -75; //resets chest's position
                chest = false;
                chestTouched = false;
                t7.setInitialDelay(20000);
                
                if (chestNumber <= 1)
                    t7.start();
            }
            
            if (cy >= 740 && chestTouched == false) //checks if the the chest's position is off the screen BUT it hasn't been caught yet 
            {
                t1.stop(); //stops all the timers
                t2.stop();
                t3.stop();
                t4.stop();
                t5.stop();
                t6.stop();
                t7.stop();
                
                ChestFrame cf = new ChestFrame(chestNumber, t1, t2, t3, t4, t5, t6, t7); //calls Chest Frame
                 
                chestNumber++; //increments number of chests called so far
                chestTouched = true;
            }
            
            if ((x >= chestx && x <= (chestx+75) || ((x+144) >= chestx && (x+144) <= (chestx+75)) || ((x+72) >= chestx && (x+72) <= (chestx+75))) && (cy <= 602 && cy >= 590) && chestTouched == false) //checks if frog and chest overlap
            {
                t1.stop(); //stops all the timers
                t2.stop();
                t3.stop();
                t4.stop();
                t5.stop();
                t6.stop();
                t7.stop();
                
                ChestFrame cf = new ChestFrame(chestNumber, t1, t2, t3, t4, t5, t6, t7); //calls Chest Frame
                
                chestNumber++; //increments number of chests called so far
                chestTouched = true;
            }
        }
        
                
        public void moveBackground1()
        {
            if (bush_1 == false) //checks if bush1 has a position yet
            {
                bush1 = (int)(Math.random()*640); //generates a random x-value for bush1's position
                
                while ((bush1 <= 440 && bush1 >= 200) || bush1 == pos)
                {
                    bush1 = (int)(Math.random()*640);
                }
                    
                bush_1 = true;
            }
            
            else
            {
                by1 += 5; //increments bush1's y-value by 5
            }
            
            if (by1 >= 740) //checks if by1's position is off the screen
            {
                t2.restart(); //restarts Timer t2
                by1 = -100; //resets bush1's position
                bush_1 = false;
                t2.setInitialDelay(3000);
                t2.start(); //starts t2
            }
        }
        
        public void moveBackground2()
        {
            if (bush_2 == false) //checks if bush2 has a position yet
            {
                bush2 = (int)(Math.random()*640); //generates a random x-value for bush2's position
                
                while ((bush2 <= 440 && bush2 >= 200) || bush2 == pos)
                {
                    bush2 = (int)(Math.random()*640);
                }
                    
                bush_2 = true;
            }
            
            else
            {
                by2 += 5; //increments bush2's y-value by 5
            }
            
            if (by2 >= 740) //checks if by2's position is off the screen
            {
                t3.restart(); //restarts Timer t2
                by2 = -100; //resets bush2's position
                bush_2 = false;
                t3.setInitialDelay(3000);
                t3.start(); //starts t3
            }
        }
        
        public void moveBackground3()
        {
            if (bush_3 == false) //checks if bush3 has a position yet
            {
                bush3 = (int)(Math.random()*640); //generates a random x-value for bush3's position
                
                while ((bush3 <= 440 && bush3 >= 200) || bush3 == pos)
                {
                    bush3 = (int)(Math.random()*640);
                }
                    
                bush_3 = true;
            }
            
            else
            {
                by3 += 5; //increments bush3's y-value by 5
            }
            
            if (by3 >= 740) //checks if by3's position is off the screen
            {
                t4.restart(); //restarts Timer t4
                by3 = -100; //resets bush3's position
                bush_3 = false;
                t4.setInitialDelay(3000);
                t4.start(); //starts t4
            }
        }
        
        public void keyPressed(KeyEvent evt)  //checks if a key was pressed
        {
        	requestFocusInWindow();
        	
            if (evt.getKeyCode() == KeyEvent.VK_RIGHT) //checks if right arrow was checked
            {
                x += 6; //increments x by 4 so frog will be moved to the right
            }
            
            if (evt.getKeyCode() == KeyEvent.VK_LEFT) //checks if left arrow was checked
            {
                x -= 6; //decrements x by 4 so frog will be moved to the left
            }
            
            repaint();  //calls paintComponent() method
            
            if (evt.getKeyCode() == KeyEvent.VK_SPACE) //checks if the spacebar has been pressed
            {
                tongue = true; //if so, tongue is set to true so the frog's tongue can be drawn and paintComponent() is called
                repaint();
                
                if (x+60 <= pos+50 && x+60 >= pos) //checks if the frog's tongue and the fly are overlapping
                {
                    if ((chestNumber == 0 || chestNumber == 3) && flyTouched == false) //checks if the first chest hasn't been called
                    {
                        pw.println("10 points"); //increments score by 10
                        System.out.println("increment by 10");
                        fy = -50; //resets fly's position
                        repaint();
                        flyTouched = true;
                    }
                
                    else if ((chestNumber == 1 || chestNumber == 2 || chestNumber == 4) && enemyTouched1 == false) //checks if the first chest has been called
                    {
                        loop++;
                
                        t1.stop(); //stops all the timers
                        t2.stop();
                        t3.stop();
                        t4.stop();
                        t5.stop();
                        t6.stop();
                        t7.stop();
                        
                        fy = -50; //resets fly's position
                
                        if (qf == null) //checks if qf has been initialized
                        {
                            f = new JFrame("Question");
                            fly = true;
                            qf = new QuestionFrame(f, flies, lives, fly, t1, t2, t3, t4, t5, t6, t7, by1, by2, by3, ey1, ey2, cy); //calls qf with given parameters
                        }
                        else
                        {
                        	fly = true;
                            this.handleQF(); //calls handleQF();
                        }
                        
                        tongue = false; //sets the tongue to invisible again
                    }
                    
                    if (fy == -50) //checks if the fly has returned to its default position
                    {
                        flyTouched = false;
                        
                        pos = (int)(Math.random()*690); //generates a random number for the x-coordinate of the fly image; saved to pos
                
                        while (pos <= 440 && pos >= 250)
                        {
                            pos = (int)(Math.random()*690);
                        }
                    }
                }
                
                if (x+60 <= chestx+75 && x+60 >= chestx && chestTouched == false && cy > -75) //checks if the frog's tongue and the chest are touching
                {
                    t1.stop(); //stops all the timers
                    t2.stop();
                    t3.stop();
                    t4.stop();
                    t5.stop();
                    t6.stop();
                    t7.stop();
                    
                    ChestFrame cf = new ChestFrame(chestNumber, t1, t2, t3, t4, t5, t6, t7); //calls Chest Frame
                    
                    chestNumber++; //increments the number of chests called so far
                    tongue = false;
                    chestTouched = true;
                }
                
               if (x+60 <= enemy1+75 && x+60 >= enemy1) //checks if the frog's tongue and enemy1 are touching 
                {
                    if ((chestNumber == 0 || chestNumber == 3) && enemyTouched1 == false) //checks if the first chest hasn't been called yet
                    {
                        pw.println("-10 points"); //decrements the score by 10
                        System.out.println("decrement by 10");
                        ey1 = -75; //resets enemy1's position
                        repaint(); //calls paintComponent()
                        enemyTouched1 = true;
                    }
                
                    else if (chestNumber == 1 || chestNumber == 2 || chestNumber == 4) //checks if the first chest has been called
                    {
                        loop++;
                
                        t1.stop(); //stops all the timers
                        t2.stop();
                        t3.stop();
                        t4.stop();
                        t5.stop();
                        t6.stop();
                        t7.stop();
                        
                        ey1 = -75; //resets enemy1's position
                
                        
                        if (qf == null) //checks if qf has been initialized
                        {
                            f = new JFrame("Question");
                            fly = false;
                            qf = new QuestionFrame(f, flies, lives, fly, t1, t2, t3, t4, t5, t6, t7, by1, by2, by3, ey1, ey2, cy); //calls qf with given parameters
                        }
                        else
                        {
                        	fly = false;
                            this.handleQF(); //calls handleQF();
                        }
                        
                        tongue = false; //sets the tongue to invisible again
                    }
                    
                    if (ey1 == -75) //checks if enemy1 has returned to its default position
                    {
                        enemyTouched1 = false;
                        
                        enemy1 = (int)(Math.random()*665); //generates a random number for the x-coordinate of enemy2
                
                        while (enemy1 <= 440 && enemy1 >= 225)
                        {
                            enemy1 = (int)(Math.random()*665);
                        }
                    }
                }
                
                if (x+60 <= enemy2+75 && x+60 >= enemy2) //checks if the frog's tongue and enemy2 are touching 
                {
                    if ((chestNumber == 0 || chestNumber == 3) && enemyTouched2 == false) //checks if the first chest hasn't been called yet
                    {
                        pw.println("-10 points"); //decrements the score by 10
                        System.out.println("decrement by 10");
                        ey2 = -75; //resets enemy2's position
                        repaint(); //calls paintComponent()
                        enemyTouched2 = true;
                    }
                
                    else if (chestNumber == 1 || chestNumber == 2 || chestNumber == 4) //checks if the first chest has been called
                    {
                        loop++;
                
                        t1.stop(); //stops all the timers
                        t2.stop();
                        t3.stop();
                        t4.stop();
                        t5.stop();
                        t6.stop();
                        t7.stop();
                        
                        ey2 = -75; //resets enemy2's position
                
                        
                        if (qf == null) //checks if qf has been initialized
                        {
                            f = new JFrame("Question");
                            fly = false;
                            qf = new QuestionFrame(f, flies, lives, fly, t1, t2, t3, t4, t5, t6, t7, by1, by2, by3, ey1, ey2, cy); //calls qf with given parameters
                        }
                        else
                        {
                        	fly = false;
                            this.handleQF(); //calls handleQF();
                        }
                        
                        tongue = false; //sets the tongue to invisible again
                    }
                    
                    if (ey2 == -75) //checks if enemy2 has returned to its default position
                    {
                        enemyTouched2 = false;
                        
                        enemy2 = (int)(Math.random()*665); //generates a random number for the x-coordinate of enemy2; saved to enemy2
                
                        while (enemy2 <= 440 && enemy2 >= 225)
                        {
                            enemy2 = (int)(Math.random()*665);
                        }
                    }
                }
            }
        }
        
       public void handleQF() 
        {
            f.setVisible(true); //sets Question Frame to visible
            
            qf.getQP().initialize(lives, fly, by1, by2, by3, ey1, ey2, cy); //initializes the variables in Question Panel class
            qf.getQP().tryAndCatch(); //calls all the required method
            qf.getQP().readFile();  
            qf.getQP().addButtons();

			System.out.println("before while loop");
			tryCatch();
			lives = 3;
			
			while(in.hasNext())
			{
				String readLine = in.nextLine();
                
                if (readLine.indexOf("-10") == 0)
                {
                    lives--;
                    System.out.println("lives lost" + lives);
                }
            }
                
            if (lives == 0)
            {
                cl.show(FrogKing.fkph, "End");
                t1.stop(); //stops all the timers
                t2.stop();
                t3.stop();
                t4.stop();
                t5.stop();
                t6.stop();
                t7.stop();
            	System.out.println("lives == 0");
            }
            else
            {
                repaint();
                System.out.println("Repaint called");
            }
			
		//	in.close();
            
            begin = qf.getQP().restart(begin); //sets int begin to what is returned from qf's restart() method
            if (begin != 1) //checks if begin isn't equal to 1/ready to change levels
            {
                if (begin == 2)
                {
                    enemyname = "Hedgehog.png"; //sets enemyname to "Hedgehog.png"
                    setBackground(new Color(195, 125, 0)); //sets to according background color
                    chestNumber = 3;
                
                                    
                    tryCatch(); //calls tryCatch() method to re-initialize images
                    newLevel = true;
                    x = 298; //resets positions of all the images
                    fy = -50;
                    by1 = -100;
                    by2 = -100;
                    by3 = -100;
                    cy = -75;  
                    ey1 = -75;
                    ey2 = -75;   
                    
                    t1.restart(); //restarts all the timers
                    t2.restart();
                    t3.restart();
                    t4.restart();
                    t5.restart();
                    t6.restart();
                    t7.restart();
                    
                    t1.stop(); //stops all the timers
                    t2.stop();
                    t3.stop();
                    t4.stop();
                    t5.stop();
                    t6.stop();
                    t7.stop();
                    
                    t1.setInitialDelay(0); //sets the initial delays for all the timers
                    t2.setInitialDelay(3000);
                    t3.setInitialDelay(6000);
                    t4.setInitialDelay(9000);
                    t5.setInitialDelay(5000);
                    t6.setInitialDelay(10000);
                    t7.setInitialDelay(20000);
                    
                    removeAll(); //clears screen
                    revalidate();
                    add(b1); //adds "Start" button
                    qf.getQP().name = "Level2.txt";
                    qf = null;
                    
                    
                    repaint(); //calls paintComponent()
                }
                                
                else 
                {
                    cl.show(FrogKing.fkph, "End");  //shows EndPanel
                    
                    t1.stop(); //stops all the timers
                    t2.stop();
                    t3.stop();
                    t4.stop();
                    t5.stop();
                    t6.stop();
                    t7.stop();
                }
            }  
        } 
        
        public void keyReleased(KeyEvent evt) //keyReleased method
        {
            if (evt.getKeyCode() == KeyEvent.VK_SPACE) //checks if the spacebar was pressed
            {
                tongue = false; //sets tongue to false, won't be drawn
                repaint(); //calls paintComponent()
            }
        }
        
        public void keyTyped(KeyEvent evt) {}; //rest of KeyEvent methods are empty
        
        public void tryCatch()
        {        
            File f1 = new File(img1name); //creates a Files with parameters 
            File f2 = new File(img2name); 
            File f3 = new File(frogname); 
            File f4 = new File(flyname);  
            File f5 = new File(bushname); 
            File f6 = new File(enemyname);
            File f7 = new File(chestname);
            File f8 = new File("Score.txt");
            
            try //try-catch block to scan for errors
            {
                img1 = ImageIO.read(f1); //adds images to their files
                img2 = ImageIO.read(f2); 
                frogimg = ImageIO.read(f3); 
                flyimg = ImageIO.read(f4); 
                bushimg = ImageIO.read(f5); 
                eimg = ImageIO.read(f6);
                cimg = ImageIO.read(f7);
            }
            
            catch (IOException io) //
            {
                System.out.println("Cannot load image(s)."); //deals with any errors by printing error message
                io.printStackTrace();
            }
            
            try
            {
                FileWriter fw = new FileWriter(f8, true);
              	BufferedWriter bw = new BufferedWriter(fw);
              	pw = new PrintWriter(bw);
                in = new Scanner(f8);
            }
            
            catch (IOException io)
            {
                System.out.println("Cannot find text file."); //deals with any errors by printing error message
                System.exit(1);
            }
        }
            
        public void actionPerformed(ActionEvent evt)
        {
            requestFocusInWindow(); //makes sure computer is ready to record data received from the user
            String cmd = evt.getActionCommand(); //initializes String cmd to the text of the button pressed
                
            if (cmd.equals("Start")) //checks if Start button was pressed
            {
                t1.start(); //timers are started; allows the animations to begin
                t2.setInitialDelay(3000);
                t2.start();
                t3.setInitialDelay(6000);
                t3.start();
                t4.setInitialDelay(9000);
                t4.start();
                t5.setInitialDelay(5000);
                t5.start();
                t6.setInitialDelay(10000);
                t6.start();
                t7.setInitialDelay(20000);
                t7.start();
                repaint(); //calls paintComponent() method
                start = true; //initializes boolean start to true
            }
            
            if (cmd.equals("Pause")) //checks if "Pause" button was selected
            {
                t1.stop(); //stops all the timers
                t2.stop(); 
                t3.stop();
                t4.stop();
                t5.stop();
                t6.stop();
                t7.stop();
                cl.show(FrogKing.fkph, "Pause");   //shows Pause Panel
            }        
        }
        
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g); //calls paintComponent() method's parent constructor
            
            if (newLevel == true) //checks if level 1 is being played
            {
                Color c1 = new Color(0, 0, 0, 175); //creates Color c1 with given parameter and sets drawing color to c1
                g.setColor(c1);
                g.fillRect(170, 232, 400, 225); //draws a filled rectangle with given size and location
            
                Color c2 = new Color(255, 185, 0); //creates Color c2 with given parameter and sets drawing color to c2
                g.setColor(c2);
                Font f = new Font("Serif", Font.BOLD, 60); //creates Font f with given parameters and sets font to f
                g.setFont(f);
            
                if (flies <= 10 && flies >= 5) //checks if radio button "Brief" was pressed
                {
                    flies = (int)(Math.random()*6 + 5); //sets amount of flies to a random number between 5 and 10
                }
                else if (flies <= 15 && flies > 10)  //checks if radio button "Intermediate" was pressed
                {
                    flies = (int)(Math.random()*6 + 10); //sets amount of flies to a random number between 10 and 15
                }
                else if (flies <= 20 && flies > 15)  //checks if radio button "Thorough" was pressed
                {
                     flies = (int)(Math.random()*6+ 15); //sets amount of flies to a random number between 15 and 20
                }
                else
                {
                    flies = (int)(Math.random()*6 + 10);
                }
                
                if (begin == 2)
                {
                	if (lives == 2) //checks if the user has two lives remaining
            		{
            			System.out.println("lives == 2");
            			g.setColor(new Color(195, 125, 0));
            			g.fillRect(157, 25, 51, 48);
                		g.drawImage(img2, 157, 25, 51, 48, this); //draws an empty heart to indicate one life is lost
            		}
            
           		 if (lives == 1) //checks if the user has one life remaining
            		{
            			System.out.println("lives == 1");
            			g.setColor(new Color(195, 125, 0));
            			g.fillRect(157, 25, 51, 48);
            			g.fillRect(91, 25, 51, 48);
                		g.drawImage(img2, 91, 25, 51, 48, this); //draws two empty hearts to indicate two lives is lost
                		g.drawImage(img2, 157, 25, 51, 48, this);
            		}
                }
            
                String fly = new String("Catch " + flies + " flies"); //initializes String fly with the text "Catch ___ flies"
                
                g.drawString(fly, 175, 300); //Draws the String fly in given location
                g.drawString("Avoid the", 175, 375);
                g.drawImage(eimg, 465, 325, 85, 85, this);
                    
                newLevel = false;  //sets level 1 to false   
            }
            
            if (start == false && add == true) //checks if game has been paused and returned to
            {
                g.drawImage(img1, 25, 25, 51, 48, this); //draws hearts on to screen (for the lives the user has)
                g.drawImage(img1, 91, 25, 51, 48, this);
                g.drawImage(img1, 157, 25, 51, 48, this);
                
                add(b2); //adds pause button to screen

                g.drawImage(frogimg, x, 590, 144, 112, this); //draws frog for gameplay onto screen
                
                if (insect == true) //checks if insect is ready to be drawn onto screen; made true in drawInsect() method
                    g.drawImage(flyimg, pos, fy, 50, 50, this); //draws flyimg at given position
                    
                g.drawImage(bushimg, bush1, by1, 100, 100, this); //draws bush images
                g.drawImage(bushimg, bush2, by2, 100, 100, this);
                g.drawImage(bushimg, bush3, by3, 100, 100, this);
                
                g.drawImage(eimg, enemy1, ey1, 75, 75, this); //draws enemy images
                g.drawImage(eimg, enemy2, ey2, 75, 75, this);
                g.drawImage(cimg, chestx, cy, 75, 75, this); //draws chest image
                
                g.setFont(new Font("Serif", Font.BOLD, 20)); //sets font
                
                try //initializing Scanner in in try-catch block
            	{
                	in = new Scanner(new File("Score.txt"));
            	}
            
            	catch (IOException io)
            	{
                	System.out.println("Cannot find text file."); //deals with any errors by printing error message
                	System.exit(1);
            	}

            	score = 0;
            	
                while (in.hasNextLine()) //checks that text file has more lines
                {
                    System.out.println("in while loop");
                    
                    String lineRead = in.nextLine();
                    if (lineRead.indexOf("10") == 0)
                    {
                        score += 10; //incrementing score variable by 10
                        System.out.println("CHANGING SCORE +10");
                       
                    }
                    else if (lineRead.indexOf("-10") == 0)
                    {
                        score -= 10; //decrementing score variable by 10
                         System.out.println("CHANGING SCORE -10");
                       
                    }
                }
                g.drawString("Score = " + score, 35, 100); //draws String with score to screen
                
                if (tongue == true) //checks if tongue is true
                {
                    g.setColor(new Color(255, 179, 179)); //sets color to a shade of pink
                    g.fillRect(x+60, fy+5, 15, 587-fy); //draws the tongue
                    
                    if (590 - fy >= 0)
                        g.fillArc(x+60, fy, 15, 15, 0, 180);
                }
            }
            
            if (start == true) //checks if game has just been started for the first time
            {
                super.paintComponent(g); //clears screen completely
                removeAll();
                revalidate();
                
                start = false; //sets start back to false
                
                g.drawImage(img1, 25, 25, 51, 48, this); //draws hearts on to screen (for the lives the user has)
                g.drawImage(img1, 91, 25, 51, 48, this);
                g.drawImage(img1, 157, 25, 51, 48, this);
                
                g.drawImage(frogimg, x, 590, 144, 112, this); //draws frog for gameplay onto screen
                
                add(b2); //adds pause button
                
                add = true; //made true so computer knows how to deal with the game if the user pauses it and returns
                lives = 3; //stats are set to default values
                score = 0;
                stage = 1;
                
                if (insect == true) //checks if insect is ready to be drawn onto screen; made true in drawInsect() method
                    g.drawImage(flyimg, pos, fy, 50, 50, this); //draws flyimg at given position
                
                g.drawImage(bushimg, bush1, by1, 100, 100, this); //draws bush images
                g.drawImage(bushimg, bush2, by2, 100, 100, this);
                g.drawImage(bushimg, bush3, by3, 100, 100, this);
                
                g.drawImage(eimg, enemy1, ey1, 75, 75, this); //draws enemy images
                g.drawImage(eimg, enemy2, ey2, 75, 75, this);
                g.drawImage(cimg, chestx, cy, 75, 75, this); //draws chest image
                
                g.setFont(new Font("Serif", Font.BOLD, 20)); //sets font
                
                while (in.hasNextLine()) //checks that text file has more lines
                {
                  
                    String lineRead = in.nextLine();
                    if (lineRead.indexOf("10") == 0)
                    {
                        score += 10; //increments score variable by 10
                        System.out.println("gain");
                    }
                    else if (lineRead.indexOf("-10") == 0)
                    {
                        score -= 10; //decrements score variable by 10
                        System.out.println("lost");
                    }
                   
                }
                
            //    in.close(); //closes Scanner
                g.drawString("Score = " + score, 35, 100); //draws score to screen
                
                if (tongue == true) //checks if tongue is true
                {
                    g.setColor(new Color(255, 179, 179)); //sets color to a shade of pink
                    g.fillRect(x+60, fy+5, 15, 587-fy); //draws the tongue
                    
                    if (590 - fy >= 0)
                        g.fillArc(x+60, fy, 15, 15, 0, 180);
                }
            }   
            
            if (lives == 2) //checks if the user has two lives remaining
            {
            	System.out.println("lives == 2");
            	if (begin == 1)
            		g.setColor(new Color(0, 204, 0));
            	if (begin == 2)
            		g.fillRect(157, 25, 51, 48);
                g.drawImage(img2, 157, 25, 51, 48, this); //draws an empty heart to indicate one life is lost
            }
            
            if (lives == 1) //checks if the user has one life remaining
            {
            	System.out.println("lives == 1");
            	if (begin == 1)
            		g.setColor(new Color(0, 204, 0));
            	if (begin == 2)
            		g.setColor(new Color(195, 125, 0));
            	g.fillRect(157, 25, 51, 48);
            	g.fillRect(91, 25, 51, 48);
                g.drawImage(img2, 91, 25, 51, 48, this); //draws two empty hearts to indicate two lives is lost
                g.drawImage(img2, 157, 25, 51, 48, this);
            }
        }
    } //end of GamePanel class
    
    class PausePanel extends JPanel implements ActionListener
    {
        JButton play, quit, instructions; //declares JButtons to allow user to navigate the game
        
        PausePanel()
        {
            setBackground(Color.WHITE); //sets background color to white
            setLayout(new FlowLayout(FlowLayout.CENTER, 350, 40)); //sets layout to flow layout
            
            play = new JButton("Play"); //initializes JButton play with name "Play"
            play.setPreferredSize(new Dimension(100, 35)); //sets play's size
            play.addActionListener(this); //adds an ActionListener to the button
            add(play); //adds play to the panel
            
            quit = new JButton("Quit"); //initializes JButton quit with name "Quit"
            quit.setPreferredSize(new Dimension(100, 35)); //sets quit's size
            quit.addActionListener(this); //adds an ActionListener to the button
            add(quit); //adds quit to the panel
            
            instructions = new JButton("Instructions"); //initializes JButton instructions with name "Instructions"
            instructions.setPreferredSize(new Dimension(250, 40)); //sets instructions' size
            instructions.addActionListener(this); //adds an ActionListener to the button
            add(instructions); //adds instructions to the panel
        }
        
        public void actionPerformed(ActionEvent evt)
        {
            String cmd = evt.getActionCommand(); //creates String cmd and sets it equal to the title of the button pressed
            
            if (cmd.equals("Play")) //checks if play button was pressed
            {
                cl.show(FrogKing.fkph, "Game"); //changes card displayed to GamePanel
                card4.addKeyListener(card4);
                requestFocusInWindow();
                
                t1.start(); //starts all the timers
                t2.start();
                t3.start();
                t4.start();
                t5.start();
                t6.start();
                t7.start();
                
                t1.setInitialDelay(0); //sets the timers to no initial delay
                t2.setInitialDelay(0);
                t3.setInitialDelay(0);
                t4.setInitialDelay(0);
                t5.setInitialDelay(0);
                t6.setInitialDelay(0);
                t7.setInitialDelay(0);
            }
            
            if (cmd.equals("Quit")) //checks if quit button was pressed
            {
            	card4 = new GamePanel();
                cl.show(FrogKing.fkph, "Start"); //changes card displayed to StartPanel
                
         /*       t1.restart(); //restarts all the timers
                t2.restart();
                t3.restart();
                t4.restart();
                t5.restart();
                t6.restart();
                t7.restart();
                
                x = 298; //resets the positions of the all the images
                fy = -50;
                by1 = -100;
                by2 = -100;
                by3 = -100;
                ey1 = -75;
                ey2 = -75;
                cy = -75;
                insect = false; //sets the booleans back to false
                bush_1 = false;
                bush_2 = false;
                bush_3 = false;
                enemy_1 = false;
                enemy_2 = false; */
            }
            
            if (cmd.equals("Instructions")) //checks if instructions button was pressed
            {
                cl.show(FrogKing.fkph, "Instructions"); //changes card displayed to InstructionsPanel
                
                t1.restart(); //restarts all the timers
                t2.restart();
                t3.restart();
                t4.restart();
                t5.restart();
                t6.restart();
                t7.restart();
                
                x = 298; //resets the positions of the all the images
                fy = -50;
                by1 = -100;
                by2 = -100;
                by3 = -100;
                ey1 = -75;
                ey2 = -75;
                cy = -75;
                insect = false; //sets the booleans back to false
                bush_1 = false;
                bush_2 = false;
                bush_3 = false;
                enemy_1 = false;
                enemy_2 = false; 
            }
        }
        
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g); //calls paintComponent()'s parent constructor
            
            Font f = new Font("Serif", Font.BOLD, 55); //creates Font f with given parameters
            g.setFont(f); //sets font to f
            g.setColor(Color.BLACK); //sets drawing color to black
            
            g.drawString(("Lives: " + lives), 245, 480); //draws player stats onto screen
            g.drawString(("Score: " + score), 245, 385);
            g.drawString(("Stage: " + stage), 245, 575);
        }
    }
    
     class EndPanel extends JPanel
    {
    	CardLayout cl;
    	GameWon c1;
    	GameLost c2;
    	Scores c3;
    	
        EndPanel(JFrame fIn)
        {
            setBackground(Color.WHITE); //sets background color to pink; placeholder for now
            cl = new CardLayout();
            setLayout(cl);
            
            c1 = new GameWon();
            c2 = new GameLost();
            c3 = new Scores(fIn);
            
            add (c1, "Won");
            add (c2, "Lost");
            add(c3, "Scores");
            
            if (lives == 0)
            	cl.show(this, "Lost");
            else
            	cl.show(this, "Won");
        }
        
        class GameWon extends JPanel implements ActionListener
        {
        	JButton b;
        	
        	GameWon() 
        	{
        		setLayout(null);
        		setBackground(Color.WHITE);
        		
        		b = new JButton("See scores");
        		b.setBounds(320, 375, 100, 20);
        		b.addActionListener(this);
        		add(b);
        	} 
        	
        	public void actionPerformed(ActionEvent evt)
        	{
        		String cmd = evt.getActionCommand();
        		
        		if (cmd.equals("See scores"))
        		{
        			cl.show(this, "Scores");
        		}
        	}
        	
        	public void paintComponent(Graphics g)
        	{
        		g.setColor(Color.BLACK);
        		g.setFont(new Font("Serif", Font.BOLD, 50));
        		g.drawString("Game won!", 125, 200);
        		g.drawString("Score = " + score, 100, 300);
        	}
        } 
        
        class GameLost extends JPanel implements ActionListener
        {
        	JButton b;
        	
        	GameLost() 
        	{
        		setLayout(null);
        		setBackground(Color.WHITE);
        		
        		b = new JButton("See scores");
        		b.setBounds(320, 375, 100, 20);
        		b.addActionListener(this);
        		add(b);
        	} 
        	
        	public void actionPerformed(ActionEvent evt)
        	{
        		String cmd = evt.getActionCommand();
        		
        		if (cmd.equals("See scores"))
        		{
        			cl.show(this.getParent(), "Scores");
        		}
        	}
        	
        	public void paintComponent(Graphics g)
        	{
        		g.setColor(Color.BLACK);
        		g.setFont(new Font("Serif", Font.BOLD, 50));
        		g.drawString("Game lost!", 200, 200);
        		g.drawString("Score = " + score, 250, 300);
        	}
        } 
        
        class Scores extends JPanel implements ActionListener
        {
        	File fIn;
        	String content;
        	Scanner in;
        	JTextArea ta; 
        	JButton b;
        	PrintWriter pw;
        	JFrame f;
        	
        	Scores(JFrame frameIn)
        	{
        		setLayout(null);
        		
        		ta = new JTextArea(""); //initializes JTextArea
            	ta.setBounds(25, 25, 690, 500);
            	ta.setLineWrap(true); //sets format
            	ta.setWrapStyleWord(true);
            	ta.setEditable(false);
            	
            	b = new JButton("End Game");
            	b.setBounds(320, 550, 100, 20);
            	b.addActionListener(this);
            	add(b);
            	
            	f = frameIn;
            	fIn = null;
            	content = "";
            	
            	in = null;
            	
            	tryCatch();
            	read();
        	}
        	
        	public void tryCatch()
        	{
        		fIn = new File("AllScores.txt");
        		
        		try
        		{
        			in = new Scanner(fIn);
        			FileWriter fw = new FileWriter(fIn,true); //creating PrintWriter pw
              		BufferedWriter bw = new BufferedWriter(fw);
              		pw = new PrintWriter(bw);
        		}
        		
        		catch (IOException io)
        		{
        			System.err.println("Cannot find file.");
        			System.exit(1);
        		}
        	}
        	
        	public void read()
        	{
        	//	pw.println(username + ": " + score);
        		
        		content = "Scores:";
        		
        		while (in.hasNextLine())
        		{
        			content += ("\n" + in.nextLine());
        			System.out.println(content);
        		}
        		
        		content += ("\n" + username + ": " + score);
        		
        		ta.setText(content);
        		add(ta);
        	}
        	
        	public void actionPerformed(ActionEvent evt)
        	{
        		String cmd = evt.getActionCommand();
        		
        		if (cmd.equals("End Game"))
        		{
        			f.dispose();
        		} 
        	}
        	
        }
    }
}

class QuestionFrame extends JFrame
{
    QuestionPanel qp = null; //creates an instance of QuestionPanel; sets it to null
    
    QuestionFrame(JFrame f, int fliesIn, int livesIn, boolean flyIn, Timer t1, Timer t2, Timer t3, Timer t4, Timer t5, Timer t6, Timer t7, int by1In, int by2In, int by3In, int ey1In, int ey2In, int cyIn)
    {
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //creates JFrame f based on the parameter passed in from Game Panel
        f.setSize(475, 550);
        f.setLocation(890, 95);
        f.setResizable(false);
        
        qp = new QuestionPanel(f, fliesIn, livesIn, flyIn, t1, t2, t3, t4, t5, t6, t7, by1In, by2In, by3In, ey1In, ey2In, cyIn); //initializes Question Panel qp
        
        f.getContentPane().add(qp);
        f.setVisible(true);
    }
    
    public QuestionPanel getQP() //getQP() method, returns qp
    {
        return qp;
    }
    
    class QuestionPanel extends JPanel implements ActionListener
    {
        String name, temp, qIn, fQuestion, oIn, outName; //String names for text files, questions to be asked, and answer choices to be displayed
        Scanner in; //Scanner to read from text file
        JTextArea ta; //JTextArea to hold the question to present to user
        JFrame frameIn; //JFrame passed in from Question Frame
        int loop, q, flies, score, lives, by1, by2, by3, ey1, ey2, cy; //integers to store parameters
        boolean [] questions, answers; //boolean array to check whether questions have been repeated and determine the order of the answer choices
        boolean repaint, correct, clear, fly, livesNotReady; //booleans to repaint the screen, check whether answer is correct, etc.
        JRadioButton o1, o2, o3, o4; //JRadioButtons for answer choices
        JButton b1, b2; //JButtons to navigate around Question Panel
        ButtonGroup bg; //ButtonGroup for JRadioButtons 
        Timer t1, t2, t3, t4, t5, t6, t7; //Timers to store parameters
        File level; //File to read from
        PrintWriter pw; //PrintWriter to write to file
        
        QuestionPanel(JFrame fIn, int fliesIn, int livesIn, boolean flyIn, Timer t1In, Timer t2In, Timer t3In, Timer t4In, Timer t5In, Timer t6In, Timer t7In, int by1In, int by2In, int by3In, int ey1In, int ey2In, int cyIn)
        {
            setLayout(null); //setting layout to null and background to brown
            setBackground(new Color(195, 125, 0));
            
            name = "Level1.txt"; //initializing Strings for PrintWriter and Scanner
            outName = "Score.txt";
            in = null; //initializing PrintWriter & Scanner to null
            level = null;
            
            ta = new JTextArea(""); //initializes JTextArea
            Font f1 = new Font("Serif", Font.BOLD, 20); //changes location, size, and font of JTextArea
            ta.setFont(f1);
            ta.setBounds(25, 25, 425, 125);
            ta.setLineWrap(true); //sets format
            ta.setWrapStyleWord(true);
            ta.setEditable(false);
            
            questions = new boolean[22]; //creates new boolean array to hold questions
            loop = 0;
            q = 0;
            qIn = "";
            oIn = "";
            fQuestion = "";
            temp = "";
            flies = fliesIn;
            repaint = false;
            correct = false;
            clear = false;
            
            frameIn = fIn; //saves all the values passed in
            t1 = t1In;
            t2 = t2In;
            t3 = t3In;
            t4 = t4In;
            t5 = t5In;
            t6 = t6In;              
            t7 = t7In;
            score = 0;   
            by1 = by1In;
            by2 = by2In;
            by3 = by3In;
            ey1 = ey1In;
            ey2 = ey2In;
            cy = cyIn;  
            fly = flyIn;
            lives = livesIn;
                        
            Font f2 = new Font("Serif", Font.PLAIN, 25);
            
            o1 = new JRadioButton("1"); //initializes JRadioButton o1
            o1.setBounds(25, 175, 425, 75); //sets location, size, and color of JRadioButton
            o1.setFont(f2);
            o1.setForeground(Color.WHITE);
            o1.setBackground(new Color(195, 125, 0));
            
            o2 = new JRadioButton("2"); //initializes JRadioButton o2
            o2.setBounds(25, 250, 425, 75);  //sets location, size, and color of JRadioButton
            o2.setFont(f2);
            o2.setForeground(Color.WHITE);
            o2.setBackground(new Color(195, 125, 0));
            
            o3 = new JRadioButton("3"); //initializes JRadioButton o3
            o3.setBounds(25, 325, 425, 75);  //sets location, size, and color of JRadioButton
            o3.setFont(f2);
            o3.setForeground(Color.WHITE);
            o3.setBackground(new Color(195, 125, 0));
            
            o4 = new JRadioButton("4"); //initializes JRadioButton o4
            o4.setBounds(25, 400, 425, 75);  //sets location, size, and color of JRadioButton
            o4.setFont(f2);
            o4.setForeground(Color.WHITE);
            o4.setBackground(new Color(195, 125, 0));
            
            b1 = new JButton("Check Answer");  //initializes JButton b1
            b1.setBounds(163, 475, 150, 25);  //sets location and size
            b1.addActionListener(this);
            add(b1);
            
            b2 = new JButton("Close"); //initializes JButton b2
            b2.setBounds(188, 300, 100, 35); //sets location and size
            b2.addActionListener(this);
            
            
            bg = new ButtonGroup(); //initializes ButtonGroup bg
            
            o1.addActionListener(this); //adds ActionListeners for all the JRadioButtons
            o2.addActionListener(this);
            o3.addActionListener(this);
            o4.addActionListener(this);
                        
            bg.add(o1); //adds the JRadioButtons to the Button Group
            bg.add(o2);
            bg.add(o3);
            bg.add(o4);
                       
            tryAndCatch(); //calls methods
            readFile();
            addButtons();
        }
        
        public void tryAndCatch() //tryCatch method(); where Scanner in is initialized
        {
            level = new File(name);
            
            try
            {
                in = new Scanner(level);
            }
            
            catch (FileNotFoundException e)
            {
                System.out.println("Cannot find file(s).");
                System.exit(1);
            }
        }
        
        public void initialize(int livesIn, boolean flyIn, int by1In, int by2In, int by3In, int ey1In, int ey2In, int cyIn)
        {
            by1 = by1In; //all variables passed in from GamePanel are re-initialized for later use
            by2 = by2In;
            by3 = by3In;
            ey1 = ey1In;
            ey2 = ey2In;
            cy = cyIn;
            fly = flyIn;
            lives = livesIn;
            questions = null;
            questions = new boolean[22];
        }
            
        public void readFile()
        {
            if (loop == flies+2) //checks if loop is equal to number of flies; if so, qf is disposed
            {
                frameIn.dispose();
            }
            
            if (flies >= 5 && flies < 10) //based on the number of flies to catch and the loop number (number of times Question Frame has been called), a random number for q is generated
            {
            	if (loop < 5)
                {
                    q = (int)(Math.random()*9+1);
                }
                else
                {
                    q = (int)(Math.random()*13+10);
                }
            }
            
        	else if (flies >= 10 && flies < 15)
        	{
        		if (loop < 7)
                {
                    q = (int)(Math.random()*9+1);
                }
                else
                {
                    q = (int)(Math.random()*13+10);
                }
        	}
        	
        	else if (flies >= 15 && flies < 20)
        	{
        		if (loop < 9)
                {
                    q = (int)(Math.random()*9+1);
                }
                else
                {
                    q = (int)(Math.random()*13+10);
                }
        	}
        	
            else
                System.out.println("Error occurred."+q);
            
            while (questions[q-1] == true) //makes sure q hasn't been selected yet; uses boolean array
            {
                if (flies >= 5 && flies < 10)
            {
            	if (loop < 5)
                {
                    q = (int)(Math.random()*9+1);
                }
                else
                {
                    q = (int)(Math.random()*13+10);
                }
            }
            
        	else if (flies >= 10 && flies < 15)
        	{
        		if (loop < 7)
                {
                    q = (int)(Math.random()*9+1);
                }
                else
                {
                    q = (int)(Math.random()*13+10);
                }
        	}
        	
        	else if (flies >= 15 && flies < 20)
        	{
        		if (loop < 9)
                {
                    q = (int)(Math.random()*9+1);
                }
                else
                {
                    q = (int)(Math.random()*13+10);
                }
        	}
        	
            else
                System.out.println("Error occurred."+q);
            } 
            
            questions[q-1] = true;
                 
            while (in.hasNext()) //checks that the text file being read has more lines
            {
                qIn = in.nextLine();
                int index = qIn.indexOf("-" + q);
                int startIndex = ("-" + q).length();
                
                if (index == 0) //checks if the index of "-" + q in the line read from the text file is 0; if so, the program proceeds
                {
                    fQuestion = qIn.substring(startIndex+2, qIn.length());
                    break;
                }
                
            } 
            
            if (fly == true)
            	loop++; //increments loop
                    
            ta.setText(fQuestion); //sets JTextArea with question read from text file
            ta.setForeground(Color.WHITE);
            ta.setBackground(new Color(195, 125, 0));
            add(ta);
        }
        
        public void addButtons()
        {
            int option1 = 0; //sets all ints that will determine the order of the JRadioButtons to 0
            int option2 = 0;
            int option3 = 0;
            int option4 = 0;
            
            oIn = in.nextLine(); //reads in next line of text file; answer choice 1
            o1.setText(oIn.substring(2, oIn.length()));
                
            oIn = in.nextLine(); //reads in next line of text file; answer choice 2
            o2.setText(oIn.substring(2, oIn.length()));
                
            oIn = in.nextLine(); //reads in next line of text file; answer choice 3
            o3.setText(oIn.substring(2, oIn.length()));
                
            oIn = in.nextLine(); //reads in next line of text file; answer choice 4
            o4.setText(oIn.substring(2, oIn.length()));
                
            option1 = (int)(Math.random()*4+1); //generates random numbers for option1 and option2
            option2 = (int)(Math.random()*4+1);
                
             while (option1 == option2) //makes sure there are no overlaps
             {
                 option2 = (int)(Math.random()*4+1);
             }
                
            option3 = (int)(Math.random()*4+1); //repeats process until option1, option2, option3, and option4 all have their own, unique values from 1-4
                
            while (option1 == option3 || option2 == option3)
            {
                option3 = (int)(Math.random()*4+1);
            }
                
            option4 = (int)(Math.random()*4+1);
                
         	while (option1 == option4 || option2 == option4 || option3 == option4)
            {
            	option4 = (int)(Math.random()*4+1);
            }
                
            if (option1 == 1) //checks whether option1, option2, option3, or option4 is equal to 1; sets the JRadioButton in according position on the screen
                o1.setBounds(25, 175, 425, 75);
            else if (option2 == 1)
                o2.setBounds(25, 175, 425, 75);
            else if (option3 == 1)
                o3.setBounds(25, 175, 425, 75);
            else if (option4 == 1)
                o4.setBounds(25, 175, 425, 75);
                    
            if (option1 == 2) //checks whether option1, option2, option3, or option4 is equal to 2; sets the JRadioButton in according position on the screen
                 o1.setBounds(25, 250, 425, 75);
            else if (option2 == 2)
                 o2.setBounds(25, 250, 425, 75);
            else if (option3 == 2)
                 o3.setBounds(25, 250, 425, 75);
            else if (option4 == 2)
                o4.setBounds(25, 250, 425, 75);
                    
            if (option1 == 3) //checks whether option1, option2, option3, or option4 is equal to 3; sets the JRadioButton in according position on the screen
                o1.setBounds(25, 325, 425, 75);
            else if (option2 == 3)
                    o2.setBounds(25, 325, 425, 75);
            else if (option3 == 3)
                o3.setBounds(25, 325, 425, 75);
            else if (option4 == 3)
                o4.setBounds(25, 325, 425, 75);
                    
            if (option1 == 4) //checks whether option1, option2, option3, or option4 is equal to 4; sets the JRadioButton in according position on the screen
                o1.setBounds(25, 400, 425, 75);
            else if (option2 == 4)
                o2.setBounds(25, 400, 425, 75);
            else if (option3 == 4)
                o3.setBounds(25, 400, 425, 75);
            else if (option4 == 4)
                o4.setBounds(25, 400, 425, 75);
                    
            add(o1); //adds the JRadioButtons to the screen
            add(o2);
            add(o3);
            add(o4);
        }
        
        public int getLives() //returns the number of lives the user has left
        {
        	return lives;
        }
        
        public int restart(int beginIn) //returns the integer begin; checks if a new level is ready to be started
        {
            if (loop == flies+2 && beginIn == 1)
                return 2;
            else if (loop == flies+2 && beginIn == 2)
                return 3;
            else 
                return 1;
        }
        
        public void actionPerformed(ActionEvent evt)
        {
            String cmd = evt.getActionCommand();
            
            if (cmd.equals("Check Answer")) //checks if "Check Answer" button was clicked
            { 
            	if (o1.isSelected() && fly == true) //if the right answer was selected and the fly was caught
            	{
                	score = 10;
            	}
            	else if (o1.isSelected() && fly == false) //if the right answer was selected and the enemy was caught
            	{
                	score = 0;
            	}
            	else if (!o1.isSelected() && fly == true) //if the wrong answer was selected and the fly was caught
            	{
                	score = 0;
            	}
            	else //if the wrong answer was selected and the fly was caught
            	{
                	score = -10;
                	lives--;
           	 	}
           	 	
                repaint = true; //calls paintComponent()
                repaint();
            }
            
            if (cmd.equals("Close") && loop != (flies+2)) //checks if the "Close" button has been pressed
            {    
                frameIn.setVisible(false); //frame is made invisible
                
                t1.setInitialDelay(0); //initial delays of all timers is set to 0 if they are on the screen
                
                if (cy != -75)
                    t7.setInitialDelay(0);
                if (by1 != -100)
                    t2.setInitialDelay(0);
                if (by2 != -100)
                    t3.setInitialDelay(0);
                if (by3 != -100)
                    t4.setInitialDelay(0);
                if (ey1 != -75)
                    t5.setInitialDelay(0);
                if (ey1 != -75)
                    t6.setInitialDelay(0);
                
                t1.start(); //timers are started
                t2.start();
                t3.start();
                t4.start();
                t5.start();
                t6.start();
                t7.start();
                
                revalidate(); //screen is completely cleared
                removeAll();
                add(b1); //"Check Answer" button is added
                tryAndCatch();
            }
            
           if (cmd.equals("Close") && loop == (flies+2)) //checks if "Close" button is pressed and loop is equal to flies
            {    
                frameIn.dispose(); //closes Question Frame qf
            } 
        }
        
        public void writeIt()
        {
            
            File of = new File("Score.txt"); //deletes Score.tt if it exists at the end of the run-through
            try
            {
        		if (loop == 1)
        		{
        			if (of.exists()) 
                	{
                    	of.delete();                     
                 	}
                 	of.createNewFile();
                 }
              	
              	FileWriter fw = new FileWriter(of,true); //creating PrintWriter pw
              	BufferedWriter bw = new BufferedWriter(fw);
              	pw = new PrintWriter(bw);
             
            }
            catch(IOException io)
            {
                System.out.println("Exception occurred:");
                io.printStackTrace();
            }
            
            if (score == 10)
            { 
               
                pw.println("10 points"); //if score is equal to 10, then "10 points" is printed to the text file
                pw.close();
            }
            if (score == -10)
            { 
                
                pw.println("-10 points"); //if score is equal to -10, then "-10 points" is printed to the text file
                pw.close();
            }
                
           
        }
    
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            
            if (repaint == true) //checks if repaint was set true in the actionPerformed() method
            {
                removeAll(); //clears screen
                revalidate();
                
                if (score == 10 || loop == 1 || score == -10) //checks if score is equal to 10 or -10, and loop is equal to 1; if so, writeIt() is called
                {
                    writeIt();
                }
                
                g.setFont(new Font("Serif", Font.BOLD, 50)); //sets drawing Font and color
                g.setColor(Color.WHITE);
                if (score != -10)
                	g.drawString(score + " points gained!", 40, 100); //draws number of points gained or lost
                else
                	g.drawString(10 + " points lost!", 40, 100);
                	
                if (lives != -1 || lives != 1)
               	 	g.drawString(lives + " lives left!", 100, 200); //draws the number of lives left
               	else if (lives == 1)
               		g.drawString(lives + " life left!", 100, 200); //draws the number of lives left
               	else
               		g.drawString("Game over!", 125, 200); //draws "Game Over"
                
                add(b2); //adds "Close" button to the screen
                
                repaint = false;
            }
        }
    }
}

class ChestFrame extends JFrame
{
    public static CFPHolder cfph;
    public JFrame frame;
    
    ChestFrame(int chestNum, Timer t1, Timer t2, Timer t3, Timer t4, Timer t5, Timer t6, Timer t7)
    {
        frame = new JFrame("Chest");
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(475, 740);
        frame.setLocation(890, 0);
        frame.setResizable(false);
        
        cfph = new CFPHolder(chestNum, t1, t2, t3, t4, t5, t6, t7);
        
        frame.getContentPane().add(cfph);
        frame.setVisible(true);
    }
    
    class CFPHolder extends JPanel
    {
        public CardLayout cardl;
        Concept1 c1;
        Concept2 c2;
        Concept3 c3;
        Concept4 c4;
        public Timer t1, t2, t3, t4, t5, t6, t7;
        int chest;
        
        CFPHolder(int chestNum, Timer t1In, Timer t2In, Timer t3In, Timer t4In, Timer t5In, Timer t6In, Timer t7In)
        {
            cardl = new CardLayout();
            setLayout(cardl);
            
            chest = chestNum;
            
            c1 = new Concept1();
            c2 = new Concept2();
            c3 = new Concept3();
            c4 = new Concept4();
            
            add(c1, "Concept1");
            add(c2, "Concept2");
            add(c3, "Concept3");
            add(c4, "Concept4");
            
            t1 = t1In;
            t2 = t2In;
            t3 = t3In;
            t4 = t4In;
            t5 = t5In;
            t6 = t6In;              
            t7 = t7In;
            
            if (chest == 0)
                cardl.show(this, "Concept1");
            else if (chest == 1)
                cardl.show(this, "Concept4");
            else if (chest == 2)
                cardl.show(this, "Concept2");
        }
        
        class Concept1 extends JPanel implements ActionListener, ChangeListener
        {
            JTextArea ta1, ta2;
            String text1, text2, imgname1, imgname2;
            JSlider s1, s2;
            JButton b;
            Font f;
            int status1, status2;
            Image img1, img2;
            
            Concept1()
            {
                setBackground(new Color(195, 125, 0));
                setLayout(null);
                
                text1 = new String("Commutative Law of Addition\n\nA + B = B + A.\n\nWhen calculating the sum of two vectors, put the head of vector 1 to the tail of vector 2 and draw a vector connecting the tail of vector 1 and the head of vector 2.");
                text2 = new String("Scalar Multiplication:\n\nMagnitude kA = |k| * (magnitude of A); k = scalar quantity and A = the original vector");
                text2 += "\n\nThe direction of kA is the same as A if k is positive, and is the opposite of A if k is negative.";
                imgname1 = new String("Diagram1.png");
                imgname2 = new String("Diagram5.png");
                f = new Font("Serif", Font.PLAIN, 23);
                
                ta1 = new JTextArea("");
                ta1.setBounds(25, 30, 425, 250);
                ta1.setEditable(false);
                ta1.setVisible(true);
                ta1.setLineWrap(true);
                ta1.setWrapStyleWord(true);
                ta1.setText(text1);
                ta1.setFont(f);
                add(ta1);
                
                
                ta2 = new JTextArea("");
                ta2.setBounds(25, 350, 425, 250);
                ta2.setEditable(false);
                ta2.setVisible(true);
                ta2.setLineWrap(true);
                ta2.setWrapStyleWord(true);
                ta2.setText(text2);
                ta2.setFont(f);
                add(ta2);
                
                b = new JButton("Next");
                b.setBounds(188, 650, 100, 25);
                b.addActionListener(this);
                add(b);
                
                s1 = new JSlider(0, 0, 1, 0);
                s1.setBounds(25, 280, 425, 25);
                s1.addChangeListener(this);
                add(s1);
                
                s2 = new JSlider(0, 0, 1, 0);
                s2.setBounds(25, 600, 425, 25);
                s2.addChangeListener(this);
                add(s2);
                
                status1 = 0;
                status2 = 0;
                
                getMyImage();
            }
            
            public void getMyImage()
            {
                File f1 = new File(imgname1);
                File f2 = new File(imgname2);
                
                try
                {
                    img1 = ImageIO.read(f1);
                    img2 = ImageIO.read(f2);
                }
                
                catch (IOException io)
                {
                    System.out.println("Error loading image.");
                    io.printStackTrace();
                }
            }
            
            public void actionPerformed(ActionEvent evt)
            {
                String cmd = evt.getActionCommand();
                
                if (cmd.equals("Next"))
                {
                    cardl.show(ChestFrame.cfph, "Concept2");
                }
            }
            
            public void stateChanged(ChangeEvent evt)
            {
                status1 = s1.getValue();
                status2 = s2.getValue();
                
                repaint();
            }
            
            public void paintComponent(Graphics g)
            {
                super.paintComponent(g);
                
                if (status1 == 0)
                {
                    ta1.setVisible(true);
                }
                else if (status1 == 1)
                {
                    g.drawImage(img1, 25, 30, 425, 250, this);
                    ta1.setVisible(false);
                }
                    
                if (status2 == 0)
                {
                    ta2.setVisible(true);
                }
                else if (status2 == 1)
                {
                    g.drawImage(img2, 25, 350, 425, 250, this);
                    ta2.setVisible(false);
                }
            }
        }
        
        class Concept2 extends JPanel implements ActionListener, ChangeListener
        {
            JTextArea ta1, ta2;
            String text1, text2, imgname1, imgname2;
            JButton b;
            Font f;
            JSlider s1, s2;
            int status1, status2;
            Image img1, img2;
            
            Concept2()
            {
                setBackground(new Color(195, 125, 0));
                setLayout(null);
                
                text1 = new String("To subtract one vector from another (ex: A - B), simply form the vector -B and add it to A.\n\nTo create vector -B, flip vector B 180 degrees.\n\nA - B = A + (-B)");
                text2 = new String("A two-dimensional vector is the sum of the horizontal and vertical vector. For example, the sum of horizontal vector B and vertical vector C is A.\n\nThere are vectors called unit vectors which have magnitudes of 1. They are usually denoted by i or j.");
                imgname1 = new String("Diagram2.png");
                imgname2 = new String("Diagram3.png");
                f = new Font("Serif", Font.PLAIN, 25);
                
                ta1 = new JTextArea("");
                ta1.setBounds(25, 30, 425, 250);
                ta1.setEditable(false);
                ta1.setVisible(true);
                ta1.setLineWrap(true);
                ta1.setWrapStyleWord(true);
                ta1.setText(text1);
                ta1.setFont(f);
                add(ta1);
                
                ta2 = new JTextArea("");
                ta2.setBounds(25, 350, 425, 250);
                ta2.setEditable(false);
                ta2.setVisible(true);
                ta2.setLineWrap(true);
                ta2.setWrapStyleWord(true);
                ta2.setText(text2);
                ta2.setFont(f);
                add(ta2);
                
                b = new JButton("Next");
                b.setBounds(188, 650, 100, 25);
                b.addActionListener(this);
                add(b);
                
                s1 = new JSlider(0, 0, 1, 0);
                s1.setBounds(25, 280, 425, 25);
                s1.addChangeListener(this);
                add(s1);
                
                s2 = new JSlider(0, 0, 1, 0);
                s2.setBounds(25, 600, 425, 25);
                s2.addChangeListener(this);
                add(s2);
                
                status1 = 0;
                status2 = 0;
                
                getMyImage();
            }
            
            public void getMyImage()
            {
                File f1 = new File(imgname1);
                File f2 = new File(imgname2);
                
                try
                {
                    img1 = ImageIO.read(f1);
                    img2 = ImageIO.read(f2);
                }
                
                catch (IOException io)
                {
                    System.out.println("Error loading image.");
                    io.printStackTrace();
                }
            }
            
            public void actionPerformed(ActionEvent evt)
            {
                String cmd = evt.getActionCommand();
                
                if (cmd.equals("Next"))
                {
                    cardl.show(ChestFrame.cfph, "Concept3");
                }
            }
            
            public void stateChanged(ChangeEvent evt)
            {
                status1 = s1.getValue();
                status2 = s2.getValue();
                
                repaint();
            }
            
            public void paintComponent(Graphics g)
            {
                super.paintComponent(g);
                
                if (status1 == 0)
                {
                    ta1.setVisible(true);
                }
                else if (status1 == 1)
                {
                    g.drawImage(img1, 25, 30, 425, 250, this);
                    ta1.setVisible(false);
                }
        
                if (status2 == 0)
                {    
                    ta2.setVisible(true);
                }
                else if (status2 == 1)
                {
                    g.drawImage(img2, 25, 350, 425, 250, this);
                    ta2.setVisible(false);
                }
            }
        }
        
        class Concept3 extends JPanel implements ActionListener, ChangeListener
        {
            JTextArea ta1, ta2;
            String text1, text2, imgname1, imgname2;
            JButton b;
            Font f;
            JSlider s1, s2;
            int status1, status2;
            Image img1, img2;
            
            Concept3()
            {
                setBackground(new Color(195, 125, 0));
                setLayout(null);
                
                text1 = new String("Vector addition: Add the respective components.\nA + B = (Ax + Bx)i + (Ay + By)j");
                text1 += "\n\nVector subtraction: Subtract the respective components.\nA - B = (Ax - Bx)i + (Ay - By)j";
                text1 += "\n\nScalar multiplication: Multiply each component by k.\nkA = k(Ax)i + k(Ay)j";
                text2 = new String("The direction of a vector can be specified by the angle it makes with the positive x-axis.\n\nA*cos(theta) = Ax, A*sin(theta) = Ay");
                imgname1 = new String("Diagram4.png");
                imgname2 = new String("Diagram6.png");
                f = new Font("Serif", Font.PLAIN, 25);
                
                ta1 = new JTextArea("");
                ta1.setBounds(25, 30, 425, 250);
                ta1.setEditable(false);
                ta1.setVisible(true);
                ta1.setLineWrap(true);
                ta1.setWrapStyleWord(true);
                ta1.setText(text1);
                ta1.setFont(new Font("Serif", Font.PLAIN, 21));
                add(ta1);
                
                ta2 = new JTextArea("");
                ta2.setBounds(25, 350, 425, 250);
                ta2.setEditable(false);
                ta2.setVisible(true);
                ta2.setLineWrap(true);
                ta2.setWrapStyleWord(true);
                ta2.setText(text2);
                ta2.setFont(f);
                add(ta2);
                
                b = new JButton("Done");
                b.setBounds(188, 650, 100, 25);
                b.addActionListener(this);
                add(b);
                
                s1 = new JSlider(0, 0, 1, 0);
                s1.setBounds(25, 280, 425, 25);
                s1.addChangeListener(this);
                add(s1);
                
                s2 = new JSlider(0, 0, 1, 0);
                s2.setBounds(25, 600, 425, 25);
                s2.addChangeListener(this);
                add(s2);
                
                status1 = 0;
                status2 = 0;
                
                getMyImage();
            }
            
            public void getMyImage()
            {
                File f1 = new File(imgname1);
                File f2 = new File(imgname2);
                
                try
                {
                    img1 = ImageIO.read(f1);
                    img2 = ImageIO.read(f2);
                }
                
                catch (IOException io)
                {
                    System.out.println("Error loading image.");
                    io.printStackTrace();
                }
            }
            
            public void actionPerformed(ActionEvent evt)
            {
                String cmd = evt.getActionCommand();
                
                if (cmd.equals("Done"))
                {
                    frame.setVisible(false);
                    
                    t1.setInitialDelay(0);
                    t2.setInitialDelay(0);
                    t3.setInitialDelay(0);
                    t4.setInitialDelay(0);
                    t5.setInitialDelay(0);
                    t6.setInitialDelay(0);
                    t7.setInitialDelay(0);
                
                    t1.start();
                    t2.start();
                    t3.start();
                    t4.start();
                    t5.start();
                    t6.start();
                    t7.start();
                }
            }
            
               public void stateChanged(ChangeEvent evt)
            {
                status1 = s1.getValue();
                status2 = s2.getValue();
                
                repaint();
            }
            
            public void paintComponent(Graphics g)
            {
                super.paintComponent(g);
                
                if (status1 == 0)
                {
                    ta1.setVisible(true);
                }
                else if (status1 == 1)
                {
                    g.drawImage(img2, 25, 30, 425, 250, this);
                    ta1.setVisible(false);
                }
        
                if (status2 == 0)
                {    
                    ta2.setVisible(true);
                }
                else if (status2 == 1)
                {
                    g.drawImage(img1, 25, 350, 425, 250, this);
                    ta2.setVisible(false);
                }
            }
        }
         
        class Concept4 extends JPanel implements ActionListener, ChangeListener
        {
            JTextArea ta1, ta2;
            String text1, text2, imgname1, imgname2;
            JButton b;
            Font f;
            JSlider s1, s2;
            int status1, status2;
            Image img1, img2;
            
            Concept4()
            {
                setBackground(new Color(195, 125, 0));
                setLayout(null);
                
                text1 = new String("An object's position is its location in a certain space.\n\nSince it is difficult to an object's location, a coordinate system is used.");
                text1 += "\n\nThis way, positive and negative positions can also be described.";
                text2 = new String("Displacement is an object's change in position. It is the vector that points from the object's initial position to its final position.");
                text2 += "\n\nThe total distance is also the measure of an object's change in position, but the object's path of travel is taken into account.";
                imgname1 = new String("Diagram7.png");
                imgname2 = new String("Diagram8.png");
                f = new Font("Serif", Font.PLAIN, 25);
                
                ta1 = new JTextArea("");
                ta1.setBounds(25, 30, 425, 250);
                ta1.setEditable(false);
                ta1.setVisible(true);
                ta1.setLineWrap(true);
                ta1.setWrapStyleWord(true);
                ta1.setText(text1);
                ta1.setFont(f);
                add(ta1);
                
                ta2 = new JTextArea("");
                ta2.setBounds(25, 350, 425, 250);
                ta2.setEditable(false);
                ta2.setVisible(true);
                ta2.setLineWrap(true);
                ta2.setWrapStyleWord(true);
                ta2.setText(text2);
                ta2.setFont(f);
                add(ta2);
                
                b = new JButton("Next");
                b.setBounds(188, 650, 100, 25);
                b.addActionListener(this);
                add(b);
                
                s1 = new JSlider(0, 0, 1, 0);
                s1.setBounds(25, 280, 425, 25);
                s1.addChangeListener(this);
                add(s1);
                
                s2 = new JSlider(0, 0, 1, 0);
                s2.setBounds(25, 600, 425, 25);
                s2.addChangeListener(this);
                add(s2);
                
                status1 = 0;
                status2 = 0;
                
                getMyImage();
            }
            
            public void getMyImage()
            {
                File f1 = new File(imgname1);
                File f2 = new File(imgname2);
                
                try
                {
                    img1 = ImageIO.read(f1);
                    img2 = ImageIO.read(f2);
                }
                
                catch (IOException io)
                {
                    System.out.println("Error loading image.");
                    io.printStackTrace();
                }
            }
            
            public void actionPerformed(ActionEvent evt)
            {
                String cmd = evt.getActionCommand();
                
                if (cmd.equals("Next"))
                {
                    cardl.show(ChestFrame.cfph, "Concept3");
                }
            }
            
               public void stateChanged(ChangeEvent evt)
            {
                status1 = s1.getValue();
                status2 = s2.getValue();
                
                repaint();
            }
            
            public void paintComponent(Graphics g)
            {
                super.paintComponent(g);
                
                if (status1 == 0)
                {
                    ta1.setVisible(true);
                }
                else if (status1 == 1)
                {
                    g.drawImage(img1, 25, 30, 425, 250, this);
                    ta1.setVisible(false);
                }
        
                if (status2 == 0)
                {    
                    ta2.setVisible(true);
                }
                else if (status2 == 1)
                {
                    g.drawImage(img2, 25, 350, 425, 250, this);
                    ta2.setVisible(false);
                }
            }
        }
    }
}                        