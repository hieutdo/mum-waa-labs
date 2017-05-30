package edu.mum.waa.controllers;

public abstract class AbstractController {
    public abstract String getTitle();

    public abstract String getBody();

    public String generate() {
        StringBuilder response = new StringBuilder();

        response.append("<!DOCTYPE html>");
        response.append("<html>");
        response.append("<head>");
        response.append("<title>" + getTitle() + "</title>");
        response.append("</head>");
        response.append("<body>");
        response.append(getBody());
        response.append("</body>");
        response.append("</html>");

        return response.toString();
    }

    ;
}
