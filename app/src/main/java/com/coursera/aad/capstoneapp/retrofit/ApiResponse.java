package com.coursera.aad.capstoneapp.retrofit;

import android.os.Parcel;
import android.os.Parcelable;

public class ApiResponse<T> implements Parcelable {

    private String get;
//    private String[] parameters;
    private String[] errors;
    private int results;
    private T response;

    protected ApiResponse(Parcel in) {
        get = in.readString();
//        parameters = in.createStringArray();
        errors = in.createStringArray();
        results = in.readInt();
    }

    public static final Creator<ApiResponse> CREATOR = new Creator<ApiResponse>() {
        @Override
        public ApiResponse createFromParcel(Parcel in) {
            return new ApiResponse(in);
        }

        @Override
        public ApiResponse[] newArray(int size) {
            return new ApiResponse[size];
        }
    };

    public String getGet() {
        return get;
    }

    public void setGet(String get) {
        this.get = get;
    }

    public String[] getParameters() {
        return null;
    }

    public void setParameters(String[] parameters) {
//        this.parameters = parameters;
    }

    public String[] getErrors() {
        return errors;
    }

    public void setErrors(String[] errors) {
        this.errors = errors;
    }

    public int getResults() {
        return results;
    }

    public void setResults(int results) {
        this.results = results;
    }

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(get);
//        parcel.writeStringArray(parameters);
        parcel.writeStringArray(errors);
        parcel.writeInt(results);
        parcel.writeParcelable((Parcelable) response, flags);
    }
}
