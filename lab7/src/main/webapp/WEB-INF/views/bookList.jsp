<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml11.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Books currently in the shop</title>
    </head>
    <body>
        <h1>Books currently in the shop</h1>
        <table border="1" cellpadding="10" cellspacing="1">
            <tr>
                <th>Title</th>
                <th>ISBN</th>
                <th>Author</th>
                <th>Price</th>
                <th></th>
            </tr>
            <c:forEach var="book" items="${books}">
                <tr>
                    <td>${book.title}</td>
                    <td>${book.ISBN}</td>
                    <td>${book.author}</td>
                    <td>${book.price}</td>
                    <td><a href="books/${book.id}">edit</a></td>
                </tr>
            </c:forEach>
        </table>

        <a href="/books/add">Add a Book</a>
    </body>
</html>