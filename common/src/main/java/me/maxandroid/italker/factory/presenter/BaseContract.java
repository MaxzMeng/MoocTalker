package me.maxandroid.italker.factory.presenter;

import android.support.annotation.StringRes;

public interface BaseContract {
    interface View<T extends Presenter> {

        void showError(@StringRes int str);

        void showLoading();

        void setPresenter(T presenter);
    }

    interface Presenter {

        void start();

        void destroy();
    }
}
