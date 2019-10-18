<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib  prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link
	href="${pageContext.servletContext.contextPath }/assets/css/user.css"
	rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<jsp:include page="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="user">

				<form:form modelAttribute="userVo" id="join-form" name="joinForm" method="post"
					action="${pageContext.servletContext.contextPath }/user/update">
					
					<label class="block-label" for="name">이름</label> 
					<form:input path="name" value = "${userVo.name }"/>
					
					<p style="font-weight:bold; color:red; text-align:left; padding:2px 0 0 0 ">
						<form:errors path="name" />
					</p>
					
					<label class="block-label" for="email">이메일</label>
					<h4>${userVo.email }</h4>
					<form:hidden path="email" />
						
					<label class="block-label">패스워드</label> 
					<form:password path = 'password'/>

					<label class="block-label">성별</label>
					<p>
					<form:radiobuttons items="${userVo.genders }" path="gender" value="${userVo.gender }"/>					
					</p>
					
					<input type="submit" value="수정하기">

				</form:form>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp" />
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>