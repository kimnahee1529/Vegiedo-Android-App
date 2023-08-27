package com.devinsight.vegiedo.data.response;

import java.util.List;

public class PostInquiryResponseDTO {
    private Long postId;
    private String postTitle;
    private String userName;
    private String content;
    private List<String> images;

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public PostInquiryResponseDTO(Long postId, String postTitle, String userName, String content, List<String> images) {
        this.postId = postId;
        this.postTitle = postTitle;
        this.userName = userName;
        this.content = content;
        this.images = images;
    }
}
