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
import com.d3.duy.citipocket.core.loader.MessageLoader;

/**
 * Created by daoducduy0511 on 3/3/16.
 */
public class FragmentMessages extends Fragment implements CustomFragment {

    private static final String TITLE = "Messages";
    private static MessagesListAdapter messagesListAdapter = null;

    public FragmentMessages() {
        super();
    }

    @Override
    public String getTitle() {
        return TITLE;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_messages, container, false);

        TextView textView = (TextView) rootView.findViewById(R.id.section_label_messages);
        textView.setText(String.format(
                getString(R.string.section_label_messages_format),
                MessageLoader.getInstance().getMessagesSize()
        ));

        // load initial batch of data
        if (messagesListAdapter == null) {
            messagesListAdapter = new MessagesListAdapter(
                    getContext(), MessageLoader.getInstance().getMessagesInBatch());
        }

        // Set ListAdapter for list view
        final ListView listView = (ListView) rootView.findViewById(R.id.list_messages);
        listView.setAdapter(messagesListAdapter);

        // Set scroll behavior to load more next/previous batch
        // considering predefined window size
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            int firstVisibleItem = 0;
            int visibleItemCount = 0;
            int totalItemCount = 0;
            boolean isMoving = false;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (!isMoving) return;

                if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount != 0
                        && scrollState == SCROLL_STATE_IDLE) {

                    if (MessageLoader.getInstance().isMoreNext()) {
                        MessageLoader.getInstance().moveToNextBatch();
                        messagesListAdapter.setData(MessageLoader.getInstance().getMessagesInBatch());

                        // restore current location
                        listView.setSelectionFromTop(totalItemCount - MessageLoader.BATCH_SIZE - visibleItemCount + 1, 0);
                    }
                } else if (firstVisibleItem == 0 && totalItemCount != 0
                        && scrollState == SCROLL_STATE_IDLE) {

                    if (MessageLoader.getInstance().isMorePrevious()) {
                        MessageLoader.getInstance().moveToPreviousBatch();
                        messagesListAdapter.setData(MessageLoader.getInstance().getMessagesInBatch());

                        // restore current location
                        listView.setSelectionFromTop(MessageLoader.BATCH_SIZE, 0);
                    }
                }

                isMoving = false;
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                this.firstVisibleItem = firstVisibleItem;
                this.visibleItemCount = visibleItemCount;
                this.totalItemCount = totalItemCount;
                this.isMoving = true;
            }
        });

        // Setup spinner
//        Spinner spinner = (Spinner) rootView.findViewById(R.id.spinner_messages);
//
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
//                R.array.filter_options, R.layout.spinner_options_item);
//
//        adapter.setDropDownViewResource(R.layout.spinner_options_dropdown_item);
//        spinner.setAdapter(adapter);

        return rootView;
    }

}
