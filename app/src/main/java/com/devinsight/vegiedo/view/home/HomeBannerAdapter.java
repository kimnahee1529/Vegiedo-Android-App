package com.devinsight.vegiedo.view.home;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class HomeBannerAdapter extends FragmentStateAdapter {
    public HomeBannerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch(position){
            case 0:
                return new BannerFragment1();
            case 1:
                return new BannerFragment1();
            case 2:
                return new BannerFragment1();
            case 3:
                return new BannerFragment1();
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return 4;
    }


}