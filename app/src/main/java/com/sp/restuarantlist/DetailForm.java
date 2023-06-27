package com.sp.restuarantlist;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class DetailForm extends AppCompatActivity {
    private EditText restaurantName;
    private RadioGroup restaurantTypes;
    private Button buttonSave;
    private EditText restaurantAddress;
    private EditText restaurantTel;

    private RestaurantHelper helper = null;
    private String restaurantID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_form);

        restaurantName = findViewById(R.id.restauarant_name);
        restaurantTypes = findViewById(R.id.restaurant_types);
        restaurantAddress = findViewById(R.id.restaurant_address);
        restaurantTel = findViewById(R.id.restaurant_tel);

        buttonSave = findViewById(R.id.button_save);
        buttonSave.setOnClickListener(onSave);
        helper = new RestaurantHelper(this);

        restaurantID = getIntent().getStringExtra("ID");
        if (restaurantID != null) {
            load();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        helper.close();
    }

    private void load() {
        Cursor c = helper.getByID(restaurantID);
        c.moveToFirst();
        restaurantName.setText(helper.getRestaurantName(c));
        restaurantAddress.setText(helper.getRestaurantAddress(c));
        restaurantTel.setText(helper.getRestaurantTel(c));

        if (helper.getRestaurantType(c).equals("Chinese")) {
            restaurantTypes.check(R.id.chinese);
        }
        else if (helper.getRestaurantType(c).equals("Indian")) {
            restaurantTypes.check(R.id.indian);
        }
        else if (helper.getRestaurantType(c).equals("Indonesian")) {
            restaurantTypes.check(R.id.indonesian);
        }
        else if (helper.getRestaurantType(c).equals("Korean")) {
            restaurantTypes.check(R.id.korean);
        }
        else if (helper.getRestaurantType(c).equals("Japanese")) {
            restaurantTypes.check(R.id.japanese);
        }
        else {
            restaurantTypes.check(R.id.thai);
        }
    }

    private View.OnClickListener onSave = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String nameStr = restaurantName.getText().toString();
            String addressStr = restaurantAddress.getText().toString();
            String telStr = restaurantTel.getText().toString();
            String restType= "";

            int radioID = restaurantTypes.getCheckedRadioButtonId();
            if (radioID == R.id.chinese) {
                restType = "Chinese";
            } else if (radioID == R.id.western) {
                restType = "Western";
            } else if (radioID == R.id.indian) {
                restType = "Indian";
            } else if (radioID == R.id.indonesian) {
                restType = "Indonesian";
            } else if (radioID == R.id.korean) {
                restType = "Korean";
            } else if (radioID == R.id.japanese) {
                restType = "Japanese";
            } else if (radioID == R.id.thai) {
                restType = "Thai";
            }

            if (restaurantID == null) {
                helper.insert(nameStr, addressStr,telStr,restType);
            }
            else {
                helper.update(restaurantID, nameStr, addressStr ,telStr,restType);
            }
            finish();
        }
    };
}
