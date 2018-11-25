package me.maxandroid.italker.factory.net;

import me.maxandroid.italker.common.Common;
import me.maxandroid.italker.factory.Factory;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Network {

    // 构建一个Retrofit
    public static Retrofit getRetrofit() {
        // 得到一个OK Client

        OkHttpClient client = new OkHttpClient.Builder()
                .build();


        Retrofit.Builder builder = new Retrofit.Builder();

        return builder.baseUrl(Common.Constance.API_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(Factory.getGson()))
                .build();

    }

}
