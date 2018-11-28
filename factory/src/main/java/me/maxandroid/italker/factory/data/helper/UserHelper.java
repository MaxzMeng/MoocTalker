package me.maxandroid.italker.factory.data.helper;


import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

import me.maxandroid.italker.factory.Factory;
import me.maxandroid.italker.factory.R;
import me.maxandroid.italker.factory.data.DataSource;
import me.maxandroid.italker.factory.model.api.RspModel;
import me.maxandroid.italker.factory.model.api.user.UserUpdateModel;
import me.maxandroid.italker.factory.model.card.UserCard;
import me.maxandroid.italker.factory.model.db.User;
import me.maxandroid.italker.factory.model.db.User_Table;
import me.maxandroid.italker.factory.net.Network;
import me.maxandroid.italker.factory.net.RemoteService;
import me.maxandroid.italker.utils.CollectionUtil;
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
                    Factory.getUserCenter().dispatch(userCard);
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

    public static Call search(String name, final DataSource.Callback<List<UserCard>> callback) {
        RemoteService service = Network.remote();
        Call<RspModel<List<UserCard>>> call = service.userSearch(name);

        call.enqueue(new Callback<RspModel<List<UserCard>>>() {
            @Override
            public void onResponse(Call<RspModel<List<UserCard>>> call, Response<RspModel<List<UserCard>>> response) {
                RspModel<List<UserCard>> rspModel = response.body();
                if (rspModel.success()) {
                    callback.onDataLoaded(rspModel.getResult());
                } else {
                    Factory.decodeRspCode(rspModel, callback);
                }
            }

            @Override
            public void onFailure(Call<RspModel<List<UserCard>>> call, Throwable t) {
                callback.onDataNotAvailable(R.string.data_network_error);
            }
        });
        return call;
    }

    public static void follow(String id, final DataSource.Callback<UserCard> callback) {
        RemoteService service = Network.remote();
        Call<RspModel<UserCard>> call = service.userFollow(id);

        call.enqueue(new Callback<RspModel<UserCard>>() {
            @Override
            public void onResponse(Call<RspModel<UserCard>> call, Response<RspModel<UserCard>> response) {
                RspModel<UserCard> rspModel = response.body();
                if (rspModel.success()) {
                    UserCard userCard = rspModel.getResult();
                    Factory.getUserCenter().dispatch(userCard);
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

    public static void refreshContacts() {
        RemoteService service = Network.remote();
        service.userContacts()
                .enqueue(new Callback<RspModel<List<UserCard>>>() {
                    @Override
                    public void onResponse(Call<RspModel<List<UserCard>>> call, Response<RspModel<List<UserCard>>> response) {
                        RspModel<List<UserCard>> rspModel = response.body();
                        if (rspModel.success()) {
                            List<UserCard> cards = rspModel.getResult();
                            if (cards == null || cards.size() == 0)
                                return;
                            UserCard[] cards1 = cards.toArray(new UserCard[0]);
                            Factory.getUserCenter().dispatch(cards1);

                        } else {
                            Factory.decodeRspCode(rspModel, null);
                        }
                    }

                    @Override
                    public void onFailure(Call<RspModel<List<UserCard>>> call, Throwable t) {
                    }
                });
    }

    public static User findFromLocal(String id) {
        return SQLite.select()
                .from(User.class)
                .where(User_Table.id.eq(id))
                .querySingle();
    }

    public static User findFromNet(String id) {
        RemoteService remoteService = Network.remote();
        try {
            Response<RspModel<UserCard>> response = remoteService.userFind(id).execute();
            UserCard card = response.body().getResult();
            if (card != null) {
                User user = card.build();
                Factory.getUserCenter().dispatch(card);
                return user;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    public static User search(String id) {
        User user = findFromLocal(id);
        if (user == null) {
            return findFromNet(id);
        }
        return user;
    }
    public static User searchFirstOfNet(String id) {
        User user = findFromNet(id);
        if (user == null) {
            return findFromLocal(id);
        }
        return user;
    }

}
