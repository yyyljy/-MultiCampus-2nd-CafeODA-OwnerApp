package com.example.ownercafeoda.OwnerMain;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.ownercafeoda.MenuAdd.OwnerMenuListFragment;
import com.example.ownercafeoda.OrderHistory.OrderHistoryFragment;
import com.example.ownercafeoda.OrderList.OrderListFragment;
import com.example.ownercafeoda.R;
import com.example.ownercafeoda.SalesAverage.Event;
import com.example.ownercafeoda.SalesAverage.Sales;
import com.example.ownercafeoda.SalesAverage.Statistic;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONException;
import org.json.JSONObject;

public class OwnerMainPageActivity extends AppCompatActivity {

    //JsonObject 변환 변수들
    public String tempcafename = "";
    public String tempaddress = "";
    public String tempweekdayopen = "";
    public String tempweekdayclose = "";
    public String tempweekendopen = "";
    public String tempweekendclose = "";
    public String tempcafetel = "";
    public String tempcafeid = "";
    //JSONObject 변수 끝

    // Menu Add 용 변수 //
    public String tempMenuName = "";
    public String tempMenuCountry = "";
    public String tempMenuPrice = "";
    public int tempProdId = -1;
    // Menu Add 용 변수 끝 //

    // Menu Delete 용 변수 //
    public String tempDtMenuName = "";
    public String tempDtMenuCountry = "";
    public String tempDtMenuNum = "";


    //프래그먼트들//
    OwnerMainPageFragment ownerMainPageFragment = new OwnerMainPageFragment();
    FragmentManager manager = getSupportFragmentManager();
    OrderListFragment orderListFragment = new OrderListFragment();
    Statistic statisticFragment = new Statistic();
    Sales salesFragment = new Sales();
    Event eventFragment = new Event();
    OrderHistoryFragment orderHistoryFragment = new OrderHistoryFragment();
    OwnerMenuListFragment ownerMenuListFragment = new OwnerMenuListFragment();

    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;



    //정보를 보여줄 객체들
    TextView cafenameText,addressText,weekdayopenText,weekdaycloseText,weekendopenText,weekendcloseText,cafetelText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("---","OwnerMainPage Onloaded");

        //로그인액티비티에서 로그인 성공하면 intent로 값 넘겨와서 main액티비티로 온다.
        Intent intent = getIntent();
        String cafeinfo = intent.getExtras().getString("cafeinfo");
        Log.d("---","getFromLoginPage"+cafeinfo);


        setContentView(R.layout.owner_main_page);
        Log.d("---", "Owner main page onloaded");

        drawerLayout = findViewById(R.id.main_drwer);


        try {
            JSONObject jo = new JSONObject(cafeinfo);
            String cafename = jo.getString("cafename");
            tempcafename = cafename;
            String address = jo.getString("address");
            tempaddress = address;
            String weekdayopen = jo.getString("weekday_opentime");
            tempweekdayopen = weekdayopen;
            String weekdayclose = jo.getString("weekday_closetime");
            tempweekdayclose = weekdayclose;
            String weekendopen = jo.getString("weekend_opentime");
            tempweekendopen = weekendopen;
            String weekendclose = jo.getString("weekend_closetime");
            tempweekendclose = weekendclose;
            String cafetel = jo.getString("tel");
            tempcafetel = cafetel;
            String cafeid = jo.getString("cafeid");
            tempcafeid = cafeid;

            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.main_container,ownerMainPageFragment);
            transaction.commit();

            Log.d("---","Data Inserted in Main Temp Activity");
            //String
        } catch (JSONException e) {
            Log.d("---", "error from getting data");
            e.printStackTrace();
        }

        toggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.string.open_str, R.string.close_str);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.main_naviView);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                /*
                주문현황    item1
                주문기록    item2
                나의매장    item3
                메뉴관리    item4
                매출 전표   item5
                매출 통계   item6
                추천 이벤트  item7
                */
                int id = menuItem.getItemId();
                FragmentTransaction transaction = manager.beginTransaction();
                switch (id){
                    case R.id.item1 :
                        transaction.replace(R.id.main_container, orderListFragment);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.item2 :
                        transaction.replace(R.id.main_container,orderHistoryFragment);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.item3 :
                        transaction.replace(R.id.main_container,ownerMainPageFragment);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.item4 :
                        transaction.replace(R.id.main_container,ownerMenuListFragment);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.item5 :
                        transaction.replace(R.id.main_container,salesFragment);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.item6 :
                        transaction.replace(R.id.main_container,statisticFragment);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.item7 :
                        transaction.replace(R.id.main_container,eventFragment);
                        drawerLayout.closeDrawers();
                        break;
                }
                transaction.commit();
                drawerLayout.closeDrawers();
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.msg,menu);
        return true;
        /*타이틀바에 종모양*/
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        toggle.onOptionsItemSelected(item);
        int id = item.getItemId();
        FragmentTransaction transaction = manager.beginTransaction();
        switch (id){
            case R.id.msg :
                transaction.replace(R.id.main_container, orderListFragment);
                drawerLayout.closeDrawers();
        }
        transaction.commit();
        /*타이틀바에 종모양*/
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onResume() {
        super.onResume();

    }
}