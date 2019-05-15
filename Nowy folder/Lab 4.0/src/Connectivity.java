import java.net.URL;
import java.net.URLConnection;

public class Connectivity {

    public void All(){
        CheckInternetConnection();
        CheckWebsiteConnection();
    }

    public void CheckInternetConnection(){
        try {
            URL url = new URL("https://www.google.com");
            URLConnection connection = url.openConnection();
            connection.connect();

            System.out.println("Internet Connection Successful");
        }
        catch (Exception e) {
            System.out.println("Internet Not Connected");
        }
    }

    public void CheckWebsiteConnection(){
        try {
            URL url = new URL("https://www.nbp.pl/kursy/xml/c071z190410.xml");
            URLConnection connection = url.openConnection();
            connection.connect();

            System.out.println("Website Connection Successful");
        }
        catch (Exception e) {
            System.out.println("Website Not Connected");
        }
    }


}
