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
import com.d3.duy.citipocket.core.store.ColorCodeStore;
import com.d3.duy.citipocket.core.store.MessageStore;
import com.d3.duy.citipocket.model.MessageEnrichmentHolder;
import com.d3.duy.citipocket.model.enums.MessageType;

import java.util.ArrayList;
import java.util.List;

/**
 * List adapter for storing SMS data
 *
 * @author itcuties
 *
 */
public class MessagesListAdapter extends ArrayAdapter<MessageEnrichmentHolder> {

    private static LayoutInflater inflater = null;

    // List values
    private static final List<MessageEnrichmentHolder> messageEnrichmentHolders = new ArrayList<>();

    private Context context = null;
    private static final int LAYOUT_ID = R.layout.list_message;

    public MessagesListAdapter(Context context) {
        super(context, LAYOUT_ID, messageEnrichmentHolders);

        this.context = context;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        List<MessageEnrichmentHolder> loadedEnrichedMessages = MessageStore.getInstance().getEnrichedMessagesInBatch();
        messageEnrichmentHolders.addAll(loadedEnrichedMessages);
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
        mType.setTextColor(context.getResources().getColor(
                ColorCodeStore.getColorCodeMap().get(mEnrichment.getType())));

        mOriginator.setText(mEnrichment.getOriginator());
        mAmount.setText(mEnrichment.getAmount().toString());
        mDate.setText(mEnrichment.getDateStr());

        // As recommended, only card name is enough
        // so split by space and take the first item only
        String cardInfo = mEnrichment.getCardInfo();
        String[] cardInfoArr = cardInfo.split("\\s+");
        mCard.setText(cardInfoArr[0]);

        // since OTP is not important, put a grey background to indicate that
        if (mEnrichment.getType() == MessageType.OTP) {
            rowView.setBackgroundColor(context.getResources().getColor(R.color.colorOTPBackground));
        }

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, messageEnrichmentHolders.get(position).getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        return rowView;
    }

    public void setData(List<MessageEnrichmentHolder> enrichedMessages) {
        messageEnrichmentHolders.clear();

        for (MessageEnrichmentHolder enrichedMessage : enrichedMessages) {
            messageEnrichmentHolders.add(enrichedMessage);
        }
    }

}
