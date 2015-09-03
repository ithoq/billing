<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<?xml version="1.0" encoding="ISO-8859-1" ?>

<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

  <title>Device</title>


  <jsp:include page="/WEB-INF/jsp/include/css_js_incl.jsp"/>


</head>
<body>
<jsp:include page="/WEB-INF/jsp/include/nav_header.jsp"/>


<div class="well">
  <form:form commandName="uploadFile" id="upload" method="post" enctype="multipart/form-data" class="form">
    <div class="form-group">
      <label for="exampleInputFile"><h3>Select file to upload</h3></label>
      <input type="file" id="exampleInputFile" multiple >
      <ul id="list" class="list-group"></ul>

      <p class="help-block">Example block-level help text here.</p>
    </div>
    <button type="submit" value="upload" class="btn btn-default">Upload File</button>
  </form:form>
</div>


<script type="text/javascript">



  $('#exampleInputFile').change(function handleFileSelect(evt) {
    var files = evt.target.files;
    var output = [];
    for (var i = 0, f; f = files[i]; i++) {
      var sub = (f.name).substring(0,20);
      $('#list').append('<li class="list-group-item"><strong>'+sub+'...</strong> <b>File type:</b> '+ (f.type || 'n/a')+ ' - ' +
              f.size+' bytes, last modified: '+
              (f.lastModifiedDate ? f.lastModifiedDate.toLocaleDateString() : 'n/a')+
              '</li>');
    }
  })


</script>


</body>
</html>