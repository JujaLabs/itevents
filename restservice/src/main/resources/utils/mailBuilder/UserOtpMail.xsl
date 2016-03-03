<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                version="2.0"
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
                To do this, you need to follow this link:
                <!-- @TODO: add OTP and link to this template, issue 156
                https://github.com/JuniorsJava/itevents/issues/156 -->

                This link will work for 24 hours.
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>