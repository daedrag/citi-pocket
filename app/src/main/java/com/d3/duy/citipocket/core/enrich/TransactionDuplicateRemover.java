package com.d3.duy.citipocket.core.enrich;

import com.d3.duy.citipocket.model.MessageEnrichmentHolder;
import com.d3.duy.citipocket.model.enums.MessageType;

/**
 * Created by daoducduy0511 on 3/12/16.
 */
public class TransactionDuplicateRemover {

    /**
     * Temporary logic to remove duplicate messages
     * currently, all "transfer" type is considered duplicated
     *
     * @param enrichmentHolder
     * @return true if it is duplicated
     */
    public static boolean isDuplicate(MessageEnrichmentHolder enrichmentHolder) {
        return enrichmentHolder.getType() == MessageType.TRANSFER;
    }

}
