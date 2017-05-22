<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
 <%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Select Employee To Edit</title>
<style type="text/css">
.pageheadercat {
	color: #3a5a87;
	font-size: 9pt;
	font-family: Arial, Helvetica;
	font-weight: bold;
}

.tableoutline {
	border: #99bedc;
	border-style: solid;
	border-top-width: thin;
	border-right-width: thin;
	border-bottom-width: thin;
	border-left-width: thin;
	position: relative;
	height: 100%;
	width: 100%;
}

.smallprompt {
	font-family: Tahoma, Arial, Helvetica, sans-serif;
	font-size: 9pt;
	font-weight: bold;
	color: #3c3c3c;
	text-decoration: none;
	text-transform: none;
}


.heading2 {
    color: #3a5a87;
    font-size: 12pt;
    font-family: Arial, Helvetica;
    font-weight: bold;
}

.normal {
    font-family: Tahoma, Arial, Helvetica, sans-serif;
    font-size: 9pt;
    color: #3c3c3c;
}

.fieldname {
    font-family: Tahoma, Arial, Helvetica, sans-serif;
    font-size: 9pt;
    font-weight: bold;
}

.tableheadrow {
    background-color: #cfe0f1;
    font-family: Tahoma;
    font-size: 9pt;
    font-weight: bold;
    color: #3c3c3c;
    text-align: center;
}
</style>
</head>
<body>
<form action="">
		<table width="100%" border="0" cellspacing="3" cellpadding="3">
			<tr>
				<td class="pageheadercat">Employee Search Results</td>
			</tr>
			<tr bgcolor="#CCCCCC">
				<td colspan="2"></td>
			</tr>
			<tr>
        <td class="smallprompt">Select a Employee ID to continue</td>
      
    </tr>
    
    
    
    <tr>
        <td colspan="2">
            <table width="100%" class="tableoutline">
                <tbody><tr>
                    <td>
                        
                    </td>
                </tr>
                <tr>
                    <td>
                        <table width="93%" border="0" cellspacing="1" cellpadding="3">
                            <tbody><tr>
                                <td colspan="2" align="left" class="heading2">Search criteria</td>
                            </tr>
                            <tr>
                                <td colspan="2" bgcolor="cccccc"></td>
                            </tr>
                           
                            <tr>
                                <td colspan="2" align="left" class="heading2"><br>
                                    Search results
                                </td>
                            </tr>
                        </tbody></table>
                    </td>
                </tr>
                <tr>
                    <td align="center">
                        <table width="95%" border="1" frame="HSIDES" rules="ROWS" bordercolorlight="WHITE" bordercolor="#CCCCCC" cellspacing="1">
                            <tbody><tr>
      

                               
                                <td width="15%" class="tableheadrow">Last Name</td>

                                <td width="15%" class="tableheadrow">First Name</td>
                                <td width="15%" class="tableheadrow">Position Code</td>
                                <td width="15%" class="tableheadrow">Employee Number</td>
                                <td width="15%" class="tableheadrow">Employee Status</td>
                                
                            </tr>
											<logic:iterate id="employee" name="employees">
												<tr>


													<td class="normal" align="center">
													<bean:write name="employee" property="firstName"/>
													</td>
													<td class="normal" align="center">
													<bean:write name="employee" property="lastName"/>
													</td>													
													<td class="normal" align="center">
													<bean:write name="employee" property="positionCode"/>
													</td>
													<td class="normal" align="center">
													<bean:write name="employee" property="emplNumber"/>
													</td>
													<td class="normal" align="center">
													<bean:write name="employee" property="emplStatus"/>
													</td>													
												</tr>

											</logic:iterate>

										</tbody></table>
                    </td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                </tr>
            </tbody></table>
        </td>
    </tr>
    
    
		</table>

	</form>
</body>
</html>