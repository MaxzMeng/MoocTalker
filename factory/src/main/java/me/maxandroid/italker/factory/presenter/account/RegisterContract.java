package me.maxandroid.italker.factory.presenter.account;

import android.support.annotation.StringRes;

import me.maxandroid.italker.factory.presenter.BaseContract;

public interface RegisterContract {
    interface View extends BaseContract.View<Presenter> {
        void registerSuccess();

        void showError(@StringRes int str);

        void showLoading();

        void setPresenter(Presenter presenter);
    }

    interface Presenter extends BaseContract.Presenter {
        void register(String phone, String name, String password);

        boolean checkMobile(String phone);
    }
}
