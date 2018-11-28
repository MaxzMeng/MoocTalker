package me.maxandroid.italker.factory.data.helper;

import android.text.TextUtils;

import me.maxandroid.italker.factory.Factory;
import me.maxandroid.italker.factory.R;
import me.maxandroid.italker.factory.data.DataSource;
import me.maxandroid.italker.factory.model.api.RspModel;
import me.maxandroid.italker.factory.model.api.account.AccountRspModel;
import me.maxandroid.italker.factory.model.api.account.LoginModel;
import me.maxandroid.italker.factory.model.api.account.RegisterModel;
import me.maxandroid.italker.factory.model.db.User;
import me.maxandroid.italker.factory.net.Network;
import me.maxandroid.italker.factory.net.RemoteService;
import me.maxandroid.italker.factory.persistence.Account;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountHelper {

    public static void register(RegisterModel model, final DataSource.Callback<User> callback) {
        RemoteService service = Network.remote();
        Call<RspModel<AccountRspModel>> call = service.accountRegister(model);
        call.enqueue(new AccountRspCallback(callback));
    }

    public static void login(LoginModel model, final DataSource.Callback<User> callback) {
        RemoteService service = Network.remote();
        Call<RspModel<AccountRspModel>> call = service.accountLogin(model);
        call.enqueue(new AccountRspCallback(callback));
    }

    public static void bindPush(final DataSource.Callback<User> callback) {
        String pushId = Account.getPushId();
        if (TextUtils.isEmpty(pushId)) {
            return;
        }
        RemoteService service = Network.remote();
        Call<RspModel<AccountRspModel>> call = service.accountBind(pushId);
        call.enqueue(new AccountRspCallback(callback));
    }


    private static class AccountRspCallback implements Callback<RspModel<AccountRspModel>> {
        final DataSource.Callback<User> callback;

        public AccountRspCallback(DataSource.Callback<User> callback) {
            this.callback = callback;
        }

        @Override
        public void onResponse(Call<RspModel<AccountRspModel>> call,
                               Response<RspModel<AccountRspModel>> response) {
            RspModel<AccountRspModel> rspModel = response.body();
            if (rspModel.success()) {
                AccountRspModel accountRspModel = rspModel.getResult();
                User user = accountRspModel.getUser();
                DbHelper.save(User.class, user);
                Account.login(accountRspModel);
                if (accountRspModel.isBind()) {
                    Account.setBind(true);
                    if (callback != null) {
                        callback.onDataLoaded(user);
                    }
                } else {
                    bindPush(callback);
                }
            } else {
                Factory.decodeRspCode(rspModel, callback);
            }
        }

        @Override
        public void onFailure(Call<RspModel<AccountRspModel>> call, Throwable t) {
            if (callback != null) {
                callback.onDataNotAvailable(R.string.data_network_error);
            }
        }
    }

}
