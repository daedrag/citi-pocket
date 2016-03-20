package com.d3.duy.citipocket.core.aggregate;

import com.d3.duy.citipocket.core.enrich.CurrencyEnrichment;
import com.d3.duy.citipocket.model.CurrencyAmount;
import com.d3.duy.citipocket.model.MessageEnrichmentHolder;

import java.util.List;

/**
 * Created by daoducduy0511 on 3/10/16.
 */
public class AmountAggregator {

    public static CurrencyAmount aggregateMessages(List<MessageEnrichmentHolder> messages) {
        double totalAmount = 0;
        String baseCcy = CurrencyAmount.BASE_CURRENCY_CODE; // SGD

        for (MessageEnrichmentHolder message : messages) {
            if (baseCcy.equals(message.getAmount().getCurrencyCode())) {
                totalAmount += message.getAmount().getAmount();
            } else {
                totalAmount += CurrencyEnrichment.convertToSgd(message.getAmount()).getAmount();
            }
        }

        return new CurrencyAmount(baseCcy, totalAmount);
    }

    public static CurrencyAmount aggregateAmounts(List<CurrencyAmount> amounts) {
        double totalAmount = 0;
        String baseCcy = CurrencyAmount.BASE_CURRENCY_CODE; // SGD

        for (CurrencyAmount amount : amounts) {
            if (baseCcy.equals(amount.getCurrencyCode())) {
                totalAmount += amount.getAmount();
            } else {
                totalAmount += CurrencyEnrichment.convertToSgd(amount).getAmount();
            }
        }

        return new CurrencyAmount(baseCcy, totalAmount);
    }
}
