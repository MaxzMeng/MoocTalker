package me.maxandroid.italker.factory.data.helper;

import me.maxandroid.italker.factory.Factory;
import me.maxandroid.italker.factory.R;
import me.maxandroid.italker.factory.data.DataSource;
import me.maxandroid.italker.factory.model.api.RspModel;
import me.maxandroid.italker.factory.model.api.account.AccountRspModel;
import me.maxandroid.italker.factory.model.api.account.RegisterModel;
import me.maxandroid.italker.factory.model.db.User;
import me.maxandroid.italker.factory.net.Network;
import me.maxandroid.italker.factory.net.RemoteService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountHelper {

    public static void register(RegisterModel model, final DataSource.Callback<User> callback) {
        RemoteService service = Network.getRetrofit().create(RemoteService.class);
        Call<RspModel<AccountRspModel>> call = service.accountRegister(model);
        call.enqueue(new Callback<RspModel<AccountRspModel>>() {
            @Override
            public void onResponse(Call<RspModel<AccountRspModel>> call,
                                   Response<RspModel<AccountRspModel>> response) {
                RspModel<AccountRspModel> rspModel = response.body();
                if (rspModel.success()) {
                    AccountRspModel accountRspModel = rspModel.getResult();
                    if (accountRspModel.isBind()) {
                        User user = accountRspModel.getUser();
                        callback.onDataLoaded(user);
                    } else {
                        bindPush(callback);
                    }
                } else {
                    Factory.decodeRspCode(rspModel, callback);
                }
            }

            @Override
            public void onFailure(Call<RspModel<AccountRspModel>> call, Throwable t) {
                callback.onDataNotAvailable(R.string.data_network_error);
            }
        });
    }

    public static void bindPush(final DataSource.Callback<User> callback) {
        callback.onDataNotAvailable(R.string.app_name);
    }
}
