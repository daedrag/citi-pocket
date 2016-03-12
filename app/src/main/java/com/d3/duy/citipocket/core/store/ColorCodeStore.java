package com.d3.duy.citipocket.core.store;

import com.d3.duy.citipocket.R;
import com.d3.duy.citipocket.model.enums.MessageType;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by daoducduy0511 on 3/12/16.
 */
public class ColorCodeStore {

    private static final Map<MessageType, Integer> colorMap;
    static {
        Map<MessageType, Integer> aColorMap = new HashMap<>();
        aColorMap.put(MessageType.PAYMENT, R.color.colorTypePayment);
        aColorMap.put(MessageType.TRANSFER, R.color.colorTypeTransfer);
        aColorMap.put(MessageType.WITHDRAW, R.color.colorTypeWithdrawal);
        aColorMap.put(MessageType.GIRO, R.color.colorTypeGiro);
        aColorMap.put(MessageType.DEBIT, R.color.colorTypeDebit);
        aColorMap.put(MessageType.REVERSAL, R.color.colorTypeReversal);
        aColorMap.put(MessageType.OTP, R.color.colorTypeOTP);
        aColorMap.put(MessageType.UNKNOWN, R.color.colorTypeUnknown);

        // handle other cases if any
        for (MessageType type: MessageType.values()) {
            if (!aColorMap.containsKey(type))
                aColorMap.put(type, R.color.colorTypeUnknown);
        }

        colorMap = Collections.unmodifiableMap(aColorMap);
    }

    public static Map<MessageType, Integer> getColorCodeMap() {
        return colorMap;
    }
}
