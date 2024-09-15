package org.alexsmith.RokuDriver;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.net.http.HttpClient.newHttpClient;

public class Session {

    @Getter
    @Setter
    private static String sessionId;

    private static final String SESSION_ALREADY_EXISTS = "Session already exist";

    public static List<String> getSessions() {
        List<String> sessions = new ArrayList<>();
        // Creating a URL object
        URI uri = null;
        try {
            uri = new URI("http://localhost:9000/v1/sessions");

            String requestBody = "{\"ip\": \"" + RokuDriver.getDeviceIp() + "\"}";

            HttpRequest request =
                    HttpRequest.newBuilder()
                            .GET()
                            .uri(uri)
                            .header("Content-Type", "application/json")
                            .build();

            HttpResponse<String> response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());
            //HttpHeaders headers = response.headers();
            //headers.map().forEach((k, v) -> System.out.println(k + ": " + v));
            SessionInfo[] sessionsArray = new Gson().fromJson(response.body(), SessionInfo[].class);
            for (SessionInfo session : sessionsArray) sessions.add(session.sessionId);

        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage() + "\n");
            e.printStackTrace();
        }

        return sessions;
    }

    public static String startSession() {
        if (getSessionId() != null) {
            return getSessionId();
        } else {
            // Creating a URL object
            URI uri = null;
            try {
                uri = new URI("http://localhost:9000/v1/session");
                String requestBody = "{\"ip\": \"" + RokuDriver.getDeviceIp() + "\"}";
                HttpRequest request =
                        HttpRequest.newBuilder()
                                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                                .uri(uri)
                                .header("Content-Type", "application/json")
                                .build();

                HttpResponse<String> response =
                        newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
                HttpHeaders headers = response.headers();
                //headers.map().forEach((k, v) -> System.out.println(k + ": " + v));
                Gson gson = new Gson();
                Type type = new TypeToken<Map<String, Object>>() {}.getType();
                Map<String, Object> responseMap = gson.fromJson(response.body(), type);
                if (!responseMap.get("status").toString().equalsIgnoreCase("0.0")) {
                    throw new Exception(
                            "Session is already created. Delete or use existing session."); // TODO : create new
                    // exception for this.
                }
                setSessionId(responseMap.get("sessionId").toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return getSessionId();
    }


}
