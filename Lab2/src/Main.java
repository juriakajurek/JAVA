import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import static java.lang.Math.abs;

public class Main extends JFrame implements ActionListener {

    private JFrame frame;
    private JPanel panel;
    private button button;

    public class button extends JButton{
        Color colors[]={Color.yellow, Color.white,Color.red,Color.pink,Color.orange,Color.yellow,Color.white,Color.red,Color.pink,Color.orange,Color.magenta,Color.lightGray,Color.green,Color.gray,Color.cyan,Color.blue,Color.black};
        Random random = new Random();
        double old_a=400; //poczatkowe polozenie horyzontalne
        double old_b=400; //poczatkowe polozenie wertykalne
        boolean inMove = false;
        boolean isMoving(){
            return (inMove);
        }

        void move () {
            //int x=100,y = 100;  //dla stałego rozmiaru przycisku
            int x=random.nextInt(200)+100;  //szerokosc przycisku z zakresu 100:300
            int y=random.nextInt(200)+20;  //wysokosc przycisku z zakresu 20:220
            int a=random.nextInt(700-(x>y?x:y))+50;  //polozenie horyzontalne z zakresu 50:750 - wieksza z dl/szer przycisku,
            int b=random.nextInt(700-(x>y?x:y))+50;  //polozenie wertykalne z zakresu 50:750 - wieksza z dl/szer przycisku
            double step_b = abs(old_b-b)/100; //wartość kroku w pionie
            double step_a = (abs( old_a-a))/100; //wartosc kroku w poziomie

            Timer timer = new Timer();
            inMove=true;
            TimerTask task = new TimerTask(){
                @Override
                public void run(){
                    setBounds((int)((old_a<a)?(old_a+=step_a):(old_a-=step_a)), (int)((old_b<b)?(old_b+=step_b):(old_b-=step_b)), x, y);
                    if(abs(old_a-a)<step_a){
                        setBounds(a, b, x, y);
                        inMove=false;
                        cancel();
                    }
                }
            };
            panel.setBackground(colors[a%(colors.length)]);
            timer.scheduleAtFixedRate(task, 5, 5); // kazdy ruch trwa 5(period)*100steps=0,5s
        }
    }

    public Main(){
        frame = new JFrame ("Uciekający przycisk");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize(800,   800);

        button = new button();
        button.setText("Złap mnie!");
        button.addActionListener(this);

        panel = new JPanel();
        panel.setVisible(false);
        panel.setLayout(new CardLayout (300, 300));
        frame.add(panel);
        panel.setVisible(true);
        panel.add(button);
    }

    public void actionPerformed(ActionEvent e){
        if(!button.isMoving()){
            button.move();
        }
    }

    public static void main(String[] args){
        Main w = new Main();
    }
}
