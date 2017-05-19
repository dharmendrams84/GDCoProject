<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Loyality Edit</title>
<style type="text/css">
.pageheadercat {
	color: #3a5a87;
	font-size: 9pt;
	font-family: Arial, Helvetica;
	font-weight: bold;
}

.smallprompt {
	font-family: Tahoma, Arial, Helvetica, sans-serif;
	font-size: 9pt;
	font-weight: bold;
	color: #3c3c3c;
	text-decoration: none;
	text-transform: none;
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

.data {
    font-family: Tahoma, Arial, Helvetica, sans-serif;
    font-size: 9pt;
    font-weight: normal;
}

.fieldname {
    font-family: Tahoma, Arial, Helvetica, sans-serif;
    font-size: 9pt;
    font-weight: bold;
}
</style>

</head>
<body>
	<table width="100%" cellspacing="3" cellpadding="2" border="0">

		<tr>
			<td><span class="pageheadercat">Loyality Transaction </span></td>
		</tr>
		<tr bgcolor="#cccccc">
			<td colspan="2"></td>
		</tr>

		<tbody>
			<tr>
				<td class="smallprompt">Enter Loyality ID or Loyality Email to
					search the Loyality Transaction</td>
			</tr>
		</tbody>
	</table>
	<table width="100%" class="tableoutline">
		<tbody>
			<tr style="height: 20px;">
				<td></td>
			</tr>
			<tr>
				<td width="15%" height="20" class="fieldname"  align="right">Loyality ID:</td>
				<td width="70%" height="20" colspan="3" align="left"><input
					type="text" name="loyalityId" id="loyalityId" maxlength="30"
					size="40" value="" class="data"> *</td>
			</tr>
			<tr>
				<td width="15%" height="20" class="fieldname"  align="right">--OR--</td>
				
			</tr>
			<tr>
				<td width="15%" height="20" class="fieldname"  align="right">Loyality Email:</td>
				<td width="70%" height="20" colspan="3" align="left"><input
					type="text" name="loyalityEmailId" id="loyalityEmailId" maxlength="30"
					size="40" value="" class="data"> *</td>
			</tr>
			<tr style="height: 20px;">
				<td></td>
			</tr>
		</tbody>
	</table>

<table width="13%" cellspacing="3" cellpadding="3" border="0" align="right">
  <tbody><tr>
    <td class="smallprompt">
    </td><td valign="bottom">
        <div align="right">
            <table cellspacing="3" cellpadding="3" align="right" class="tableoutline">
            <tbody><tr>
                <td nowrap="">
                    <div align="right">
                        <input type="submit" name="submitValue" value="Search">
                        <input type="button" name="clear" value="Clear Search" onclick="resetSearch()">
                       </div>
                </td>
            </tr>
            </tbody></table>
        </div>
    </td>
  </tr>
</tbody></table>
</body>
</html>