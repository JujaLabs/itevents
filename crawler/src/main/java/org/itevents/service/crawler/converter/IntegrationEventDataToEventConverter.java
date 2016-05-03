package org.itevents.service.crawler.converter;

import javax.inject.Inject;
import org.itevents.dao.model.Currency;
import org.itevents.dao.model.Event;
import org.itevents.dao.model.Location;
import org.itevents.dao.model.builder.EventBuilder;
import org.itevents.service.CurrencyService;
import org.itevents.service.crawler.interfaces.IntegrationEventData;
import org.itevents.service.exception.ServiceException;
import org.itevents.util.time.DateTimeUtil;
import org.springframework.stereotype.Component;

/**
 * Created by vaa25 on 03.05.2016.
 */
@Component
@SuppressWarnings("PMD")
public class IntegrationEventDataToEventConverter {
    @Inject
    private CurrencyService currencyService;
    private IntegrationEventData data;
    private EventBuilder builder;

    public Event convert(final IntegrationEventData data) {
        init(data);
        setAddress();
        setTitle();
        setCreateDate();
        setEventDate();
        setRegistrationLink();
        setLocation();
        setContact();
        setPrice();
        setTechnologies();
        setDescription();
        return builder.build();
    }

    private void init(final IntegrationEventData data) {
        this.data = data;
        builder = EventBuilder.anEvent();
    }

    private void setDescription() {
        builder.description(data.getDescription());
    }

    private void setTechnologies() {
        // TODO(vaa25): 03.05.2016 add Technologies extractor
    }

    private void setPrice() {
        final String priceData = data.getPrice();
        if (priceData != null) {
            builder.price(extractPriceValue(priceData));
            builder.currency(extractPriceCurrency(priceData));
        }
    }

    private Currency extractPriceCurrency(final String priceData) {
        // TODO(vaa25): 03.05.2016 add Currency extractor
        return currencyService.getCurrency(-1);
    }

    private Integer extractPriceValue(final String priceData) {
        return Integer.parseInt(priceData.split("\\D")[0]);
    }

    private void setContact() {
        // TODO(vaa25): 03.05.2016 add contact in IntegrationEventData
    }

    private void setLocation() {
        // TODO(vaa25): 03.05.2016 implement in #206 geocoding
        builder.location(new Location(46.4772772, 30.7392599));
    }

    private void setRegistrationLink() {
        builder.regLink(data.getRegistrationLink());
    }

    private void setEventDate() {
        // TODO(vaa25): 03.05.2016 implement after #172 merges
        try {
            builder.eventDate(DateTimeUtil.yyyyMMddStringToDate("2017.07.17"));
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    private void setCreateDate() {
        // TODO(vaa25): 03.05.2016 implement after #172 merges
        builder.createDate(DateTimeUtil.getNowDate());
    }

    private void setTitle() {
        builder.title(data.getTitle());
    }

    private void setAddress() {
        builder.address(data.getAddress());
    }
}
