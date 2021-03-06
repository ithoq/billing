<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<?xml version="1.0" encoding="ISO-8859-1" ?>

<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>

    <title><spring:message code="label.csv"/></title>
    <link rel="icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico"/>
    <jsp:include page="/WEB-INF/jsp/include/css_js_incl.jsp"/>
    <jsp:include page="/WEB-INF/jsp/include/totop_res_incl.jsp"/>
    <spring:url value="/resources/css/file-tree.min.css" var="fileTreeCss"/>
    <link href="${fileTreeCss}" rel="stylesheet"/>
    <spring:url value="/resources/css/loader-style.css" var="loader" />
    <link href="${loader}" rel="stylesheet"/>

    <spring:url value="/resources/js/util.js" var="util"/>
    <script src="${util}"></script>
    <spring:url value="/resources/js/uploadCSVFile.js" var="csvScript"/>
    <script src="${csvScript}"></script>
    <spring:url value="/resources/js/file-tree.min.js" var="fileTreeMin"/>
    <script src="${fileTreeMin}"></script>
    <spring:url value="/resources/js/jquery-ui.min.js" var="jqueryMinUi"/>
    <script src="${jqueryMinUi}"></script>
    <spring:url value="/resources/js/jquery.mjs.nestedSortable.js" var="nestedFileTree"/>
    <script src="${nestedFileTree}"></script>


</head>
<body>
<jsp:include page="/WEB-INF/jsp/include/nav_header.jsp"/>

