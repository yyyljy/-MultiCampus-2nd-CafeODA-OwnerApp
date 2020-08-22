package com.example.ownercafeoda.OrderList;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ownercafeoda.OwnerMain.OwnerMainPageActivity;
import com.example.ownercafeoda.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderListFragment extends Fragment implements getCafeInfo{
    OrderListAdapter myadapter;
    LinearLayoutManager manager;
    String cafeid;
    RecyclerView list;
    OrderListItemDTO item;
    List<OrderListItemDTO> recycler_order_data = new ArrayList<OrderListItemDTO>();
    public OrderListFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        cafeid = ((OwnerMainPageActivity)getActivity()).tempcafeid;
        Log.d("===","오너메인액티비티에서가져온 id:"+cafeid);

        View view = inflater.inflate(R.layout.order_list_main, container, false);

        list = view.findViewById(R.id.rlist);

        getOrderlistDataFromHttp task = new getOrderlistDataFromHttp();
        task.execute();

        return view;
    }

    @Override
    public String getCafeId() {
        return cafeid;
    }

    //orderlist AsyncTask
    class getOrderlistDataFromHttp extends AsyncTask<Void, Void, String> {


        @Override
        protected String doInBackground(Void... voids) {
            String urlstr = "";
            URL url;
            BufferedReader in = null;
            String data = "";
            OutputStream os = null;
            Log.d("===","알림내역:"+((OwnerMainPageActivity) getActivity()).tempcafeid);
            String cafeid = ((OwnerMainPageActivity) getActivity()).tempcafeid;
            JSONArray ja;

            try {
                urlstr = "http://" + getString(R.string.ipaddress) + ":8088/cafeoda/ownerorderlist.do?";
                urlstr += "cafeid=" + cafeid;
                url = new URL(urlstr);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(10000);
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-type", "application/json");

                //정상응답을 받았을 때 실행한다.

                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    in = new BufferedReader(
                            new InputStreamReader(connection.getInputStream(), "UTF-8")
                    );
                    data = in.readLine();
                    Log.d("===", "알림내역 가져오기 정상"+data);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return data;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            //웹서버에서 가져온 데이터가 json형식이므로
            //파싱해서 JSONObject를 MenuVO로 변환
            //변환한 AlertHistoryDTO를 ArrayList에 저장

            JSONArray ja = null;
            try {
                ja = new JSONArray(s);
                for (int i = 0; i < ja.length(); i++) {
                    JSONObject jo = ja.getJSONObject(i);
                    String ordid = jo.getString("ordid");
                    String guestphone = jo.getString("guestphone");
                    String ordertime = jo.getString("orderdate");
                    String menuname = jo.getString("prdname");
                    String icehot = jo.getString("icehot");
                    String quantity = jo.getString("quantity");
                    String price = jo.getString("oneprice");
                    String statusmsg = jo.getString("statusmsg");


                    OrderListItemDTO alertitem = new OrderListItemDTO(ordid, guestphone, ordertime, menuname,
                            icehot,quantity,price,statusmsg);
                    recycler_order_data.add(alertitem);
                    Log.d("===","ordid???"+ordid);

                }

                myadapter = new OrderListAdapter(getActivity().getApplicationContext(),
                        R.layout.order_list_item, recycler_order_data, OrderListFragment.this);



                manager = new LinearLayoutManager(getActivity().getApplicationContext());
                manager.setOrientation(LinearLayoutManager.VERTICAL);

                list.setLayoutManager(manager);
                list.setAdapter(myadapter);
                myadapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }


}