package com.ridhwandaud.jomjogging;

import android.app.Notification;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by maideasy on 30/08/2017.
 */

public class NavigationAdapter extends FragmentPagerAdapter {
    private Context mContext;

    public NavigationAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new JoggingFragment();
//        } else if (position == 1){
//            return new JoggingFragment();
//        }else if (position == 2){
//            return new AccountFragment();
        }else {
            return new NotificationsFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "Run";
//        } else if (position == 1) {
//            return mContext.getString(R.string.tab_running);
//        } else if (position == 2) {
//            return mContext.getString(R.string.tab_account);
        } else {
            return "Notifications";
        }
    }
}
