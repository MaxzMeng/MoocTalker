package me.maxandroid.italker.frags.message;


import me.maxandroid.italker.R;
import me.maxandroid.italker.factory.model.db.Group;
import me.maxandroid.italker.factory.presenter.message.ChatContract;


public class ChatGroupFragment extends ChatFragment<Group>
        implements ChatContract.GroupView {


    public ChatGroupFragment() {
        // Required empty public constructor
    }


    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_chat_group;
    }

    @Override
    protected ChatContract.Presenter initPresenter() {
        return null;
    }

    @Override
    public void onInit(Group group) {

    }
}

