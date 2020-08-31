package com.example.myfood.Activity.Popup;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myfood.Activity.Login;
import com.example.myfood.Class.FoodItem;
import com.example.myfood.Class.Group;
import com.example.myfood.Class.User;
import com.example.myfood.Fragment.FoodStock;
import com.example.myfood.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AddFoodList extends Activity implements AdapterView.OnItemSelectedListener {
    private int width;
    private int heigh;
    private NumberPicker addNumberPicker;
    private Button addBtn;
    private Spinner categorySpinner, unitsSpiner;
    private String currentCategory, currentUnit;
    private ImageView foodImage;
    private Context context;
    private DatabaseReference reff_group;
    private DatabaseReference reff_foodItem;
    public User user = new User();
    public Group group=new Group();
    private String itemUrl;
    private final String cheeseUrlPic = "https://hameshek.co.il/wp-content/uploads/2019/07/7290002824183-600x600.jpg";
    private final String milkUrlPic = "https://www.offisal.co.il/wp-content/uploads/2017/11/5a13cb221b43705b84a7d136029d184701f13a1c.jpg";
    private final String sugarUrlPic = "https://images.theconversation.com/files/307440/original/file-20191217-58292-nlmvmh.jpg?ixlib=rb-1.1.0&q=45&auto=format&w=926&fit=clip";
    private final String spaghettiUrlPic = "https://previews.123rf.com/images/pavlok/pavlok1804/pavlok180400192/100084317-stack-of-raw-spaghetti-isolated-on-white-background.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }


        setContentView(R.layout.activity_add_food_list);

        Intent intent = getIntent();
        if(intent != null){
            this.user = (User) intent.getExtras().getSerializable(Login.LOGIN_USER_KEY);
            this.group = (Group) intent.getExtras().getSerializable(Login.LOGIN_GROUP_KEY);
        }


        context = this;
        categorySpinner = findViewById(R.id.category_spinner);
        unitsSpiner = findViewById(R.id.units_spinner);
        addNumberPicker = findViewById(R.id.add_numberPicker);
        addBtn = findViewById(R.id.add_food_item);
        foodImage = findViewById(R.id.food_image_add);

        addNumberPicker.setMinValue(0);
        addNumberPicker.setMaxValue(1000);


        ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(this, R.array.categories, android.R.layout.simple_spinner_item);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);
        categorySpinner.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> unitsAdapter = ArrayAdapter.createFromResource(this, R.array.units, android.R.layout.simple_spinner_item);
        unitsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unitsSpiner.setAdapter(unitsAdapter);
        unitsSpiner.setOnItemSelectedListener(this);


        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int foodItemHashCode = this.hashCode();
                FoodItem foodItem = new FoodItem(currentCategory, addNumberPicker.getValue(), currentUnit, itemUrl, user, group, foodItemHashCode);
                FoodStock.foodList.add(foodItem);
                finish();
                FoodStock.mAdapter.notifyDataSetChanged();

                //insert data to firebase- not working

                reff_group = FirebaseDatabase.getInstance().getReference("Groups").child(group.getGroupName());
                reff_foodItem = FirebaseDatabase.getInstance().getReference("FoodItems");

                reff_foodItem.push().setValue(foodItem);
                group.addFoodItemToAvailableItems(foodItem.getId());
                reff_group.setValue(group);

                Toast.makeText(context, "המוצר נוסף בהצלחה", Toast.LENGTH_SHORT).show();


            }
        });


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        width = dm.widthPixels;
        heigh = dm.heightPixels;

        getWindow().setLayout((int) (width * .6), (int) (heigh * .5));


        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;

        getWindow().setAttributes(params);

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.category_spinner:
                currentCategory = parent.getItemAtPosition(position).toString();
              {
                    switch (parent.getItemAtPosition(position).toString()) {
                        case "גבינה לבנה":
                            Picasso.get()
                                    .load(cheeseUrlPic)
                                    .fit()
                                    .centerCrop()
                                    .into(foodImage);
                            itemUrl = cheeseUrlPic;
                            break;
                        case "חלב":
                            Picasso.get()
                                    .load(milkUrlPic)
                                    .fit()
                                    .centerCrop()
                                    .into(foodImage);
                            itemUrl = milkUrlPic;
                            break;
                        case "סוכר":
                            Picasso.get()
                                    .load(sugarUrlPic)
                                    .fit()
                                    .centerCrop()
                                    .into(foodImage);
                            itemUrl = sugarUrlPic;
                            break;
                        case "ספגטי":
                            Picasso.get()
                                    .load(spaghettiUrlPic)
                                    .fit()
                                    .centerCrop()
                                    .into(foodImage);
                            itemUrl = spaghettiUrlPic;
                            break;
                    }

                }
                break;
            case R.id.units_spinner:
                currentUnit = parent.getItemAtPosition(position).toString();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}
