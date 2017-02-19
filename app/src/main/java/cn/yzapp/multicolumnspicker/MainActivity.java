package cn.yzapp.multicolumnspicker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.List;

import cn.yzapp.multicolumnspickerlib.MultiColumnsPicker;
import cn.yzapp.multicolumnspickerlib.adapter.ColumnAdapter;
import cn.yzapp.multicolumnspickerlib.listener.OnSelected;
import cn.yzapp.multicolumnspickerlib.Mapper;

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
