package co.edu.eci;

import static spark.Spark.*;
import com.google.gson.Gson;

public class MathService {
    public static void main( String[] args ) {
        Gson gson = new Gson();
        port(getPort());

        get("/log", (req, res) -> {
            Double value = Double.parseDouble(req.queryParams("value"));
            Double result = getLog(value);
            System.out.println("Petición recibida " + req.url() + ", con párametro " + value);
            return createJson("log", value, result);
        });

        get("/cos", (req, res) -> {
            Double value = Double.parseDouble(req.queryParams("value"));
            Double result = getCos(value);
            System.out.println("Petición recibida " + req.url() + ", con párametro " + value);
            return createJson("cos", value, result);
        });
    }

    public static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 35000;
    }

    public static Double getLog(Double value) {
        return Math.log(value);
    }

    public static Double getCos(Double value) {
        return Math.cos(value);
    }

    public static String createJson(Double input, Double output, String operation) {
        String string = "{";
        string += "\"operation\":" + operation + ",";
        string += "\"input\":" + input + ",";
        string += "\"output\":" + output;
        string += "}";
        return string;
    }

    public static String createJson(String operation, Double input, Double output) {
        return "{\"operation\":\"" + operation + "\", \"input\":" + input.toString() + ",  \"output\":" + output + "}";
    }
}
