package org.itevents.service.crawler.integration;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;

/**
 * Created by vaa25 on 07.04.2016.
 */
public class ResponseWrapper {
    private final HttpResponse response;
    private final StatusLine statusLine;

    public ResponseWrapper(final HttpResponse response) {
        this.response = response;
        this.statusLine = response.getStatusLine();
    }

    public boolean isOk() {
        return this.statusLine.getStatusCode() == HttpStatus.SC_OK;
    }

    public String getStatusString() {
        return this.statusLine.toString();
    }

    public HttpEntity getEntity() {
        return this.response.getEntity();
    }
}
