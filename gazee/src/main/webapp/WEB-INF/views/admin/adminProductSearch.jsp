<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" type="text/css" href="resources/css/adminProduct.css"/>
<!DOCTYPE html>
<table id="boardtable">
    <thead>
    <tr>
        <td>상품 ID</td>
        <td>등록일시</td>
        <td>상품명</td>
        <td>판매자</td>
        <td>판매가격</td>
        <td>조회수</td>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${searchList}" var="bag" varStatus="status">
        <tr>
            <td>
                    ${bag.productId}
            </td>
            <td>
                <fmt:formatDate value="${bag.savedTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
            </td>
            <td>
                    ${bag.productName}
            </td>
            <td>
                    ${bag.memberId}
            </td>
            <td>
                <fmt:formatNumber value="${bag.price}" type="number" pattern="#,###"/>원
            </td>
            <td>
                    ${bag.productViews}회
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

