package me.maxandroid.italker.factory.presenter.search;

import java.util.List;

import me.maxandroid.italker.factory.model.card.GroupCard;
import me.maxandroid.italker.factory.model.card.UserCard;
import me.maxandroid.italker.factory.presenter.BaseContract;

public interface SearchContract {
    interface Presenter extends BaseContract.Presenter {
        void search(String content);
    }

    interface UserView extends BaseContract.View<Presenter> {
        void onSearchDone(List<UserCard> userCards);
    }

    interface GroupView extends BaseContract.View<Presenter> {
        void onSearchDone(List<GroupCard> groupCards);
    }

}