package com.devinsight.vegiedo.data.request;

public class StoreReportRequestDTO {

    private String reportType;
    private String opinion;

    public StoreReportRequestDTO(String reportType, String opinion) {
        this.reportType = reportType;
        this.opinion = opinion;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }
}
