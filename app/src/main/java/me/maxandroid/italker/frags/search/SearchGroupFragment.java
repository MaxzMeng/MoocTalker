package me.maxandroid.italker.frags.search;


import java.util.List;

import me.maxandroid.italker.R;
import me.maxandroid.italker.activities.SearchActivity;
import me.maxandroid.italker.common.app.PresenterFragment;
import me.maxandroid.italker.factory.model.card.GroupCard;
import me.maxandroid.italker.factory.presenter.search.SearchContract;

public class SearchGroupFragment extends PresenterFragment<SearchContract.Presenter>
        implements SearchActivity.SearchFragment,SearchContract.GroupView {


    @Override
    public void search(String content) {

    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_search_group;
    }

    @Override
    protected SearchContract.Presenter initPresenter() {
        return null;
    }

    @Override
    public void onSearchDone(List<GroupCard> groupCards) {

    }
}
