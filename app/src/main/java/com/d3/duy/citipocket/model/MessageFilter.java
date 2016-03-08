package com.d3.duy.citipocket.model;

/**
 * Created by daoducduy0511 on 27/2/16.
 */
public class MessageFilter {

    public static final String TAG = MessageFilter.class.getSimpleName();
    /**
     * Filters
     */
//    public static final String FILTER_CURRENCY_AMOUNT = "";
    public static final String FILTER_CARD_PAYMENT =
            "There was a charge of (.*)";
    public static final String FILTER_CASH_WITHDRAWAL =
            "There was a cash withdrawal of (.*)";
    public static final String FILTER_TRANSFER_REQUEST =
            "We have received your request on (.*)";
    public static final String FILTER_GIRO_CREDIT =
            "There was a credit of (.*)";

    /**
     * Splitters
     */
    public static final String SPLITTER_CARD_INFO = "Citibank | Card ending | no. ending ";
    public static final String SPLITTER_CARD_PAYMENT = "There was a charge of | on your | by | on ";
    public static final String SPLITTER_CASH_WITHDRAWAL = "There was a cash withdrawal of | on your | on | via";
    public static final String SPLITTER_TRANSFER_REQUEST = "We have received your request on | to transfer | to |. To amend your alerts, login to";

    public enum MessageType {
        PAYMENT, WITHDRAWAL, TRANSFER, GIRO, UNKNOWN
    };

    public static MessageEnrichmentHolder classify(MessageHolder sms) {
        String body = sms.getBody();

        if (body.matches(FILTER_CARD_PAYMENT)) {
            return parseCashPayment(body);

        } else if (body.matches(FILTER_CASH_WITHDRAWAL)) {
            return parseCashWithdrawal(body);

        } else if (body.matches(FILTER_GIRO_CREDIT)) {
            return parseGiroCredit(body);

        } else if (body.matches(FILTER_TRANSFER_REQUEST)) {
            return parseTransferRequest(body);

        } else {
            // Unknown type by default
            return new MessageEnrichmentHolder(MessageType.UNKNOWN);

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

    private static MessageEnrichmentHolder parseCashPayment(String messageBody) {
        String cardInfo, amount, originator, date;

        String[] tokens = messageBody.split(SPLITTER_CARD_PAYMENT);
        if (tokens.length != 5) {
            return new MessageEnrichmentHolder(MessageType.PAYMENT);
        } else {
            amount = tokens[1];
            cardInfo = parseCardInfo(tokens[2]);
            originator = tokens[3];
            date = tokens[4];

            // remove last dot
            if (date.contains(".")) {
                date = date.substring(0, date.length() - 1);
            }

            return new MessageEnrichmentHolder(MessageType.PAYMENT, cardInfo, amount, originator, date);
        }
    }

    private static MessageEnrichmentHolder parseCashWithdrawal(String messageBody) {
        String cardInfo, amount, originator, date;

        String[] tokens = messageBody.split(SPLITTER_CASH_WITHDRAWAL);
        if (tokens.length != 5) {
            return new MessageEnrichmentHolder(MessageType.WITHDRAWAL);
        } else {
            amount = tokens[1];
            cardInfo = parseCardInfo(tokens[2]);
            originator = tokens[4];
            date = tokens[3];

            // remove last dot
            if (originator.contains(".")) {
                originator = originator.substring(0, originator.length() - 1);
            }

            return new MessageEnrichmentHolder(MessageType.WITHDRAWAL, cardInfo, amount, originator, date);
        }
    }

    private static MessageEnrichmentHolder parseGiroCredit(String messageBody) {
        String cardInfo, amount, originator, date;

        cardInfo = "";
        amount = "";
        originator = "";
        date = "";

        return new MessageEnrichmentHolder(MessageType.GIRO, cardInfo, amount, originator, date);
    }

    private static MessageEnrichmentHolder parseTransferRequest(String messageBody) {
        String cardInfo, amount, originator, date;

        String[] tokens = messageBody.split(SPLITTER_TRANSFER_REQUEST);
//        Log.d(TAG, "Parsing Transfer: " + Arrays.toString(tokens));
        if (tokens.length != 5) {
            return new MessageEnrichmentHolder(MessageType.TRANSFER);
        } else {
            amount = tokens[2];
            cardInfo = "Main account";
            originator = tokens[3];
            date = tokens[1];

            return new MessageEnrichmentHolder(MessageType.TRANSFER, cardInfo, amount, originator, date);
        }

    }


}
