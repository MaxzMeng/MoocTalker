package me.maxandroid.italker.factory.data.user;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

import me.maxandroid.italker.factory.data.BaseDbRepository;
import me.maxandroid.italker.factory.data.DataSource;
import me.maxandroid.italker.factory.model.db.User;
import me.maxandroid.italker.factory.model.db.User_Table;
import me.maxandroid.italker.factory.persistence.Account;

public class ContactRepository extends BaseDbRepository<User> implements ContactDataSource {
    @Override
    public void load(DataSource.SucceedCallback<List<User>> callback) {
        super.load(callback);
        SQLite.select()
                .from(User.class)
                .where(User_Table.isFollow.eq(true))
                .and(User_Table.id.notEq(Account.getUserId()))
                .orderBy(User_Table.name, true)
                .limit(100)
                .async()
                .queryListResultCallback(this)
                .execute();
    }

    @Override
    protected boolean isRequired(User user) {
        return user.isFollow() && !user.getId().equals(Account.getUserId());
    }
}