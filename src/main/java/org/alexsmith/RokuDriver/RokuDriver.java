package org.alexsmith.RokuDriver;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

public class RokuDriver {
    @Getter @Setter
    private static String deviceIp;

    public static void startRokuWebDriver() throws InterruptedException, IOException {
    ProcessBuilder processBuilder =
        new ProcessBuilder(
            "go", "run", "/Users/alex/code/automated-channel-testing/src/main.go",
            getDeviceIp());
        processBuilder.redirectErrorStream(true);

        Process process = processBuilder.start();

//        assertThat("Results should not be empty", results, is(not(empty())));
//        assertThat("Results should contain output of script: ", results, hasItem(
//                containsString("Hello Baeldung Readers!!")));
//
//        int exitCode = process.waitFor();
//        System.out.println("Exit code ('0' would be no errors): " + exitCode);
    }

    private String resolvePythonScriptPath(String path){
        File file = new File(path);
        return file.getAbsolutePath();
    }

    public static String startSession() {
        String sessionId = null;
        List<String> sessionIds = null;
        try {
            sessionIds = Session.getSessions();
        } catch (NullPointerException e) {
            System.out.println("Could not get sessions. Try creating one.");
        }
        if (sessionIds.isEmpty()) sessionId = Session.startSession();
        else sessionId = sessionIds.get(0);
        Session.setSessionId(sessionId);
        return sessionId;
    }

    public static String getSource(String sessionId) {
        // Creating a URL object
        URI uri = null;
        String decodedStr = null;
        try {
            uri = new URI("http://localhost:9000/v1/session/" + sessionId + "/source");
            HttpRequest request =
                    HttpRequest.newBuilder()
                            .GET()
                            .uri(uri)
                            .header("Content-Type", "application/json")
                            .build();

            HttpResponse<String> response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());
            HttpHeaders headers = response.headers();
            //headers.map().forEach((k, v) -> System.out.println(k + ": " + v));

            Type type = new TypeToken<Map<String, Object>>(){}.getType();
            Map<String, Object> responseMap = new Gson().fromJson(response.body(), type);
            decodedStr = org.alexsmith.utilities.Base64.decode(String.valueOf(responseMap.get("value")));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return decodedStr;
    }

    public static String launchApp(String sessionId) {
        // Creating a URL object
        URI uri = null;
        String decodedStr = null;
        try {
            uri = new URI("http://localhost:9000/v1/session/" + sessionId + "/launch");
            String requestBody = "{\"channelId\": \"dev\"}";
            HttpRequest request = HttpRequest.newBuilder()
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .uri(uri)
                    .header("Content-Type", "application/json")
                    .build();

            HttpResponse<String> response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());
            HttpHeaders headers = response.headers();
            //headers.map().forEach((k, v) -> System.out.println(k + ": " + v));

            Gson gson = new Gson();
            Type type = new TypeToken<Map<String, Object>>(){}.getType();
            Map<String, Object> responseMap = gson.fromJson(response.body(), type);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return decodedStr;
    }

    public RokuDriver sendKeyPress(Button button) {

        return this;
    }
}
