package com.paulomelo.tripserver.dto;

public class TripResponse {

    private Object data;
    private ErrorResponse error;

    private TripResponse() {}

    public static TripResponse withData(Object data) {
        return new TripResponse().setData(data);
    }

    public static TripResponse withError(ErrorResponse error) {
        return new TripResponse().setError(error);
    }

    public Object getData() {
        return data;
    }

    public ErrorResponse getError() {
        return error;
    }

    private TripResponse setData(Object data) {
        this.data = data;
        return this;
    }

    private TripResponse setError(ErrorResponse error) {
        this.error = error;
        return this;
    }
}
