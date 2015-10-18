<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<html>
<body>
<script type="text/javascript">
	var url = location.protocol.concat("//", location.host, '/', location.pathname.split('/')[1], "/static/doc/index.html");
	window.location.href = url
</script>
	<span style="float: right">
		<button type="button"><a href="?lang=en"><spring:message code="indexPage.language_en" /></a></button>
    	<button type="button"><a href="?lang=uk"><spring:message code="indexPage.language_uk" /></a></button>
    	<button type="button"><a href="?lang=ru"><spring:message code="indexPage.language_ru" /></a></button>
	</span>
	<h1>
		<spring:message code="indexPage.message1" />
	</h1>
    <h2>
		<spring:message code="indexPage.message2" />
	</h2>
</body>
</html>