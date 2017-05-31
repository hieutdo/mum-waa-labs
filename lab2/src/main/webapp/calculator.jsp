<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
    <head><title>Calculator - JSP</title>
        <style>
            input {
                width: 60px;
                height: 20px;
            }

            .row {
                display: flex;
                margin-bottom: 10px;
                width: 300px;
            }

            .operator {
                display: flex;
                align-items: center;
                justify-content: center;
                width: 25px;
            }   </style>
    </head>
    <body>
        <form action="calculator.jsp" method="post">
            <div class="row">
                <input type="text" name="op11" value="${param.get("op11")}">
                <div class="operator">+</div>
                <input type="text" name="op12" value="${param.get("op12")}">
                <div class="operator">=</div>
                <input type="text" name="res1" value="${param.get("op11") + param.get("op12")}" readonly>
            </div>
            <div class="row">
                <input type="text" name="op21" value="${param.get("op21")}">
                <div class="operator">*</div>
                <input type="text" name="op22" value="${param.get("op22")}">
                <div class="operator">=</div>
                <input type="text" name="res2" value="${param.get("op21") * param.get("op22")}" readonly>
            </div>
            <button type="submit">Submit</button>
        </form>
    </body>
</html>

