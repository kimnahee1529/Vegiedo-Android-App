package com.devinsight.vegiedo.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.devinsight.vegiedo.R;
import com.devinsight.vegiedo.data.ui.map.MapStoreCardUiData;

import java.util.ArrayList;

public class MapStoreCardAdapter extends RecyclerView.Adapter<MapStoreCardAdapter.cardViewHolder> {

    private ArrayList<MapStoreCardUiData> cardDataList;
    Context context;
    protected mapCardItemListner mapCardItemListner;

    public MapStoreCardAdapter(Context context, ArrayList<MapStoreCardUiData> cardList, mapCardItemListner itemListner ){
        this.cardDataList = cardList;
        this.context = context;
        this.mapCardItemListner = itemListner;
    }

    @NonNull
    @Override
    public MapStoreCardAdapter.cardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_map_viewpager2_item,parent,false);
        return new cardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MapStoreCardAdapter.cardViewHolder holder, int position) {
        holder.setData(cardDataList.get(position));
    }

    @Override
    public int getItemCount() {
        return cardDataList.size();
    }

    public class cardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private CardView mapCardView;
        private ImageView storeImage;
        private TextView storeName;
        private TextView storeTag1;
        private TextView storeTag2;
        private TextView address;
        private RatingBar starRating;
        private TextView distance;
        private TextView reviewers;
        private ToggleButton like;
        MapStoreCardUiData cardData;


        public cardViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mapCardView = itemView.findViewById(R.id.map_cardView);
            storeImage = itemView.findViewById(R.id.map_card_image);
            storeName = itemView.findViewById(R.id.map_card_store_name);
            storeTag1 = itemView.findViewById(R.id.map_store_tag1);
            storeTag2 = itemView.findViewById(R.id.map_store_tag2);
            address = itemView.findViewById(R.id.map_store_address);
            starRating = itemView.findViewById(R.id.map_ratingbar_star);
            distance = itemView.findViewById(R.id.map_store_distance);
            reviewers = itemView.findViewById(R.id.map_store_reviewes);
            like = itemView.findViewById(R.id.btn_map_store_like);
        }

//        public void setData(MapStoreCardUiData cardData){
//            this.cardData = cardData;
//
//            storeImage.setImageResource(cardData.getStoreImage());
//            storeName.setText(cardData.getStoreName());
//            storeTag1.setText(cardData.getStoreTag1());
//            storeTag2.setText(cardData.getStoreTag2());
//            address.setText(cardData.getAddress());
//            starRating.setNumStars(cardData.getStarlating());
//            distance.setText(cardData.getDistance());
//            reviewers.setText(cardData.getReviewNum() + " reviews");
//            like.setChecked(cardData.isLike());
//        }

        public void setData(MapStoreCardUiData cardData){
            this.cardData = cardData;

            // Using Glide to load image from URL or file path
            Glide.with(context)
                    .load(cardData.getStoreImage())
                    .into(storeImage);
            storeName.setText(cardData.getStoreName());

            // Convert int values to String to display in TextViews
            storeTag1.setText(String.valueOf(cardData.getStoreTag1()));
            storeTag2.setText(String.valueOf(cardData.getStoreTag2()));
            address.setText(cardData.getAddress());
            starRating.setRating(cardData.getStarlating()); // Use setRating instead of setNumStars
            distance.setText(String.valueOf(cardData.getDistance()) + " m"); // assuming distance is in meters
            reviewers.setText(cardData.getReviewNum() + " reviews");
            like.setChecked(cardData.isLike());
        }


        @Override
        public void onClick(View view) {
            if(mapCardItemListner != null){
                mapCardItemListner.onCardClick(cardData);
            }
        }
    }

    public interface mapCardItemListner{
        void onCardClick(MapStoreCardUiData item);
    }
}
