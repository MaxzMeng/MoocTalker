package me.maxandroid.italker.factory.data.message;

import me.maxandroid.italker.factory.model.card.MessageCard;

public interface MessageCenter {
    void dispatch(MessageCard... cards);
}
