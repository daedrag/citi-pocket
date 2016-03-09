package com.d3.duy.citipocket.core.group;

import com.d3.duy.citipocket.model.MessageEnrichmentHolder;

import java.util.List;
import java.util.Map;

/**
 * Created by daoducduy0511 on 3/9/16.
 */
public interface IMessageGroup<T> {

    List<T> getKeys();
    List<MessageEnrichmentHolder> getValues();
    Map<T, List<MessageEnrichmentHolder>> groupBy();

}
