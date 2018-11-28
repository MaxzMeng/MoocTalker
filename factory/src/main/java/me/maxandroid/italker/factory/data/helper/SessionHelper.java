package me.maxandroid.italker.factory.data.helper;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import me.maxandroid.italker.factory.model.db.Session;
import me.maxandroid.italker.factory.model.db.Session_Table;

public class SessionHelper {
    public static Session findFromLocal(String id) {
        return SQLite.select()
                .from(Session.class)
                .where(Session_Table.id.eq(id))
                .querySingle();
    }
}
