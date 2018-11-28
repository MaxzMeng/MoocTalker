package me.maxandroid.italker.factory.data.user;

import java.util.List;

import me.maxandroid.italker.factory.data.DataSource;
import me.maxandroid.italker.factory.model.db.User;

public interface ContactDataSource {
    void load(DataSource.SucceedCallback<List<User>> callback);

    void dispose();
}
