<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:template match="/">
        <html>
            <body>
                <h2>Events list</h2>
                <xsl:for-each select="events/event">
                    <table border="1" width="100%" style="margin-bottom:20px;">
                        <tr>
                            <td>
                                <xsl:value-of select="eventDate"/> - <xsl:value-of select="address"/> - <xsl:value-of select="contact"/>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <a>
                                    <xsl:attribute name='href'><xsl:value-of select="regLink"/></xsl:attribute>
                                    <xsl:value-of select="title"/></a>
                            </td>
                        </tr>
                    </table>

                </xsl:for-each>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>