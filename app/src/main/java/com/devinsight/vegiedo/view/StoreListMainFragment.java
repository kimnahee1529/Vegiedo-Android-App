package com.devinsight.vegiedo.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.devinsight.vegiedo.R;
import com.devinsight.vegiedo.data.response.StoreListData;
import com.devinsight.vegiedo.service.api.StoreApiService;
import com.devinsight.vegiedo.utill.RetrofitClient;
import com.devinsight.vegiedo.view.map.MapMainFragment;
import com.devinsight.vegiedo.view.search.ActivityViewModel;
import com.devinsight.vegiedo.view.search.StoreDetailListAdapter;
import com.devinsight.vegiedo.view.store.StoreDetailPageDDFragment;
import com.devinsight.vegiedo.view.store.StoreDetailPageFragment;
import com.devinsight.vegiedo.view.store.filterData;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StoreListMainFragment extends Fragment implements StoreDetailListAdapter.searchListner {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerView;
    private StoreDetailListAdapter storeDetailListAdapter;

    private List<StoreListData> storeList;
    private List<Long> storeIdList;
    private ActivityViewModel viewModel;

    ConstraintLayout layout;
    LinearLayout not_found_Sheep;

    ImageView sheep;
    TextView tt_not_found;
    TextView tt_set_more_option;
    Button tt_recomanded_tag;

    FloatingActionButton btn_page_to_map;
    double centerLatitude;
    double centerLongitude;
    private boolean isBundleDataAvailable = false;

    StoreDetailPageDDFragment storeDetailPageDDFragment;
    Fragment mapMainFragment;

    public static StoreListMainFragment instance() {
        return new StoreListMainFragment();
    }

    public StoreListMainFragment() {

    }

    public static StoreListMainFragment newInstance(String param1, String param2) {
        StoreListMainFragment fragment = new StoreListMainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

//            // storeIdArray를 가져옴
//            Long[] storeIdArray = (Long[]) getArguments().getSerializable("storeIdArray");
//            Log.d("Map", ""+storeIdArray);
//
//            // Long[]를 List<Long>으로 변환
//            if (storeIdArray != null) {
//                storeIdList = new ArrayList<>(Arrays.asList(storeIdArray));
//            }

            Bundle bundle = getArguments();
            if (bundle != null) {
                centerLatitude = bundle.getDouble("centerLatitude");
                centerLongitude = bundle.getDouble("centerLongitude");
                isBundleDataAvailable = true;
            }

//            Log.d("넘어온 MapCenter", ""+centerLatitude+", "+centerLongitude);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_store_list_main, container, false);
        Log.d("가게메인리스트", "가게메인리스트");

        layout = view.findViewById(R.id.store_setting_message);
        sheep = view.findViewById(R.id.sheep);
        tt_not_found = view.findViewById(R.id.tt_not_found);
        tt_set_more_option = view.findViewById(R.id.tt_set_more_option);
        btn_page_to_map = view.findViewById(R.id.btn_go_map);
        tt_recomanded_tag = view.findViewById(R.id.tt_recomanded_tag);

        mapMainFragment = new MapMainFragment();

        recyclerView = view.findViewById(R.id.recycler_store_list);
        storeList = new ArrayList<>();
        storeDetailListAdapter = new StoreDetailListAdapter(getActivity(), storeList, this);
        recyclerView.setAdapter(storeDetailListAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        viewModel = new ViewModelProvider(requireActivity()).get(ActivityViewModel.class);
        Log.d("가게메인리스트2", "가게메인리스트2");

        if (!isBundleDataAvailable) {
            viewModel.storeApiData();
            viewModel.getStoreListLiveData().observe(getViewLifecycleOwner(), new Observer<List<StoreListData>>() {
                @Override
                public void onChanged(List<StoreListData> storeListData) {
                    if (storeListData.size() == 0) {
                        Log.d("검색 리스트 없음 ", " 검색리스트 갯수 : " + storeListData.size());
                        setNotificationVisible();
                    } else {
                        Log.d("가게메인리스트4", "가게메인리스트4");
                        setNotificationInVisible();
                        storeDetailListAdapter.setStoreList(storeListData);
                        storeDetailListAdapter.notifyDataSetChanged();

                    }
                }
            });
        }else{
            viewModel.storeMapApiData(centerLatitude,centerLongitude);
            Log.d("넘어온 MapCenter", ""+centerLatitude+", "+centerLongitude);
            viewModel.getStoreListLiveData().observe(getViewLifecycleOwner(), new Observer<List<StoreListData>>() {
                @Override
                public void onChanged(List<StoreListData> storeListData) {
                    if (storeListData.size() == 0) {
                        Log.d("검색 리스트 없음 ", " 검색리스트 갯수 : " + storeListData.size());
                        setNotificationVisible();
                    } else {
                        Log.d("가게메인리스트4", "가게메인리스트4");
                        setNotificationInVisible();
                        storeDetailListAdapter.setStoreList(storeListData);
                        storeDetailListAdapter.notifyDataSetChanged();

                    }
                }
            });
        }
//        viewModel.searchDetailList();
//        Log.d("가게메인리스트3", "가게메인리스트3");

        storeDetailListAdapter.setLikeBtnListener(new StoreDetailListAdapter.LikeBtnListener() {
            @Override
            public void onLikeButton(Long storeId) {
                viewModel.StoreActiveLikeData(storeId);
            }
        });

        storeDetailListAdapter.setCancleLikeBtnListener(new StoreDetailListAdapter.CancleLikeBtnListener() {
            @Override
            public void onCancleLiketButton(Long storeId) {
                viewModel.StoreInactiveLikeData(storeId);
            }
        });

        btn_page_to_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.filterData();
                viewModel.getFilterDataLiveData().observe(getViewLifecycleOwner(), new Observer<filterData>() {
                    @Override
                    public void onChanged(filterData filterData) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("distance",filterData.getDistance());
                        bundle.putFloat("latitude", filterData.getLatitude());
                        bundle.putFloat("longitude",filterData.getLongitude());
                        bundle.putBoolean("fromList", true);
                        mapMainFragment.setArguments(bundle);
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame, mapMainFragment,"mapMainFragment").addToBackStack("mapMainFragment").commit();
                    }
                });
            }
        });

        tt_recomanded_tag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.storeApiDataFromRecommand();
                viewModel.getStoreListFromRecommendLiveData().observe(getViewLifecycleOwner(), new Observer<List<StoreListData>>() {
                    @Override
                    public void onChanged(List<StoreListData> storeListData) {
                        if (storeListData.size() == 0) {
                            Log.d("검색 리스트 없음 ", " 검색리스트 갯수 : " + storeListData.size());
                            setNotificationVisible();
                        } else {
                            Log.d("가게메인리스트4", "가게메인리스트4");
                            setNotificationInVisible();
                            storeDetailListAdapter.setStoreList(storeListData);
                            storeDetailListAdapter.notifyDataSetChanged();

                        }
                    }
                });
            }
        });


        return view;
    }

    public void setNotificationVisible() {
        layout.setVisibility(View.INVISIBLE);
        sheep.setVisibility(View.VISIBLE);
        tt_not_found.setVisibility(View.VISIBLE);
        tt_set_more_option.setVisibility(View.VISIBLE);
    }

    public void setNotificationInVisible() {
        layout.setVisibility(View.VISIBLE);
        sheep.setVisibility(View.INVISIBLE);
        tt_not_found.setVisibility(View.INVISIBLE);
        tt_set_more_option.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onSearchClick(View view, StoreListData searchData, int position) {

//        StoreDetailPageDDFragment detailFragment = new StoreDetailPageDDFragment();
        StoreDetailPageFragment detailFragment = new StoreDetailPageFragment();

        Bundle bundle = new Bundle();
        bundle.putString("storeImage", storeList.get(position).getImages());
        bundle.putString("storeName", storeList.get(position).getStoreName());
        bundle.putString("storeAddress", storeList.get(position).getAddress());
        bundle.putInt("storeRating", storeList.get(position).getStars());
        bundle.putInt("storeReview", storeList.get(position).getReviewCount());
        bundle.putLong("storeIdFromD", storeList.get(position).getStoreId());

        detailFragment.setArguments(bundle);

        // 프래그먼트 트랜잭션 시작
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frame, detailFragment);  // R.id.container는 당신의 FrameLayout 또는 호스트 뷰의 ID여야 합니다.
        transaction.addToBackStack(null);
        transaction.commit();

        /* 가게의 스토어 아이디를 액티비티 뷰모델로 넘김*/
        viewModel.setStoreIdLiveData(storeList.get(position).getStoreId());

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}