package me.maxandroid.italker.factory.presenter.account;

import android.text.TextUtils;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

import me.maxandroid.italker.factory.R;
import me.maxandroid.italker.factory.data.DataSource;
import me.maxandroid.italker.factory.data.helper.AccountHelper;
import me.maxandroid.italker.factory.model.api.account.LoginModel;
import me.maxandroid.italker.factory.model.db.User;
import me.maxandroid.italker.factory.persistence.Account;
import me.maxandroid.italker.factory.presenter.BasePresenter;

public class LoginPresenter extends BasePresenter<LoginContract.View>
        implements LoginContract.Presenter, DataSource.Callback<User> {
    public LoginPresenter(LoginContract.View view) {
        super(view);
    }

    @Override
    public void login(String phone, String password) {
        start();

        final LoginContract.View view = getView();

        if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(password)) {
            view.showError(R.string.data_account_login_invalid_parameter);
        } else {
            LoginModel model = new LoginModel(phone, password, Account.getPushId());
            AccountHelper.login(model, this);
        }
    }

    @Override
    public void onDataLoaded(User user) {
        final LoginContract.View view = getView();
        if (view == null)
            return;
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                view.loginSuccess();
            }
        });
    }

    @Override
    public void onDataNotAvailable(final int strRes) {
        final LoginContract.View view = getView();
        if (view == null)
            return;
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                view.showError(strRes);
            }
        });
    }
}
