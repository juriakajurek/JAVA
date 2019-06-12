import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    {
        try {
            new FileReader(new File("highscore.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    ArrayList<Integer> wynik = new ArrayList<Integer>(0);

    public static JButton newGameButton = new JButton();
    public static JButton showScoreBoardButton = new JButton();
    public static JButton exitButton = new JButton();
    public static JLabel score = new JLabel();

    public static Main m;
    public static JFrame frame;
    public static Gamepanel gamepanel;

    public Main() {


        frame = new JFrame();

        newGameButton.setText("Nowa gra");
        newGameButton.setSize(150,50);
        newGameButton.setLocation(750  ,750);
        newGameButton.setVisible(false);

        showScoreBoardButton.setText("Pokaż ranking");
        showScoreBoardButton.setSize(150,50);
        showScoreBoardButton.setLocation(750  ,800);
        showScoreBoardButton.setVisible(false);

        exitButton.setText("Zakończ");
        exitButton.setSize(150,50);
        exitButton.setLocation(750  ,850);
        exitButton.setVisible(false);

        score.setSize(300,50);
        score.setFont(new Font("Calibri", Font.BOLD, 25));
        score.setLocation(700,20);
        frame.add(score);

        gamepanel = new Gamepanel();
        frame.add(newGameButton);
        frame.add(showScoreBoardButton);
        frame.add(exitButton);
        frame.add(gamepanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("SNAKE");

        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

        newGameButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                try{
                    frame.dispose();
                    frame = new JFrame();
                    frame.setVisible(false);
                    newGameButton.setVisible(false);
                    showScoreBoardButton.setVisible(false);
                    frame.remove(gamepanel);
                    gamepanel = null;
                    System.gc();
                    gamepanel = new Gamepanel();
                    frame.add(newGameButton);
                    frame.add(showScoreBoardButton);
                    frame.add(exitButton);
                    frame.add(gamepanel);
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.setTitle("SNAKE");

                    frame.pack();
                    frame.setVisible(true);
                    frame.setLocationRelativeTo(null);
                    gamepanel.tick();
                }
                catch (Exception e1){
                    System.out.println("Nie udało się rozpocząć nowej gry.");
                }
            }
        });

        showScoreBoardButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent q) {

                int wynik1 = 0, wynik2 = 0, wynik3 = 0;
                int temp = 0;

                File file = new File("highscore.txt");
                try {
                    Scanner sc = new Scanner(file);
                    while (sc.hasNextLine()) {
                        temp = Integer.parseInt(sc.nextLine());
                        if (temp > wynik1) {
                            wynik1 = temp;
                        }
                    }
                    sc.close();

                    sc = new Scanner(file);
                    while (sc.hasNextLine()) {
                        temp = Integer.parseInt(sc.nextLine());
                        if (temp > wynik2 && temp != wynik1) {
                            wynik2 = temp;
                        }
                    }
                    sc.close();

                    sc = new Scanner(file);
                    while (sc.hasNextLine()) {
                        temp = Integer.parseInt(sc.nextLine());
                        if (temp > wynik3 && temp != wynik1 && temp != wynik2) {
                            wynik3 = temp;
                        }
                    }
                    sc.close();

                } catch (FileNotFoundException e5) {
                    e5.printStackTrace();
                }
                try {
                    JOptionPane.showMessageDialog(frame,
                            "1. " + wynik1 + "\n2. " + wynik2 + "\n3. " + wynik3,
                            "Ranking",
                            JOptionPane.PLAIN_MESSAGE);
                } catch (Exception e1) {
                }
            }
        });
        exitButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        Main.m =new Main();
    }
}