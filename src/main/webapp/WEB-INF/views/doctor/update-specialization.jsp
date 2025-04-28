<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/views/fragments/header.jsp" %>

<h2>Update Specialization</h2>
<%@ include file="/WEB-INF/views/fragments/sidebar.jsp" %>
<form action="${pageContext.request.contextPath}/doctor/update-specialization" method="post">
    <div class="form-group">
        <label for="specialization">Specialization</label>
        <input type="text" class="form-control" id="specialization" name="specialization" value="${user.specialization}" required />
    </div>
    <button type="submit" class="btn btn-success">Save Specialization</button>
</form>

<%@ include file="/WEB-INF/views/fragments/footer.jsp" %>
