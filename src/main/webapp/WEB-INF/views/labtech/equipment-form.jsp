<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ include file="/WEB-INF/views/fragments/header.jsp" %>
<%@ include file="/WEB-INF/views/fragments/sidebar.jsp" %>

<head>
<link rel="stylesheet" href="/css/styles.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
</head>
<div class="container mt-4">



    <form action="${pageContext.request.contextPath}/labtech/equipment/save" method="post">

      <!-- hidden id for edit -->
      <input type="hidden" name="id" value="${equipment.id}" />

      <div class="mb-3">
        <label class="form-label">Name</label>
        <input type="text"
               name="name"
               value="${equipment.name}"
               class="form-control"
               required />
      </div>

      <div class="mb-3">
        <label class="form-label">Status</label>
        <select name="status" class="form-select" required>
          <option value="Operational"
                  ${equipment.status == 'Operational' ? 'selected' : ''}>
            Operational
          </option>
          <option value="Maintenance"
                  ${equipment.status == 'Maintenance' ? 'selected' : ''}>
            Maintenance
          </option>
          <option value="Out of Service"
                  ${equipment.status == 'Out of Service' ? 'selected' : ''}>
            Out of Service
          </option>
        </select>
      </div>

      <button type="submit" class="btn btn-primary">💾 Save </button>
      <a href="${pageContext.request.contextPath}/labtech/equipment" class="btn btn-secondary">🔙 Back </a>

    </form>
  



</div>

<%@ include file="/WEB-INF/views/fragments/footer.jsp" %>
