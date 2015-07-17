<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<html>
<body>
	<span style="float: right">
    	<a href="?lang=en"><spring:message code="indexPage.language_en" /></a>
    	|
    	<a href="?lang=uk"><spring:message code="indexPage.language_uk" /></a>
		|
    	<a href="?lang=ru"><spring:message code="indexPage.language_ru" /></a>
	</span>
	<h1>
		<spring:message code="indexPage.message1" />
	</h1>
    <p/>
	<h2>
		<spring:message code="indexPage.message2" />
	</h2>
</body>
</html>