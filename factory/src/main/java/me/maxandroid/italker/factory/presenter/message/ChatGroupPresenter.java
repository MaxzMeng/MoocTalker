package me.maxandroid.italker.factory.presenter.message;

import java.util.List;

import me.maxandroid.italker.factory.data.helper.GroupHelper;
import me.maxandroid.italker.factory.data.message.MessageGroupRepository;
import me.maxandroid.italker.factory.model.db.Group;
import me.maxandroid.italker.factory.model.db.Message;
import me.maxandroid.italker.factory.model.db.view.MemberUserModel;
import me.maxandroid.italker.factory.persistence.Account;

public class ChatGroupPresenter extends ChatPresenter<ChatContract.GroupView>
        implements ChatContract.Presenter {

    public ChatGroupPresenter(ChatContract.GroupView view, String receiverId) {
        // 数据源，View，接收者，接收者的类型
        super(new MessageGroupRepository(receiverId), view, receiverId, Message.RECEIVER_TYPE_GROUP);
    }

    @Override
    public void start() {
        super.start();

        // 拿群的信息
        Group group = GroupHelper.findFromLocal(mReceiverId);
        if (group != null) {
            // 初始化操作
            ChatContract.GroupView view = getView();

            boolean isAdmin = Account.getUserId().equalsIgnoreCase(group.getOwner().getId());
            view.showAdminOption(isAdmin);

            // 基础信息初始化
            view.onInit(group);

            // 成员初始化
            List<MemberUserModel> models = group.getLatelyGroupMembers();
            final long memberCount = group.getGroupMemberCount();
            // 没有显示的成员的数量
            long moreCount = memberCount - models.size();
//            moreCount = 10;
            view.onInitGroupMembers(models, moreCount);
        }

    }
}