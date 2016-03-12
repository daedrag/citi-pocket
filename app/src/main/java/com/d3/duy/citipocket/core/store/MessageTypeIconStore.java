package com.d3.duy.citipocket.core.store;

import com.d3.duy.citipocket.R;
import com.d3.duy.citipocket.model.enums.MessageType;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by daoducduy0511 on 3/12/16.
 */
public class MessageTypeIconStore {
    private static final Map<MessageType, Integer> iconMap;
    static {
        Map<MessageType, Integer> aIconMap = new HashMap<>();
        aIconMap.put(MessageType.PAYMENT, R.drawable.ic_cart);
        aIconMap.put(MessageType.TRANSFER, R.drawable.ic_creditcard);
        aIconMap.put(MessageType.WITHDRAW, R.drawable.ic_wallet);
        aIconMap.put(MessageType.GIRO, R.drawable.ic_moneypig);
        aIconMap.put(MessageType.DEBIT, R.drawable.ic_bag);
        aIconMap.put(MessageType.REVERSAL, R.drawable.ic_reversal);
        aIconMap.put(MessageType.OTP, R.drawable.ic_chat);
        aIconMap.put(MessageType.UNKNOWN, R.drawable.ic_search);

        // handle other cases if any
        for (MessageType type: MessageType.values()) {
            if (!aIconMap.containsKey(type))
                aIconMap.put(type, R.drawable.ic_search);
        }

        iconMap = Collections.unmodifiableMap(aIconMap);
    }

    public static Map<MessageType, Integer> getMessageTypeIconMap() {
        return iconMap;
    }
}
