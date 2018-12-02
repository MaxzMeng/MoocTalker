package me.maxandroid.italker.factory.presenter.group;

import me.maxandroid.italker.factory.model.db.Group;
import me.maxandroid.italker.factory.presenter.BaseContract;

public interface GroupsContract {
    // 什么都不需要额外定义，开始就是调用start即可
    interface Presenter extends BaseContract.Presenter {

    }

    // 都在基类完成了
    interface View extends BaseContract.RecyclerView<Presenter, Group> {

    }
}
