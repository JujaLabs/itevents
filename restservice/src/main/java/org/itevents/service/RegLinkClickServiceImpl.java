package org.itevents.service;

import org.itevents.mapper.RegLinkClickMapper;

/**
 * @author Alexander Vlasov
 */
public class RegLinkClickServiceImpl implements RegLinkClickService {

    private RegLinkClickMapper regLinkClickMapper;

    public RegLinkClickMapper getRegLinkClickMapper() {
        return regLinkClickMapper;
    }

    public void setRegLinkClickMapper(RegLinkClickMapper regLinkClickMapper) {
        this.regLinkClickMapper = regLinkClickMapper;
    }

    @Override
    public void addClick(int eventId, int userId) {
        regLinkClickMapper.addClick(eventId, userId);
    }
}
