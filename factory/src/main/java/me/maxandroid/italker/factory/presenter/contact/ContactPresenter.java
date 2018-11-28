package me.maxandroid.italker.factory.presenter.contact;

import android.support.v7.util.DiffUtil;

import java.util.List;

import me.maxandroid.italker.common.widget.recycler.RecyclerAdapter;
import me.maxandroid.italker.factory.data.DataSource;
import me.maxandroid.italker.factory.data.helper.UserHelper;
import me.maxandroid.italker.factory.data.user.ContactDataSource;
import me.maxandroid.italker.factory.data.user.ContactRepository;
import me.maxandroid.italker.factory.model.db.User;
import me.maxandroid.italker.factory.presenter.BaseRecyclerPresenter;
import me.maxandroid.italker.factory.utils.DiffUiDataCallback;

public class ContactPresenter extends BaseRecyclerPresenter<User, ContactContract.View>
        implements ContactContract.Presenter, DataSource.SucceedCallback<List<User>> {

    private ContactDataSource mSource;

    public ContactPresenter(ContactContract.View view) {
        super(view);
        mSource = new ContactRepository();
    }


    @Override
    public void start() {
        super.start();
        mSource.load(this);

        UserHelper.refreshContacts();
    }

    @Override
    public void onDataLoaded(List<User> users) {
        final ContactContract.View view = getView();
        if (view == null)
            return;

        RecyclerAdapter<User> adapter = view.getRecyclerAdapter();
        List<User> old = adapter.getItems();

        DiffUtil.Callback callback = new DiffUiDataCallback<>(old, users);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);

        refreshData(result, users);
    }

    @Override
    public void destroy() {
        super.destroy();
        mSource.dispose();
    }
}
