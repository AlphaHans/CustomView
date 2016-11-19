package xyz.hans.customview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import xyz.hans.customview.view.RippleButton;
import xyz.hans.customview.view.pie.PieData;
import xyz.hans.customview.view.pie.PieView;

public class MainActivity extends AppCompatActivity {
    private RippleButton mBtnRipple;
    private TextView mTvInRipple;
    private PieView mPieView;

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

        mPieView = (PieView) findViewById(R.id.main_pie_view);
        ArrayList<PieData> list = new ArrayList<>();
        list.add(new PieData("优秀", 20));
        list.add(new PieData("中等", 20));
        list.add(new PieData("良好", 60));
        mPieView.setData(list);
    }
}
