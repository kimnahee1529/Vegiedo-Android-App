package com.devinsight.vegiedo.service.api;

import com.devinsight.vegiedo.data.request.StoreModifyRequestDTO;
import com.devinsight.vegiedo.data.request.StoreRegisterRequestDTO;
import com.devinsight.vegiedo.data.request.StoreReportRequestDTO;
import com.devinsight.vegiedo.data.response.StampBookInquiryResponseDTO;
import com.devinsight.vegiedo.data.response.StoreInquiryResponseDTO;
import com.devinsight.vegiedo.data.response.StoreListData;
import com.devinsight.vegiedo.data.response.StoreListData;
import com.devinsight.vegiedo.data.response.StoreListInquiryResponseDTO;
import com.devinsight.vegiedo.view.store.StoreDetailData;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface StoreApiService {

    //가게 리스트 조회
    @GET("stores")
    Call<List<StoreListData>> getStoreLists(
            @Query("tags") List<String> tags,
            @Query("latitude") float latitude,
            @Query("longitude") float longitude,
            @Query("distance") int distance,
            @Query("count") int count,
            @Query("cursor") int cursor,
            @Header("Authorization") String token
    );



    //가게 등록
    @Multipart
    @POST("/stores")
    Call<Void> createStore(
            @Header("Authorization") String token,
            @Part("storeName") RequestBody storeName,
            @Part("address") RequestBody address,
            @Part MultipartBody.Part images,
            @Part("tags") RequestBody tags,
            @Part("latitude") RequestBody latitude,
            @Part("longitude") RequestBody longitude
    );


    //가게 조회
    @GET("/stores/{storeId}")
    Call<StoreInquiryResponseDTO> readStore(
            @Header("Authorization") String token,
            @Path("storeId") Long storeId
    );

    //가게 수정
    @PATCH("/stores/{storeId}")
    Call<Void> updateStore(
            @Header("Authorization") String token,
            @Path("storeId") Long storeId,
            @Body StoreModifyRequestDTO modifyStore
    );

    //가게 삭제
    @DELETE("/stores/{storeId}")
    Call<Void> deleteStore(
            @Header("Authorization") String token,
            @Path("storeId") Long storeId
    );

    //가게 신고(폐점)
    @POST("/stores/{storeId}/reports")
    Call<Void> reportStore(
            @Header("Authorization") String token,
            @Path("storeId") Long storeId
    );

    //TODO 반환값이 없는지 보고 수정해야 함
    //가게 좋아요
    @POST("/stores/{storeId}/likes")
    Call<Void> likeStore(
            @Header("Authorization") String token,
            @Path("storeId") Long storeId
    );

    //가게 좋아요 취소
    @DELETE("/stores/{storeId}/likes")
    Call<Void> cancleLikeStore(
            @Header("Authorization") String token,
            @Path("storeId") Long storeId
    );

    //가게 스탬프
    @POST("/stores/{storeId}/stamps")
    Call<Void> activeStamp(
            @Header("Authorization") String token,
            @Path("storeId") Long storeId
    );

    //가게 스탬프 취소
    @DELETE("/stores/{storeId}/stamps")
    Call<Void> inactiveStamp(
            @Header("Authorization") String token,
            @Path("storeId") Long storeId
    );

    //마이페이지
    @GET("stores/stamps")
    Call<StampBookInquiryResponseDTO> myPageStampBook(
            @Header("Authorization") String token
    );
    //검색어 추천
}
