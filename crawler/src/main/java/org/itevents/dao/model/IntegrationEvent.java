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
    public int hashCode() {
        int result = integrationName != null ? integrationName.hashCode() : 0;
        result = 31 * result + (integrationEventUrl != null ? integrationEventUrl.hashCode() : 0);
        result = 31 * result + (event != null ? event.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final IntegrationEvent that = (IntegrationEvent) o;

        if (integrationName != null ? !integrationName.equals(that.integrationName) : that.integrationName != null)
            return false;
        if (integrationEventUrl != null ? !integrationEventUrl.equals(that.integrationEventUrl) : that.integrationEventUrl != null)
            return false;
        return event != null ? event.equals(that.event) : that.event == null;

    }
}
