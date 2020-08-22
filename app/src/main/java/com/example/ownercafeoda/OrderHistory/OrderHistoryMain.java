package com.example.ownercafeoda.OrderHistory;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ownercafeoda.R;

import java.util.ArrayList;
import java.util.List;

public class OrderHistoryMain extends AppCompatActivity {

    RecyclerView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_history_main);
        list = findViewById(R.id.rlist);
        OrderHistoryItemDTO item;
        List<OrderHistoryItemDTO> recycler_history_data = new ArrayList<OrderHistoryItemDTO>();

     /*   item = new OrderHistoryItemDTO("민정", "2020-04-28 13:12:00","2020-04-28 13:15:00","아메리카노/ICE/1","5000");
        recycler_history_data.add(item);
        item = new OrderHistoryItemDTO("혜란", "2020-04-28 13:12:00","2020-04-28 13:15:00","아메리카노/ICE/1","5000");
        recycler_history_data.add(item);
        item = new OrderHistoryItemDTO("성식", "2020-04-28 13:12:00","2020-04-28 13:15:00","아메리카노/ICE/1","5000");
        recycler_history_data.add(item);
        item = new OrderHistoryItemDTO("정윤", "2020-04-28 13:12:00","2020-04-28 13:15:00","아메리카노/ICE/1","5000");
        recycler_history_data.add(item);
        item = new OrderHistoryItem("혜란", "2020-04-28 13:12:00","2020-04-28 13:15:00","아메리카노/ICE/1","5000");
        recycler_history_data.add(item);
        item = new OrderHistoryItem("혜란", "2020-04-28 13:12:00","2020-04-28 13:15:00","아메리카노/ICE/1","5000");
        recycler_history_data.add(item);
        item = new OrderHistoryItem("혜란", "2020-04-28 13:12:00","2020-04-28 13:15:00","아메리카노/ICE/1","5000");
        recycler_history_data.add(item);*/



        OrderHistoryAdapter myadapter = new OrderHistoryAdapter(this,
                R.layout.order_history_item,
                recycler_history_data);


        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());

        manager.setOrientation(LinearLayoutManager.VERTICAL);
        list.setLayoutManager(manager);

        list.setAdapter(myadapter);


    }
}
