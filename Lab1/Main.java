import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Main extends JFrame implements ActionListener {

    private JButton button;
    private JFrame frame;
    private JPanel panel;

    public Main(){
        frame = new JFrame ("Uciekający przycisk");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize(800,   800);

        button = new JButton("Złap mnie!");
        button.addActionListener(this);

        panel = new JPanel();
        frame.add(panel);
        panel.add(button);

    }

    public void actionPerformed(ActionEvent e){
        Color colors[]={Color.yellow, Color.white,Color.red,Color.pink,Color.orange,Color.yellow,Color.white,Color.red,Color.pink,Color.orange,Color.magenta,Color.lightGray,Color.green,Color.gray,Color.cyan,Color.blue,Color.black};
        Random random = new Random();
        int x=random.nextInt(200)+100;  //z zakresu 100:300
        int y=random.nextInt(200)+20;  //z zakresu 20:220
        int z=random.nextInt(700-(x>y?x:y))+50;  //z zakresu 50:750 - wieksza z dl/szer przycisku
        button.setBounds(frame.getWidth()-x-z, frame.getHeight()-y-z, x, y);
        panel.setBackground(colors[z%(colors.length)]);
    }

    public static void main(String[] args){
        Main w = new Main();
    }
}
