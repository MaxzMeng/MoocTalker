package me.maxandroid.italker.factory.data;

import android.support.annotation.NonNull;

import com.raizlabs.android.dbflow.structure.BaseModel;
import com.raizlabs.android.dbflow.structure.database.transaction.QueryTransaction;

import net.qiujuer.genius.kit.reflect.Reflector;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

import me.maxandroid.italker.factory.data.helper.DbHelper;
import me.maxandroid.italker.factory.model.db.BaseDbModel;
import me.maxandroid.italker.utils.CollectionUtil;

public abstract class BaseDbRepository<Data extends BaseDbModel<Data>>
        implements DbDataSource<Data>,
        DbHelper.ChangedListener<Data>,
        QueryTransaction.QueryResultListCallback<Data> {
    private SucceedCallback<List<Data>> callback;
    private final List<Data> dataList = new LinkedList<>();
    private Class<Data> dataClass;

    public BaseDbRepository() {
        Type[] types = Reflector.getActualTypeArguments(BaseDbRepository.class, this.getClass());
        dataClass = (Class<Data>) types[0];
    }

    @Override
    public void load(SucceedCallback<List<Data>> callback) {
        this.callback = callback;
        registerDbChangedListener();
    }

    @Override
    public void dispose() {
        this.callback = null;
        DbHelper.removeChangedListener(dataClass, this);
        dataList.clear();
    }

    @Override
    public void onDataSave(Data[] list) {
        boolean isChanged = false;
        for (Data data : list) {
            if (isRequired(data)) {
                insertOrUpdate(data);
                isChanged = true;
            }
        }
        if (isChanged)
            notifyDataChange();
    }

    @Override
    public void onDataDelete(Data[] list) {
        boolean isChanged = false;
        for (Data data : list) {
            if (dataList.remove(data))
                isChanged = true;
        }

        if (isChanged)
            notifyDataChange();
    }

    @Override
    public void onListQueryResult(QueryTransaction transaction,
                                  @NonNull List<Data> tResult) {
        if (tResult.size() == 0) {
            dataList.clear();
            notifyDataChange();
            return;
        }

        Data[] users = CollectionUtil.toArray(tResult, dataClass);
        onDataSave(users);
    }

    private void insertOrUpdate(Data data) {
        int index = indexOf(data);
        if (index >= 0) {
            replace(index, data);
        } else {
            insert(data);
        }
    }

    protected void replace(int index, Data data) {
        dataList.remove(index);
        dataList.add(index, data);
    }

    protected void insert(Data data) {
        dataList.add(data);
    }

    protected int indexOf(Data newData) {
        int index = -1;
        for (Data data : dataList) {
            index++;
            if (data.isSame(newData)) {
                return index;
            }
        }
        return -1;
    }

    protected abstract boolean isRequired(Data data);

    protected void registerDbChangedListener() {
        DbHelper.addChangedListener(dataClass, this);
    }

    private void notifyDataChange() {
        SucceedCallback<List<Data>> callback = this.callback;
        if (callback != null)
            callback.onDataLoaded(dataList);
    }
}
