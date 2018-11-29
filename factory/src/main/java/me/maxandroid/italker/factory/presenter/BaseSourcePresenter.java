package me.maxandroid.italker.factory.presenter;

import java.util.List;

import me.maxandroid.italker.factory.data.DataSource;
import me.maxandroid.italker.factory.data.DbDataSource;

public abstract class BaseSourcePresenter<Data, ViewModel,
        Source extends DbDataSource<Data>,
        View extends BaseContract.RecyclerView>
        extends BaseRecyclerPresenter<ViewModel, View>
        implements DataSource.SucceedCallback<List<Data>> {

    protected Source mSource;

    public BaseSourcePresenter(Source source, View view) {
        super(view);
        this.mSource = source;
    }

    @Override
    public void start() {
        super.start();
        if (mSource != null) {
            mSource.load(this);
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        if (mSource != null) {
            mSource.dispose();
        }
    }
}
