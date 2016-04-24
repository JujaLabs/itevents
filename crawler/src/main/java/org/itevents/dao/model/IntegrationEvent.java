package org.itevents.dao.model;

/**
 * Created by vaa25 on 22.04.2016.
 */
public class IntegrationEvent {
    private int id;
    private String integrationName;
    private String integrationEventUrl;
    private Event event;

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getIntegrationName() {
        return integrationName;
    }

    public void setIntegrationName(final String integrationName) {
        this.integrationName = integrationName;
    }

    public String getIntegrationEventUrl() {
        return integrationEventUrl;
    }

    public void setIntegrationEventUrl(final String integrationEventUrl) {
        this.integrationEventUrl = integrationEventUrl;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(final Event event) {
        this.event = event;
    }

    @Override
    @SuppressWarnings("PMD")
    public int hashCode() {
        int result = integrationName.hashCode();
        result = 31 * result + integrationEventUrl.hashCode();
        result = 31 * result + event.hashCode();
        return result;
    }

    @Override
    @SuppressWarnings("PMD")
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final IntegrationEvent that = (IntegrationEvent) o;

        if (!integrationName.equals(that.integrationName)) {
            return false;
        }
        if (!integrationEventUrl.equals(that.integrationEventUrl)) {
            return false;
        }
        return event.equals(that.event);

    }
}
