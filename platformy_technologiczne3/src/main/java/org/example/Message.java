package org.example;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Message implements Serializable {
    private String content;

    Message(String content){
        this.content = content;
    }

    public String getMessage(){
        return content;
    }

    public void setString(String content){
        this.content = content;
    }

    public void GET(String string){
        this.content = string + " 200 OK\n" +
                "Date: " + LocalDateTime.now() + "\n" +
                "Server: Apache\n" +
                "Content-Type: text/html\n" +
                "Content-Length: 1234\n" +
                "\n" +
                "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "<title>Strona główna</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<h1>Witaj na stronie głównej!</h1>\n" +
                "<p>To jest zawartość strony.</p>\n" +
                "</body>\n" +
                "</html>\n";
    }

    public void POST(String string){
        this.content = string + " 201 Created\n" +
                "Date: " + LocalDateTime.now() + "\n" +
                "Server: Apache\n" +
                "Content-Type: application/json\n" +
                "Content-Length: 36\n" +
                "\n" +
                "{\n" +
                "  \"status\": \"success\",\n" +
                "  \"message\": \"Dane zostały przesłane.\"\n" +
                "}";
    }

    public void PUT(String string){
        this.content = string + " 200 OK\n" +
                "Date: " + LocalDateTime.now() + "\n" +
                "Server: Apache\n" +
                "Content-Type: application/json\n" +
                "Content-Length: 45\n" +
                "\n" +
                "{\n" +
                "  \"status\": \"success\",\n" +
                "  \"message\": \"Dane użytkownika zaktualizowane.\"\n" +
                "}\n";
    }

    public void DELETE(String string){
        this.content = string + " 204 No Content\n" +
                "Date: " + LocalDateTime.now() + "\n" +
                "Server: Apache\n";
    }

    public void HEAD(String string){
        this.content = string + " 200 OK\n" +
                "Date: " + LocalDateTime.now() + "\n" +
                "Server: Apache\n" +
                "Content-Type: text/html\n" +
                "Content-Length: 0\n";
    }

    public void OPTIONS(String string){
        this.content = string + " 200 OK\n" +
                "Date: " + LocalDateTime.now() + "\n" +
                "Server: Apache\n" +
                "Allow: GET, POST, PUT, DELETE, HEAD, OPTIONS\n";
    }

    public void BADREQUEST(){
        this.content = "HTTP 400 Bad Request";
    }
}
