package com.framgia.moneymanagement.data;

public interface DataCallback<T> {
    void onGetDataSucces(T data);

    void onGetDataFailed(String msg);
}
