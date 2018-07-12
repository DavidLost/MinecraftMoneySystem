package web;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;

public class JsonReader {

    private String readAll(Reader rd) throws IOException {

        String result = "";
        int currentChar;
        while ((currentChar = rd.read()) != -1) {
            result += (char)currentChar;
        }
        return result;
    }

    public JSONObject readJsonFromUrl(String url) {

        InputStream inputStream = null;
        try {
            inputStream = new URL(url).openStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
            String jsonText = readAll(bufferedReader);
            JSONObject json = null;
            try {
                json = new JSONObject(jsonText);
            } catch (JSONException e) {
                System.out.println("Json could not be read");
                e.printStackTrace();
            }
            inputStream.close();
            return json;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
