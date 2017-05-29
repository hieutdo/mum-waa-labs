package edu.mum.waa;

import java.io.File;
import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Collectors;

public class FileUtils {
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

}
