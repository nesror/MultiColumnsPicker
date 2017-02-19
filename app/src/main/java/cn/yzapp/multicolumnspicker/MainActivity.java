package cn.yzapp.multicolumnspicker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import cn.yzapp.multicolumnspickerlib.MultiColumnsPicker;
import cn.yzapp.multicolumnspickerlib.adapter.ColumnAdapter;
import cn.yzapp.multicolumnspickerlib.listener.OnSelected;
import cn.yzapp.multicolumnspickerlib.Mapper;

public class MainActivity extends AppCompatActivity {

    private MultiColumnsPicker<Address> mCityColumnSicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mCityColumnSicker = (MultiColumnsPicker) findViewById(R.id.city_columns_picker);

        mCityColumnSicker.setAdapter(1, new MultiColumnsPicker.OnAdapterProvide<Address>() {
            @Override
            public ColumnAdapter<Address> provideAdapter(Mapper<Address> mapper, List<Address> data) {
                return new AddressAdapter<>(data, mapper);
            }
        });
        mCityColumnSicker.setMapper(new Mapper<Address>() {
            @Override
            public String getString(Address address) {
                return address.name;
            }

            @Override
            public boolean isChecked(Address address) {
                return address.checked;
            }

            @Override
            public void setChecked(Address address, boolean checked) {
                address.checked = checked;
            }
        });
        mCityColumnSicker.setOnSelected(new OnSelected<Address>() {
            @Override
            public void onSelected(int page, Address chooseAddress) {
                if (page == Address.PROVINCE) {
                    mCityColumnSicker.setContent(Address.CITY, AddressFactory.getAddressList(Address.CITY));
                }
            }
        });
        mCityColumnSicker.setContent(Address.PROVINCE, AddressFactory.getAddressList(Address.PROVINCE));

        mCityColumnSicker.setContent(Address.CITY, AddressFactory.getAddressList(Address.CITY));
    }
}
