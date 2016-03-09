package com.d3.duy.citipocket.core.filter;

import com.d3.duy.citipocket.model.MessageEnrichmentHolder;

/**
 * Created by daoducduy0511 on 3/9/16.
 */
public interface IMessageFilter {
    boolean isQualified(MessageEnrichmentHolder enrichedMessage);
}
