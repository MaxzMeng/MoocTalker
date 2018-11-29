package me.maxandroid.italker.factory.presenter.contact;

import android.support.v7.util.DiffUtil;

import java.util.List;

import me.maxandroid.italker.common.widget.recycler.RecyclerAdapter;
import me.maxandroid.italker.factory.data.DataSource;
import me.maxandroid.italker.factory.data.helper.UserHelper;
import me.maxandroid.italker.factory.data.user.ContactDataSource;
import me.maxandroid.italker.factory.data.user.ContactRepository;
import me.maxandroid.italker.factory.model.db.User;
import me.maxandroid.italker.factory.presenter.BaseSourcePresenter;
import me.maxandroid.italker.factory.utils.DiffUiDataCallback;

public class ContactPresenter extends BaseSourcePresenter<User, User, ContactDataSource, ContactContract.View>
        implements ContactContract.Presenter, DataSource.SucceedCallback<List<User>> {

    public ContactPresenter(ContactContract.View view) {
        super(new ContactRepository(), view);
    }


    @Override
    public void start() {
        super.start();
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

}
