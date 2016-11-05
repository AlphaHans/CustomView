package xyz.hans.customview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import xyz.hans.customview.view.RippleButton;

public class MainActivity extends AppCompatActivity {
    private RippleButton mBtnRipple;
    private TextView mTvInRipple;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtnRipple = (RippleButton) findViewById(R.id.main_btn_ripple);
        mBtnRipple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "BtnRipple点击", Toast.LENGTH_SHORT).show();
            }
        });
        mTvInRipple = (TextView) findViewById(R.id.main_tv_ripple_layout);
        mTvInRipple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "InBtnRipple点击", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
