package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class Gameplay extends JPanel implements KeyListener, ActionListener {

    final private int[] SNAKELENGTHX = new int[750];
    final private int[] SNAKELENGTHY = new int[750];

    private boolean left = false;
    private boolean right = false;
    private boolean up = false;
    private boolean down = false;

    final private int [] TARGETPOSX = {25, 50, 75, 100, 125, 150, 175, 200, 225, 250, 275, 300, 325,
            350, 375, 400, 425, 450, 475, 500, 525, 550, 575, 600, 625, 650, 675, 700, 725,
            750, 775, 800, 825, 850};
    final private int [] TARGETPOSY = {75, 100, 125, 150, 175, 200, 225, 250, 275, 300, 325, 350,
            375, 400, 425, 450, 475, 500, 525, 550, 575, 600, 625};

    // Declare gameplay variables
    final private Random RANDOM = new Random();
    final private Timer TIMER;

    private boolean gameOver = false;
    private int gameScore = 0;
    private int moves = 0;
    private int snakeLength = 3;

    private int posX = RANDOM.nextInt(34);
    private int posY = RANDOM.nextInt(23);

    public Gameplay()
    {
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        int DELAY = 100;
        TIMER = new Timer(DELAY, this);
        TIMER.start();
    }

    public void paint(Graphics g)
    {

        // Declare image imports
        ImageIcon titleImage;
        ImageIcon targetImage;
        ImageIcon snakeHeadLeft;
        ImageIcon snakeHeadRight;
        ImageIcon snakeHeadUp;
        ImageIcon snakeHeadDown;
        ImageIcon snakeBody;

        // Set snake initial position on board
        if (moves == 0) {
            SNAKELENGTHX[2] = 50;
            SNAKELENGTHX[1] = 75;
            SNAKELENGTHX[0] = 100;

            SNAKELENGTHY[2] = 100;
            SNAKELENGTHY[1] = 100;
            SNAKELENGTHY[0] = 100;
        }

        // Draw background for gameplay
        g.setColor(Color.black);
        g.fillRect(25, 75, 850, 575);

        // Draw border for gameplay
        g.setColor(Color.white);
        g.drawRect(24, 74, 851, 577);

        // Draw title image
        titleImage = new ImageIcon(getClass().getResource("/snakeTitle.png"));
        titleImage.paintIcon(this, g, 25, 11);

        // Draw title image border
        g.setColor(Color.white);
        g.drawRect(24, 10, 851, 55);

        // Draw gameplay info, score and length of snake during gameplay
        g.setColor(Color.white);
        g.setFont(new Font("Roboto", Font.PLAIN, 14));
        g.drawString("Start / Move: Arrow Keys", 40, 32);
        g.drawString("Restart: Spacebar", 40, 54);
        g.drawString("Score: "+ gameScore, 780, 32);
        g.drawString("Length: " + snakeLength, 780, 54);

        // Set snake head to right direction on game startup
        snakeHeadRight = new ImageIcon(getClass().getResource("/snakeHeadRight.png"));
        snakeHeadRight.paintIcon(this, g, SNAKELENGTHX[0], SNAKELENGTHY[0]);

        // Set snake head to correct direction during gameplay
        for(int a = 0; a < snakeLength; a++)
        {
            if(a == 0 && right) {
                snakeHeadRight = new ImageIcon(getClass().getResource("/snakeHeadRight.png"));
                snakeHeadRight.paintIcon(this, g, SNAKELENGTHX[a], SNAKELENGTHY[a]);
            }
            if(a == 0 && left) {
                snakeHeadLeft = new ImageIcon(getClass().getResource("/snakeHeadLeft.png"));
                snakeHeadLeft.paintIcon(this, g, SNAKELENGTHX[a], SNAKELENGTHY[a]);
            }
            if(a == 0 && up) {
                snakeHeadUp = new ImageIcon(getClass().getResource("/snakeHeadUp.png"));
                snakeHeadUp.paintIcon(this, g, SNAKELENGTHX[a], SNAKELENGTHY[a]);
            }
            if(a == 0 && down) {
                snakeHeadDown = new ImageIcon(getClass().getResource("/snakeHeadDown.png"));
                snakeHeadDown.paintIcon(this, g, SNAKELENGTHX[a], SNAKELENGTHY[a]);
            }

            if(a != 0)
            {
                snakeBody = new ImageIcon(getClass().getResource("/snakeBody.png"));
                snakeBody.paintIcon(this, g, SNAKELENGTHX[a], SNAKELENGTHY[a]);
            }
        }

        // Increment game score and snake length and generate new target when snake catches target
        if(TARGETPOSX[posX] == SNAKELENGTHX[0] && TARGETPOSY[posY] == SNAKELENGTHY[0])
        {
            gameScore++;
            snakeLength++;
            posX = RANDOM.nextInt(34);
            posY = RANDOM.nextInt(23);
        }

        // Draw target image on board
        targetImage = new ImageIcon(getClass().getResource("/snakeTarget.png"));
        targetImage.paintIcon(this, g, TARGETPOSX[posX], TARGETPOSY[posY]);

        // Game over if snake head touches its own body
        for (int i = 1; i < snakeLength; i++)
        {
            if (SNAKELENGTHX[0] == SNAKELENGTHX[i] && SNAKELENGTHY[0] == SNAKELENGTHY[i])
            {
                left = false;
                right = false;
                up = false;
                down = false;
                gameOver = true;

                g.setColor(Color.white);
                g.setFont(new Font("Roboto", Font.BOLD, 50));
                g.drawString("Game Over", 300, 300);

                g.setColor(Color.white);
                g.setFont(new Font("Roboto", Font.BOLD, 20));
                g.drawString("Space to RESTART", 350, 340);
            }
        }

        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        TIMER.start();
        if (right)
        {
            if (snakeLength - 1 + 1 >= 0) System.arraycopy(SNAKELENGTHY, 0, SNAKELENGTHY, 1, snakeLength - 1 + 1);
            for (int i = snakeLength; i >= 0; i--)
            {
                if (i == 0)
                {
                    SNAKELENGTHX[i] = SNAKELENGTHX[i] + 25;
                }
                else
                {
                    SNAKELENGTHX[i] = SNAKELENGTHX[i - 1];
                }
                if(SNAKELENGTHX[i] > 850)
                {
                    SNAKELENGTHX[i] = 25;
                }
            }

            repaint();
        }
        if (left)
        {
            if (snakeLength - 1 + 1 >= 0) System.arraycopy(SNAKELENGTHY, 0, SNAKELENGTHY, 1, snakeLength - 1 + 1);
            for (int i = snakeLength; i >= 0; i--)
            {
                if (i == 0)
                {
                    SNAKELENGTHX[i] = SNAKELENGTHX[i] - 25;
                }
                else
                {
                    SNAKELENGTHX[i] = SNAKELENGTHX[i - 1];
                }
                if(SNAKELENGTHX[i] < 25)
                {
                    SNAKELENGTHX[i] = 850;
                }
            }

            repaint();
        }
        if (up)
        {
            if (snakeLength - 1 + 1 >= 0) System.arraycopy(SNAKELENGTHX, 0, SNAKELENGTHX, 1, snakeLength - 1 + 1);
            for (int i = snakeLength; i >= 0; i--)
            {
                if (i == 0)
                {
                    SNAKELENGTHY[i] = SNAKELENGTHY[i] - 25;
                }
                else
                {
                    SNAKELENGTHY[i] = SNAKELENGTHY[i - 1];
                }
                if (SNAKELENGTHY[i] < 75)
                {
                    SNAKELENGTHY[i] = 625;
                }
            }

            repaint();
        }
        if (down)
        {
            if (snakeLength - 1 + 1 >= 0) System.arraycopy(SNAKELENGTHX, 0, SNAKELENGTHX, 1, snakeLength - 1 + 1);
            for (int i = snakeLength; i >= 0; i--)
            {
                if (i == 0)
                {
                    SNAKELENGTHY[i] = SNAKELENGTHY[i] + 25;
                }
                else
                {
                    SNAKELENGTHY[i] = SNAKELENGTHY[i - 1];
                }
                if (SNAKELENGTHY[i] > 625)
                {
                    SNAKELENGTHY[i] = 75;
                }
            }

            repaint();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            gameOver = false;
            gameScore = 0;
            moves = 0;
            snakeLength = 3;
            left = false;
            right = false;
            up = false;
            down = false;
            repaint();
        }
        if (!gameOver) {
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                moves++;
                if (!left) {
                    right = true;
                } else {
                    right = false;
                    left = true;
                }
                up = false;
                down = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                moves++;
                if (!right) {
                    left = true;
                } else {
                    left = false;
                    right = true;
                }
                up = false;
                down = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                moves++;
                if (!down) {
                    up = true;
                } else {
                    up = false;
                    down = true;
                }
                right = false;
                left = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                moves++;
                if (!up) {
                    down = true;
                } else {
                    down = false;
                    up = true;
                }
                right = false;
                left = false;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
