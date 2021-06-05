package com.example.proskurina.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BigData {

    @SerializedName("result")
    @Expose
    private List<ResponseData> result = null;
    @SerializedName("totalCount")
    @Expose
    private Integer totalCount;

    public List<ResponseData> getResult() {
        return result;
    }

    public void setResult(List<ResponseData> result) {
        this.result = result;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

}