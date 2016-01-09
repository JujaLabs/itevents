<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                version="2.0"
                xmlns:redirect="http://xml.apache.org/xalan/redirect"
                extension-element-prefixes="redirect"
                xmlns:xalan="http://xml.apache.org/xslt">
    <xsl:output method="xml" indent="yes" xalan:indent-amount="4" omit-xml-declaration="yes"/>

    <xsl:template match="/">
        <html>
            <head>
                <title>Future events!</title>
                <meta content="text/html; charset=utf-8" http-equiv="Content-Type"/>
                <meta content="noindex, nofollow" name="robots"/>
            </head>
            <body>
                <table bgcolor="#f2f2f2" border="0" width="100%" cellspacing="0" cellpadding="0">
                    <tr>
                        <td align="center" valign="top">
                            <table bgcolor="#ffffff" style="border: 0 solid #cccccc;" border="0" width="560" cellpadding="0" cellspacing="0">
                                <tr>
                                    <td width="560" align="center">
                                        <table cellpadding="0" width="560" cellspacing="0" border="0">
                                            <tr>
                                                <td width="560" align="center">
                                                    <img alt="Our logo" border="0" width="530" src="" height="100"/>
                                                </td>
                                            </tr>
                                        </table>
                                        <table cellpadding="0" cellspacing="0" border="0" width="560">
                                            <tr>
                                                <td height="20" width="560"/>
                                            </tr>
                                        </table>
                                        <table cellpadding="0" cellspacing="0" border="0" width="520">
                                            <tr>
                                                <td style="color:#000000;font-family:Arial, Helvetica, sans-serif;line-height:17px;font-size:11px;" valign="top" align="left">
                                                    <p style="margin:0 0 10px;">
                                                        <h3 style="margin:0;">Java</h3>
                                                        <span style="font-style: italic;">10.07.2115</span>
                                                        <br/>
                                                        <span>Beresteyska - java@gmail.com</span>
                                                        <br/>
                                                        <a href="http://www.java.com.ua">Register to Java</a>
                                                    </p>
                                                </td>
                                            </tr>
                                        </table>
                                        <table cellpadding="0" cellspacing="0" border="0" width="520">
                                            <tr>
                                                <td height="10" width="520"/>
                                            </tr>
                                        </table>
                                        <table cellpadding="0" cellspacing="0" border="0" width="520">
                                            <tr>
                                                <td style="color:#000000;font-family:Arial, Helvetica, sans-serif;line-height:17px;font-size:11px;" valign="top" align="left">
                                                    <p style="margin:0 0 10px;">
                                                        <h3 style="margin:0;">Ruby</h3>
                                                        <span style="font-style: italic;">20.07.2115</span>
                                                        <br/>
                                                        <span>Shulyavska - ruby@gmail.com</span>
                                                        <br/>
                                                        <a href="http://www.ruby.com.ua">Register to Ruby</a>
                                                    </p>
                                                </td>
                                            </tr>
                                        </table>
                                        <table cellpadding="0" cellspacing="0" border="0" width="520">
                                            <tr>
                                                <td height="10" width="520"/>
                                            </tr>
                                        </table>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                </table>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>