package me.maxandroid.italker.activities;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import me.maxandroid.italker.R;
import me.maxandroid.italker.common.app.Activity;
import me.maxandroid.italker.common.app.Fragment;
import me.maxandroid.italker.frags.user.UpdateInfoFragment;

public class UserActivity extends Activity {


    private Fragment mCurrentFragment;

    public static void show(Context context) {
        Intent intent = new Intent(context, UserActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_user;
    }

    @Override
    protected void initWidget() {
        super.initWidget();

        mCurrentFragment = new UpdateInfoFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.lay_container, mCurrentFragment)
                .commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        mCurrentFragment.onActivityResult(requestCode, resultCode, data);
    }
}
