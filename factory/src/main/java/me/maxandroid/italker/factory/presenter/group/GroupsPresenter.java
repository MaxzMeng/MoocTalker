package me.maxandroid.italker.factory.presenter.group;

import android.support.v7.util.DiffUtil;

import java.util.List;

import me.maxandroid.italker.factory.data.group.GroupsDataSource;
import me.maxandroid.italker.factory.data.group.GroupsRepository;
import me.maxandroid.italker.factory.data.helper.GroupHelper;
import me.maxandroid.italker.factory.model.db.Group;
import me.maxandroid.italker.factory.presenter.BaseSourcePresenter;
import me.maxandroid.italker.factory.utils.DiffUiDataCallback;

public class GroupsPresenter extends BaseSourcePresenter<Group, Group,
        GroupsDataSource, GroupsContract.View> implements GroupsContract.Presenter {

    public GroupsPresenter(GroupsContract.View view) {
        super(new GroupsRepository(), view);
    }

    @Override
    public void start() {
        super.start();

        // 加载网络数据, 以后可以优化到下拉刷新中
        // 只有用户下拉进行网络请求刷新
        GroupHelper.refreshGroups();
    }

    @Override
    public void onDataLoaded(List<Group> groups) {
        final GroupsContract.View view = getView();
        if (view == null)
            return;

        // 对比差异
        List<Group> old = view.getRecyclerAdapter().getItems();
        DiffUiDataCallback<Group> callback = new DiffUiDataCallback<>(old, groups);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);

        // 界面刷新
        refreshData(result, groups);
    }
}
