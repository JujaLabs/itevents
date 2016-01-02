<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                version="2.0"
                xmlns:redirect="http://xml.apache.org/xalan/redirect"
                extension-element-prefixes="redirect"
                xmlns:xalan="http://xml.apache.org/xslt">
    <xsl:output method="xml" indent="yes" xalan:indent-amount="4" omit-xml-declaration="yes"/>

    <xsl:template match="/">
        <html>
            <head>
                <title>Sending mail with OTP!</title>
                <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
                <meta name="robots" content="noindex, nofollow"/>
            </head>
            <body>
                Dear subscriber!
                You need to activate you account.
                To do so, you need to follow this link:
                http://localhost:8080/users/activate/68eeea0d-f89d-4634-aa52-67da452eadb0
                This link will work for 24 hours.
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>