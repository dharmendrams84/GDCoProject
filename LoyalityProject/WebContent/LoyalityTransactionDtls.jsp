<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Loyality Transaction Details</title>
<style type="text/css">
div.hr {
	color: #A9A9A9;
	background-color: #A9A9A9;
	position: relative;
	height: 5px;
}

.td {
	text-align: right;
	font-weight: bold;
}
</style>
</head>
<body>
	<div id="mainDiv" style="width: 100%;">
		<div id="headerDiv"
			style="position: relative; width: 100%; bottom: 5px;">
			<B>Loyality Transaction Details</B>
			<div class="hr"></div>
			<div id="lowerHeaderDiv"
				style="position: relative; width: auto;">
				<table style="width: 100%;">
					<tr>
						<td><b>Update Loyality ID or Loyality Email and press
								Save</b></td>
						<td style="float: right;border: 1px solid"><input type="button" value="Save" />
						</td>
					</tr>
				</table>

			</div>
		</div>
		<div id="loyalityDtlsDiv"
			style="position: relative; width: 100%; height: 300px; border: 1px solid; top: 10px;">
			<div id="loyalityDtlsInnerDiv"
				style="position: relative; width: auto; top: 20%; left: 80px; float: left;">
				<table>
					<tr>
						<td class="td">Loyality ID:</td>
						<td><input type="text" />*</td>
					</tr>
					<tr>
						<td class="td">Loyality Email:</td>
						<td><input type="text" /></td>
					</tr>
					<tr>
						<td class="td">Transaction ID:</td>
					</tr>
					<tr>
						<td class="td">Store ID:</td>
					</tr>
					<tr>
						<td class="td">Register ID:</td>
					</tr>
					<tr>
						<td class="td">Business Date:</td>
					</tr>
					<tr>
						<td class="td">Channel Sales:</td>
					</tr>

				</table>
			</div>
		</div>
	</div>
</body>
</html>