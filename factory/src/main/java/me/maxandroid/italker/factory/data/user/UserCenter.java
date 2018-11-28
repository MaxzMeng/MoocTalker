package me.maxandroid.italker.factory.data.user;

import me.maxandroid.italker.factory.model.card.UserCard;

public interface UserCenter {
    void dispatch(UserCard... cards);
}
