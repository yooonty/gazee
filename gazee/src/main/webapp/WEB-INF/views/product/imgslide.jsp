<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@48,400,0,0" />
<title>Insert title here</title>
<style>
    .slideshow-container {
        position: relative;
        max-width: 500px;
        margin: auto;
    }

    .slideshow-container img {
        display: none;
        width: 100%;
        height: auto;
    }
    span{
    cursor: pointer; 
    }
    
    .material-symbols-outlined {
    	margin-top: 10px;
    }
</style>
<script type="text/javascript">
// 슬라이드 기능 구현
var slideIndex = 0;
showSlides();

function showSlides() {
    var slides = document.getElementsByClassName("slideshow-container")[0].getElementsByTagName("img");
    for (var i = 0; i < slides.length; i++) {
        slides[i].style.display = "none";
    }
    slideIndex++;
    if (slideIndex > slides.length) {
        slideIndex = 1;
    }
    slides[slideIndex - 1].style.display = "block";
}

function plusSlides(n) {
    slideIndex += n;
    var slides = document.getElementsByClassName("slideshow-container")[0].getElementsByTagName("img");
    if (slideIndex < 1) {
        slideIndex = slides.length;
    } else if (slideIndex > slides.length) {
        slideIndex = 1;
    }
    for (var i = 0; i < slides.length; i++) {
        slides[i].style.display = "none";
    }
    slides[slideIndex - 1].style.display = "block";
}
</script>
</head>
<body>
<div class="slideshow-container">
<c:forEach items="${bag2}" var="bag2">
    <img width="500px" height="500px"
        src="http://awswlqccbpkd17595311.cdn.ntruss.com/${bag2.productImageName}?type=f&w=500&h=500">
</c:forEach>
</div>
<c:if test="${bag2.size() > 1}">
    <span class="material-symbols-outlined" onclick="plusSlides(-1)">arrow_back_ios</span>
    <span class="material-symbols-outlined" onclick="plusSlides(1)">arrow_forward_ios</span>
</c:if>


</body>
</html>
