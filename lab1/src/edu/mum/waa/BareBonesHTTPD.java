package edu.mum.waa;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import static edu.mum.waa.FileUtils.*;

public class BareBonesHTTPD extends Thread {

    private static final int PORT_NUMBER = 8080;
    private static final String PUBLIC_DIRECTORY = "lab1/public";
    private static final String DATE_FORMAT = "MMM dd yyyy HH:mm";

    private Socket connectedClient = null;
    private SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT);

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

    private String generateOneRow(String name, String path, File file) throws IOException {
        String fileSize = readableFileSize(file.length());
        BasicFileAttributes attributes = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
        String lastModifiedTime = df.format(attributes.lastModifiedTime().toMillis());

        StringBuilder out = new StringBuilder();

        out.append("<tr>");
        out.append("<td style=\"text-align: right; padding-left: 1em\"><code>" + fileSize + "</code></td>");
        out.append("<td style=\"text-align: right; padding-left: 1em\"><code>" + lastModifiedTime + "</code></td>");
        out.append("<td style=\"padding-left: 1em\"><code><a href=\"" + path + "\">");
        if (file.isDirectory()) {
            out.append("<b>" + name + "</b>");
        } else {
            out.append(name);
        }
        out.append("</a></code>");
        out.append("</td>");
        out.append("</tr>");

        return out.toString();
    }

    private String generateFolderContent(Path path) throws IOException {
        String pathStr = path.toString().replace(PUBLIC_DIRECTORY, "");

        if (pathStr.isEmpty()) {
            pathStr = "/";
        }

        StringBuilder response = new StringBuilder();

        response.append("<!DOCTYPE html>");
        response.append("<html>");
        response.append("<head>");
        response.append("<title>BareBonesHTTPD - Index of " + pathStr + "</title>");
        response.append("</head>");
        response.append("<body>");
        response.append("<h1>Index of " + pathStr + "</h1>");
        response.append("<table>");

        List<File> files = getFilesInFolder(path);

        response.append(generateOneRow("./", pathStr, path.toFile()));

        if (!pathStr.equals("/")) {
            response.append(generateOneRow("../", "..", path.getParent().toFile()));
        }

        for (File file : files) {
            response.append(generateOneRow(
                    file.getName(),
                    file.getPath().replace(PUBLIC_DIRECTORY, ""),
                    file)
            );
        }

        response.append("</table>");
        response.append("<br/>");
        response.append("<address>BareBonesHTTPD server running @ localhost:" + PORT_NUMBER + "</address>");
        response.append("</body>");
        response.append("</html>");

        return response.toString();
    }

    private void processRequest(BBHttpRequest httpRequest, BBHttpResponse httpResponse) throws IOException {
        String fileURI = httpRequest.getUri();
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
