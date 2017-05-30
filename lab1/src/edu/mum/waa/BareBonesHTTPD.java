package edu.mum.waa;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import static edu.mum.waa.FileUtils.*;

public class BareBonesHTTPD extends Thread {
    public static final int PORT_NUMBER = 8080;
    public static final Map<String, String> URI_MAPPING = new HashMap<String, String>() {{
        try {
            Files.readAllLines(Paths.get("lab1/uri.mapping")).forEach(s -> {
                String[] parts = s.split("-");
                String[] URIs = parts[0].split(",");
                String className = parts[1];
                for (String URI : URIs) {
                    put(URI, className);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }};

    private Socket connectedClient = null;

    public BareBonesHTTPD(Socket client) {
        connectedClient = client;
    }

    public void run() {
        try {
            System.out.println(connectedClient.getInetAddress() + ":" + connectedClient.getPort() + " is connected");

            BBHttpRequest httpRequest = getRequest(connectedClient.getInputStream());

            if (httpRequest != null) {
                BBHttpResponse httpResponse = new BBHttpResponse();
                processRequest(httpRequest, httpResponse);
                sendResponse(httpResponse);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void processRequest(BBHttpRequest httpRequest, BBHttpResponse httpResponse) throws IOException, NoSuchMethodException, ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException {
        String fileURI = httpRequest.getUri();

        if (URI_MAPPING.containsKey(fileURI)) {
            Class<?> controllerClass = Class.forName(URI_MAPPING.get(fileURI));
            Object controller = controllerClass.newInstance();
            Method generateMethod = controller.getClass().getMethod("generate");
            String output = (String) generateMethod.invoke(controller);

            httpResponse.setStatusCode(200);
            httpResponse.setMessage(output);

            return;
        }

        Path filePath = Paths.get(PUBLIC_DIRECTORY + fileURI);

        if (!Files.exists(filePath)) {
            httpResponse.setStatusCode(404);
            httpResponse.setMessage("Oops! File not found: " + fileURI);
            return;
        }

        if (Files.isDirectory(filePath)) {
            httpResponse.setMessage(generateFolderContent(filePath));
        } else {
            httpResponse.setPayload(Files.readAllBytes(filePath));
            httpResponse.setContentType(getFileMimeType(filePath.toFile()));
        }

        httpResponse.setStatusCode(200);
    }

    private BBHttpRequest getRequest(InputStream inputStream) throws IOException {
        BBHttpRequest httpRequest = new BBHttpRequest();
        BufferedReader fromClient = new BufferedReader(new InputStreamReader(inputStream));
        String headerLine = fromClient.readLine();

        if (headerLine.isEmpty()) {
            return null;
        }

        System.out.println("The HTTP request is ....");
        System.out.println(headerLine);

        // Header Line
        StringTokenizer tokenizer = new StringTokenizer(headerLine);
        httpRequest.setMethod(tokenizer.nextToken());
        httpRequest.setUri(tokenizer.nextToken());
        httpRequest.setHttpVersion(tokenizer.nextToken());

        // Header Fields and Body
        boolean readingBody = false;
        ArrayList<String> fields = new ArrayList<>();
        ArrayList<String> body = new ArrayList<>();

        while (fromClient.ready()) {
            headerLine = fromClient.readLine();
            System.out.println(headerLine);

            if (!headerLine.isEmpty()) {
                if (readingBody) {
                    body.add(headerLine);
                } else {
                    fields.add(headerLine);
                }
            } else {
                readingBody = true;
            }
        }

        httpRequest.setFields(fields);
        httpRequest.setMessage(body);

        return httpRequest;
    }

    private void sendResponse(BBHttpResponse response) throws IOException {
        String statusLine = null;

        if (response.getStatusCode() == 200) {
            statusLine = "HTTP/1.1 200 OK";
        } else if (response.getStatusCode() == 404) {
            statusLine = "HTTP/1.1 404 Not Found";
        } else {
            statusLine = "HTTP/1.1 501 Not Implemented";
        }

        long contentLength = response.getMessage() != null
                ? response.getMessage().length()
                : response.getPayload().length;

        String serverDetails = "Server: BareBones HTTPServer";
        String contentLengthLine = "Content-Length: " + contentLength;
        String contentTypeLine = "Content-Type: " + response.getContentType();

        try (DataOutputStream toClient = new DataOutputStream(connectedClient.getOutputStream())) {
            toClient.writeBytes(statusLine + "\r\n");
            toClient.writeBytes(serverDetails + "\r\n");
            toClient.writeBytes(contentTypeLine + "\r\n");
            toClient.writeBytes(contentLengthLine + "\r\n");
            toClient.writeBytes("Connection: close\r\n");
            toClient.writeBytes("\r\n");

            if (response.getPayload() != null) {
                toClient.write(response.getPayload());
            } else {
                toClient.writeBytes(response.getMessage());
            }
        }
    }

    public static void main(String args[]) throws Exception {
        try (ServerSocket server = new ServerSocket(PORT_NUMBER, 10, InetAddress.getByName("127.0.0.1"))) {
            System.out.println("Server Started on port " + PORT_NUMBER);

            while (true) {
                Socket connected = server.accept();
                (new BareBonesHTTPD(connected)).start();
            }
        }
    }
}
