<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link
	href="${pageContext.servletContext.contextPath }/assets/css/board.css"
	rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		
		<div id="content">
			<div id="board">
				<form id="search_form" action="${pageContext.servletContext.contextPath }/board/list" method="post">
					<input type="hidden" id="page" name="page" value="1">
					<input type="text" id="kwd" name="kwd" value=""> 
					<input type="submit" value="찾기">
				</form>
				<table class="tbl-ex">

					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>
					<c:forEach items='${list }' var='vo' varStatus='status'>

						<tr>
							<td>${count-5*(param.page-1)-status.index}</td>


							<td style='text-align:left; padding-left:${20*vo.depth}px;'>

							<c:if test="${vo.depth>0 }">
									<img src='${pageContext.servletContext.contextPath }/assets/images/reply.png' />
							</c:if> 
							
							<c:choose>
								<c:when test="${vo.view ==true}">
									<a href=" ${pageContext.servletContext.contextPath }/board/view?page=${param.page }&no=${vo.no}&kwd=${param.kwd}">
									${vo.title } 
									</a>	
								</c:when>
								<c:otherwise>
									삭제된게시물입니다.	
								</c:otherwise>
							</c:choose>
							
							</td>
							<td>${vo.user_name }</td>
							<td>${vo.hit }</td>
							<td>${vo.reg_date }</td>
							<td>
							<c:if test='${authUser.no == vo.user_no&&authUser.no!=null&&vo.view==true }'>
								<a href=" ${pageContext.servletContext.contextPath }/board/delete?page=${param.page}&no=${vo.no}&kwd=${param.kwd}" class="del">삭제</a>
							</c:if>
							</td>
						</tr>
					
					</c:forEach>
					<tr>
					</tr>
				</table>
				<!-- pager 추가 -->
				
				
				<div class="pager">
					<ul>
						<li><c:choose> 
						<c:when test="${page_count!=0 }"><a href="${pageContext.servletContext.contextPath }/board/list?page=${(page_count*5)}&kwd=${param.kwd }"> ◀ </a></c:when>
						<c:otherwise>◀</c:otherwise>
						</c:choose></li>

						<c:forEach begin='1' end='5' step='1' var='i'>
							
						<li <c:if test="${param.page==(page_count*5)+i }"> class="selected" </c:if>> 	
						<c:choose>
						<c:when test = "${(page_count*5)+i <= (count-1)/5+1}"><a href="${pageContext.servletContext.contextPath }/board/list?page=${page_count*5+i }&kwd=${param.kwd}"> ${page_count*5+i } </a></c:when>
						<c:otherwise>${page_count*5+i }</c:otherwise>
						</c:choose>
						</li>
						
						</c:forEach>
						
						<li>
						<c:choose>
						<c:when test = "${(page_count*5)+6 <= (count-1)/5+1}"><a href="${pageContext.servletContext.contextPath }/board/list?page=${(page_count*5)+6}&kwd=${param.kwd}"> ▶</a></c:when>
						<c:otherwise>▶</c:otherwise>
						</c:choose>
						</li>
					</ul>
				</div>
				<!-- pager 추가 -->
				
				
				<c:if test='${!empty authUser }'>
					<div class="bottom">
						<a href="${pageContext.servletContext.contextPath }/board/write?page=${param.page}&kwd=${param.kwd}" id="new-book">글쓰기</a>
					</div>
				</c:if>
			</div>
		</div>
		
		
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
		<c:param name="menu" value="board" />
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>