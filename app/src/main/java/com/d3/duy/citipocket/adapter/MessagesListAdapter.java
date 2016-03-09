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
import com.d3.duy.citipocket.core.enrich.MessageEnrichment;
import com.d3.duy.citipocket.model.MessageEnrichmentHolder;
import com.d3.duy.citipocket.model.MessageHolder;
import com.d3.duy.citipocket.model.enums.MessageType;

import java.util.ArrayList;
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

    private Context context = null;
    private static LayoutInflater inflater = null;
    private Map<MessageType, Integer> colorMap;

    // List values
    private static final List<MessageHolder> messageHolders = new ArrayList<>();
    private static final List<MessageEnrichmentHolder> messageEnrichmentHolders = new ArrayList<>();

    public MessagesListAdapter(Context context, List<MessageHolder> messageHolders) {
        super(context, R.layout.list_message, messageEnrichmentHolders);

        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        this.colorMap = new HashMap<>();
        this.colorMap.put(MessageType.PAYMENT, R.color.colorTypePayment);
        this.colorMap.put(MessageType.TRANSFER, R.color.colorTypeTransfer);
        this.colorMap.put(MessageType.WITHDRAWAL, R.color.colorTypeWithdrawal);
        this.colorMap.put(MessageType.GIRO, R.color.colorTypeGiro);
        this.colorMap.put(MessageType.DEBIT, R.color.colorTypeDebit);
        this.colorMap.put(MessageType.REVERSAL, R.color.colorTypeReversal);
        this.colorMap.put(MessageType.OTP, R.color.colorTypeOTP);
        this.colorMap.put(MessageType.UNKNOWN, R.color.colorTypeUnknown);

        this.messageHolders.addAll(messageHolders);
        for (MessageHolder message: messageHolders) {
            this.messageEnrichmentHolders.add(MessageEnrichment.classify(message));
        }

        notifyDataSetChanged();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final MessageEnrichmentHolder mEnrichment = messageEnrichmentHolders.get(position);
        View rowView = inflater.inflate(R.layout.list_message, null);

        TextView mType = (TextView) rowView.findViewById(R.id.row_message_type);
        TextView mOriginator = (TextView) rowView.findViewById(R.id.row_message_originator);
        TextView mAmount = (TextView) rowView.findViewById(R.id.row_message_amount);
        TextView mCard = (TextView) rowView.findViewById(R.id.row_message_card);
        TextView mDate = (TextView) rowView.findViewById(R.id.row_message_date);

        mType.setText(mEnrichment.getType().name());
        mType.setTextColor(context.getResources().getColor(this.colorMap.get(mEnrichment.getType())));

        mOriginator.setText(mEnrichment.getOriginator());
        mAmount.setText(mEnrichment.getAmount().toString());
        mCard.setText(mEnrichment.getCardInfo());
        mDate.setText(mEnrichment.getDateStr());

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

    public void setData(List<MessageHolder> messages) {
        messageHolders.clear();
        messageEnrichmentHolders.clear();

        for (MessageHolder message: messages) {
            messageHolders.add(message);
            messageEnrichmentHolders.add(MessageEnrichment.classify(message));
        }

        notifyDataSetChanged();
    }

}
