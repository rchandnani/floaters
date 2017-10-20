package test.rchandnani.com.testapplication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;

import com.rchandnani.floaters.base.FloaterContainer;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (!canDrawOverOtherApps(this)) {
            startActivity(
                    new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName())));
        } else {
            FloaterContainer.init(this);
            FloaterContainer.getInstance().push(new TestFloater1(this));
        }
    }

    public static boolean canDrawOverOtherApps(Context context) {
        return Settings.canDrawOverlays(context);
    }

}
