package com.d3.duy.citipocket.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.d3.duy.citipocket.R;
import com.d3.duy.citipocket.activity.permission.RequestorCallback;
import com.d3.duy.citipocket.adapter.SectionsPagerAdapter;
import com.d3.duy.citipocket.core.loader.MessageLoader;
import com.d3.duy.citipocket.core.loader.StatisticsLoader;
import com.d3.duy.citipocket.fragment.FragmentAccount;
import com.d3.duy.citipocket.model.MessageEnrichmentHolder;

import java.util.List;

public class MainActivity extends PermissionRequestorActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final int PERMISSIONS_REQUEST_READ_SMS = 0;
    private static final int PERMISSIONS_REQUEST_RECEIVE_SMS = 1;

    private static final int MESSAGES_LOADER = 0;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;


    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        request(PERMISSIONS_REQUEST_READ_SMS, new RequestorCallback() {
            @Override
            public void onSuccess(int permission) {
//                Snackbar.make(MainActivity.this.toolbar, "Permission READ SMS granted", Snackbar.LENGTH_SHORT).show();
                new LoadMessageTask().execute();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected View getViewForSnackbar() {
        return toolbar;
    }

    private class LoadMessageTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            MessageLoader.getInstance(getContentResolver()).load(true);

            // Move cursor to initial batch
            MessageLoader.getInstance().moveToNextBatch();

            //
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d(TAG, "Loading sms from database...");
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d(TAG, "Loading sms from database... Done!");
            Toast.makeText(MainActivity.this, "Loading sms from database finished!", Toast.LENGTH_SHORT).show();

            // Start calculating statistics
            List<MessageEnrichmentHolder> messages = MessageLoader.getInstance().getEnrichedMessages();
            new StatisticsTask().execute();
        }
    }

    private class StatisticsTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            // 1. classify by date
            // 2. for each group, classify by type
            // 3. aggregate all messages with the same type
            StatisticsLoader.getInstance().load();

            // UGLY CODE: set adapter to Fragment Account
            // http://stackoverflow.com/questions/5161951/android-only-the-original-thread-that-created-a-view-hierarchy-can-touch-its-vi
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    FragmentAccount fragmentAccount = (FragmentAccount) mSectionsPagerAdapter.getAccountFragment();
                    fragmentAccount.reloadStatAdapter();
                }
            });

            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d(TAG, "Calculating statistics...");
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d(TAG, "Calculating statistics... Done!");
            Toast.makeText(MainActivity.this, "Calculating statistics finished!", Toast.LENGTH_SHORT).show();
        }

    }
}
