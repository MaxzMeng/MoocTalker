package me.maxandroid.italker.activities;

import me.maxandroid.italker.MainActivity;
import me.maxandroid.italker.R;
import me.maxandroid.italker.common.app.Activity;
import me.maxandroid.italker.frags.assist.PermissionFragment;

public class LaunchActivity extends Activity {

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_launch;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (PermissionFragment.haveAll(this, getSupportFragmentManager())) {
            MainActivity.show(this);
            finish();
        }
    }
}
