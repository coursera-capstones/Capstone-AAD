package com.coursera.aad.capstoneapp.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Deaths implements Serializable {
    @SerializedName("new")
    private String newD = null;
    @SerializedName("1M_pop")
    private String m_pop = null;
    private long total = 0;

    public Deaths() {
    }

    public String getNewD() {
        return newD;
    }

    public void setNewD(String newD) {
        this.newD = newD;
    }

    public String getM_pop() {
        return m_pop;
    }

    public void setM_pop(String m_pop) {
        this.m_pop = m_pop;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
