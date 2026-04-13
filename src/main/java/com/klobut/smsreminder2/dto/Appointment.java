package com.klobut.smsreminder2.dto;

import com.google.api.client.util.DateTime;

import java.util.Objects;

public class Appointment {
    private String summary;
    private String startDate;
    private String phone;

    public Appointment(String summary, String startDate, String phone) {
        this.summary = summary;
        this.startDate = startDate;
        this.phone = phone;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Appointment that = (Appointment) o;
        return Objects.equals(summary, that.summary) && Objects.equals(startDate, that.startDate) && Objects.equals(phone, that.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(summary, startDate, phone);
    }
}
