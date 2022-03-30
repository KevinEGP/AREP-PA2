package co.edu.eci;

import static spark.Spark.*;
import java.io.*;  
import java.net.*;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

public class ProxyServer {
    
    private static String[] servers = {"http://54.174.254.11:35000", "http://3.91.183.51:35000"};
    private static int currentServer = 0;
    public static void main( String[] args ) {
        port(getPort());
        staticFiles.location("/public");
        get("/", "text/html", (req, res) -> {
            System.out.println("Petición recibida " + req.url());
            res.redirect("index.html");            
            return null;
        });
        
        get("/log", (req, res) -> {
            Integer value = Integer.parseInt(req.queryParams("value"));
            System.out.println("Petición recibida " + req.url() + ", con párametro " + value);
            try {
                HttpResponse<String> response = Unirest.get(servers[currentServer] + "/log").header("accept", "application/json").queryString("value", value.toString()).asString();
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
            System.out.println("Petición recibida " + req.url() + ", con párametro " + value);
            try {
                HttpResponse<String> response = Unirest.get(servers[currentServer] + "/cos").header("accept", "application/json").queryString("value", value.toString()).asString();
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
        return 8080;
    }

    private static void changeServer() {
        System.out.println("Petición enviada a " + servers[currentServer]);
        currentServer = (currentServer + 1) % servers.length;
    }
}