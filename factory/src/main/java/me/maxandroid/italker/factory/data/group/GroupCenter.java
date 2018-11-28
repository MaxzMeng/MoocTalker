package me.maxandroid.italker.factory.data.group;

import me.maxandroid.italker.factory.model.card.GroupCard;
import me.maxandroid.italker.factory.model.card.GroupMemberCard;

public interface GroupCenter {
    void dispatch(GroupCard... cards);

    void dispatch(GroupMemberCard... cards);
}