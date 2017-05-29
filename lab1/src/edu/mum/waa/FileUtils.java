package edu.mum.waa;

import java.io.File;
import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

import static edu.mum.waa.BareBonesHTTPD.PORT_NUMBER;

public class FileUtils {
    public static final String PUBLIC_DIRECTORY = "lab1/public";
    public static final String DATE_FORMAT = "MMM dd yyyy HH:mm";
    private static final SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT);

    public static String readableFileSize(long size) {
        if (size <= 0) return "0";
        final String[] units = new String[]{"B", "kB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }

    public static String getFileMimeType(File file) throws IOException {
        return URLConnection.guessContentTypeFromName(file.getName());
    }

    public static List<File> getFilesInFolder(Path path) throws IOException {
        return Files.list(path).map(Path::toFile).collect(Collectors.toList());
    }

    private static String generateOneRow(String name, String path, File file) throws IOException {
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

    public static String generateFolderContent(Path path) throws IOException {
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

}
