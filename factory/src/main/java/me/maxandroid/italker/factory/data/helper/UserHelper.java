package me.maxandroid.italker.factory.data.helper;


import me.maxandroid.italker.factory.Factory;
import me.maxandroid.italker.factory.R;
import me.maxandroid.italker.factory.data.DataSource;
import me.maxandroid.italker.factory.model.api.RspModel;
import me.maxandroid.italker.factory.model.api.user.UserUpdateModel;
import me.maxandroid.italker.factory.model.card.UserCard;
import me.maxandroid.italker.factory.model.db.User;
import me.maxandroid.italker.factory.net.Network;
import me.maxandroid.italker.factory.net.RemoteService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserHelper {
    public static void update(UserUpdateModel model, final DataSource.Callback<UserCard> callback) {
        RemoteService service = Network.remote();
        Call<RspModel<UserCard>> call = service.userUpdate(model);
        call.enqueue(new Callback<RspModel<UserCard>>() {
            @Override
            public void onResponse(Call<RspModel<UserCard>> call, Response<RspModel<UserCard>> response) {
                RspModel<UserCard> rspModel = response.body();
                if (rspModel.success()) {
                    UserCard userCard = rspModel.getResult();
                    User user = userCard.build();
                    user.save();
                    callback.onDataLoaded(userCard);
                } else {
                    Factory.decodeRspCode(rspModel, callback);
                }
            }

            @Override
            public void onFailure(Call<RspModel<UserCard>> call, Throwable t) {
                callback.onDataNotAvailable(R.string.data_network_error);
            }
        });
    }
}
