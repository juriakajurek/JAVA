import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

public class Gamepanel extends JPanel implements Runnable, KeyListener{


    private static final long serialVersionUID = 1L;

    public static final int WIDTH = 900, HEIGHT = 900;

    public int speed = 800000;

    private Thread thread;

    private boolean running;

    private boolean right = true, left = false, up = false, down = false;

    private Segment b;
    private ArrayList<Segment> snake;

    private Apple apple;
    private ArrayList<Apple> apples;

    private Pear pear;
    private ArrayList<Pear> pears;

    private Grape grape;
    private ArrayList<Grape> grapes;

    private Bomb bomb;
    private ArrayList<Bomb> bombs;

    private Random r;

    private int xCoor = 10, yCoor = 10, size = 3;
    private int ticks = 0;
    public int switcher = 0;


    public Gamepanel() {
        setFocusable(true);

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        addKeyListener(this);

        snake = new ArrayList<Segment>();
        apples = new ArrayList<Apple>();
        pears = new ArrayList<Pear>();
        grapes = new ArrayList<Grape>();
        bombs = new ArrayList<Bomb>();

        r = new Random();

        start();
    }


    public void start() {
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public void stop() {
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void tick() {
        if(snake.size() ==0) {
            b = new Segment(xCoor, yCoor, 10);
            snake.add(b);
        }
        ticks++;
        if(ticks > speed) {
            if(right) xCoor++;
            if(left) xCoor--;
            if(up) yCoor--;
            if(down) yCoor++;

            ticks = 0;

            b = new Segment(xCoor, yCoor, 10);
            snake.add(b);

            if(snake.size() > size) {
                snake.remove(0);
            }
        }


        //dodawanie jablek
        if(apples.size() <= 1) {
            int xCoor = r.nextInt(89);
            int yCoor = r.nextInt(89);

            apple = new Apple(xCoor, yCoor, 10);
            apples.add(apple);
        }

        for(int i = 0 ; i < apples.size() ; i++) {
            if(xCoor == apples.get(i).getxCoor() && yCoor == apples.get(i).getyCoor()) {
                size++;
                apples.remove(i);
                i++;
            }
        }

        //dodawanie gruszek
        if(pears.size() == 0 ) {

            int xCoor = r.nextInt(89);
            int yCoor = r.nextInt(89);

            pear = new Pear(xCoor, yCoor, 10);
            pears.add(pear);
        }


        for(int i = 0 ; i < pears.size() ; i++) {
            if(xCoor == pears.get(i).getxCoor() && yCoor == pears.get(i).getyCoor()) {
                size+=pear.getNutrition();
                pears.remove(i);
                i++;
            }
        }

        //dodawanie bomb
        if(bombs.size() < 19 ) {

            int xCoor = r.nextInt(89);
            int yCoor = r.nextInt(89);

            bomb = new Bomb(xCoor, yCoor, 10);
            bombs.add(bomb);
        }

        for(int i = 0 ; i < bombs.size() ; i++) {
            if(xCoor == bombs.get(i).getxCoor() && yCoor == bombs.get(i).getyCoor()) {
                System.out.println("Game Over");
                saveScore();
                Main.newGameButton.setVisible(true);
                Main.showScoreBoardButton.setVisible(true);
                Main.exitButton.setVisible(true);
                Main.frame.pack();
                Main.frame.setVisible(true);
                stop();
            }
        }

        //dodawanie winogron
        if(grapes.size() ==0 ) {

            int xCoor =0;
            int yCoor =0;

            switcher = r.nextInt(3);
            switch (switcher){
                case 0:
                    xCoor = 0;
                    yCoor = r.nextInt(89);
                    switcher++;
                    break;
                case 1:
                    xCoor = r.nextInt(89);
                    yCoor = 0;
                    switcher = 0;
                    break;

                case 2:
                    xCoor = 89;
                    yCoor = r.nextInt(89);
                    switcher++;
                    break;

                case 3:
                    xCoor = r.nextInt(89);
                    yCoor = 89;
                    switcher = 0;
                    break;

                    default:
                        xCoor = 45;
                        yCoor = 45;
            }

            grape = new Grape(xCoor, yCoor, 10);
            grapes.add(grape);
        }

        for(int i = 0 ; i < grapes.size() ; i++) {
            if(xCoor == grapes.get(i).getxCoor() && yCoor == grapes.get(i).getyCoor()) {
                size+=grape.getNutrition();
                grapes.remove(i);
                speed -= 150000;
                i++;
            }
        }

        ///GAME OVER
        //kolizja ze sciana
        if(xCoor < 0 || xCoor > 89 || yCoor < 0 || yCoor > 89) {
            System.out.println("Game Over");
            saveScore();
            Main.newGameButton.setVisible(true);
            Main.showScoreBoardButton.setVisible(true);
            Main.exitButton.setVisible(true);
            Main.frame.pack();
            Main.frame.setVisible(true);
            stop();
        }

        //kolizja z cialkiem
        for(int i = 0 ; i < snake.size(); i++) {
            if(xCoor == snake.get(i).getxCoor() && yCoor == snake.get(i).getyCoor()) {
                if(i !=snake.size()- 1) {
                    System.out.println("Game Over");
                    saveScore();
                    Main.newGameButton.setVisible(true);
                    Main.showScoreBoardButton.setVisible(true);
                    Main.exitButton.setVisible(true);
                    Main.frame.pack();
                    Main.frame.setVisible(true);
                    stop();
                }
            }
        }

    }

    //rysowanie
    public void paint(Graphics g) {
        g.clearRect(0, 0, WIDTH, HEIGHT);

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        for(int i = 0; i < WIDTH/10 ; i++) {
            g.drawLine(i * 10, 0, i *10, HEIGHT);
        }
        for(int i = 0; i < HEIGHT/10 ; i++) {
            g.drawLine(0, i * 10 , HEIGHT, i * 10);
        }
        for(int i = 0 ; i <snake.size(); i++) {
            snake.get(i).draw(g);
        }
        for(int i = 0 ; i < apples.size(); i++) {
            apples.get(i).draw(g);
        }
        for(int i = 0 ; i < pears.size(); i++) {
            pears.get(i).draw(g);
        }
        for(int i = 0 ; i < grapes.size(); i++) {
            grapes.get(i).draw(g);
        }
        for(int i = 0 ; i < bombs.size(); i++) {
            bombs.get(i).draw(g);
        }

    }

    @Override
    public void run() {
        while(running) {
            tick();
            repaint();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_RIGHT && !left) {
            right = true;
            up = false;
            down = false;
        }
        if(key == KeyEvent.VK_LEFT && !right) {
            left = true;
            up = false;
            down = false;
        }
        if(key == KeyEvent.VK_UP && !down) {
            up = true;
            left = false;
            right = false;
        }
        if(key == KeyEvent.VK_DOWN && !up) {
            down = true;
            left = false;
            right = false;
        }
        if(key == KeyEvent.VK_CONTROL) {
            speed = speed/2;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_CONTROL) {
            speed = speed*2;
        }
    }

    @Override
    public void keyTyped(KeyEvent arg0) {
    }


    public void saveScore (){
        Main.score.setText("Twój wynik to: " +size);
        try { Writer highscore = new FileWriter("highscore.txt",true);
            String score = String.valueOf(size);
            highscore.append(score);
            highscore.append("\n");
            highscore.close();
        } catch (Exception e) { };
    }

}
