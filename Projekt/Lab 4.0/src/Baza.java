import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class  Baza {

    public static final String DRIVER = "org.sqlite.JDBC";
    public static final String DB_URL = "jdbc:sqlite:waluty.db";

    private Connection conn;
    private Statement stat;

    public Baza() {
        try {
            Class.forName(Baza.DRIVER);
        } catch (ClassNotFoundException e) {
            System.err.println("Brak sterownika JDBC");
            e.printStackTrace();
        }

        try {
            conn = DriverManager.getConnection(DB_URL);
            stat = conn.createStatement();
        } catch (SQLException e) {
            System.err.println("Problem z otwarciem polaczenia z bazą danych");
            e.printStackTrace();
        }

        createTables();
    }

    public boolean createTables()  {
        String createCurriences = "CREATE TABLE IF NOT EXISTS currencies (currency_id INTEGER PRIMARY KEY AUTOINCREMENT, code varchar(255), date varchar(255), course int)";
        try {
            stat.execute(createCurriences);
        } catch (SQLException e) {
            System.err.println("Blad przy tworzeniu tabeli bazy danych");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean insertCurrency(String code, String date, String course) {
        try {
            PreparedStatement prepStmt = conn.prepareStatement(
                    "insert into currencies values (NULL, ?, ?, ?);");
            prepStmt.setString(1, code);
            prepStmt.setString(2, date);
            prepStmt.setString(3, course);
            prepStmt.execute();
        } catch (SQLException e) {
            System.err.println("Blad przy wstawianiu waluty");
            e.printStackTrace();
            return false;
        }
        return true;
    }



    public List<Currency> selectCurriences() {
        List<Currency> waluty = new LinkedList<Currency>();
        try {
            ResultSet result = stat.executeQuery("SELECT * FROM currencies");
            int id;
            String code, date, course;
            while(result.next()) {
                id = result.getInt("currency_id");
                code = result.getString("code");
                date = result.getString("date");
                course = result.getString("course");
                waluty.add(new Currency(id, code, date, course));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return waluty;
    }



}








