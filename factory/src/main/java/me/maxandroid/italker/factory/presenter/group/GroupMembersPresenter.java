package me.maxandroid.italker.factory.presenter.group;

import java.util.List;

import me.maxandroid.italker.factory.Factory;
import me.maxandroid.italker.factory.data.helper.GroupHelper;
import me.maxandroid.italker.factory.model.db.view.MemberUserModel;
import me.maxandroid.italker.factory.presenter.BaseRecyclerPresenter;

public class GroupMembersPresenter extends BaseRecyclerPresenter<MemberUserModel, GroupMembersContract.View>
        implements GroupMembersContract.Presenter {

    public GroupMembersPresenter(GroupMembersContract.View view) {
        super(view);
    }

    @Override
    public void refresh() {
        // 显示Loading
        start();

        // 异步加载
        Factory.runOnAsync(loader);
    }

    private Runnable loader = new Runnable() {
        @Override
        public void run() {
            GroupMembersContract.View view = getView();
            if (view == null)
                return;

            String groupId = view.getGroupId();

            // 传递数量为-1 代表查询所有
            List<MemberUserModel> models = GroupHelper.getMemberUsers(groupId, -1);

            refreshData(models);
        }
    };
}
