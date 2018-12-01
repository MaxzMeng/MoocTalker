package me.maxandroid.italker.factory.presenter.message;

import android.support.v7.util.DiffUtil;

import java.util.List;

import me.maxandroid.italker.factory.data.message.SessionDataSource;
import me.maxandroid.italker.factory.data.message.SessionRepository;
import me.maxandroid.italker.factory.model.db.Session;
import me.maxandroid.italker.factory.presenter.BaseSourcePresenter;
import me.maxandroid.italker.factory.utils.DiffUiDataCallback;

public class SessionPresenter extends BaseSourcePresenter<Session, Session,
        SessionDataSource, SessionContract.View> implements SessionContract.Presenter {

    public SessionPresenter(SessionContract.View view) {
        super(new SessionRepository(), view);
    }

    @Override
    public void onDataLoaded(List<Session> sessions) {
        SessionContract.View view = getView();
        if (view == null)
            return;

        // 差异对比
        List<Session> old = view.getRecyclerAdapter().getItems();
        DiffUiDataCallback<Session> callback = new DiffUiDataCallback<>(old, sessions);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);

        // 刷新界面
        refreshData(result, sessions);
    }
}