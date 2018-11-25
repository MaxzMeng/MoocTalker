package me.maxandroid.italker;

import android.content.IntentFilter;

import com.igexin.sdk.PushManager;

import me.maxandroid.italker.common.app.Application;
import me.maxandroid.italker.factory.Factory;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Factory.setup();
        PushManager.getInstance().initialize(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.igexin.sdk.action.sxjLlJy9Lu5lfVuya7c6q");
        MessageReceiver receiver = new MessageReceiver();
        registerReceiver(receiver, intentFilter);
    }
}
