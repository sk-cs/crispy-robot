package network;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class WeatherAPI {

    // https://dzone.com/articles/how-to-parse-json-data-from-a-rest-api-using-simpl
    // used this to accomplish the following tutorial

    public double  receiveWeatherInfo() throws IOException, ParseException {
        String apikey = "8cdf4782431ba32e17dc5381c001d8d1";
        String vancouverweatherkey = "https://api.openweathermap.org/data/2.5/weather?q=Vancouver,CA&APPID=";
        String theURL = vancouverweatherkey + apikey;

        URL url = new URL(theURL);
        HttpURLConnection cc = (HttpURLConnection) url.openConnection();
        cc.setRequestMethod("GET");
        cc.connect();
        int responseCode = cc.getResponseCode();
        if (responseCode != 200) {
            throw new RuntimeException("Http Response code wasn't 200;" + responseCode);

        } else {
            return parseJsoNContent(url);
        }
    }

    public Double parseJsoNContent(URL url) throws IOException, ParseException {
        Scanner webcontent = new Scanner(url.openStream());
        String parseContent = "";
        while (webcontent.hasNext()) {
            parseContent += webcontent.nextLine();
        }
        webcontent.close();
        JSONParser parser = new JSONParser();
        JSONObject obj = (JSONObject) parser.parse(parseContent);
        JSONObject temp = (JSONObject) obj.get("main");
        double d = 0.0;

        if (temp.get("temp") == (Double) temp.get("temp")) {
            d = (Double) temp.get("temp");
        } else {
            Long l = new Long((Long) temp.get("temp"));
            d = l.doubleValue();
        }
        return d;
    }

}
