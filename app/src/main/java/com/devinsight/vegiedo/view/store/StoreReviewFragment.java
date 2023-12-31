package com.devinsight.vegiedo.view.store;

import static com.devinsight.vegiedo.view.store.UserReviewItem.ItemType.REVIEW_RC;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.devinsight.vegiedo.R;
import com.devinsight.vegiedo.data.request.ReviewReportRequestDTO;
import com.devinsight.vegiedo.data.response.ReviewListInquiryResponseDTO;
import com.devinsight.vegiedo.view.search.ActivityViewModel;


import java.util.ArrayList;
import java.util.List;

public class StoreReviewFragment extends Fragment {
    private RecyclerView recyclerView;
    private List<UserReviewItem> userReviewItems = new ArrayList<>();
    private UserReviewItemAdapter adapter;
    ActivityViewModel viewModel;
    private static final String ARG_STORE_ID = "storeId";
    private Long mStoreId;
    boolean canWriteReview;
    Button review_more_btn;
//    public boolean canWriteReview = true;

    public static StoreReviewFragment newInstance(Long storeId) {
        Log.d("리뷰", "리뷰화면에 들어옴");
        StoreReviewFragment fragment = new StoreReviewFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_STORE_ID, storeId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mStoreId = getArguments().getLong(ARG_STORE_ID);
            // 이제 mStoreId 변수에 storeId 값이 저장되어 있습니다.
            // 필요에 따라 이 변수를 사용하면 됩니다.
        }
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(requireActivity()).get(ActivityViewModel.class);
        return inflater.inflate(R.layout.fragment_store_review, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.store_review_recycler_view);
        setupRecyclerView();

        review_more_btn = view.findViewById(R.id.Review_moreButton);
//        canWriteReview = viewModel.getCanWriteReview().getValue();
//        Log.d("리뷰 작성 가능함? reviewF", String.valueOf(canWriteReview));

        //어댑터에서의 더보기 버튼 리스너
        adapter.setMoreItemsListener(new UserReviewItemAdapter.MoreItemsListener() {
            @Override
            public void onHideMoreButton() {
                review_more_btn.setVisibility(View.GONE);  // "더보기" 버튼 숨기기
                Log.d("review더보기", "onHideMoreButton");
            }
        });
        //어댑터에서의 삭제 버튼 리스너
        adapter.setDeleteListener(new UserReviewItemAdapter.ReviewDeleteListener() {
            @Override
            public void ReviewDelete(Long storeId, Long reviewId) {
                Log.d("리뷰삭제", "ReviewDelete");
                viewModel.ReviewDeleteData(storeId, reviewId);
                viewModel.setCanWriteReview(true);

                // 삭제하면 아이템의 위치 부분을 알아내 바로 삭제되게 하는 부분
                for (int i = 0; i < userReviewItems.size(); i++) {
                    UserReviewItem item = userReviewItems.get(i);
                    if (item.getReviewId() != null && item.getReviewId().equals(reviewId)) {
                        userReviewItems.remove(i);
                        adapter.notifyItemRemoved(i);  // Notify the adapter about the removed item.
                        break;
                    }
                }
            }
        });

        //어댑터에서의 신고완료 버튼 리스너
        adapter.setReportListener(new UserReviewItemAdapter.ReportCompleteListener() {
            @Override
            public void ReportListener(Long storeId, Long reviewId, ReviewReportRequestDTO requestDTO) {
                Log.d("report완료", "ReportListener storeId:"+storeId+", reviewId:"+reviewId+", DTO:"+requestDTO);
                viewModel.ReviewReportData(storeId, reviewId, requestDTO);
            }
        });

        review_more_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.showMoreItems();
            }
        });

        callReviewAPI();
    }

    private void setupRecyclerView() {
        recyclerView.setNestedScrollingEnabled(false);
        adapter = new UserReviewItemAdapter(userReviewItems, viewModel);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }


    private void callReviewAPI() {
        Log.d("리뷰", "리뷰 api 호출");
        viewModel.getReviewLiveData().observe(getViewLifecycleOwner(), new Observer<ReviewListInquiryResponseDTO>() {
            @Override
            public void onChanged(ReviewListInquiryResponseDTO data) {
                if (data != null) {
                    Log.d("리뷰", "리뷰 api "+data.getReviews());
                    List<UserReviewItem> updatedItems = new ArrayList<>();
                    int reviewsSize = data.getReviews().size();
                    Log.d("리뷰 개수", String.valueOf(reviewsSize));
                    //내가 쓴 리뷰가 없을 때일 경우를 대비해 무조건 쓸 수 있게(true)로 만들어놓고
                    //내가 쓴 리뷰가 있을 때 false로 바꿈. 160줄
                    viewModel.setCanWriteReview(true);
                    for (int i = 0; i < reviewsSize; i++) {
                        String userName = data.getReviews().get(i).getUserName();
                        Integer star = data.getReviews().get(i).getStar();
                        String content = data.getReviews().get(i).getContent();
                        List<String> images = data.getReviews().get(i).getImages();
                        ArrayList<String> userReviewImageUrlList = new ArrayList<>(images);
                        Long reviewId = data.getReviews().get(i).getReviewId();
                        boolean isMine = data.getReviews().get(i).isMine();
                        //만약 내가 쓴 리뷰가 있을 경우
                        if(isMine){
                            Log.d("내가 쓴 리뷰가 있음!! reviewF", String.valueOf(isMine));
                            viewModel.setCanWriteReview(false);
                        }else{
                            Log.d("내가 쓴 리뷰가 아님 reviewF", String.valueOf(isMine));
                        }
                        UserReviewItem item = new UserReviewItem(reviewId, REVIEW_RC, userName, star, content, userReviewImageUrlList, isMine);
                        updatedItems.add(item);
                    }

                    for(int i = 0; i < reviewsSize / 3; i++){
                        int index = i * 4 + 3;
                        Log.d("리뷰", String.valueOf(index));
                        updatedItems.add(index, new UserReviewItem(UserReviewItem.ItemType.AD_BANNER));
                    }

                    userReviewItems = updatedItems;
                    Log.d("어댑터로 보내는 updatedItems", updatedItems.toString());
                    adapter.setReviewItems(updatedItems);  // 어댑터에 데이터 전달
//                    adapter.notifyDataSetChanged();

                    // 리뷰가 3개 미만일 때는 더보기 버튼 숨김
                    if(userReviewItems.size() < 3){
                        review_more_btn.setVisibility(View.GONE);
                        Log.d("review더보기", "getItemCount<3");
                    } else {
                        review_more_btn.setVisibility(View.VISIBLE);  // Optional, to ensure the button is visible when there are 3 or more items
                    }
                }
            }

        });
        // 데이터 로드
        viewModel.ReviewInquiryData(mStoreId, 100, 0, false);
        // 상세페이지를 보여주는 가게의 storeId
        viewModel.setStoreIdLiveData(mStoreId);
    }

}