<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ include file="/WEB-INF/views/fragments/header.jsp" %>
<%@ include file="/WEB-INF/views/fragments/sidebar.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <title>Update Profile</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css">
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</head>
<body>

<div class="main-content" style="margin-left: 250px; padding: 20px;"> <!-- Added margin for sidebar -->

    <div class="container mt-5">
        <h2 class="mb-4">👤 Update Your Profile</h2>

        <!-- Profile Update Form -->
        <form action="${pageContext.request.contextPath}/patient/update-profile" method="post">
            
            <div class="mb-3">
                <label for="name" class="form-label">Full Name</label>
                <input type="text" class="form-control" id="name" name="name" value="${patient.name}" required>
            </div>

            <div class="mb-3">
                <label for="email" class="form-label">Email address</label>
                <input type="email" class="form-control" id="email" name="email" value="${patient.email}" required>
            </div>

            <div class="mb-3">
                <label for="phone" class="form-label">Phone Number</label>
                <input type="text" class="form-control" id="mobileNo" name="mobileNo" value="${patient.mobileNo}" required>
            </div>
            
            <!-- Optional gender and age fields, commented out as per original -->
            <!-- 
            <div class="mb-3">
                <label for="gender" class="form-label">Gender</label>
                <select class="form-select" id="gender" name="gender" required>
                    <option value="">-- Select Gender --</option>
                    <option value="Male" ${patient.gender == 'Male' ? 'selected' : ''}>Male</option>
                    <option value="Female" ${patient.gender == 'Female' ? 'selected' : ''}>Female</option>
                    <option value="Other" ${patient.gender == 'Other' ? 'selected' : ''}>Other</option>
                </select>
            </div>

            <div class="mb-3">
                <label for="age" class="form-label">Age</label>
                <input type="number" class="form-control" id="age" name="age" value="${patient.age}" required>
            </div>
            -->

            <button type="submit" class="btn btn-primary">Update Profile</button>
        </form>

    </div>

</div>

<%@ include file="/WEB-INF/views/fragments/footer.jsp" %>

<script src="${pageContext.request.contextPath}/assets/js/bootstrap.bundle.min.js"></script>

<!-- SweetAlert for success notification -->
<script>
    const urlParams = new URLSearchParams(window.location.search);
    if (urlParams.has('success')) {
        Swal.fire({
            title: 'Profile Updated!',
            text: 'Your profile has been updated successfully.',
            icon: 'success',
            confirmButtonText: 'OK'
        }).then(() => {
            // Remove the 'success' query param from the URL after showing the message
            window.history.replaceState({}, document.title, window.location.pathname);
        });
    }

    // Error message handling if there's an email duplication
    if (urlParams.has('error')) {
        Swal.fire({
            title: 'Email Already Exists!',
            text: 'The email you provided is already in use. Please try a different one.',
            icon: 'error',
            confirmButtonText: 'OK'
        });
    }
</script>

</body>
</html>
