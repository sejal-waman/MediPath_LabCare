<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ include file="/WEB-INF/views/fragments/header.jsp" %>
<%@ include file="/WEB-INF/views/fragments/sidebar.jsp" %>

<head>
<link rel="stylesheet" href="/css/styles.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
</head>

<div class="container mt-4">
  <h2>🛠 Equipment Status </h2>

  <!-- Show Add button only if user is ADMIN -->
 
    <a href="${pageContext.request.contextPath}/labtech/equipment/add"
       class="btn btn-success mb-3">➕ Add Equipment</a>
  

  <c:if test="${empty equipmentList}">
    <div class="alert alert-info">No equipment data available.</div>
  </c:if>

  <c:if test="${not empty equipmentList}">
    <div class="table-responsive">
      <table class="table table-bordered table-striped">
        <thead class="table-dark">
          <tr>
            <th>#</th>
            <th>Name</th>
            <th>Status</th>
            <!-- Actions column only if ADMIN -->
           
              <th>Actions</th>
           
          </tr>
        </thead>
        <tbody>
          <c:forEach var="equipment" items="${equipmentList}" varStatus="loop">
            <tr>
              <td>${loop.index + 1}</td>
              <td>${equipment.name}</td>
              <td>${equipment.status}</td>
             
                <td>
                  <a href="${pageContext.request.contextPath}/labtech/equipment/edit/${equipment.id}"
                     class="btn btn-sm btn-warning">Edit</a>
                  <a href="${pageContext.request.contextPath}/labtech/equipment/delete/${equipment.id}"
                     class="btn btn-sm btn-danger"
                     onclick="return confirm('Are you sure you want to delete this equipment?')">
                     Delete
                  </a>
                </td>
             
            </tr>
          </c:forEach>
        </tbody>
      </table>
    </div>
  </c:if>
</div>

<%@ include file="/WEB-INF/views/fragments/footer.jsp" %>
