package com.paulomelo.carserver.dto;

public class CarResponse {

    private Object data;
    private ErrorResponse error;

    private CarResponse() {}

    public static CarResponse withData(Object data) {
        return new CarResponse().setData(data);
    }

    public static CarResponse withError(ErrorResponse error) {
        return new CarResponse().setError(error);
    }

    public Object getData() {
        return data;
    }

    public ErrorResponse getError() {
        return error;
    }

    private CarResponse setData(Object data) {
        this.data = data;
        return this;
    }

    private CarResponse setError(ErrorResponse error) {
        this.error = error;
        return this;
    }

}
