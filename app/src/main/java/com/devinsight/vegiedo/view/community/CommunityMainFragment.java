package com.devinsight.vegiedo.view.community;

import android.graphics.Typeface;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.devinsight.vegiedo.R;
import com.devinsight.vegiedo.view.MainActivity;
import com.devinsight.vegiedo.view.search.ActivityViewModel;


public class CommunityMainFragment extends Fragment {


    TextView general_post;
    TextView popular_post;
    Button btn_writing;
    ActivityViewModel activityViewModel;
    FragmentManager fragmentManager;
    FragmentTransaction transaction;
    Fragment communityPostListFragment;
    Fragment postMainFragment;
    Fragment writingFragment;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_community, container, false);
        general_post = view.findViewById(R.id.general_posts);
        popular_post = view.findViewById(R.id.popular_posts);
        btn_writing = view.findViewById(R.id.writing_btn);

        activityViewModel = new ViewModelProvider(requireActivity()).get(ActivityViewModel.class);

        communityPostListFragment = new CommunityPostListFragment();
        postMainFragment = new PostMainFragment();
        writingFragment = new WritingFragment();


        fragmentManager = getChildFragmentManager();
        transaction = fragmentManager.beginTransaction();

        transaction.replace(R.id.community_frame, communityPostListFragment, "communityPostListFragment").addToBackStack("communityPostListFragment").commit();

        activityViewModel.setPostType(true);

        general_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setGeneralFontStyle();
                CommunityPostListFragment newFragment = new CommunityPostListFragment();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                // 프래그먼트 교체
                transaction.replace(R.id.community_frame, newFragment, "communityPostListFragment")
                        .addToBackStack("communityPostListFragment")
                        .commit();
                activityViewModel.setPostType(true);
            }
        });

        popular_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setPopuarFontStyle();
                CommunityPostListFragment newFragment = new CommunityPostListFragment();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                // 프래그먼트 교체
                transaction.replace(R.id.community_frame, newFragment, "communityPostListFragment")
                        .addToBackStack("communityPostListFragment")
                        .commit();
                activityViewModel.setPostType(false);
            }
        });

        btn_writing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = ((MainActivity)getActivity()).getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.frame, writingFragment).commit();
            }
        });


        return view;
    }


    /* 일반 게시글 폰트 설정 */
    public void setGeneralFontStyle() {
        general_post.setTypeface(general_post.getTypeface(), Typeface.BOLD);
        general_post.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
        popular_post.setTypeface(popular_post.getTypeface(), Typeface.NORMAL);
        popular_post.setTextColor(ContextCompat.getColor(getContext(), R.color.grey));
    }
    /* 인기 게시글 폰트 설정 */
    public void setPopuarFontStyle() {
        popular_post.setTypeface(popular_post.getTypeface(), Typeface.BOLD);
        popular_post.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
        general_post.setTypeface(general_post.getTypeface(), Typeface.NORMAL);
        general_post.setTextColor(ContextCompat.getColor(getContext(), R.color.grey));
    }

}