package edu.mum.waa.lab2;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class CalculatorServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");

        Double op11 = null, op12 = null, res1 = null;
        Double op21 = null, op22 = null, res2 = null;

        if (req.getParameter("op11") != null
                && req.getParameter("op12") != null
                && !req.getParameter("op11").isEmpty()
                && !req.getParameter("op12").isEmpty()) {
            op11 = Double.valueOf(req.getParameter("op11"));
            op12 = Double.valueOf(req.getParameter("op12"));
            res1 = op11 + op12;
        }

        if (req.getParameter("op21") != null
                && req.getParameter("op22") != null
                && !req.getParameter("op21").isEmpty()
                && !req.getParameter("op22").isEmpty()) {
            op21 = Double.valueOf(req.getParameter("op21"));
            op22 = Double.valueOf(req.getParameter("op22"));
            res2 = op21 * op22;
        }

        String op11Str = op11 == null ? "" : op11.toString();
        String op12Str = op12 == null ? "" : op12.toString();
        String res1Str = res1 == null ? "" : res1.toString();
        String op21Str = op21 == null ? "" : op21.toString();
        String op22Str = op22 == null ? "" : op22.toString();
        String res2Str = res2 == null ? "" : res2.toString();

        String html = "<html>" +
                "<head>" +
                "   <title>Calculator - Servlet</title>" +
                "   <style>\n" +
                "       input { width: 60px; height: 20px; }\n" +
                "       .row {\n" +
                "           display: flex;\n" +
                "           margin-bottom: 10px;\n" +
                "           width: 300px;\n" +
                "       }" +
                "       .operator {\n" +
                "           display: flex;\n" +
                "           align-items: center;\n" +
                "           justify-content: center;\n" +
                "           width: 25px;\n" +
                "       }" +
                "   </style>" +
                "</head>" +
                "<body>\n" +
                "   <form action=\"calculator-servlet\" method=\"post\">\n" +
                "   <div class=\"row\">\n" +
                "       <input type=\"text\" name=\"op11\" value=\"" + op11Str + "\">\n" +
                "       <div class=\"operator\">+</div>\n" +
                "       <input type=\"text\" name=\"op12\" value=\"" + op12Str + "\">\n" +
                "       <div class=\"operator\">=</div>\n" +
                "       <input type=\"text\" name=\"res1\" value=\"" + res1Str + "\" readonly>\n" +
                "   </div>\n" +
                "   <div class=\"row\">\n" +
                "       <input type=\"text\" name=\"op21\" value=\"" + op21Str + "\">\n" +
                "       <div class=\"operator\">*</div>\n" +
                "       <input type=\"text\" name=\"op22\" value=\"" + op22Str + "\">\n" +
                "       <div class=\"operator\">=</div>\n" +
                "       <input type=\"text\" name=\"res2\" value=\"" + res2Str + "\" readonly>\n" +
                "   </div>\n" +
                "   <button type=\"submit\">Submit</button>\n" +
                "   </form>" +
                "</body>" +
                "</html>";

        PrintWriter out = resp.getWriter();
        out.println(html);
        out.close();
    }
}
