<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.servletContext.contextPath }/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		
		<div id="content">
			<div id="board">
				<form class="board-form" method="post" action="${pageContext.servletContext.contextPath }/board/write">	
					<input type = "hidden" name = "no" value="${param.no }">
					<input type = "hidden" name = "page" value="${param.page }">
					<input type = "hidden" name = "kwd" value="${param.kwd }">
					<c:if test ='${param.no != null }'>		
						<input type = "hidden" name = "g_no" value="${vo.g_no }">
						<input type = "hidden" name = "o_no" value="${vo.o_no+1 }">
						<input type = "hidden" name = "depth" value="${vo.depth+1 }">	
					</c:if>
					
					<table class="tbl-ex">
						<tr>
							<th colspan="2">글쓰기 </th>
						</tr>
						<tr>
							<td class="label">제목</td>
							<td><input type="text" name="title" value=""></td>
						</tr>
						<tr>
							<td class="label">내용</td>
							<td>
								<textarea id="content" name="contents"></textarea>
							</td>
						</tr>
						<tr>
							<td colspan=2><input type="file" name="file"></td>
						</tr>
					</table>
					<div class="bottom">
						<c:if test ='${param.no != null }'>
							<a href="${pageContext.servletContext.contextPath }/board/view?page=${param.page}&no=${param.no}&kwd=${param.kwd}">취소</a>
						</c:if>
						<c:if test='${param.no==null }'>
							<a href="${pageContext.servletContext.contextPath }/board/list?page=${param.page }&kwd=${param.kwd}">취소</a>
						</c:if>
						
						
						<input type="submit" value="등록">
					</div>
				</form>				
			</div>
		</div>
		
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
		<c:param name="menu" value="board" />
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
		
	</div>
</body>
</html>