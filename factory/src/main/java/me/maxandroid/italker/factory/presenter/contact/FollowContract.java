package me.maxandroid.italker.factory.presenter.contact;

import me.maxandroid.italker.factory.model.card.UserCard;
import me.maxandroid.italker.factory.presenter.BaseContract;

public interface FollowContract {
    interface Presenter extends BaseContract.Presenter {
        void follow(String id);
    }

    interface View extends BaseContract.View<Presenter> {
        void onFollowSucceed(UserCard userCard);
    }
}