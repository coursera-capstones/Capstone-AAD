package com.coursera.aad.capstoneapp.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Cases implements Serializable {
    @SerializedName("new")
    private String newCase = "";
    private long active = 0;
    private String critical = null;
    private long recovered = 0;
    @SerializedName("1M_pop")
    private String m_pop = "";
    private long total = 0;

    public Cases() {
    }

    public String getNewCase() {
        return newCase;
    }

    public void setNewCase(String newCase) {
        this.newCase = newCase;
    }

    public long getActive() {
        return active;
    }

    public void setActive(long active) {
        this.active = active;
    }

    public String getCritical() {
        return critical;
    }

    public void setCritical(String critical) {
        this.critical = critical;
    }

    public long getRecovered() {
        return recovered;
    }

    public void setRecovered(long recovered) {
        this.recovered = recovered;
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
