package cn.yzapp.multicolumnspicker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import cn.yzapp.multicolumnspickerlib.Mapper;
import cn.yzapp.multicolumnspickerlib.MultiColumnsPicker;
import cn.yzapp.multicolumnspickerlib.adapter.ColumnAdapter;
import cn.yzapp.multicolumnspickerlib.listener.OnSelected;

public class MultiColumnsActivity extends AppCompatActivity {

    private MultiColumnsPicker<Address> mCityColumnSicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_columns);
        initView();
    }

    private void initView() {
        mCityColumnSicker = (MultiColumnsPicker) findViewById(R.id.city_columns_picker);

        mCityColumnSicker.setAdapter(Address.CITY, new MultiColumnsPicker.OnAdapterProvide<Address>() {
            @Override
            public ColumnAdapter<Address> provideAdapter(Mapper<Address> mapper, List<Address> data) {
                return new CityAdapter<>(data, mapper);
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
                if (page == Address.CITY) {
                    mCityColumnSicker.setContent(Address.AREA, AddressFactory.getAddressList(Address.AREA));
                }
            }
        });
        mCityColumnSicker.setContent(Address.PROVINCE, AddressFactory.getAddressList(Address.PROVINCE));

        mCityColumnSicker.setContent(Address.CITY, AddressFactory.getAddressList(Address.CITY));

        mCityColumnSicker.setContent(Address.AREA, AddressFactory.getAddressList(Address.AREA));

    }
}
