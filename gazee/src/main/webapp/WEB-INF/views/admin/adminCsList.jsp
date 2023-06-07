<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" type="text/css" href="resources/css/adminReport.css"/>
<script>
    $(function () {
            var thisPage = "${currentPage}"
            $(".pagination button").each(function(){
                var idx = $(this).index();
                var thistitle = $(this).attr("title");
                if(thistitle == thisPage){
                    $(".pagination").find("button").eq(idx).addClass("active");
                }
            });
        }
    )

    function loadPage(pageNumber) {
        $.ajax({
            type: 'GET',
            url: "csList.do",
            data: {
                pageNumber: pageNumber
            },
            success: function (result) {
                $("#cs_list").html(result);
            }
        })
    }
</script>
<div class="cardHeader">
    <span><h2>전체 문의 목록</h2></span>
    <a href="#" class="btn" onclick="loadCs()">답변이 필요한 문의 목록</a>
</div>
<table>
    <thead>
    <tr>
        <td>카테고리</td>
        <td>ID</td>
        <td>제목</td>
        <td>날짜</td>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${pagedList}" var="bag">
        <tr>
            <p style="display: none" id="bag_cs_id">${bag.csId}</p>
            <td>${bag.csCategory}</td>
            <td>${bag.csWriter}</td>
            <td><a href=# id="cs_list_title" onclick="loadCsOne()">${bag.csTitle} </a></td>
            <td>${bag.csDate}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination" style="text-align: center">
    <c:forEach begin="1" end="${pages}" varStatus="page">
        <button class="paging" title="${page.index}" onclick="loadPage(${page.index})">${page.index}</button>
    </c:forEach>
</div>