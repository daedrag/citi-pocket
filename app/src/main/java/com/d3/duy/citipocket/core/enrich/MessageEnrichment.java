package com.d3.duy.citipocket.core.enrich;

import com.d3.duy.citipocket.model.CurrencyAmount;
import com.d3.duy.citipocket.model.MessageEnrichmentHolder;
import com.d3.duy.citipocket.model.MessageHolder;
import com.d3.duy.citipocket.model.enums.MessageType;

import java.text.SimpleDateFormat;

/**
 * Created by daoducduy0511 on 27/2/16.
 */
public class MessageEnrichment {

    public static final String TAG = MessageEnrichment.class.getSimpleName();
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yy");

    /**
     * Filters
     */
//    public static final String FILTER_CURRENCY_AMOUNT = "";
    private static final String FILTER_CARD_PAYMENT = "There was a charge of (.*)";
    private static final String FILTER_CASH_WITHDRAWAL = "There was a cash withdrawal of (.*)";
    private static final String FILTER_TRANSFER_REQUEST = "We have received your request on (.*)";
    private static final String FILTER_GIRO_CREDIT = "There was a credit of (.*)";
    private static final String FILTER_OTP = "Your Citibank OTP code (.*)";
    private static final String FILTER_REVERSAL = "There was a reversal of (.*)";
    private static final String FILTER_DEBIT = "Account no. (.*) was debited (.*)";

    /**
     * Splitters
     */
    public static final String SPLITTER_CARD_INFO = "Citibank | Card ending | no. ending ";
    public static final String SPLITTER_CARD_PAYMENT = "There was a charge of | on your | by | on ";
    public static final String SPLITTER_REVERSAL = "There was a reversal of | on your | by | on ";
    public static final String SPLITTER_CASH_WITHDRAWAL = "There was a cash withdrawal of | on your | on | via";
    public static final String SPLITTER_TRANSFER_REQUEST = "We have received your request on | to transfer | to |. To amend your alerts, login to";
    public static final String SPLITTER_GIRO_CREDIT = "There was a credit of | to your account no. | on | via |. For details, login to";
    public static final String SPLITTER_DEBIT = "Account no. | was debited by |. To amend your alerts";

    public static MessageEnrichmentHolder classify(MessageHolder sms) {
        String body = sms.getBody();
        String date = DATE_FORMAT.format(sms.getTimestamp());

        if (body.matches(FILTER_CARD_PAYMENT)) {
            return parseCashPayment(body, date);

        } else if (body.matches(FILTER_CASH_WITHDRAWAL)) {
            return parseCashWithdrawal(body, date);

        } else if (body.matches(FILTER_GIRO_CREDIT)) {
            return parseGiroCredit(body, date);

        } else if (body.matches(FILTER_TRANSFER_REQUEST)) {
            return parseTransferRequest(body, date);

        } else if (body.matches(FILTER_OTP)) {
            return parseOTP(body, date);

        } else if (body.matches(FILTER_REVERSAL)) {
            return parseReversal(body, date);

        } else if (body.matches(FILTER_DEBIT)) {
            return parseDebit(body, date);

        } else {
            // Unknown type by default
            // pass timestamp to enhance as no date in message body
            return new MessageEnrichmentHolder(body, MessageType.UNKNOWN, date);

        }
    }

    private static String parseCardInfo(String fullCardInfo) {
        String[] tokens = fullCardInfo.split(SPLITTER_CARD_INFO);
        int len = tokens.length;

        if ((len >= 2 && !tokens[0].isEmpty()) ||
                (len >= 3 && tokens[0].isEmpty())) {
            int start = 0;
            if (tokens[0].isEmpty()) start = 1;
            StringBuilder builder = new StringBuilder();

            for (int i = start; i < len - 1; i++) {
                builder.append(tokens[i]);
                builder.append(" ");
            }
            builder.append(tokens[len - 1]);

            return builder.toString();
        } else {
            return fullCardInfo;
        }
    }

    private static CurrencyAmount parseAmount(String amountStr) {
        return CurrencyEnrichment.fromCurrencyAmountString(amountStr);
    }

