package app.nexd.com.androidTeam.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import app.nexd.com.androidTeam.R;

/**
 * Created by lawrence on 2015/11/10.
 */
public class BaseActivity extends Activity implements View.OnClickListener {

    public ImageButton leftImageButton;
    public TextView titleTextView;
    public ImageButton rightImageButton;
    public RelativeLayout bottonLayout;
    public TextView leftTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_layout);
        leftImageButton = (ImageButton) findViewById(R.id.leftImageButton);
        leftTextView = (TextView) findViewById(R.id.leftText);
        titleTextView = (TextView) findViewById(R.id.titleTextView);
        rightImageButton = (ImageButton) findViewById(R.id.rightImageButton);
        bottonLayout = (RelativeLayout) findViewById(R.id.bottomLaytou);
        this.setListener();
    }

    private void setListener() {
        leftImageButton.setOnClickListener(this);
        rightImageButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.leftImageButton) {
            this.finish();
        }
    }
}
