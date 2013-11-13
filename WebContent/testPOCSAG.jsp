<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
	function changeRic(RIC) {
		document.send_form.action = "rest/pocsag/send/" + RIC;
		
	};

</script>
</head>
<body>



	<form action="rest/pocsag/send/" name="send_form" method="post">
		<p>
			RIC: <input type="text" name="RIC" />
		</p>		
		<p>
			Frequency: <input type="text" name="frequency" />
		</p>
		<p>
			Bauds: <select name="bauds">
				<option value="512">512</option>
				<option value="1200">1200</option>
				<option value="2400" selected>2400</option>
			</select>
		</p>
		<p>
			Type: <select name="msgType">
				<option value="TONE">Tone</option>
				<option value="NUMERIC">Numeric</option>
				<option value="ALPHA" selected>Alpha</option>
			</select>
		</p>
		<p>
			Message: <input type="text" name="message" />
		</p>
		<br /> <input type="submit" value="Send" onclick="changeRic(document.send_form.RIC.value)">

	</form>
</body>
</html>