package me.maxandroid.italker.activities;

import android.content.Context;

import me.maxandroid.italker.R;
import me.maxandroid.italker.common.app.Activity;
import me.maxandroid.italker.factory.model.Author;

public class MessageActivity extends Activity {

    public static void show(Context context, Author author) {

    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_message;
    }

}