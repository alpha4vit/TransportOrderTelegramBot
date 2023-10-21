package by.gurinovich.PricePodschet.utils.parsers;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.FileReader;

@Service
public class HTMLParser {
    public String readHTML(String path){
        StringBuilder content = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String line;
            while ((line = reader.readLine()) != null){
                content.append(line).append("\n\n");
            }
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        return content.toString();
    }
}