<div class="well well-lg">
    <div id="totopscroller"> </div>

    <%--divs for messages--%>
   <div id="errorMessage" class="alert alert-danger navbar-fixed-top text-center" style="display: none">
        <strong><spring:message code="csv.error"/></strong>
    </div>
    <div id="errorSelectFile" class="alert alert-warning navbar-fixed-top text-center" style="display: none">
        <strong><spring:message code="csv.selectFile"/></strong>
    </div>
    <div id="errorIncorrectType" class="alert alert-warning navbar-fixed-top text-center" style="display: none">
        <strong><spring:message code="label.incorrectFileCSV"/></strong>
    </div>
    <div id="errorUnavailable" class="alert alert-danger navbar-fixed-top text-center" style="display: none">
        <strong><spring:message code="csv.unavailable"/></strong>
    </div>
    <div id="successMessage" class="alert alert-success navbar-fixed-top text-center" style="display: none">
        <strong><spring:message code="csv.success"/></strong>
    </div>
    <div id="successMessageReport" class="alert alert-success navbar-fixed-top text-center" style="display: none">
        <strong><spring:message code="csv.successReport"/></strong>
    </div>
    <div id="errorMessageReport" class="alert alert-danger navbar-fixed-top text-center" style="display: none">
        <strong><spring:message code="csv.errorReport"/></strong>
    </div>
    <div id="errorMessageReportChoose" class="alert alert-warning navbar-fixed-top text-center" style="display: none">
        <strong><spring:message code="csv.chooseAnyReport"/></strong>
    </div>
    <div id="errorMessageCSVBUSY" class="alert alert-warning navbar-fixed-top text-center" style="display: none">
        <strong><spring:message code="label.BUSY"/></strong>
    </div>


    <legend>
        <span class="glyphicon glyphicon-cloud-upload" aria-hidden="true"></span><spring:message code="label.selectCSV"/>
        &nbsp;&nbsp;
        <a id="reportsList" href="#reportModal" data-toggle="modal" style="text-decoration: none; color: rgba(88,124,173,0.54)">
            <span class="glyphicon glyphicon-th-large" id="iconHover"></span><spring:message code="label.generateReport"/>
        </a>
    </legend>

    <div class="progress" style="display: none;" id="progress">
        <div class="progress-bar" id="progress-bar" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100">
            <span class="sr-only">60% Complete</span>
        </div>
    </div>

    <div class="modal fade" id="reportModal" tabindex="-1" role="dialog" aria-labelledby="myReportModal" aria-hidden="true">
        <div class="modal-dialog modal-lg" role="document">
            <div class="modal-content">
                <div class="modal-body">
                    <div class="tab-container">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title" id="myReportModal" style="color: #d9230f;"><spring:message code="label.selectReport"/></h4>
                            &nbsp;&nbsp;

                            <form class="navbar-form">
                                <div class="form-group">

                                    <label><spring:message code="label.year"/>
                                        <select class="selectpicker" id="yearSelect">
                                            <c:forEach items="${yearList}" var="current">
                                                <option>${current}</option>
                                            </c:forEach>
                                        </select>

                                    </label>

                                </div>
                                &nbsp;&nbsp;
                                <div class="form-group">
                                    <label><spring:message code="label.month"/>
                                        <select class="selectpicker" id="monthSelect">
                                            <option>01</option>
                                            <option>02</option>
                                            <option>03</option>
                                            <option>04</option>
                                            <option>05</option>
                                            <option>06</option>
                                            <option>07</option>
                                            <option>08</option>
                                            <option>09</option>
                                            <option>10</option>
                                            <option>11</option>
                                            <option>12</option>
                                        </select>
                                    </label>
                                </div>
                            </form>

                        </div>
                        <table class="table table-striped" id='table'>

                            <th></th>
                            <TH><spring:message code="label.name"/></th>

                            <tr id="longReport">
                                <td>
                                    <div class=".col-xs-6 .col-sm-3">
                                        <input type="checkbox" name="optradio" class="check-box-table-cell">
                                    </div>
                                </td>
                                <td class="unDefaultTDStyle">Long Report</td>
                            </tr>
                            <tr id="longReportRA">
                                <td>
                                    <div class=".col-xs-6 .col-sm-3">
                                        <input type="checkbox" name="optradio" class="check-box-table-cell">
                                    </div>
                                </td>
                                <td class="unDefaultTDStyle">Long Report RA</td>
                            </tr>
                            <tr id="longReportRAUkrTel">
                                <td>
                                    <div class=".col-xs-6 .col-sm-3">
                                        <input type="checkbox" name="optradio" class="check-box-table-cell">
                                    </div>
                                </td>
                                <td class="unDefaultTDStyle">Long Report RA UkrTel</td>
                            </tr>
                            <tr id="longReportRAVega">
                                <td>
                                    <div class=".col-xs-6 .col-sm-3">
                                        <input type="checkbox" name="optradio" class="check-box-table-cell">
                                    </div>
                                </td>
                                <td class="unDefaultTDStyle">Long Report RA Vega</td>
                            </tr>
                            <tr id="longReportVega">
                                <td>
                                    <div class=".col-xs-6 .col-sm-3">
                                        <input type="checkbox" name="optradio" class="check-box-table-cell">
                                    </div>
                                </td>
                                <td class="unDefaultTDStyle">Long Report Vega</td>
                            </tr>
                            <tr id="shortReport">
                                <td>
                                    <div class=".col-xs-6 .col-sm-3">
                                        <input type="checkbox" name="optradio" class="check-box-table-cell">
                                    </div>
                                </td>
                                <td class="unDefaultTDStyle">Short Report</td>
                            </tr>
                            <tr id="shortReportRE">
                                <td>
                                    <div class=".col-xs-6 .col-sm-3">
                                        <input type="checkbox" name="optradio" class="check-box-table-cell">
                                    </div>
                                </td>
                                <td class="unDefaultTDStyle">Short Report RE</td>
                            </tr>
                            <tr id="shortReportREUkrTel">
                                <td>
                                    <div class=".col-xs-6 .col-sm-3">
                                        <input type="checkbox" name="optradio" class="check-box-table-cell">
                                    </div>
                                </td>
                                <td class="unDefaultTDStyle">Short Report RE UkrTel</td>
                            </tr>
                            <tr id="shortReportREVega">
                                <td>
                                    <div class=".col-xs-6 .col-sm-3">
                                        <input type="checkbox" name="optradio" class="check-box-table-cell">
                                    </div>
                                </td>
                                <td class="unDefaultTDStyle">Short Report RE Vega</td>
                            </tr>
                            <tr id="shortReportVega">
                                <td>
                                    <div class=".col-xs-6 .col-sm-3">
                                        <input type="checkbox" name="optradio" class="check-box-table-cell">
                                    </div>
                                </td>
                                <td class="unDefaultTDStyle">Short Report Vega</td>
                            </tr>
                            <tr id="localCallsCostReport">
                                <td>
                                    <div class=".col-xs-6 .col-sm-3">
                                        <input type="checkbox" name="optradio" class="check-box-table-cell">
                                    </div>
                                </td>
                                <td class="unDefaultTDStyle">Local Calls Cost Report</td>
                            </tr>
                            <tr id="localCallsDetailReport">
                                <td>
                                    <div class=".col-xs-6 .col-sm-3">
                                        <input type="checkbox" name="optradio" class="check-box-table-cell">
                                    </div>
                                </td>
                                <td class="unDefaultTDStyle">Local Calls Detail Report</td>
                            </tr>
                            <tr id="localCallsMainReport">
                                <td>
                                    <div class=".col-xs-6 .col-sm-3">
                                        <input type="checkbox" name="optradio" class="check-box-table-cell">
                                    </div>
                                </td>
                                <td class="unDefaultTDStyle">Local Calls Main Report</td>
                            </tr>

                        </table>
                        <button id="selectAllBtn" type="button" class="btn btn-info float-lt"><spring:message
                                code="label.selectAll"/></button>

                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal"><spring:message
                                    code="label.cancel"/></button>
                            <button type="button" class="btn btn-success" data-dismiss="modal" id="generateReport">
                                <spring:message code="label.submit"/>
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </div>

    <%--Form for uploading files--%>
    <form:form commandName="uploadFile" id="upload" method="post" enctype="multipart/form-data" class="form">
        <div class="form-group" id="idForm">
            <span class="file-input btn btn-info btn-file">
                <spring:message code="label.browse"/> <input type="file" id="exampleInputFile" name="file"/>
            </span>
            <ul id="list" class="list-group"></ul>
        </div>
        <div class="form-group">
            <label for="csvFiles"><spring:message code="label.csvfiles" />
                <select id="csvFiles">
                    <option value="vega"><spring:message code="csv.vega"/></option>
                    <option value="ukrnet"><spring:message code="csv.ukrnet"/></option>
                </select>
            </label>
        </div>
        <button type="button" value="upload" id="uploadFile" class="btn btn-toolbar" href="#myModal"
                data-toggle="modal"><spring:message code="label.upload"/>
        </button>

        <div class="modal" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div style="padding-top: 16%">
                <div class="loader"></div>
            </div>
        </div>

    </form:form>
    &nbsp;&nbsp;
    <div class="row">
        <div id="fileTree" class="col-md-4"></div>
    </div>

</div>
</body>
</html>
