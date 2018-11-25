package me.maxandroid.italker.frags.account;


import android.content.Context;

import me.maxandroid.italker.R;
import me.maxandroid.italker.common.app.Fragment;

public class LoginFragment extends Fragment {
    private AccountTrigger mAccountTrigger;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mAccountTrigger = (AccountTrigger) context;
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    public void onResume() {
        super.onResume();
        mAccountTrigger.triggerView();
    }
}
