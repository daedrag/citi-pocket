package com.d3.duy.citipocket.core.group;

import com.d3.duy.citipocket.model.MessageEnrichmentHolder;
import com.d3.duy.citipocket.model.enums.MessageType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by daoducduy0511 on 3/10/16.
 */
public class MessageGroupByType implements IMessageGroup<MessageType> {

    private List<MessageType> types;
    private List<MessageEnrichmentHolder> messages;
    public MessageGroupByType(List<MessageEnrichmentHolder> messages) {
        this.types = Arrays.asList(MessageType.values());
        this.messages = messages;
    }

    @Override
    public List<MessageType> getKeys() {
        return types;
    }

    @Override
    public List<MessageEnrichmentHolder> getValues() {
        return messages;
    }

    @Override
    public Map<MessageType, List<MessageEnrichmentHolder>> groupBy() {
        Map<MessageType, List<MessageEnrichmentHolder>> mapping = new HashMap<>();
        for (MessageType type: types) {
            mapping.put(type, new ArrayList<MessageEnrichmentHolder>());
        }

        for (MessageEnrichmentHolder message: messages) {
            mapping.get(message.getType()).add(message);
        }
        return mapping;
    }
}
