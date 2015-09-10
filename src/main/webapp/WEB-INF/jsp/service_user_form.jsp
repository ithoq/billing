<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<?xml version="1.0" encoding="ISO-8859-1" ?>

<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Add/Edit User to Service</title>

    <jsp:include page="/WEB-INF/jsp/include/css_js_incl.jsp"/>
    <spring:url value="/resources/js/util.js" var="util" />
    <script src="${util}"></script>

</head>
<body>

<jsp:include page="/WEB-INF/jsp/include/nav_header.jsp"/>


<div class="col-lg-6">
    <form:form class="form-horizontal" method="POST" commandName="serviceForm" action="${pageContext.request.contextPath}/service/user/form">
        <fieldset>
            <legend>Add/Edit Service for User "<strong>${user.username}</strong>"</legend>
            <div class="form-group">
                <label class="col-lg-8 ${errorClass}">Please fill in all fields below.</label>
            </div>
            <div class="form-group">
                <label class="col-lg-3 control-label">Services</label>
                <div class="col-lg-9">
                    <form:select path="service" multiple="false" class="form-control">
                    <c:forEach items="${serviceList}" var="service">
                        <form:option value="${service}" label="${service.name}"/>
                    </c:forEach>
                    </form:select>
                    <form:errors path="service" cssClass="alert-danger" />
                </div>
            </div>
            <div class="form-group">
                <label for="dateStart" class="col-lg-3 control-label">Date Start</label>
                <div class="col-lg-9">
                    <form:input path="dateStart" class="form-control" id="dateStart" placeholder="Date Start"/>
                    <form:errors path="dateStart" cssClass="alert-danger" />
                </div>
            </div>
            <div class="form-group">
                <label for="dateEnd" class="col-lg-3 control-label">Date End</label>
                <div class="col-lg-9">
                    <form:input path="dateEnd" class="form-control" id="dateEnd" placeholder="Date End"/>
                    <form:errors path="dateEnd" cssClass="alert-danger" />
                </div>
            </div>
            <div class="form-group">
                <div class="col-lg-10 col-lg-offset-2">
                    <form:hidden path="user" />
                    <button type="reset" class="btn btn-default">Cancel</button>
                    <button type="submit" class="btn btn-primary">Submit</button>
                </div>
            </div>
        </fieldset>
    </form:form>

</div>

</body>
</html>