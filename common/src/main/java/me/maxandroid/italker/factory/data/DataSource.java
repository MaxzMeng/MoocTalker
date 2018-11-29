package me.maxandroid.italker.factory.data;

import android.support.annotation.StringRes;

public interface DataSource {
    interface Callback<T> extends SucceedCallback<T>, FailedCallback {

    }

    interface SucceedCallback<T> {
        void onDataLoaded(T t);
    }

    interface FailedCallback {
        void onDataNotAvailable(@StringRes int strRes);
    }

    void dispose();
}
