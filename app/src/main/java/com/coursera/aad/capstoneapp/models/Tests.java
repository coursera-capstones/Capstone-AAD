package com.coursera.aad.capstoneapp.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Tests implements Serializable {
    @SerializedName("1M_pop")
    private String m_pop = null;
    private long total = 0;

    public Tests() {
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
