<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml11.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Book Detail</title>
    </head>
    <body>
        <form action="../books/${book.id}" method="post">
            <table>
                <tr>
                    <td>Title:</td>
                    <td><input type="text" name="title" value="${book.title}" /></td>
                </tr>
                <tr>
                    <td>ISBN:</td>
                    <td><input type="text" name="ISBN" value="${book.ISBN}" /></td>
                </tr>
                <tr>
                    <td>Author:</td>
                    <td><input type="text" name="author" value="${book.author}" /></td>
                </tr>
                <tr>
                    <td>Color:</td>
                    <td><input type="text" name="price" value="${book.price}" /></td>
                </tr>
            </table>
            <input type="submit" value="Update" />
        </form>
        <form action="delete?bookId=${book.id}" method="post">
            <input type="submit" value="Delete" />
        </form>
    </body>
</html>