package test.rchandnani.com.testapplication;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.rchandnani.floaters.base.Floater;
import com.rchandnani.floaters.base.FloaterContainer;

/**
 * Created by rchandnani on 20/10/17
 */

public class TestFloater1 extends Floater implements View.OnClickListener {

    public TestFloater1(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.test;
    }

    @Override
    public void onViewCreated(View view) {
        Button one = (Button) view.findViewById(R.id.next);
        one.setOnClickListener(this);
        one.setText("First - next");
        Button two = (Button) view.findViewById(R.id.previous);
        two.setOnClickListener(this);
        two.setText("First - previous");
    }

    @Override
    public Bundle onSaveInstance() {
        return null;
    }

    @Override
    public void onRecreateInstance() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next: {
                FloaterContainer.getInstance().push(new TestFloater2(getContext()));
                break;
            }
            case R.id.previous: {

                break;
            }
        }
    }

}