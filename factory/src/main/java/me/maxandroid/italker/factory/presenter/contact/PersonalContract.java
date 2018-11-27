package me.maxandroid.italker.factory.presenter.contact;

import me.maxandroid.italker.factory.model.db.User;
import me.maxandroid.italker.factory.presenter.BaseContract;

public interface PersonalContract {
    interface Presenter extends BaseContract.Presenter {
        User getUserPersonal();
    }

    interface View extends BaseContract.View<Presenter> {
        String getUserId();

        void onLoadDone(User user);

        void allowSayHello(boolean isAllow);

        void setFollowStatus(boolean isFollow);
    }
}
