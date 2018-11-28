package me.maxandroid.italker.factory.data.message;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import me.maxandroid.italker.factory.data.helper.DbHelper;
import me.maxandroid.italker.factory.data.helper.GroupHelper;
import me.maxandroid.italker.factory.data.helper.MessageHelper;
import me.maxandroid.italker.factory.data.helper.UserHelper;
import me.maxandroid.italker.factory.model.card.MessageCard;
import me.maxandroid.italker.factory.model.db.Group;
import me.maxandroid.italker.factory.model.db.Message;
import me.maxandroid.italker.factory.model.db.User;

public class MessageDispatcher implements MessageCenter {
    private static MessageCenter instance;
    private final Executor executor = Executors.newSingleThreadExecutor();

    public static MessageCenter instance() {
        if (instance == null) {
            synchronized (MessageDispatcher.class) {
                if (instance == null) {
                    instance = new MessageDispatcher();
                }
            }
        }
        return instance;
    }

    @Override
    public void dispatch(MessageCard... cards) {
        executor.execute(new MessageCardHandler(cards));
    }

    private class MessageCardHandler implements Runnable {
        private final MessageCard[] cards;

        MessageCardHandler(MessageCard[] cards) {
            this.cards = cards;
        }

        @Override
        public void run() {
            List<Message> messages = new ArrayList<>();
            for (MessageCard card : cards) {
                if (card == null || TextUtils.isEmpty(card.getSenderId())
                        || TextUtils.isEmpty(card.getId())
                        || (TextUtils.isEmpty(card.getReceiverId())
                        && TextUtils.isEmpty(card.getGroupId())))
                    continue;

                Message message = MessageHelper.findFromLocal(card.getId());
                if (message != null) {
                    if (message.getStatus() == Message.STATUS_DONE)
                        continue;
                    if (card.getStatus() == Message.STATUS_DONE)
                        message.setCreateAt(card.getCreateAt());
                    message.setContent(card.getContent());
                    message.setAttach(card.getAttach());
                    message.setStatus(card.getStatus());
                } else {
                    User sender = UserHelper.search(card.getSenderId());
                    User receiver = null;
                    Group group = null;
                    if (!TextUtils.isEmpty(card.getReceiverId())) {
                        receiver = UserHelper.search(card.getReceiverId());
                    } else if (!TextUtils.isEmpty(card.getGroupId())) {
                        group = GroupHelper.findFromLocal(card.getGroupId());
                    }
                    if (receiver == null && group == null)
                        continue;

                    message = card.build(sender, receiver, group);
                }
                messages.add(message);
            }
            if (messages.size() > 0)
                DbHelper.save(Message.class, messages.toArray(new Message[0]));
        }
    }
}
