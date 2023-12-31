package com.devinsight.vegiedo.data.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CommentListData {

    @Expose
    @SerializedName("commentId") private Long commentId;
    @Expose
    @SerializedName("commentContent")private String commentContent;
    @Expose
    @SerializedName("userName")private String userName;
    @Expose
    @SerializedName("createdAt")private String createdAt;
    @Expose
    @SerializedName("report")private boolean report;

    public CommentListData(Long commentId, String commentContent, String userName, String createdAt, boolean report) {
        this.commentId = commentId;
        this.commentContent = commentContent;
        this.userName = userName;
        this.createdAt = createdAt;
        this.report = report;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isReport() {
        return report;
    }

    public void setReport(boolean report) {
        this.report = report;
    }
}
