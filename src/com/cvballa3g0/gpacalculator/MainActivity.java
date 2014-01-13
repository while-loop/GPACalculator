package com.cvballa3g0.gpacalculator;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.widget.ShareActionProvider;

public class MainActivity extends SherlockFragmentActivity {

    private static final String VERSION = "1.03";
    private ShareActionProvider mShareActionProvider;
    ViewPager mViewPager;
    TabsAdapter mTabsAdapter;
    TextView tabCenter;
    TextView tabText;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewPager = new ViewPager(this);
        mViewPager.setId(R.id.pager);
        setContentView(mViewPager);

        final ActionBar bar = getSupportActionBar();
        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        mTabsAdapter = new TabsAdapter(this, mViewPager);
        mTabsAdapter.addTab(bar.newTab().setText("College"), AFragment.class,
                null);
        mTabsAdapter.addTab(bar.newTab().setText("High School"),
                BFragment.class, null);

        checkUpdate();

    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getSupportMenuInflater().inflate(R.menu.activity_main, menu);
        mShareActionProvider = (ShareActionProvider) menu.findItem(R.id.share)
                .getActionProvider();

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
        case R.id.share:
            shareGPA();
            return (true);
        case R.id.reset:
            if (TabsAdapter.fragPos == 1) {
                AFragment.reset();
            } else if (TabsAdapter.fragPos == 2) {
                BFragment.reset();
            }
            return (true);
        case R.id.exit:
            ExitApp();
            return (true);
        case R.id.about_option:
            showAbout();
            return (true);
        }
        return false;

    }

    private void shareGPA() {
        String GPA = "";
        if (TabsAdapter.fragPos == 1) {
            GPA = AFragment.GPA;
        } else if (TabsAdapter.fragPos == 2) {
            GPA = BFragment.GPA;
        }

        GPA = "My GPA is " + GPA + ".\n\n- Sent from the GPA Calculator App";

        final Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, GPA);
        startActivity(Intent.createChooser(shareIntent, "Share your GPA to..."));
    }

    private void showAbout() {

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                MainActivity.this);
        alertDialogBuilder.setTitle("About");
        alertDialogBuilder
                .setMessage(
                        "The App:"
                                + "\n"
                                + "This app was made to calculate you GPA for the current term."
                                + "\n"
                                + "It is NOT made for your cummulative GPA."
                                + "\n\n"
                                + "About me:"
                                + "\n"
                                + "I'm a freshman in college studying Computer Science."
                                + "\n"
                                + "I love Android and want to learn as much as possible."
                                + "\n\n" + "Contact me:" + "\n"
                                + "Email me bugs or suggestions :)" + "\n"
                                + "Gmail & Google Talk: Cvballa3g0@gmail.com"
                                + "\n")
                .setCancelable(false)
                .setNegativeButton("Close",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialog,
                                    final int id) {
                            }
                        })
                .setPositiveButton("Email me",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialog,
                                    final int id) {
                                SendEmail();
                            }
                        })
                .setNeutralButton("Rate my app",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialog,
                                    final int id) {
                                final Intent browserIntent = new Intent(
                                        Intent.ACTION_VIEW,
                                        Uri.parse("https://play.google.com/store/apps/details?id=com.cvballa3g0.gpacalculator#?t=W251bGwsMSwyLDIxMiwiY29tLmN2YmFsbGEzZzAuZ3BhY2FsY3VsYXRvciJd"));
                                startActivity(browserIntent);
                            }
                        });
        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    private void SendEmail() {

        final Intent emailIntent = new Intent(
                android.content.Intent.ACTION_SEND);
        emailIntent.putExtra(Intent.EXTRA_EMAIL,
                new String[] { "Cvballa3g0@gmail.com" });
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
                "GPA Calculator");
        emailIntent.setType("plain/text");
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT,
                "\n\n\n- Sent from GPA Calculator App");
        startActivity(emailIntent);

    }

    private void ExitApp() {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                MainActivity.this);

        alertDialogBuilder.setTitle("Exiting Application");

        alertDialogBuilder
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialog,
                                    final int id) {
                                MainActivity.this.finish();
                            }
                        })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog,
                            final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    public static class TabsAdapter extends FragmentPagerAdapter implements
            ActionBar.TabListener, ViewPager.OnPageChangeListener {
        private final Context mContext;
        private final ActionBar mActionBar;
        private final ViewPager mViewPager;
        private final ArrayList<TabInfo> mTabs = new ArrayList<TabInfo>();
        public static int fragPos = 0;

        static final class TabInfo {
            private final Class<?> clss;
            private final Bundle args;

            TabInfo(final Class<?> _class, final Bundle _args) {
                clss = _class;
                args = _args;
            }
        }

        public TabsAdapter(final SherlockFragmentActivity activity,
                final ViewPager pager) {
            super(activity.getSupportFragmentManager());
            mContext = activity;
            mActionBar = activity.getSupportActionBar();
            mViewPager = pager;
            mViewPager.setAdapter(this);
            mViewPager.setOnPageChangeListener(this);

        }

        public void addTab(final ActionBar.Tab tab, final Class<?> clss,
                final Bundle args) {
            final TabInfo info = new TabInfo(clss, args);
            tab.setTag(info);
            tab.setTabListener(this);
            mTabs.add(info);
            mActionBar.addTab(tab);
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return mTabs.size();
        }

        @Override
        public Fragment getItem(final int position) {
            final TabInfo info = mTabs.get(position);
            return Fragment.instantiate(mContext, info.clss.getName(),
                    info.args);
        }

        @Override
        public void onPageScrolled(final int position,
                final float positionOffset, final int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(final int position) {
            mActionBar.setSelectedNavigationItem(position);
        }

        @Override
        public void onPageScrollStateChanged(final int state) {
        }

        @Override
        public void onTabSelected(final Tab tab, final FragmentTransaction ft) {
            final Object tag = tab.getTag();
            if (fragPos == 0) {
                fragPos = 1;
            } else if (fragPos == 1) {
                fragPos = 2;
            } else if (fragPos == 2) {
                fragPos = 1;
            }
            for (int i = 0; i < mTabs.size(); i++) {
                if (mTabs.get(i) == tag) {
                    mViewPager.setCurrentItem(i);
                }
            }
        }

        @Override
        public void onTabUnselected(final Tab tab, final FragmentTransaction ft) {
        }

        @Override
        public void onTabReselected(final Tab tab, final FragmentTransaction ft) {
        }
    }

    public void Dialog() {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this)
                .setTitle("Update!")
                .setMessage(
                        "A new version of GPA Calculator is available. Your current version is v"
                                + VERSION)
                .setPositiveButton("Update Now",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialog,
                                    final int which) {
                                final Intent browserIntent = new Intent(
                                        Intent.ACTION_VIEW,
                                        Uri.parse("https://play.google.com/store/apps/details?id=com.cvballa3g0.gpacalculator#?t=W251bGwsMSwxLDIxMiwiY29tLmN2YmFsbGEzZzAuZ3BhY2FsY3VsYXRvciJd"));
                                startActivity(browserIntent);
                            }
                        })
                .setNegativeButton("Close",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialog,
                                    final int which) {
                            }
                        });
        alertDialog.setCancelable(false);
        alertDialog.show();

    }

    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(final android.os.Message msg) {
            Dialog();
        }
    };

    private void checkUpdate() {

    }

}
