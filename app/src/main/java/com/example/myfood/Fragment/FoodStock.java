package com.example.myfood.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfood.Activity.Login;
import com.example.myfood.Activity.Popup.AddFoodList;
import com.example.myfood.Activity.Popup.EditFoodList;
import com.example.myfood.Class.FoodItem;
import com.example.myfood.Adapter.FoodListAdapter;
import com.example.myfood.Class.Group;
import com.example.myfood.Class.User;
import com.example.myfood.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class FoodStock extends Fragment  {

    public static ArrayList<FoodItem> foodList;
    private RecyclerView mRecyclerView;
    public static FoodListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Button addBtn;
    DatabaseReference reff;
   // private FoodItem newFoodItem;
    private User user;
    private Group group;
    private Bundle bundle;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_food_stock, container, false);

        reff = FirebaseDatabase.getInstance().getReference("FoodItems");

        //not passing here when clikingo on addButton
        if (getArguments() != null) {
            this.user = (User) getArguments().get(Login.LOGIN_USER_KEY);
            this.group = (Group) getArguments().get(Login.LOGIN_GROUP_KEY);

        }


        addBtn=view.findViewById(R.id.add_food_item);
     //   foodItemName = view.findViewById(R.id.food_name);
      //  add_numberPicker =  view.findViewById(R.id.add_numberPicker);
       // addAmount = view.findViewById(R.id.amountTV);


        foodList = new ArrayList<>();
      //  if(Integer.parseInt(Login.birthDayET.getText().toString())<=20){
            foodList.add(new FoodItem("חמאה", 200, "גרם","https://dairyfarmersofcanada.ca/sites/default/files/product_butter_thumb.jpg",user,group,111));
            foodList.add(new FoodItem("קמח", 2, "קילוגרם","https://www.apk-inform.com/uploads/Redakciya/2019/%D0%98%D1%8E%D0%BD%D1%8C/%D0%BC%D1%83%D0%BA%D0%B0.jpg",user,group,222));
            foodList.add(new FoodItem("ביצים", 12, "יחידות","https://chriskresser.com/wp-content/uploads/iStock-172696992.jpg",user,group,333));



        mRecyclerView = view.findViewById(R.id.food_stock_recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mAdapter = new FoodListAdapter(foodList);

        mRecyclerView.setLayoutManager(mLayoutManager);
       mRecyclerView.setAdapter(mAdapter);


        mAdapter.setOnItemClickListener(new FoodListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent=new Intent(getContext(), EditFoodList.class);
                bundle.putSerializable(Login.LOGIN_USER_KEY, user);
                bundle.putSerializable(Login.LOGIN_GROUP_KEY, group);
                intent.putExtras(bundle);
                intent.putExtra("foodItem",foodList.get(position));
                startActivity(intent);
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getContext(), AddFoodList.class);
                bundle = new Bundle(); //must create it here or on top
                bundle.putSerializable(Login.LOGIN_USER_KEY, user);
                bundle.putSerializable(Login.LOGIN_GROUP_KEY, group);
                intent.putExtras(bundle);

                startActivity(intent);

            }
        });


        return view;


    }


}
