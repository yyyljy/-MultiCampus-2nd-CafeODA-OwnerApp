package com.example.ownercafeoda.MenuAdd;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ownercafeoda.OnItemClick;
import com.example.ownercafeoda.OwnerLogin.OwnerLoginMainActivity;
import com.example.ownercafeoda.OwnerMain.OwnerMainPageActivity;
import com.example.ownercafeoda.R;
import com.example.ownercafeoda.zzzNetwork.InsertMenuHttpHandler;

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
public class OwnerMenuListFragment extends Fragment implements OnItemClick {

    Spinner sp1, sp2;
    //ArrayList<MenuVO> menulist;
    MenuListAdapter myadapter;
    LinearLayoutManager manager;
    RecyclerView list;
    //List<MenuListItem> recycler_order_data = new ArrayList<MenuListItem>();
    List<MenuVO> menulist = new ArrayList<MenuVO>();

    MenuVO item;


    public OwnerMenuListFragment() {

    }

    Button addMenu;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.owner_menu_list, container, false);


        list = view.findViewById(R.id.rlist);


        //웹에서 메뉴리스트 데이터 가져온다.
        getMenuDataFromHttp task = new getMenuDataFromHttp();
        task.execute();


        addMenu = view.findViewById(R.id.Btn_menuAdd);


        // 메뉴 추가 기능 부분 //


        addMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("메뉴추가");
                LayoutInflater inflater = getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.alert_dialog, null);
                sp1 = dialogView.findViewById(R.id.Spin_menuname);
                sp2 = dialogView.findViewById(R.id.Spin_menucountry);
                final TextView result_name = dialogView.findViewById(R.id.Txt_selectedName);
                final TextView result_country = dialogView.findViewById(R.id.Txt_selectedCountry);
                final EditText result_price = dialogView.findViewById(R.id.Txt_insertPrice);
                ArrayAdapter sp1_adapter = ArrayAdapter.createFromResource(dialogView.getContext(),
                        R.array.coffee_menu, android.R.layout.simple_spinner_dropdown_item);
                ArrayAdapter sp2_adapter = ArrayAdapter.createFromResource(dialogView.getContext(),
                        R.array.coffee_country, android.R.layout.simple_spinner_dropdown_item);
                sp1.setAdapter(sp1_adapter);
                sp2.setAdapter(sp2_adapter);
                sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        TextView resultname = sp1.findViewById(android.R.id.text1);
                        result_name.setText(resultname.getText());
                        ((OwnerMainPageActivity) getActivity()).tempMenuName = resultname.getText().toString();
                        ((OwnerMainPageActivity) getActivity()).tempProdId = sp1.getSelectedItemPosition() + 1;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
                sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        TextView resultcountry = sp2.findViewById(android.R.id.text1);
                        result_country.setText(resultcountry.getText());
                        ((OwnerMainPageActivity) getActivity()).tempMenuCountry = resultcountry.getText().toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ((OwnerMainPageActivity) getActivity()).tempMenuPrice = result_price.getText().toString();
                        try {
                            sendToServer();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("---", "Sending Server Click Error");
                        }
                    }
                });
                builder.setView(dialogView);
                builder.show();
            }
        });


        // 메뉴 추가 기능 부분 끝 (다이얼로그형태) //
        return view;
    }

    // 메뉴 데이터 서버로 보내기 전에 JSON 으로 만드는 함수 //
    public void sendToServer() throws JSONException {
        JSONObject jo = new JSONObject();
        String menuName = ((OwnerMainPageActivity) getActivity()).tempMenuName;
        String menuCountry = ((OwnerMainPageActivity) getActivity()).tempMenuCountry;
        String menuPrice = ((OwnerMainPageActivity) getActivity()).tempMenuPrice;
        String cafeid = ((OwnerMainPageActivity) getActivity()).tempcafeid;
        int proid = ((OwnerMainPageActivity) getActivity()).tempProdId;
        jo.put("proname", menuName);
        jo.put("country", menuCountry);
        jo.put("price", menuPrice);
        jo.put("cafeid", cafeid);
        jo.put("proid", proid);
        insertMenuData task = new insertMenuData(jo);
        Log.d("---", "MenuJsonObject Task execute" + jo.toString());
        task.execute();
    }

    @Override
    public void onClick(String value,Bundle data) {

    }

    // 메뉴 데이터 JSON to String 으로 서버로 보내기 //
    class insertMenuData extends AsyncTask<Void, Void, String> {
        JSONObject jo;
        String urlstr;

        public insertMenuData(JSONObject jo) {
            this.jo = jo;
            urlstr = "http://" + OwnerLoginMainActivity.ip + ":8088/cafeoda/menuinsert.do";
        }

        @Override
        protected String doInBackground(Void... voids) {//여기서 커넥션 시작한다.
            //백그라운드로 커넥션 돌린다.
            return InsertMenuHttpHandler.requestData(urlstr, jo);
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(getContext(), "메뉴가 추가되었습니다.", Toast.LENGTH_SHORT).show();
            // 메뉴가 추가되었으므로 fragment를 refresh 해주자 //
           refreshData();
//            if(s.trim() != null && s.trim() != "") {
//                if (s.trim() == null && s.trim().equals("")) {
//                    Log.d("---", "no return value" + s);
//                } else {
//                    Log.d("---", "return value :" + s);
//                }
//            }
        }


    }
    // insert 후 데이터 새로고침 //
    public void refreshData() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        myadapter.notifyDataSetChanged();
        if (myadapter.data != null) {
            myadapter.data.clear();
        }
        ft.detach(OwnerMenuListFragment.this).attach(OwnerMenuListFragment.this).commit();
    }

    // 서버에서 메뉴 정보 가져오기 //

    class getMenuDataFromHttp extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            String urlstr = "";
            URL url;
            BufferedReader in = null;
            String data = "";
            OutputStream os = null;
            String cafeid = ((OwnerMainPageActivity) getActivity()).tempcafeid;
            JSONArray ja;

            try {
                urlstr = "http://" + OwnerLoginMainActivity.ip + ":8088/cafeoda/menulist.do?";
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
                    Log.d("---", data);
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
            //변환한 MenuVO를 ArrayList에 저장

            JSONArray ja = null;
            try {
                ja = new JSONArray(s);
                for (int i = 0; i < ja.length(); i++) {
                    JSONObject jo = ja.getJSONObject(i);
                    String proname = jo.getString("proname");
                    String country = jo.getString("country");
                    int price = jo.getInt("price");
                    int menunum = jo.getInt("menunum");

                    MenuVO menuitem = new MenuVO(proname, country, price, menunum);
                    menulist.add(menuitem);

                }

                myadapter = new MenuListAdapter(getActivity().getApplicationContext(),
                        R.layout.owner_menu_item, menulist, new OnItemClick() {
                    @Override
                    public void onClick(String value,Bundle data) {
                        if (value.equals("delete")) {
                            // Adapter 에서 값 가져오기 //
                            String dtname = data.getString("dtname");
                            String dtcon = data.getString("dtcon");
                            String dtnum = data.getString("dtnum");
                            // Activitiy 에 임시로 값 저장하기 (dialog에서 쓰려고)//
                            ((OwnerMainPageActivity)getActivity()).tempDtMenuName = dtname;
                            ((OwnerMainPageActivity)getActivity()).tempDtMenuCountry = dtcon;
                            ((OwnerMainPageActivity)getActivity()).tempDtMenuNum = dtnum;

                            MenuDeleteDialogFragment mddf = MenuDeleteDialogFragment.getInstance();
                            // onDismiss 를 Override 하여 Recyclerview 를 새로고침한다. //
                            mddf.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dialog) {
                                    refreshData();
                                }
                            });
                            mddf.show(getFragmentManager(), MenuDeleteDialogFragment.TAG_DeleteConfrim_Dialog);
                        }
                    }
                });


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