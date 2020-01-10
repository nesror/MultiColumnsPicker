package cn.yzapp.multicolumnspicker;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void horizontal(View view) {
        startActivity(new Intent(this, MultiColumnsActivity.class));
    }

    public void vertical(View view) {
        startActivity(new Intent(this, VerticalActivity.class));
    }

}
