<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<?xml version="1.0" encoding="ISO-8859-1" ?>

<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title><spring:message code="label.userActivityAddEdit"/></title>

    <jsp:include page="/WEB-INF/jsp/include/css_js_incl.jsp"/>
    <spring:url value="/resources/js/util.js" var="util" />
    <script src="${util}"></script>
    <link rel="icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico" />
</head>
<body>

<jsp:include page="/WEB-INF/jsp/include/nav_header.jsp"/>


<div class="col-lg-6">
    <form:form class="form-horizontal" method="POST" commandName="activityForm" action="${pageContext.request.contextPath}/addactivity.html">
        <fieldset>
            <legend><spring:message code="label.userActivityAddEdit"/></legend>
            <div class="form-group">
                <label class="col-lg-8 ${errorClass}"><spring:message code="label.fillField"/></label>
            </div>
            <div class="form-group">
                <label for="activityName" class="col-lg-3 control-label"><spring:message code="label.name"/></label>
                <div class="col-lg-9">
                    <spring:message code="label.name" var="name"/>
                    <form:input path="name" class="form-control" id="activityName" placeholder="${name}" />
                    <form:errors path="name" cssClass="alert-danger" />
                </div>
            </div>
            <div class="form-group">
                <label for="activityDescription" class="col-lg-3 control-label"><spring:message code="label.description"/></label>
                <div class="col-lg-9">
                    <spring:message code="label.description" var="descr"/>
                    <form:input path="description" class="form-control" id="activityDescription" placeholder="${descr}"/>
                    <form:errors path="description" cssClass="alert-danger" />
                </div>
            </div>
            <div class="form-group">
                <div class="col-lg-9 col-lg-offset-3">
                    <form:hidden path="id" />
                    <button type="submit" class="btn btn-primary"><spring:message code="label.submit"/></button>
                </div>
            </div>
        </fieldset>
    </form:form>

</div>

</body>
</html>