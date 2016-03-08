package com.d3.duy.citipocket.adapter;

/**
 * Created by daoducduy0511 on 27/2/16.
 * https://www.caveofprogramming.com/guest-posts/custom-listview-with-imageview-and-textview-in-android.html
 *
 */

import android.content.Context;
import android.database.Cursor;
import android.provider.Telephony;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.d3.duy.citipocket.R;
import com.d3.duy.citipocket.model.MessageEnrichmentHolder;
import com.d3.duy.citipocket.model.MessageFilter;
import com.d3.duy.citipocket.model.MessageHolder;

import java.util.HashMap;
import java.util.Map;

/**
 * List adapter for storing SMS data
 *
 * @author itcuties
 *
 */
public class MessagesListCursorAdapter extends CursorAdapter {

    private static LayoutInflater inflater = null;
    private Map<MessageFilter.MessageType, Integer> colorMap;
    private Context context;

    public MessagesListCursorAdapter(Context context, Cursor cursor, int flags) {
        super(context, cursor, 0);

        this.context = context;

        this.colorMap = new HashMap<>();
        this.colorMap.put(MessageFilter.MessageType.PAYMENT, R.color.colorTypePayment);
        this.colorMap.put(MessageFilter.MessageType.TRANSFER, R.color.colorTypeTransfer);
        this.colorMap.put(MessageFilter.MessageType.WITHDRAWAL, R.color.colorTypeWithdrawal);
        this.colorMap.put(MessageFilter.MessageType.GIRO, R.color.colorTypeGiro);
        this.colorMap.put(MessageFilter.MessageType.UNKNOWN, R.color.colorTypeUnknown);

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return inflater.inflate(R.layout.list_message, parent, false);
    }

    @Override
    public void bindView(View rowView, final Context context, Cursor cursor) {

        TextView mType = (TextView) rowView.findViewById(R.id.row_message_type);
        TextView mOriginator = (TextView) rowView.findViewById(R.id.row_message_originator);
        TextView mAmount = (TextView) rowView.findViewById(R.id.row_message_amount);
        TextView mCard = (TextView) rowView.findViewById(R.id.row_message_card);
        TextView mDate = (TextView) rowView.findViewById(R.id.row_message_date);

        int id = cursor.getInt(cursor.getColumnIndexOrThrow(Telephony.Sms._ID));
        String number = cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.ADDRESS)).toString();
        final String body = cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.BODY)).toString();
        String date = cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.DATE)).toString();

        MessageEnrichmentHolder mEnrichment = MessageFilter.classify(new MessageHolder(id, number, body, date));

        mType.setText(mEnrichment.getType().name());
        mType.setTextColor(context.getResources().getColor(this.colorMap.get(mEnrichment.getType())));

        mOriginator.setText(mEnrichment.getOriginator());
        mAmount.setText(mEnrichment.getAmount());
        mCard.setText(mEnrichment.getCardInfo());
        mDate.setText(mEnrichment.getDate());

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MessagesListCursorAdapter.this.context, body, Toast.LENGTH_LONG).show();
            }
        });

    }

}
