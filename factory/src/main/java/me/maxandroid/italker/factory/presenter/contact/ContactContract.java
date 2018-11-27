package me.maxandroid.italker.factory.presenter.contact;

import me.maxandroid.italker.factory.model.db.User;
import me.maxandroid.italker.factory.presenter.BaseContract;

public interface ContactContract {
    interface Presenter extends BaseContract.Presenter {

    }

    interface View extends BaseContract.RecyclerView<Presenter, User> {

    }
}
