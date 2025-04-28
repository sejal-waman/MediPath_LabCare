<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ include file="/WEB-INF/views/fragments/header.jsp" %>
<%@ include file="/WEB-INF/views/fragments/sidebar.jsp" %>

<head>
    <title>My Lab Tests Reports - MediPath LabCare</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
</head>

<div class="container mt-5 mb-5">
    <h2 class="mb-4">🧪 My Lab Tests Reports History</h2>

    <c:if test="${empty reportList}">
        <div class="alert alert-info">No lab reports available in your medical history.</div>
    </c:if>

    <c:if test="${not empty reportList}">
        <div class="table-responsive">
            <table class="table table-bordered table-hover shadow">
                <thead class="table-dark">
                    <tr>
                        <th>#</th>
                        <th>Test Name</th>
                        <th>Category</th>
                        <th>Doctor</th>
                        <th>Report Date</th>
                        <th>Cost</th>
                        <th>Status</th>
                        <th>Actions</th>
                    </tr>
                </thead>

                <tbody>
                    <c:forEach var="report" items="${reportList}" varStatus="loop">
                        <tr>
                            <td>${loop.index + 1}</td>
                            <td>${report.testName}</td>
                            <td>${report.testCategory}</td>

                            <td>
                                <c:choose>
                                    <c:when test="${report.assignedDoctor != null}">
                                        Dr. ${report.assignedDoctor.username}
                                    </c:when>
                                    <c:otherwise>
                                        <span class="text-muted">N/A</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>

                            <td>
                                <fmt:formatDate value="${report.reportDateAsUtilDate}" pattern="dd-MM-yyyy" />
                            </td>

                            <td>₹${report.testCost}</td>

                            <td>
                                <span class="badge 
                                    <c:choose>
                                        <c:when test="${report.status == 'COMPLETED'}">bg-success</c:when>
                                        <c:when test="${report.status == 'PENDING'}">bg-warning</c:when>
                                        <c:otherwise>bg-secondary</c:otherwise>
                                    </c:choose>">
                                    ${report.status}
                                </span>
                            </td>

                            <td>
                                <c:choose>
                                    <c:when test="${report.status == 'COMPLETED' && not empty report.pdfPath}">
                                        <a href="${pageContext.request.contextPath}/patient/download-report/${report.id}" 
                                           class="btn btn-sm btn-success" target="_blank">
                                            Download PDF
                                        </a>
                                    </c:when>
                                    <c:otherwise>
                                        <button class="btn btn-sm btn-secondary" disabled>Download Unavailable</button>
                                    </c:otherwise>
                                </c:choose>
                            </td>

                        </tr>
                    </c:forEach>
                </tbody>

            </table>
        </div>
    </c:if>
</div>

<%@ include file="/WEB-INF/views/fragments/footer.jsp" %>
