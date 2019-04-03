import net.miginfocom.layout.*;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Main extends JFrame {

    public Main () {
        initComponents();
        frame.setVisible(true);
    }

    private void button1ActionPerformed(ActionEvent e) {

    }

    private JFrame frame;
    private JPanel panel;
    private JLabel currencyIn_label;
    private JLabel currencyOut_label;
    private JComboBox currencyIn_comboBox;
    private JComboBox currencyOut_comboBox;
    private JSeparator separator1;
    private JLabel amoundIn_label;
    private JLabel amoundOut_label;
    private JTextField amoundIn_textField;
    private JTextField amoundOut__textField;
    private JSeparator separator2;
    private JButton calculateButton;

    public void initComponents(){

        frame = new JFrame();
        panel = new JPanel();
        currencyIn_label = new JLabel();
        currencyOut_label = new JLabel();
        currencyIn_comboBox = new JComboBox();
        currencyOut_comboBox = new JComboBox();
        separator1 = new JSeparator();
        amoundIn_label = new JLabel();
        amoundOut_label = new JLabel();
        amoundIn_textField = new JTextField();
        amoundOut__textField = new JTextField();
        separator2 = new JSeparator();
        calculateButton = new JButton();

        frame.setSize(600, 320);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();  // wyswietli sie
        frame.setLocation(d.width/2-300, d.height/2-160);     //        na środku
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setTitle("Kalkulator walut na podstawie kurs\u00f3w NBP");
        frame.setLayout(new BorderLayout());

        panel.setVisible(true);
        frame.add(panel);
        panel.setLayout(new MigLayout(
                "fill",
                // columns
                "[fill]" +
                        "[fill]",
                // rows
                "[]" +
                        "[]" +
                        "[]" +
                        "[]" +
                        "[]" +
                        "[]" +
                        "[]"));
        panel.setSize(386, 16);

        //---- currencyIn_label ----
        currencyIn_label.setText("Wybierz symbol waluty wej\u015bciowej:");
        currencyIn_label.setHorizontalAlignment(SwingConstants.CENTER);
        currencyIn_label.setFont(currencyIn_label.getFont().deriveFont(currencyIn_label.getFont().getSize() + 2f));
        panel.add(currencyIn_label, "cell 0 0");

        //---- currencyOut_label ----
        currencyOut_label.setText("Wybierz symbol waluty wyj\u015bciowej:");
        currencyOut_label.setFont(currencyOut_label.getFont().deriveFont(currencyOut_label.getFont().getSize() + 2f));
        currencyOut_label.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(currencyOut_label, "cell 1 0");
        panel.add(currencyIn_comboBox, "cell 0 1");
        panel.add(currencyOut_comboBox, "cell 1 1");
        panel.add(separator1, "cell 0 2 2 1");

        //---- amoundIn_label ----
        amoundIn_label.setText("Podaj kwot\u0119:");
        amoundIn_label.setFont(amoundIn_label.getFont().deriveFont(amoundIn_label.getFont().getSize() + 2f));
        amoundIn_label.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(amoundIn_label, "cell 0 3");

        //---- amoundOut_label ----
        amoundOut_label.setText("Przeliczona kwota:");
        amoundOut_label.setHorizontalAlignment(SwingConstants.CENTER);
        amoundOut_label.setFont(amoundOut_label.getFont().deriveFont(amoundOut_label.getFont().getSize() + 2f));
        panel.add(amoundOut_label, "cell 1 3");

        //---- amoundIn_textField ----
        amoundIn_textField.setText("465");
        panel.add(amoundIn_textField, "cell 0 4");
        panel.add(amoundOut__textField, "cell 1 4");
        panel.add(separator2, "cell 0 5 2 1");

        //---- calculateButton ----
        calculateButton.setText("PRZELICZ");
        calculateButton.setIcon(null);
        calculateButton.setFont(calculateButton.getFont().deriveFont(calculateButton.getFont().getSize() + 2f));
        calculateButton.addActionListener(e -> button1ActionPerformed(e));
        panel.add(calculateButton, "cell 0 6 2 1");
    }

    public static void main(String[] args) {
        Main M = new Main();
    }
}
