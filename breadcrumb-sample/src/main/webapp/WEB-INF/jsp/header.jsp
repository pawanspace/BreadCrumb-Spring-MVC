<%@ page contentType="text/html"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<div>
	<c:forEach var="entry" items="${sessionScope.currentBreadCrumb}">
		<c:choose>
			<c:when test="${entry.currentPage == true}">
				${entry.label}
			</c:when>
			<c:otherwise>
					<a href="${entry.url}">${entry.label}></a>
			</c:otherwise>
		</c:choose>
	</c:forEach>

</div>