import jdk.nashorn.api.scripting.JSObject;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dirk on 4/26/16.
 */
public class Parser {

    private JSONObject jsonObject;
    private JSONArray jsonArray;

    public static void main(String str[])
    {
        Parser parser = new Parser("./src/jsonInput.txt");
        Object obj = parser.getRuns("54862a7d-717b-44b9-a1d4-c07e190a74fd");
        System.out.println(obj);
    }

    public Parser(String fileName) {
        String line;
        String text = null;

        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                text += line;
                System.out.println(line.charAt(0));
            }

            bufferedReader.close();

            text = text.trim();
            text = text.substring(text.indexOf('{'));
            this.jsonObject = new JSONObject(text);
            this.jsonArray = jsonObject.getJSONArray("people");
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");
        }
        catch(IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");
        }
    }

    /*
    login
    getRuns(user)
    getRunsBetween(user, startDate, endDate)
    as long as the run object contains a score that should be enough
    maybe a weekly score would be useful but i'm not sure
     */

    public Object getRuns(String id) {
        for (int i = 0; i < this.jsonArray.length(); i++) {
            JSONObject line = this.jsonArray.getJSONObject(i);
            String id_found = line.getString("id");
            if (id_found.equals(id)) {
                //we found the id we want, so return the runs it's associated with
                System.out.println(line);
                List<JSONObject> runsList = new ArrayList<>();

                JSONArray weeks = line.getJSONArray("weeks");
                for (int j = 0; j < weeks.length(); j++) {
                    JSONObject week = weeks.getJSONObject(j);
                    JSONArray runs = week.getJSONArray("runs");
                    for (int k = 0; k < runs.length(); k++) {
                        JSONObject run = runs.getJSONObject(k);
                        runsList.add(run);
                    }
                }
                return runsList;
            }
        }
        return null;
    }

    public Object getObject() {
        //Object object = jsonObject.get("people");
        //JSONObject newJSON = jsonObject.getJSONObject("stat");
        //System.out.println(newJSON.toString());
        //jsonObject = new JSONObject(newJSON.toString());
        //System.out.println(jsonObject.getString("rcv"));
        //System.out.println(jsonObject.getJSONArray("argv"));

        JSONArray fullArray = jsonObject.getJSONArray("people");

        for (int i = 0; i < fullArray.length(); i++) {
            JSONObject line = fullArray.getJSONObject(i);
            System.out.println(line.getString("id"));

        }
        return null;
    }
}
