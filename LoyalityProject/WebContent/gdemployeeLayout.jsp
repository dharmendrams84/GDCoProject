<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>

<tiles:useAttribute id="gdemployeeSearchTitle" name="gdemployeeSearchTitle" scope="request" classname="java.lang.String"  ignore="true" />
<tiles:useAttribute id="gdemployeeSearchAction" name="gdemployeeSearchAction" scope="request" classname="java.lang.String" ignore="true" />
<!-- insert Customer Boday here -->
<table width="100%" height="100%" cellspacing="0" cellpadding="0">
<tr><td>
<tiles:insert attribute="gdemployeeBody" ignore="true"/>
</td></tr>
</table>
<!-- End Customer Body Here -->