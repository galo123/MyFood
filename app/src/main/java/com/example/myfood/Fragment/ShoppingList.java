package com.example.myfood.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfood.Activity.Login;
import com.example.myfood.Activity.Popup.AddFoodList;
import com.example.myfood.Activity.Popup.EditFoodList;
import com.example.myfood.Adapter.FoodListAdapter;
import com.example.myfood.Class.FoodItem;
import com.example.myfood.Class.Group;
import com.example.myfood.Class.User;
import com.example.myfood.R;

import java.util.ArrayList;

public class ShoppingList extends Fragment {
    private RecyclerView mRecyclerView;
    public static FoodListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Button addBtn;
    private ImageButton shareListBtn;

    public User user;
    public Group group;
    Bundle bundle = new Bundle();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_shopping_list,container,false);

        addBtn=view.findViewById(R.id.add_food_item);
        shareListBtn=view.findViewById(R.id.share_list);

        FoodStock.foodList = new ArrayList<>();
        if(Integer.parseInt(Login.birthDayET.getText().toString())<=20){
            FoodStock.foodList.add(new FoodItem("חמאה", 200, "גרם","https://dairyfarmersofcanada.ca/sites/default/files/product_butter_thumb.jpg",user,group,111));
            FoodStock.foodList.add(new FoodItem("קמח", 2, "קילוגרם","https://www.apk-inform.com/uploads/Redakciya/2019/%D0%98%D1%8E%D0%BD%D1%8C/%D0%BC%D1%83%D0%BA%D0%B0.jpg",user,group,222));
            FoodStock.foodList.add(new FoodItem("ביצים", 12, "יחידות","https://chriskresser.com/wp-content/uploads/iStock-172696992.jpg",user,group,333));

        }else {
            FoodStock.foodList.add(new FoodItem("חמאה", 200, "גרם",user,group,111));
            FoodStock.foodList.add(new FoodItem("קמח", 2, "קילוגרם",user,group,222));
            FoodStock.foodList.add(new FoodItem("ביצים", 12, "יחידות",user,group,333));
        }

        this.user = (User) getArguments().get(Login.LOGIN_USER_KEY);
        this.group = (Group) getArguments().get(Login.LOGIN_GROUP_KEY);

        mRecyclerView = view.findViewById(R.id.food_stock_recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mAdapter = new FoodListAdapter(FoodStock.foodList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        shareListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"הרשימה שותפה בהצלחה",Toast.LENGTH_SHORT).show();
            }
        });





        return view;

    }
}
