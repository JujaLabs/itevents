<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                version="2.0"
                xmlns:url="org.itevents.util.mail.BuilderUrl"
                extension-element-prefixes="url"
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
                <url:buildUrl/>
                This link will work for 24 hours.
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>