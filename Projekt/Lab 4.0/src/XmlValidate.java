import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.*;
import java.net.URL;

public class XmlValidate {
    public void work() throws Exception {

        URL url = new URL("https://www.nbp.pl/kursy/xml/c071z190410.xml");
        InputStream io = url.openStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(io));
        FileOutputStream fio = new FileOutputStream("file.xml");
        PrintWriter pr = new PrintWriter(fio, true);
        String data = "";
        while ((data = br.readLine()) != null) {

            pr.println(data);
        }

        File schemaFile = new File("scheme.xsd");
        Source xmlFile = new StreamSource(new File("file.xml"));
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        try {
            Schema schema = schemaFactory.newSchema(schemaFile);
            Validator validator = schema.newValidator();
            validator.validate(xmlFile);
            System.out.println("XML is valid");
        } catch (SAXException e) {
            System.out.println("XML is NOT valid, reason:" + e);
        } catch (IOException e) {
        }
    }
}
