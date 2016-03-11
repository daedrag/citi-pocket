package com.d3.duy.citipocket.core.aggregate;

import com.d3.duy.citipocket.core.enrich.CurrencyEnrichment;
import com.d3.duy.citipocket.model.CurrencyAmount;
import com.d3.duy.citipocket.model.MessageEnrichmentHolder;

import java.util.List;

/**
 * Created by daoducduy0511 on 3/10/16.
 */
public class AmountAggregator {

    public static CurrencyAmount aggregate(List<MessageEnrichmentHolder> messages) {
        double totalAmount = 0;
        String baseCcy = "SGD";

        for (MessageEnrichmentHolder message : messages) {
            if (baseCcy.equals(message.getAmount().getCurrencyCode())) {
                totalAmount += message.getAmount().getAmount();
            } else {
                totalAmount += CurrencyEnrichment.convertToSgd(message.getAmount()).getAmount();
            }
        }

        return new CurrencyAmount(baseCcy, totalAmount);
    }
}
