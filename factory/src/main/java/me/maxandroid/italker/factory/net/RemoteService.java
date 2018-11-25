package me.maxandroid.italker.factory.net;

import me.maxandroid.italker.factory.model.api.RspModel;
import me.maxandroid.italker.factory.model.api.account.AccountRspModel;
import me.maxandroid.italker.factory.model.api.account.RegisterModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RemoteService {
    @POST("account/register")
    Call<RspModel<AccountRspModel>> accountRegister(@Body RegisterModel model);

}
