package co.edu.eci;

import static spark.Spark.*;
import java.io.*;  
import java.net.*;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

public class ProxyServer {
    
    private static int[] serversPorts = {35000};
    private static int currentServer = 0;
    private static String url = "http://localhost:";
    public static void main( String[] args ) {
        port(getPort());
        staticFiles.location("/public");
        get("/", "text/html", (req, res) -> {
            res.redirect("index.html");            
            return null;
        });
        
        get("/log", (req, res) -> {
            Integer value = Integer.parseInt(req.queryParams("value"));
            try {
                HttpResponse<String> response = Unirest.get(url + serversPorts[currentServer] + "/log").header("accept", "application/json").queryString("value", value.toString()).asString();
                changeServer();
                return response.getBody();
            } catch (Exception e) {
                changeServer();
                e.printStackTrace();
            }
            return null;
        });

        get("/cos", (req, res) -> {
            Integer value = Integer.parseInt(req.queryParams("value"));
            try {
                HttpResponse<String> response = Unirest.get(url+ serversPorts[currentServer] + "/cos").header("accept", "application/json").queryString("value", value.toString()).asString();
                changeServer();
                return response.getBody();
            } catch (Exception e) {
                changeServer();
                e.printStackTrace();
            }
            return null;
        });
    }

    public static int getPort() {
        if (System.getenv("PORT") != null) {
        return Integer.parseInt(System.getenv("PORT"));
        }
        return 3500;
    }

    private static void changeServer() {
        currentServer = (currentServer + 1) % serversPorts.length;
    }

    private static boolean isServerActive() {
        boolean active = false;
        while (!active) {
            try (Socket socket = new Socket()) {
                socket.connect(new InetSocketAddress("0.0.0.0", serversPorts[currentServer]), 500);
                active = true;
            } catch (IOException e) {
                active = false;
                changeServer();
            }
        }
        return active;
    }
}