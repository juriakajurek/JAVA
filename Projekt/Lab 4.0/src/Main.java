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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;
import java.util.Vector;

import java.util.List;

import java.util.Timer;

public class Main extends JFrame {



    public static Baza B = new Baza();

    public static Vector <String> nazwy = new Vector <String>();
    public static Vector <String> przeliczniki = new Vector <String>();
    public static Vector <String> kody = new Vector <String>();
    public static Vector <String> kursyKupna = new Vector <String>();
    public static Vector <String> kursySprzedazy = new Vector <String>();




    public Main() {
        timer.scheduleAtFixedRate(task, 0, 5000);
        System.out.println("timer ruszył ");
        initComponents();
        frame.setVisible(true);

        initDataBindings();


    }

    Timer timer = new Timer();
    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            try {
                Main.dload();
            }
            catch (Exception e){
                e.printStackTrace();
            }

        }
    };

    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    public static boolean Validate(int i){
        String temp = new String();

        temp = kody.elementAt(i);
        if(temp.length() > 3 ){
            System.out.println("Błąd danych");
            return false;
        }

        temp = przeliczniki.elementAt(i);
        if(Integer.parseInt(temp) != 1 && Integer.parseInt(temp) != 100){
            System.out.println("Bład danych");
            return  false;
        }


        temp = kursyKupna.elementAt(i);
        temp = temp.replaceAll(",",".");
        float f = Float.parseFloat(temp);
        if(f > 10 ){
            System.out.println("Błąd danych");
            return false;
        }

        temp = kursySprzedazy.elementAt(i);
        temp = temp.replaceAll(",",".");
        f = Float.parseFloat(temp);
        if(f > 10 ){
            System.out.println("Błąd danych");
            return false;
        }

        return true;
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

                DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyy, HH:mm:ss");
                Date date = new Date();
                B.insertCurrency(eElement.getElementsByTagName("kod_waluty").item(0).getTextContent(), dateFormat.format(date), eElement.getElementsByTagName("kurs_kupna").item(0).getTextContent());
                Validate(temp);
            }
        }

        List<Currency> waluty = B.selectCurriences();


        //System.out.println("Do bazy danych został dodany nowy wpis");
    }


    public void showHistory() {
        coursesHistory.setText("");
        List<Currency> waluty = B.selectCurriences();
        for(Currency c: waluty) {
            String[] parts = c.toString().split(" - "); //0 - id, 1 - symbol, 2-data  //aktualnego wpisu

            if (historyCheckBox.isSelected()){
                if (parts[1].equals(showCourse_comboBox.getSelectedItem().toString())) {  //jesli symbol sie zgadzaz
                    if (historyFromDayTextField.getText()!=""){ // jesli jest cos wpisane w pola od do


                        String[] dateParts = parts[2].split("-");  //0 - dzień, 1 - miesiąc, 2-rok  // daty z bazy

                        String[] input1Parts = historyFromDayTextField.getText().split("-"); //wprowadzona data
                        String[] input2Parts = historyToDayTextField.getText().split("-"); //wprowadzona data




                        coursesHistory.append("z rownosci" + Integer.valueOf(dateParts[0]));
                        coursesHistory.append("z rownosci" +Integer.valueOf(input1Parts[0]));


                        if (Integer.valueOf(dateParts[0]) != Integer.valueOf(input1Parts[0])) {

                            coursesHistory.append("z OD" + Integer.valueOf(dateParts[0]));
                            coursesHistory.append("z DO" +Integer.valueOf(input1Parts[0]));

                            if (Integer.valueOf(dateParts[0]) > Integer.valueOf(input1Parts[0]) ||
                                    Integer.valueOf(dateParts[0]) < Integer.valueOf(input2Parts[0])

                            ) {
                                coursesHistory.append("\n"+parts[1]+" - "+parts[3]+" - "+parts[2]);

                                coursesHistory.append("z wiekszosci");

                            }



                        }else{

                            coursesHistory.setText("");
                        }






                        if (dateParts[0].equals(input1Parts[0]) ||
                                dateParts[1].equals(input1Parts[1]) ||
                                dateParts[2].equals(input1Parts[2]) ){

                            //coursesHistory.append("\n"+parts[1]+" - "+parts[3]+" - "+parts[2]);

                            System.out.println("CHUJ");
                        }
                        else {
                            coursesHistory.setText("");
                        }

                        //System.out.println(dateParts[1].toString());
                        //(historyFromDayTextField.getText().getBytes())[0]



                    }





                }

            }
            else {
                if (parts[1].equals(showCourse_comboBox.getSelectedItem().toString())) {
                    coursesHistory.append("\n"+parts[1]+" - "+parts[3]+" - "+parts[2]);
                }
            }

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
    private JLabel showCourse_label;
    private JComboBox currencyIn_comboBox;
    private JComboBox currencyOut_comboBox;
    private JComboBox showCourse_comboBox;
    private JSeparator separator1;
    private JSeparator separator2;
    private JLabel amoundIn_label;
    private JLabel amoundOut_label;
    private JTextField amoundIn_textField;
    private JTextField amoundOut_textField;
    private JPanel datePanel;

    private JTextArea coursesHistory;
    private JTextField historyFromDayTextField;
    private JTextField historyToDayTextField;
    private JLabel historyFromDayLabel;
    private JLabel historyToDayLabel;
    private JLabel historyLabel;
    private JCheckBox historyCheckBox;

    public void initComponents(){
        frame = new JFrame();
        panel = new JPanel();
        datePanel = new JPanel();
        currencyIn_label = new JLabel();
        currencyOut_label = new JLabel();
        showCourse_label = new JLabel();
        currencyIn_comboBox = new JComboBox();
        currencyOut_comboBox = new JComboBox();
        showCourse_comboBox = new JComboBox();
        separator1 = new JSeparator();
        separator2 = new JSeparator();
        amoundIn_label = new JLabel();
        amoundOut_label = new JLabel();
        amoundIn_textField = new JTextField();
        amoundOut_textField = new JTextField();
        historyFromDayTextField = new JTextField();
        historyToDayTextField = new JTextField();
        historyFromDayLabel = new JLabel();
        historyToDayLabel = new JLabel();
        historyLabel = new JLabel();
        historyCheckBox = new JCheckBox("Filtruj wg. dat");

        coursesHistory = new JTextArea(40, 1);

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
            showCourse_comboBox.addItem(kody.get(i));

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
        showCourse_comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showHistory();
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

        panel.add(separator2, "cell 0 6 2 1");

        datePanel.setLayout(new GridLayout(1, 5));
        showCourse_label.setText("Wybierz symbol waluty, której historię kursów chcesz zobaczyć:");
        showCourse_label.setHorizontalAlignment(SwingConstants.CENTER);
        showCourse_label.setFont(currencyIn_label.getFont().deriveFont(currencyIn_label.getFont().getSize() + 2f));
        panel.add(showCourse_label, "cell 0 7 2 1");

        panel.add(showCourse_comboBox, "cell 0 8 2 1");

        historyFromDayLabel.setText("Od dnia:");

        historyToDayLabel.setText("Do dnia:");
        historyFromDayLabel.setVisible(false);
        historyFromDayTextField.setVisible(false);
        historyToDayLabel.setVisible(false);
        historyToDayTextField.setVisible(false);
        //historyFromDayLabel.setHorizontalAlignment(1);

        historyCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (historyCheckBox.isSelected()) {
                    historyFromDayLabel.setVisible(true);
                    historyFromDayTextField.setVisible(true);
                    historyToDayLabel.setVisible(true);
                    historyToDayTextField.setVisible(true);
                }
                else{
                    historyFromDayLabel.setVisible(false);
                    historyFromDayTextField.setVisible(false);
                    historyToDayLabel.setVisible(false);
                    historyToDayTextField.setVisible(false);
                }
            }
        });






        datePanel.add(historyFromDayLabel, 0);
        datePanel.add(historyFromDayTextField, 1);
        datePanel.add(historyToDayLabel, 2);
        datePanel.add(historyToDayTextField, 3);
        datePanel.add(historyCheckBox, 4, 4);


        panel.add(datePanel, "cell 0 9 2 1");


        coursesHistory.setEditable(false);
        JScrollPane scroll = new JScrollPane(coursesHistory);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        panel.add(scroll, "cell 0 10 2 1");

    }

    private void textFieldActionListener(ActionEvent e) {
        //initDataBindings();
    }

    private void JTextAreaActionListener (ActionEvent e) {
        //initDataBindings();
    }

    public static void main(String[] args) {
        try {
            Connectivity conn = new Connectivity();
            conn.All();
            XmlValidate xml = new XmlValidate();
            xml.work();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Main M = new Main();

    }
}
