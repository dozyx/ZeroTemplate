package com.zerofate.androidsdk.model;

public class StatusData<T> {

    private String message;
    private Status status;
    private T data;

    private StatusData(Status status, T data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static <T> StatusData<T> success(T data) {
        return new StatusData<>(Status.SUCCESS, data, "");
    }

    public static <T> StatusData<T> error(String msg, T data) {
        return new StatusData<>(Status.ERROR, data, msg);
    }

    public static <T> StatusData<T> loading(T data) {
        return new StatusData<>(Status.LOADING, data, "");
    }

}
