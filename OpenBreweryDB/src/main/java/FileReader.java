import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileReader {

    public Object[] readDataFromFile(String fileName){

        List<Object> arr = new ArrayList<Object>();
        BufferedReader br = null;
        try {
            String sCurrentLine;
            br = new BufferedReader(new java.io.FileReader("src\\main\\resources\\"+fileName));
            while ((sCurrentLine = br.readLine()) != null) {
                arr.add(sCurrentLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null){
                    br.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return arr.toArray();
    }
}