    private static MessageEnrichmentHolder parseCashPayment(String messageBody, String date) {
        String cardInfo, originator;
        CurrencyAmount amount;

        String[] tokens = messageBody.split(SPLITTER_CARD_PAYMENT);
        if (tokens.length != 5) {
            return new MessageEnrichmentHolder(messageBody, MessageType.PAYMENT, date);
        } else {
            amount = parseAmount(tokens[1]).negate();   // payment so negative value
            cardInfo = parseCardInfo(tokens[2]);
            originator = tokens[3];
//            date = tokens[4];

            // remove last dot
//            if (date.contains(".")) {
//                date = date.substring(0, date.length() - 1);
//            }

            return new MessageEnrichmentHolder(messageBody, MessageType.PAYMENT, cardInfo, amount, originator, date);
        }
    }

    private static MessageEnrichmentHolder parseCashWithdrawal(String messageBody, String date) {
        String cardInfo, originator;
        CurrencyAmount amount;

        String[] tokens = messageBody.split(SPLITTER_CASH_WITHDRAWAL);
        if (tokens.length != 5) {
            return new MessageEnrichmentHolder(messageBody, MessageType.WITHDRAW, date);
        } else {
            amount = parseAmount(tokens[1]).negate();
            cardInfo = parseCardInfo(tokens[2]);
            originator = tokens[4];
//            date = tokens[3];

            // remove last dot
//            if (originator.contains(".")) {
//                originator = originator.substring(0, originator.length() - 1);
//            }

            return new MessageEnrichmentHolder(messageBody, MessageType.WITHDRAW, cardInfo, amount, originator, date);
        }
    }

    private static MessageEnrichmentHolder parseGiroCredit(String messageBody, String date) {
        String cardInfo, originator;
        CurrencyAmount amount;

        String[] tokens = messageBody.split(SPLITTER_GIRO_CREDIT);
        if (tokens.length != 6) {
            return new MessageEnrichmentHolder(messageBody, MessageType.GIRO, date);
        } else {
            cardInfo = tokens[2];
            amount = parseAmount(tokens[1]);
            originator = tokens[4];
//            date = tokens[3];

            return new MessageEnrichmentHolder(messageBody, MessageType.GIRO, cardInfo, amount, originator, date);
        }
    }

    private static MessageEnrichmentHolder parseTransferRequest(String messageBody, String date) {
        String cardInfo, originator;
        CurrencyAmount amount;

        String[] tokens = messageBody.split(SPLITTER_TRANSFER_REQUEST);
        if (tokens.length != 5) {
            return new MessageEnrichmentHolder(messageBody, MessageType.TRANSFER, date);
        } else {
            amount = parseAmount(tokens[2]).negate();
            cardInfo = "Main account";
            originator = tokens[3];
//            date = tokens[1];

            return new MessageEnrichmentHolder(messageBody, MessageType.TRANSFER, cardInfo, amount, originator, date);
        }

    }

    private static MessageEnrichmentHolder parseDebit(String body, String date) {
        String cardInfo, originator;
        CurrencyAmount amount;

        String[] tokens = body.split(SPLITTER_DEBIT);
        if (tokens.length != 4) {
            return new MessageEnrichmentHolder(body, MessageType.DEBIT, date);
        } else {
            amount = parseAmount(tokens[2]).negate();
            cardInfo = "Main " + tokens[1];
            originator = "UNKNOWN";
//            date = sentDate;

            return new MessageEnrichmentHolder(body, MessageType.DEBIT, cardInfo, amount, originator, date);
        }
    }

    private static MessageEnrichmentHolder parseReversal(String body, String date) {
        String cardInfo, originator;
        CurrencyAmount amount;

        String[] tokens = body.split(SPLITTER_REVERSAL);
        if (tokens.length != 5) {
            return new MessageEnrichmentHolder(body, MessageType.REVERSAL, date);
        } else {
            amount = parseAmount(tokens[1]).negate();   // payment so negative value
            cardInfo = parseCardInfo(tokens[2]);
            originator = tokens[3];
//            date = tokens[4];

            // remove last dot
//            if (date.contains(".")) {
//                date = date.substring(0, date.length() - 1);
//            }

            return new MessageEnrichmentHolder(body, MessageType.REVERSAL, cardInfo, amount, originator, date);
        }
    }

    private static MessageEnrichmentHolder parseOTP(String body, String date) {
        return new MessageEnrichmentHolder(body, MessageType.OTP, "N/A", new CurrencyAmount("SGD", 0), "Citibank", date);
    }
}
