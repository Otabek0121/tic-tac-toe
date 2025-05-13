<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Tic Tac Toe (MVC + Service)</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            display: flex;
            flex-direction: column;
            align-items: center;
        }

        .board {
            display: grid;
            grid-template-columns: repeat(3, 80px);
            grid-gap: 5px;
            margin-bottom: 20px;
        }

        .cell {
            width: 80px;
            height: 80px;
            border: 1px solid #ccc;
            display: flex;
            justify-content: center;
            align-items: center;
            font-size: 2em;
            text-decoration: none;
            color: #333;
        }

        .cell.empty:hover {
            background-color: #f0f0f0;
        }

        .cell:not(.empty) {
            cursor: default;
        }

        .status {
            margin-bottom: 15px;
            font-size: 1.2em;
            font-weight: bold;
            min-height: 1.5em;
        }

        .error-message {
            color: red;
            margin-bottom: 10px;
        }

        .reset-button {
            padding: 8px 15px;
            background-color: #007bff;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            text-decoration: none;
        }

        .reset-button:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>

<h1>Tic Tac Toe</h1>

<c:if test="${gameState.currentPlayer == 'O' && not gameState.isGameOver()}">
    <c:redirect url="${pageContext.request.contextPath}/auto-move"/>
</c:if>

<%-- O'yin holatini ko'rsatish --%>
<div class="status message">
    <c:choose>
        <c:when test="${not empty gameState.winner}">
            <span style="color: green;">G'olib: ${gameState.winner}! O'yin tugadi.</span>
        </c:when>
        <c:when test="${gameState.isDraw()}">
            <span style="color: orange;">Durrang! O'yin tugadi.</span>
        </c:when>
        <c:when test="${not empty gameState.currentPlayer && not gameState.isGameOver()}">
            Navbat: ${gameState.currentPlayer}
        </c:when>
        <c:when test="${gameState.isGameOver()}">
            O'yin tugadi. Qayta boshlash uchun tugmani bosing.
        </c:when>
        <c:otherwise>
            O'yinni boshlash uchun "O'yinni Boshlash" tugmasini bosing.
        </c:otherwise>
    </c:choose>
</div>

<%-- Xatolik xabari (RedirectAttributes dan kelgan) --%>
<c:if test="${not empty errorMessage}">
    <p class="message error">${errorMessage}</p>
</c:if>

<%-- O'yin taxtasi --%>
<div class="board">
    <c:if test="${not empty gameState.board}">
        <c:forEach var="cellValue" items="${gameState.board}" varStatus="status">
            <c:set var="cellIndex" value="${status.index}"/>
            <c:choose>
                <c:when test="${empty cellValue && not gameState.isGameOver()}">
                    <a class="cell empty" href="${pageContext.request.contextPath}/move?cell=${cellIndex}"
                       title="Hujayra ${cellIndex} ga yurish">
                    </a>
                </c:when>
                <c:otherwise>
                    <div class="cell">${not empty cellValue ? cellValue : ' '}</div>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:if>
</div>

<a href="${pageContext.request.contextPath}/reset">
    <button class="reset-button">O'yinni Boshlash / Qayta Boshlash</button>
</a>

</body>
</html>