<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="aqu.ccservice.*, java.util.List"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
	String myfile = (String) getServletContext().getInitParameter("creditcardfile");
%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Credit cards WS Application 
</title>
</head>
<body>
	<p>Input file: <%=myfile%><BR>
	
<table>
	
	<%
		CreditCardService service = new CreditCardService();
		List<CreditCard> listcc = service.readCsvFile(myfile);
		for (CreditCard currentCreditCard: listcc) {
	%>
	<%="<TR>"+currentCreditCard.toString()+"</TR>"%><BR>
	<%
		}
	%>
	</table>
</body>
</html>