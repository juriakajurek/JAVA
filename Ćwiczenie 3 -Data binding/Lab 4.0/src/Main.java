import net.miginfocom.swing.MigLayout;
import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.Bindings;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Vector;

public class Main extends JFrame {


    public static Vector <String> nazwy = new Vector <String>();
    public static Vector <String> przeliczniki = new Vector <String>();
    public static Vector <String> kody = new Vector <String>();
    public static Vector <String> kursyKupna = new Vector <String>();
    public static Vector <String> kursySprzedazy = new Vector <String>();



    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    public String calculate () {
        if (amoundIn_textField.getText().equals("") || !isNumeric(amoundIn_textField.getText()) ){
            amoundOut_textField.setText("");
            return ("");
        }
        else {

            int choosedIn = currencyIn_comboBox.getSelectedIndex();
            int choosedOut = currencyOut_comboBox.getSelectedIndex();

            Double res = Double.valueOf(amoundIn_textField.getText()) / Double.valueOf(przeliczniki.get(choosedIn)) * Double.parseDouble(kursyKupna.get(choosedIn).replaceAll(",", ".")); //z wejscioowej na zlotowki
            res = res * Double.valueOf(przeliczniki.get(choosedOut)) / Double.valueOf(kursySprzedazy.get(choosedOut).replaceAll(",", ".")); // ze zlotowek na wyjsciowa
            {
                res *= 100;                             //
                res = Double.valueOf(Math.round(res));  // zaokraglanie
                res /= 100;                             //
                amoundOut_textField.setText(String.valueOf(res));
            }
            //System.out.println(res);
            return (String.valueOf(res));
        }
    }

    public Main () {
        initComponents();
        frame.setVisible(true);
        initDataBindings();
    }

    public static void dload() throws Exception {
        String url = "https://www.nbp.pl/kursy/xml/c071z190410.xml";
        URL obj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
        connection.setRequestMethod("GET");  //domyslnie jest get, wiec redundantne
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputline;
        StringBuffer response = new StringBuffer();
        while ((inputline = in.readLine()) != null) {
            response.append(inputline);
        }
        in.close();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        InputSource is;

        builder = factory.newDocumentBuilder();
        is = new InputSource(new StringReader(response.toString()));
        Document doc = builder.parse(is);

        NodeList nList = doc.getElementsByTagName("pozycja");

        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                nazwy.add(eElement.getElementsByTagName("nazwa_waluty").item(0).getTextContent());
                przeliczniki.add(eElement.getElementsByTagName("przelicznik").item(0).getTextContent());
                kody.add(eElement.getElementsByTagName("kod_waluty").item(0).getTextContent());
                kursyKupna.add(eElement.getElementsByTagName("kurs_kupna").item(0).getTextContent());
                kursySprzedazy.add(eElement.getElementsByTagName("kurs_sprzedazy").item(0).getTextContent());
            }
        }
    }

    protected void initDataBindings() {
        BeanProperty<JTextField, String> jTextFieldBeanProperty = BeanProperty.create("text");
        BeanProperty<JTextField, String> jTextFieldBeanProperty_1 = BeanProperty.create("text");
        AutoBinding<JTextField, String, JTextField, String> autoBinding = Bindings.createAutoBinding(AutoBinding.UpdateStrategy.READ, amoundIn_textField, jTextFieldBeanProperty, amoundOut_textField, jTextFieldBeanProperty_1, "BINDING");
        autoBinding.setConverter(new Calculator());
        autoBinding.bind();
    }

    public class Calculator extends org.jdesktop.beansbinding.Converter<String, String> {

        @Override
        public String convertForward(String arg0) {
            return calculate();
        }

        @Override
        public String convertReverse(String arg0) {
            return calculate();
        }
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
    private JTextField amoundOut_textField;

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
        amoundOut_textField = new JTextField();
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
                        "[]" ));
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

        //---- currencyIn_comboBox ----
        //---- currencyOut_comboBox ----
        for (int i=0; i<nazwy.size(); ++i){
            currencyIn_comboBox.addItem(kody.get(i));
            currencyOut_comboBox.addItem(kody.get(i));
        }
        currencyIn_comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculate();
            }
        });
        currencyOut_comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculate();
            }
        });

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
        amoundIn_textField.setText("");
        panel.add(amoundIn_textField, "cell 0 4");
        amoundIn_textField.addActionListener(e -> textFieldActionListener(e));

        //---- amoundOut_textField ----
        amoundOut_textField.setText("");
        panel.add(amoundOut_textField, "cell 1 4");
    }

    private void textFieldActionListener(ActionEvent e) {
        //initDataBindings();
    }

    public static void main(String[] args) {
        try {
            Main.dload();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Main M = new Main();
    }
}
