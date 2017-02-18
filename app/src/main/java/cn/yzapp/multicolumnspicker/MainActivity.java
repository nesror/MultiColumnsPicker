package cn.yzapp.multicolumnspicker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import cn.yzapp.multicolumnspickerlib.MultiColumnsPicker;
import cn.yzapp.multicolumnspickerlib.listener.OnSelected;
import cn.yzapp.multicolumnspickerlib.Mapper;

public class MainActivity extends AppCompatActivity {

    private MultiColumnsPicker<City> mCityColumnSicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mCityColumnSicker = (MultiColumnsPicker) findViewById(R.id.city_columns_picker);

        final List<City> province = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            province.add(new City(i, "省" + i));
            if (i == 5) {
                province.get(i).checked = true;
            }
        }

        mCityColumnSicker.setPageCount(2);
        mCityColumnSicker.setMapper(new Mapper<City>() {
            @Override
            public String getString(City city) {
                return city.cityName;
            }

            @Override
            public boolean isChecked(City city) {
                return city.checked;
            }

            @Override
            public void setChecked(City city, boolean checked) {
                city.checked = checked;
            }
        });
        mCityColumnSicker.setOnSelected(new OnSelected<City>() {
            @Override
            public void onSelected(int page, City chooseCity) {
                if (page == 0) {
                    List<City> city = new ArrayList<>();
                    for (int i = 0; i < 100; i++) {
                        city.add(new City(i, chooseCity.cityName + "市" + i));
                    }
                    mCityColumnSicker.setContent(1, city);
                }
            }
        });
        mCityColumnSicker.setContent(0, province);

        List<City> city = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            city.add(new City(i, province.get(5).cityName + "市" + i));
        }
        mCityColumnSicker.setContent(1, city);
    }
}
