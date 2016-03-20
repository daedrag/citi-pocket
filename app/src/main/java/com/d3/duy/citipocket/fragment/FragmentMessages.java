package com.d3.duy.citipocket.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import com.d3.duy.citipocket.R;
import com.d3.duy.citipocket.adapter.MessagesListAdapter;
import com.d3.duy.citipocket.core.store.MessageStore;
import com.d3.duy.citipocket.model.MessageContract;

/**
 * Created by daoducduy0511 on 3/3/16.
 */
public class FragmentMessages extends Fragment implements CustomFragment {

    private static final String TITLE = "Messages";
    private MessagesListAdapter messagesListAdapter;
    private ListView listView;
    private TextView textView;

    public FragmentMessages() {
        super();
        messagesListAdapter = null;
    }

    @Override
    public String getTitle() {
        return TITLE;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_messages, container, false);

        textView = (TextView) rootView.findViewById(R.id.section_label_messages);
        listView = (ListView) rootView.findViewById(R.id.list_messages);

        // Set scroll behavior to load more next/previous batch
        // considering predefined window size
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            int firstVisibleItem = 0;
            int visibleItemCount = 0;
            int totalItemCount = 0;
            boolean isMoving = false;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (messagesListAdapter == null) return;

                if (!isMoving) return;

                if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount != 0
                        && scrollState == SCROLL_STATE_IDLE) {

                    if (MessageStore.getInstance().isMoreNext()) {
                        MessageStore.getInstance().moveToNextBatch();
                        messagesListAdapter.setData(MessageStore.getInstance().getEnrichedMessagesInBatch());
                        messagesListAdapter.notifyDataSetChanged();

                        // restore current location
                        listView.setSelectionFromTop(totalItemCount - MessageStore.BATCH_SIZE - visibleItemCount + 1, 0);
                    }
                } else if (firstVisibleItem == 0 && totalItemCount != 0
                        && scrollState == SCROLL_STATE_IDLE) {

                    if (MessageStore.getInstance().isMorePrevious()) {
                        MessageStore.getInstance().moveToPreviousBatch();
                        messagesListAdapter.setData(MessageStore.getInstance().getEnrichedMessagesInBatch());
                        messagesListAdapter.notifyDataSetChanged();

                        // restore current location
                        listView.setSelectionFromTop(MessageStore.BATCH_SIZE, 0);
                    }
                }

                isMoving = false;
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (messagesListAdapter == null) return;

                this.firstVisibleItem = firstVisibleItem;
                this.visibleItemCount = visibleItemCount;
                this.totalItemCount = totalItemCount;
                this.isMoving = true;
            }
        });

        return rootView;
    }

    public void initAdapter() {
        // load initial batch of data
        if (messagesListAdapter == null) {
            messagesListAdapter = new MessagesListAdapter(getContext());
            listView.setAdapter(messagesListAdapter);
        }

        textView.setText(String.format(
                getString(R.string.section_label_messages_format),
                MessageStore.getInstance().getMessagesSize(),
                MessageContract.SENDER_ADDRESS));

        messagesListAdapter.notifyDataSetChanged();
    }
}
