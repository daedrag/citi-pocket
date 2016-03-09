package com.d3.duy.citipocket.core.filter;

import com.d3.duy.citipocket.model.MessageEnrichmentHolder;
import com.d3.duy.citipocket.model.enums.MessageType;

/**
 * Created by daoducduy0511 on 3/10/16.
 */
public class MessageFilterByType implements IMessageFilter {

    private MessageType type;
    public MessageFilterByType(MessageType type) {
        this.type = type;
    }

    @Override
    public boolean isQualified(MessageEnrichmentHolder enrichedMessage) {
        return enrichedMessage.getType() == type;
    }

    public MessageType getType() { return type; }
}
