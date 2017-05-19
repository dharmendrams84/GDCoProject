<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Loyality Edit</title>
<style type="text/css">
div.hr {
	color: #A9A9A9;
	background-color: #A9A9A9;
	position: relative;
	height: 5px;
	bottom: 0px;
}

.td {
	text-align: right;
	font-weight: bold;
}
</style>

</head>
<body>
	<!-- <div id="mainDiv"> -->
	<div id="upperDiv" style="position: relative;">
		<div id="firstUpperDiv" style="position: relative; top: 10px;">
			<b> LoyalityTransaction</b> <br> <br>
		</div>
		<div class="hr"></div>
		<div id="firstUpperDiv" style="position: relative; top: 5px;">
			<b>Enter Loyality ID or Loyality Email to search the Loyality
				Transaction </b>
		</div>
	</div>
	<div id="lowerDiv"
		style="position: relative; border: 1px solid; height: 150px; top: 20px;">
		<div id="loyalityTableDiv"
			style="position: relative; top: 10px; height: 80px; top: 20px;">
			<table>
				<tr>
					<td class="td"><B>Loyality ID:</B></td>
					<td><input type="text" name="loyalityId" id="loyalityId" /></td>
				</tr>
				<tr>
					<td class="td"><B>--OR--</B></td>

				</tr>
				<tr>
					<td class="td"><B>Loyality Email:</B></td>
					<td><input type="text" name="loyalityEmailId"
						id="loyalityEmailId" /></td>
				</tr>
			</table>
		</div>
	</div>


	<div
		style="position: relative; top: 25px; float: right; height: 30px; border: 1px solid; display: inline-block; width: auto;">
		<table style="position: relative;">
			<tr>
				<td><input type="button" value="Search"></td>
				<td><input type="button" value="Reset"></td>
				<td><input type="button" value="Add"></td>
			</tr>
		</table>
	</div>

</body>
</html>