package com.example.harsh.newsassignment;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.content.res.Configuration;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.*;
import android.widget.Toast;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    private static final String TAG = "MainActivity";
    static final String ACTION_MSG_TO_SERVICE = "ACTION_MSG_TO_SERVICE";
    static final String ACTION_NEWS_STORY = "ACTION_NEWS_STORY";
    private ArrayAdapter mDrawerListadapter;
    private NewsReciever newsreceivercategory;
    private MyPageAdapter newsadapter;
    private List<Fragment> fragments;
    private ViewPager pager;
    private ArticleData newsarticle;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    HashMap hashmap = new HashMap();
    private int flag3 = 1;
    private static int flag2 = 1;
    private ActionBarDrawerToggle mDrawerToggle;
    private ArrayList<String> items = new ArrayList<>();
    private ArrayList<String> newsresource = new ArrayList<>();
    private ArrayList<String> newsresource1 = new ArrayList<>();
    private ArrayList<SourceGetSet> newsresourcelist = new ArrayList<>();
    String et2;
    String[] categoryStringarray = new String[newsresource.size()];
    Fragment mContent;
    private static FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //describe drawer layout
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        String allcategory = "all";
        new NewsSourceDownloader(MainActivity.this).execute(String.valueOf(allcategory));
        newsreceivercategory = new NewsReciever();

        Intent serviceintent = new Intent(MainActivity.this, NewService.class);
        startService(serviceintent);

        IntentFilter filter1 = new IntentFilter(ACTION_NEWS_STORY);
        registerReceiver(newsreceivercategory, filter1);

        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        );

        //mDarawerList
        mDrawerListadapter = new ArrayAdapter<>(this,
                R.layout.drawer_list_item, items);
        mDrawerList.setAdapter(mDrawerListadapter);
        mDrawerList.setOnItemClickListener(
                new ListView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                    {
                        selectItem(position);
                        Log.d(TAG, items.get(position));
                        pager.setBackground(null);
                        //define intent
                        for(int i = 0; i< newsresourcelist.size(); i++)
                        {
                            if(items.get(position).equals(newsresourcelist.get(i).getName()))
                            {
                                Intent newintent = new Intent();
                                Log.d("Position", items.get(position));
                                newintent.putExtra("myinfo", newsresourcelist.get(i));
                                //Broadcast the intent
                                newintent.setAction(ACTION_MSG_TO_SERVICE);
                                sendBroadcast(newintent);
                                mDrawerLayout.closeDrawer(mDrawerList);
//                                startService(intent);
                            }
                        }
                    }
                }
        );

        if (savedInstanceState != null) {
            //Restore the fragment's instance
            mContent = getSupportFragmentManager().getFragment(savedInstanceState, "myFragmentName");
        }
        //sampleReceiver = new SampleReceiver();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        //Fragment Declaration
        fragments = getFragments();

        newsadapter = new MyPageAdapter(getSupportFragmentManager());
        pager = (ViewPager) findViewById(R.id.viewpager);
        pager.setBackgroundResource(R.drawable.newswall);
        pager.setAdapter(newsadapter);

    }

    private void selectItem(int position)
    {
        Toast.makeText(this, items.get(position), Toast.LENGTH_SHORT).show();
        setTitle(items.get(position));
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    private class NewsReciever extends BroadcastReceiver
    {
        @Override
        //ONRECEIVE method
        public void onReceive(Context context, Intent intent)
        {
            switch (intent.getAction())
            {
                case ACTION_NEWS_STORY:
                    if (intent.hasExtra("harsh"))
                    {
                       reDoFragments((ArrayList<ArticleData>) intent.getSerializableExtra("harsh"));
                    }
            }

        }
    }

    public void setSources(ArrayList<SourceGetSet> newsresourcelist, ArrayList<String> newsresourcecategory1)
    {
        hashmap.clear();
        items.removeAll(items);
        this.newsresourcelist.removeAll(this.newsresourcelist);
        this.newsresourcelist.addAll(newsresourcelist);
        Log.d(TAG, "StockList" + this.newsresourcelist.toString());
        Log.d(TAG, String.valueOf(items.size()));
        Log.d(TAG, String.valueOf(this.newsresourcelist.size()));
        //clear the list of source name
        newsresourcecategory1.add(0, "all");

        Log.d("flag1", String.valueOf(flag3));
        if(flag3 == 1)
        {
            newsresource.removeAll(newsresource);
            newsresource.addAll(newsresourcecategory1);
            categoryStringarray = newsresource.toArray(new String[newsresource.size()]);
            flag3++;
            Log.d("flag2", String.valueOf(flag3));
        }

        //Fill the list of sources
        for(int k = 0; k< this.newsresourcelist.size(); k++)
        {
            items.add(this.newsresourcelist.get(k).getName());
            hashmap.put(this.newsresourcelist.get(k).getName(), this.newsresourcelist.get(k));
        }
        invalidateOptionsMenu();
        Log.d(TAG, String.valueOf(items.size()));
//            mDrawerList.setAdapter(new ArrayAdapter<>(this,
//                R.layout.drawer_list_item, items));
        mDrawerListadapter.notifyDataSetChanged();
    }

    private void reDoFragments(ArrayList<ArticleData> article)
    {
        for (int i = 0; i < newsadapter.getCount(); i++)
        {
            newsadapter.notifyChangeInPosition(i);
        }
        fragments.clear();

        for (int f = 0; f < article.size(); f++)
        {
            Log.d("DEEP", article.get(f).getTitle());
            //newsadapter.notifyChangeInPosition(i);
            fragments.add(NewsArticleFragment.newInstance(article.get(f).getTitle(), article.get(f).getUrlToImage(), article.get(f).getAuthor(), article.get(f).getDescription(), article.get(f).getPublishedAt(), article.get(f).getUrl(), " Page " + (f+1) + " of" + article.size()));
//            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment_f, "fragment1").commit();
        }

        newsadapter.notifyDataSetChanged();
        pager.setCurrentItem(0);

    }

    private List<Fragment> getFragments() {
        List<Fragment> fList = new ArrayList<Fragment>();
        return fList;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.action_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        Log.d(TAG,"onPrepareOptionsMenu "+ categoryStringarray.length);
        menu.clear();
        if(categoryStringarray.length != 0)
        {
            for (int i = 0; i < categoryStringarray.length; i++)
            {
                menu.add(R.menu.action_menu, Menu.NONE, 0, categoryStringarray[i]);
            }
            return true;
        }
        else
            return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        if (mDrawerToggle.onOptionsItemSelected(item))
        {
            Log.d(TAG, "onOptionsItemSelected: mDrawerToggle " + item);
            return true;
        }


        Log.d("item", String.valueOf(item));
        new NewsSourceDownloader(MainActivity.this).execute(String.valueOf(item));
        return true;
    }

    private class MyPageAdapter extends FragmentPagerAdapter {
        private long baseId = 0;


        public MyPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public long getItemId(int position) {
            // give an ID different from position when position has been changed
            return baseId + position;
        }

        //fragment
        public void notifyChangeInPosition(int n) {
            // shift the ID returned by getItemId outside the range of all previous fragments
            baseId += getCount() + n;
        }

    }

    //Unregister service
    @Override
    protected void onDestroy()
    {
        unregisterReceiver(newsreceivercategory);
        Intent intent = new Intent(MainActivity.this, NewService.class);
        stopService(intent);
        super.onDestroy();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
        items.addAll(savedInstanceState.getStringArrayList("HISTORY"));
        newsresource.addAll(savedInstanceState.getStringArrayList("HISTORY1"));
       // FragmentTransaction transaction = fragmentManager.beginTransaction();
//        fragments = (List<Fragment>) fragmentManager.getFragment(savedInstanceState, "myFragment");
//        mContent.getFragmentManager().getFragment(savedInstanceState, "myFragmentName");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList("HISTORY",items);
        outState.putStringArrayList("HISTORY1",newsresource);
//        if(fragments != null) {
//            fragmentManager.putFragment(outState, "myFragment", fragments);
//        }
//        mCurCheckPosition = savedInstanceState.getInt("curChoice", 0);
//        getSupportFragmentManager().putFragment(outState, "myFragmentName", mContent);
//        outState.putStringArray("HISTORY2",categoryStringarray);
    }
}


