package me.maxandroid.italker.factory.presenter;

import android.support.annotation.StringRes;

import me.maxandroid.italker.common.widget.recycler.RecyclerAdapter;

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

    interface RecyclerView<T extends Presenter, ViewMode> extends View<T> {
        RecyclerAdapter<ViewMode> getRecyclerAdapter();

        void onAdapterDataChanged();
    }
}
