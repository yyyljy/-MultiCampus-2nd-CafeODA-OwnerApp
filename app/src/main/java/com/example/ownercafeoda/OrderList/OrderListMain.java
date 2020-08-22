package com.example.ownercafeoda.OrderList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.ownercafeoda.R;

import java.util.ArrayList;
import java.util.List;

public class OrderListMain extends AppCompatActivity {

    RecyclerView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_list_main);
        list = findViewById(R.id.rlist);
        OrderListItemDTO item;
        List<OrderListItemDTO> recycler_order_data = new ArrayList<OrderListItemDTO>();

        item = new OrderListItemDTO("민정", "01074965575","2020-04-28 13:12:00","아메리카노/ICE/1");
        recycler_order_data.add(item);
        item = new OrderListItemDTO("혜란", "01074965575","2020-04-28 13:12:00","아메리카노/ICE/1");
        recycler_order_data.add(item);
        item = new OrderListItemDTO("성식", "01074965575","2020-04-28 13:12:00","아메리카노/ICE/1");
        recycler_order_data.add(item);
        item = new OrderListItemDTO("정윤", "01074965575","2020-04-28 13:12:00","아메리카노/ICE/1");
        recycler_order_data.add(item);
        item = new OrderListItemDTO("민정", "01074965575","2020-04-28 13:12:00","아메리카노/ICE/1");
        recycler_order_data.add(item);
        item = new OrderListItemDTO("민정", "01074965575","2020-04-28 13:12:00","아메리카노/ICE/1");
        recycler_order_data.add(item);
        item = new OrderListItemDTO("민정", "01074965575","2020-04-28 13:12:00","아메리카노/ICE/1");
        recycler_order_data.add(item);

        /*OrderListAdapter myadapter = new OrderListAdapter(this,
                R.layout.order_list_item,
                recycler_order_data);

        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());

        manager.setOrientation(LinearLayoutManager.VERTICAL);
        list.setLayoutManager(manager);

        list.setAdapter(myadapter);*/


    }
}
