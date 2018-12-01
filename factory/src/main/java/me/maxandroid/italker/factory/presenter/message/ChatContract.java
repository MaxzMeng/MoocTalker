package me.maxandroid.italker.factory.presenter.message;

import me.maxandroid.italker.factory.model.db.Group;
import me.maxandroid.italker.factory.model.db.Message;
import me.maxandroid.italker.factory.model.db.User;
import me.maxandroid.italker.factory.presenter.BaseContract;

public interface ChatContract {
    interface Presenter extends BaseContract.Presenter {
        // 发送文字
        void pushText(String content);

        // 发送语音
        void pushAudio(String path);

        // 发送图片
        void pushImages(String[] paths);

        // 重新发送一个消息，返回是否调度成功
        boolean rePush(Message message);
    }

    // 界面的基类
    interface View<InitModel> extends BaseContract.RecyclerView<Presenter, Message> {
        // 初始化的Model
        void onInit(InitModel model);
    }

    // 人聊天的界面
    interface UserView extends View<User> {

    }

    // 群聊天的界面
    interface GroupView extends View<Group> {

    }
}