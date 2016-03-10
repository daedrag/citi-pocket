package com.d3.duy.citipocket.adapter;

/**
 * Created by daoducduy0511 on 27/2/16.
 * https://www.caveofprogramming.com/guest-posts/custom-listview-with-imageview-and-textview-in-android.html
 *
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.d3.duy.citipocket.R;
import com.d3.duy.citipocket.core.loader.MessageLoader;
import com.d3.duy.citipocket.model.MessageEnrichmentHolder;
import com.d3.duy.citipocket.model.MessageHolder;
import com.d3.duy.citipocket.model.enums.MessageType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * List adapter for storing SMS data
 *
 * @author itcuties
 *
 */
public class MessagesListAdapter extends ArrayAdapter<MessageEnrichmentHolder> {

    private static LayoutInflater inflater = null;
    private static final Map<MessageType, Integer> colorMap;
    static {
        Map<MessageType, Integer> aColorMap = new HashMap<>();
        aColorMap.put(MessageType.PAYMENT, R.color.colorTypePayment);
        aColorMap.put(MessageType.TRANSFER, R.color.colorTypeTransfer);
        aColorMap.put(MessageType.WITHDRAWAL, R.color.colorTypeWithdrawal);
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

    // List values
    private static final List<MessageHolder> messageHolders = new ArrayList<>();
    private static final List<MessageEnrichmentHolder> messageEnrichmentHolders = new ArrayList<>();

    private Context context = null;
    private static final int LAYOUT_ID = R.layout.list_message;

    public MessagesListAdapter(Context context) {
        super(context, LAYOUT_ID, messageEnrichmentHolders);

        this.context = context;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        List<MessageHolder> loadedMessages = MessageLoader.getInstance().getMessagesInBatch();
        List<MessageEnrichmentHolder> loadedEnrichedMessages = MessageLoader.getInstance().getEnrichedMessagesInBatch();

        messageHolders.addAll(loadedMessages);
        messageEnrichmentHolders.addAll(loadedEnrichedMessages);

        notifyDataSetChanged();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final MessageEnrichmentHolder mEnrichment = messageEnrichmentHolders.get(position);
        View rowView = inflater.inflate(LAYOUT_ID, null);

        TextView mType = (TextView) rowView.findViewById(R.id.row_message_type);
        TextView mOriginator = (TextView) rowView.findViewById(R.id.row_message_originator);
        TextView mAmount = (TextView) rowView.findViewById(R.id.row_message_amount);
        TextView mCard = (TextView) rowView.findViewById(R.id.row_message_card);
        TextView mDate = (TextView) rowView.findViewById(R.id.row_message_date);

        mType.setText(mEnrichment.getType().name());
        mType.setTextColor(context.getResources().getColor(colorMap.get(mEnrichment.getType())));

        mOriginator.setText(mEnrichment.getOriginator());
        mAmount.setText(mEnrichment.getAmount().toString());
        mCard.setText(mEnrichment.getCardInfo());
        mDate.setText(mEnrichment.getDateStr());

        // since OTP is not important, put a grey background to indicate that
        if (mEnrichment.getType() == MessageType.OTP) {
            rowView.setBackgroundColor(context.getResources().getColor(R.color.colorOTPBackground));
        }

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, messageHolders.get(position).getBody(), Toast.LENGTH_LONG).show();
            }
        });
        return rowView;
    }

    public void setData(List<MessageHolder> messages, List<MessageEnrichmentHolder> enrichedMessages) {
        messageHolders.clear();
        messageEnrichmentHolders.clear();

        int size = messages.size();
        for (int i = 0; i < size; i++) {
            messageHolders.add(messages.get(i));
            messageEnrichmentHolders.add(enrichedMessages.get(i));
        }

        notifyDataSetChanged();
    }

}
