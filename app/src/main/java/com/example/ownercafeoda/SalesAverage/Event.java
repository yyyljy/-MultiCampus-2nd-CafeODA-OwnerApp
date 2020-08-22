package com.example.ownercafeoda.SalesAverage;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ownercafeoda.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Event extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    EditText roadtextView;
    TextView textView;
    TextView textView2;
    TextView textView3;
    TextView textView4;
    Button button;
    Spinner spinner;
    BarChart barChart;
    LineChart lineChart;
    BarChart barChartAge;
    LineChart lineChart2;

    LinearLayout linearLayout;

    ArrayList<salesNearbyDTO> list = null;
    ArrayList<stationDTO> stationlist = null;

    stationDTO stationdto = null;
    salesNearbyDTO salesDTO = null;

    String road;

    public Event() {
    }

    public static Event newInstance(String param1, String param2) {
        Event fragment = new Event();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_event, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        linearLayout = getView().findViewById(R.id.eventInnerLayout);
        roadtextView = getView().findViewById(R.id.event_roadtextView);
        spinner = getView().findViewById(R.id.eventSalesSpinner);
        button = getView().findViewById(R.id.eventQueryBtn);

        textView = getView().findViewById(R.id.event_textView);
        textView2 = getView().findViewById(R.id.event_textView2);
        textView3 = getView().findViewById(R.id.event_textView3);
        textView4 = getView().findViewById(R.id.event_textView4);

        barChart = getView().findViewById(R.id.eventBarChart);
        lineChart = getView().findViewById(R.id.eventLineChart);
        barChartAge = getView().findViewById(R.id.eventBarChartAge);
        lineChart2 = getView().findViewById(R.id.eventLineChart2);

        roadtextView.setText("논현");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                road = roadtextView.getText()+"";
                switch (spinner.getSelectedItemPosition()) {
                    case 0:
                        button.setEnabled(false);
                        textView.setText("날씨 변화에 따른 매출 변화(%)\n");
                        button.setEnabled(true);
                        break;
                    case 1:
                        button.setEnabled(false);
                        textView.setText("주변 카페 요일별 매출 비율(%)\n");
                        textView2.setText("주변 카페 시간대별 매출 비율(%)\n");
                        textView3.setText("주변 카페 연령대별 매출 비율(%)\n");
                        textView.setVisibility(View.VISIBLE);
                        barChart.setVisibility(View.VISIBLE);
                        textView2.setVisibility(View.VISIBLE);
                        lineChart.setVisibility(View.VISIBLE);
                        textView3.setVisibility(View.VISIBLE);
                        barChartAge.setVisibility(View.VISIBLE);
                        textView4.setVisibility(View.INVISIBLE);
                        lineChart2.setVisibility(View.INVISIBLE);
                        /*
                        textView.getLayoutParams().height = 20;
                        textView2.getLayoutParams().height = 20;
                        textView3.getLayoutParams().height = 20;
                        textView4.getLayoutParams().height = 0;
                        barChart.getLayoutParams().height = 150;
                        lineChart.getLayoutParams().height = 150;
                        barChartAge.getLayoutParams().height = 150;
                        lineChart2.getLayoutParams().height = 0;
                        */
                        linearLayout.requestLayout();
                        getJsonEventSalesData json = new getJsonEventSalesData();
                        json.execute();
                        break;
                    case 2:
                        button.setEnabled(false);
                        textView.setText("주변 유동인구 (명)\n");
                        textView2.setText("시간대별 승차인구 (명)\n");
                        textView4.setText("시간대별 하차인구 (명)\n");
                        textView.setVisibility(View.INVISIBLE);
                        barChart.setVisibility(View.INVISIBLE);
                        textView2.setVisibility(View.VISIBLE);
                        lineChart.setVisibility(View.VISIBLE);
                        textView3.setVisibility(View.INVISIBLE);
                        barChartAge.setVisibility(View.INVISIBLE);
                        textView4.setVisibility(View.VISIBLE);
                        lineChart2.setVisibility(View.VISIBLE);
                        getJsonEventStationData stationjson = new getJsonEventStationData();
                        stationjson.execute();
                        button.setEnabled(true);
                        break;
                }
            }
        });
    }

    class getJsonEventSalesData extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            URL url = null;
            BufferedReader in = null;
            String data = "논현역";
            try {
                String ip = getString(R.string.cafeip);
                Log.d("OwnerAppLog", "doInBackground: "+ip);
                String path = "http://70.12.116.63:8088/cafeoda/eventsales.json?road=" + road;
                data = "";
                url = new URL(path);
                HttpURLConnection connection =
                        (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-Type", "application/json");
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    in = new BufferedReader(
                            new InputStreamReader(
                                    connection.getInputStream(), "UTF-8")
                    );
                    data = in.readLine();
                }
            } catch (MalformedURLException e) {
                Log.d("OwnerAppLog", "MalformedURLException: " + e.toString());
                e.printStackTrace();
            } catch (IOException e) {
                Log.d("OwnerAppLog", "IOException: " + e.toString());
                e.printStackTrace();
            }
            return data;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("OwnerAppLog", "onPostExecute: " + s);
            JSONArray ja = null;
            list = new ArrayList<salesNearbyDTO>();
            try {
                JSONObject jo = new JSONObject(s);
                String year = jo.getString("year");
                int quarter = jo.getInt("quarter");
                String road = jo.getString("road");
                String totalamt = jo.getString("totalamt");
                int totalcnt = jo.getInt("totalcnt");
                int weekdayrate = jo.getInt("weekdayrate");
                int weekendrate = jo.getInt("weekendrate");
                int mondayrate = jo.getInt("mondayrate");
                int tuesdayrate = jo.getInt("tuesdayrate");
                int wednesdayrate = jo.getInt("wednesdayrate");
                int thursdayrate = jo.getInt("thursdayrate");
                int fridayrate = jo.getInt("fridayrate");
                int saturdayrate = jo.getInt("saturdayrate");
                int sundayrate = jo.getInt("sundayrate");
                int t0006rate = jo.getInt("t0006rate");
                int t0611rate = jo.getInt("t0611rate");
                int t1114rate = jo.getInt("t1114rate");
                int t1417rate = jo.getInt("t1417rate");
                int t1721rate = jo.getInt("t1721rate");
                int t2124rate = jo.getInt("t2124rate");
                int manrate = jo.getInt("manrate");
                int womanrate = jo.getInt("womanrate");
                int a10rate = jo.getInt("a10rate");
                int a20rate = jo.getInt("a20rate");
                int a30rate = jo.getInt("a30rate");
                int a40rate = jo.getInt("a40rate");
                int a50rate = jo.getInt("a50rate");
                int a60rate = jo.getInt("a60rate");
                int cafecnt = jo.getInt("cafecnt");
                salesDTO = new salesNearbyDTO(year, quarter, road, totalamt, totalcnt, weekdayrate, weekendrate, mondayrate, tuesdayrate, wednesdayrate, thursdayrate, fridayrate, saturdayrate, sundayrate, t0006rate, t0611rate, t1114rate, t1417rate, t1721rate, t2124rate, manrate, womanrate, a10rate, a20rate, a30rate, a40rate, a50rate, a60rate, cafecnt);
                list.add(salesDTO);
                button.setEnabled(true);
            } catch (JSONException ex) {
                Log.d("OwnerAppLog", "onPostExecute: "+ ex.toString());
                ex.printStackTrace();
                button.setEnabled(true);
            }
            drawEventSalesBarChart();
            drawEventSalesLineChart();
            drawEventSalesAgeBarChart();
        }
    }

    public void drawEventSalesBarChart() {
        try {
            Log.d("OwnerAppLog", "drawSalesBarChart");
            ArrayList<BarEntry> barEntries = new ArrayList<BarEntry>();
            barEntries.add(new BarEntry(1, (list.get(0).getMondayrate())));
            barEntries.add(new BarEntry(2, (list.get(0).getTuesdayrate())));
            barEntries.add(new BarEntry(3, (list.get(0).getWednesdayrate())));
            barEntries.add(new BarEntry(4, (list.get(0).getTuesdayrate())));
            barEntries.add(new BarEntry(5, (list.get(0).getFridayrate())));
            barEntries.add(new BarEntry(6, (list.get(0).getSaturdayrate())));
            barEntries.add(new BarEntry(7, (list.get(0).getSundayrate())));

            final BarDataSet barDataSet = new BarDataSet(barEntries, "요일");
            BarData data = new BarData(barDataSet);
            barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
            data.setBarWidth(0.5f);
            barChart.setData(data);
            barChart.invalidate();
            barChart.requestLayout();
            barChart.setVisibility(View.VISIBLE);
            textView.append("\n주변 카페 매장 수 : "+list.get(0).getCafecnt()+"\n");
            textView.append("최대 비율: "+data.getYMax()+"%\n최소 비율 : "+data.getYMin()+"%\n");
            textView.append("소비자 남녀 비율\n남 : "+ salesDTO.getManrate()+"\n여 : "+salesDTO.getWomanrate());
        } catch (Exception ex) {
            ex.getMessage();
        }
    }
    public void drawEventSalesLineChart() {
        try {
            Log.d("OwnerAppLog", "drawEvnetSalesLineChart");
            ArrayList<Entry> entries = new ArrayList<Entry>();
            entries.add(new BarEntry(6, (list.get(0).getT0006rate())));
            entries.add(new BarEntry(11, (list.get(0).getT0611rate())));
            entries.add(new BarEntry(14, (list.get(0).getT1114rate())));
            entries.add(new BarEntry(17, (list.get(0).getT1417rate())));
            entries.add(new BarEntry(21, (list.get(0).getT1721rate())));
            entries.add(new BarEntry(24, (list.get(0).getT2124rate())));

            final LineDataSet lineDataSet = new LineDataSet(entries, "시간");
            LineData data = new LineData(lineDataSet);
            lineDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
            lineChart.setData(data);
            lineChart.invalidate();
            lineChart.requestLayout();
            lineChart.setVisibility(View.VISIBLE);
            textView2.append("최대 비율: "+data.getYMax()+"%\n최소 비율 : "+data.getYMin()+"%\n");
        } catch (Exception ex) {
            Log.d("OwnerAppLog", "drawEventSalesLineChart: "+ex.toString());
            ex.getMessage();
        }
    }
    public void drawEventSalesAgeBarChart() {
        try {
            Log.d("OwnerAppLog", "drawEventSalesAgeBarChart");
            ArrayList<BarEntry> barEntries = new ArrayList<BarEntry>();
            barEntries.add(new BarEntry(10, (list.get(0).getA10rate())));
            barEntries.add(new BarEntry(20, (list.get(0).getA20rate())));
            barEntries.add(new BarEntry(30, (list.get(0).getA30rate())));
            barEntries.add(new BarEntry(40, (list.get(0).getA40rate())));
            barEntries.add(new BarEntry(50, (list.get(0).getA50rate())));
            barEntries.add(new BarEntry(60, (list.get(0).getA60rate())));

            final BarDataSet barDataSet = new BarDataSet(barEntries, "연령대");
            BarData data = new BarData(barDataSet);
            barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
            data.setBarWidth(0.5f);
            barChartAge.setData(data);
            barChartAge.invalidate();
            barChartAge.requestLayout();
            barChartAge.setVisibility(View.VISIBLE);
            textView3.append("최대 비율: "+data.getYMax()+"%\n최소 비율 : "+data.getYMin()+"%\n");
        } catch (Exception ex) {
            ex.getMessage();
        }
    }
    /*
    ================================================================================
    ================================================================================
    지하철 역 유동 인구 JSON Parsing
    ================================================================================
    ================================================================================
    */
    class getJsonEventStationData extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            URL url = null;
            BufferedReader in = null;
            String data = "역삼";
            try {
                String path = "http://70.12.116.63:8088/cafeoda/eventstation.json?road=" + road;
                data = "";
                url = new URL(path);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-Type", "application/json");
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    in = new BufferedReader(
                            new InputStreamReader(
                                    connection.getInputStream(), "UTF-8")
                    );
                    data = in.readLine();
                }
            } catch (MalformedURLException e) {
                Log.d("OwnerAppLog", "MalformedURLException: " + e.toString());
                e.printStackTrace();
            } catch (IOException e) {
                Log.d("OwnerAppLog", "IOException: " + e.toString());
                e.printStackTrace();
            }
            return data;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("OwnerAppLog", "onPostExecute: " + s);
            JSONArray ja = null;
            stationlist = new ArrayList<stationDTO>();
            try {
                JSONObject jo = new JSONObject(s);
                String datamonth = jo.getString("datamonth");
                String line = jo.getString("line");
                String stationname = jo.getString("stationname");
                int t0405on = jo.getInt("t0405on");
                int t0405off = jo.getInt("t0405off");
                int t0506on = jo.getInt("t0506on");
                int t0506off = jo.getInt("t0506off");
                int t0607on = jo.getInt("t0607on");
                int t0607off = jo.getInt("t0607off");
                int t0708on = jo.getInt("t0708on");
                int t0708off = jo.getInt("t0708off");
                int t0809on = jo.getInt("t0809on");
                int t0809off = jo.getInt("t0809off");
                int t0910on = jo.getInt("t0910on");
                int t0910off = jo.getInt("t0910off");
                int t1011on = jo.getInt("t1011on");
                int t1011off = jo.getInt("t1011off");
                int t1112on = jo.getInt("t1112on");
                int t1112off = jo.getInt("t1112off");
                int t1213on = jo.getInt("t1213on");
                int t1213off = jo.getInt("t1213off");
                int t1314on = jo.getInt("t1314on");
                int t1314off = jo.getInt("t1314off");
                int t1415on = jo.getInt("t1415on");
                int t1415off = jo.getInt("t1415off");
                int t1516on = jo.getInt("t1516on");
                int t1516off = jo.getInt("t1516off");
                int t1617on = jo.getInt("t1617on");
                int t1617off = jo.getInt("t1617off");
                int t1718on = jo.getInt("t1718on");
                int t1718off = jo.getInt("t1718off");
                int t1819on = jo.getInt("t1819on");
                int t1819off = jo.getInt("t1819off");
                int t1920on = jo.getInt("t1920on");
                int t1920off = jo.getInt("t1920off");
                int t2021on = jo.getInt("t2021on");
                int t2021off = jo.getInt("t2021off");
                int t2122on = jo.getInt("t2122on");
                int t2122off = jo.getInt("t2122off");
                int t2223on = jo.getInt("t2223on");
                int t2223off = jo.getInt("t2223off");
                int t2324on = jo.getInt("t2324on");
                int t2324off = jo.getInt("t2324off");
                int t0001on = jo.getInt("t0001on");
                int t0001off = jo.getInt("t0001off");
                int t0102on = jo.getInt("t0102on");
                int t0102off = jo.getInt("t0102off");
                int t0203on = jo.getInt("t0203on");
                int t0203off = jo.getInt("t0203off");
                int t0304on = jo.getInt("t0304on");
                int t0304off = jo.getInt("t0304off");
                stationdto = new stationDTO(datamonth, line, stationname, t0405on, t0405off, t0506on, t0506off, t0607on, t0607off, t0708on, t0708off, t0809on, t0809off, t0910on, t0910off, t1011on, t1011off, t1112on, t1112off, t1213on, t1213off, t1314on, t1314off, t1415on, t1415off, t1516on, t1516off, t1617on, t1617off, t1718on, t1718off, t1819on, t1819off, t1920on, t1920off, t2021on, t2021off, t2122on, t2122off, t2223on, t2223off, t2324on, t2324off, t0001on, t0001off, t0102on, t0102off, t0203on, t0203off, t0304on, t0304off);
                stationlist.add(stationdto);
                button.setEnabled(true);
            } catch (JSONException ex) {
                Log.d("OwnerAppLog", "onPostExecute: "+ ex.toString());
                ex.printStackTrace();
                button.setEnabled(true);
            }
            drawEventStationOnLineChart();
            drawEventStationOffLineChart();
        }
    }
    public void drawEventStationOnLineChart() {
        try {
            Log.d("OwnerAppLog", "drawEventStationLineChart");
            ArrayList<Entry> entries = new ArrayList<Entry>();
            entries.add(new BarEntry(5, (stationlist.get(0).getT0405on())));
            entries.add(new BarEntry(6, (stationlist.get(0).getT0506on())));
            entries.add(new BarEntry(7, (stationlist.get(0).getT0607on())));
            entries.add(new BarEntry(8, (stationlist.get(0).getT0708on())));
            entries.add(new BarEntry(9, (stationlist.get(0).getT0809on())));
            entries.add(new BarEntry(10, (stationlist.get(0).getT0910on())));
            entries.add(new BarEntry(11, (stationlist.get(0).getT1011on())));
            entries.add(new BarEntry(12, (stationlist.get(0).getT1112on())));
            entries.add(new BarEntry(13, (stationlist.get(0).getT1213on())));
            entries.add(new BarEntry(14, (stationlist.get(0).getT1314on())));
            entries.add(new BarEntry(15, (stationlist.get(0).getT1415on())));
            entries.add(new BarEntry(16, (stationlist.get(0).getT1516on())));
            entries.add(new BarEntry(17, (stationlist.get(0).getT1617on())));
            entries.add(new BarEntry(18, (stationlist.get(0).getT1718on())));
            entries.add(new BarEntry(19, (stationlist.get(0).getT1819on())));
            entries.add(new BarEntry(20, (stationlist.get(0).getT1920on())));
            entries.add(new BarEntry(21, (stationlist.get(0).getT2021on())));
            entries.add(new BarEntry(22, (stationlist.get(0).getT2122on())));
            entries.add(new BarEntry(23, (stationlist.get(0).getT2223on())));
            entries.add(new BarEntry(24, (stationlist.get(0).getT2324on())));
            entries.add(new BarEntry(1, (stationlist.get(0).getT0001on())));
            entries.add(new BarEntry(2, (stationlist.get(0).getT0102on())));
            entries.add(new BarEntry(3, (stationlist.get(0).getT0203on())));

            final LineDataSet lineDataSet = new LineDataSet(entries, "명");
            LineData data = new LineData(lineDataSet);
            lineDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
            lineChart.setData(data);
            lineChart.invalidate();
            lineChart.requestLayout();
            lineChart.setVisibility(View.VISIBLE);
            textView2.append("최대 : "+(int)data.getYMax()+"명\n최소  : "+(int)data.getYMin()+"명\n");
        } catch (Exception ex) {
            Log.d("OwnerAppLog", "drawEventSalesLineChart: "+ex.toString());
            ex.getMessage();
        }
    }
    public void drawEventStationOffLineChart() {
        try {
            Log.d("OwnerAppLog", "drawEventStationOffLineChart");
            ArrayList<Entry> entries = new ArrayList<Entry>();
            entries.add(new BarEntry(5, (stationlist.get(0).getT0405off())));
            entries.add(new BarEntry(6, (stationlist.get(0).getT0506off())));
            entries.add(new BarEntry(7, (stationlist.get(0).getT0607off())));
            entries.add(new BarEntry(8, (stationlist.get(0).getT0708off())));
            entries.add(new BarEntry(9, (stationlist.get(0).getT0809off())));
            entries.add(new BarEntry(10, (stationlist.get(0).getT0910off())));
            entries.add(new BarEntry(11, (stationlist.get(0).getT1011off())));
            entries.add(new BarEntry(12, (stationlist.get(0).getT1112off())));
            entries.add(new BarEntry(13, (stationlist.get(0).getT1213off())));
            entries.add(new BarEntry(14, (stationlist.get(0).getT1314off())));
            entries.add(new BarEntry(15, (stationlist.get(0).getT1415off())));
            entries.add(new BarEntry(16, (stationlist.get(0).getT1516off())));
            entries.add(new BarEntry(17, (stationlist.get(0).getT1617off())));
            entries.add(new BarEntry(18, (stationlist.get(0).getT1718off())));
            entries.add(new BarEntry(19, (stationlist.get(0).getT1819off())));
            entries.add(new BarEntry(20, (stationlist.get(0).getT1920off())));
            entries.add(new BarEntry(21, (stationlist.get(0).getT2021off())));
            entries.add(new BarEntry(22, (stationlist.get(0).getT2122off())));
            entries.add(new BarEntry(23, (stationlist.get(0).getT2223off())));
            entries.add(new BarEntry(24, (stationlist.get(0).getT2324off())));
            entries.add(new BarEntry(1, (stationlist.get(0).getT0001off())));
            entries.add(new BarEntry(2, (stationlist.get(0).getT0102off())));
            entries.add(new BarEntry(3, (stationlist.get(0).getT0203off())));

            final LineDataSet lineDataSet = new LineDataSet(entries, "명");
            LineData data = new LineData(lineDataSet);
            lineDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
            lineChart2.setData(data);
            lineChart2.invalidate();
            lineChart2.requestLayout();
            lineChart2.setVisibility(View.VISIBLE);
            textView4.append("최대 : "+(int)data.getYMax()+"명\n최소  : "+(int)data.getYMin()+"명\n");
        } catch (Exception ex) {
            Log.d("OwnerAppLog", "drawEventSalesLineChart2: "+ex.toString());
            ex.getMessage();
        }
    }

}

    ///json 웹에서 받아오기
    /*class getJsonData extends AsyncTask<Void,Void,String> {
        @Override
        protected String doInBackground(Void... voids) {
            //Log.d("OwnerAppLog", "doInBackground: myhttp");
            URL url = null;
            BufferedReader in=null;
            String data="";
            try {
                String path = "http://175.123.85.141:8088/cafeoda/test.json";
                url = new URL(path);
                HttpURLConnection connection =
                        (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-Type","application/json");
                //Log.d("OwnerAppLog", "doInBackground: 1111");
                //Log.d("OwnerAppLog", "doInBackground: 1.5"+connection.getResponseCode());
                if(connection.getResponseCode()==HttpURLConnection.HTTP_OK){
                    //Log.d("Owner", "doInBackground: 2222");
                    in = new BufferedReader(
                            new InputStreamReader(
                                    connection.getInputStream(),"UTF-8")
                    );
                    data = in.readLine();
                    //Log.d("OwnerAppLog",data);
                }
                //Log.d("OwnerAppLog", "doInBackground: 3333");

            } catch (MalformedURLException e) {
                Log.d("OwnerAppLog", "MalformedURLException: "+e.toString());
                e.printStackTrace();
            } catch (IOException e) {
                Log.d("OwnerAppLog", "IOException: "+e.toString());
                e.printStackTrace();
            }
            return data;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("OwnerAppLog", "onPostExecute: "+s);
            JSONArray ja = null;
            list = new ArrayList<stationDTO>();
            try {
                ja = new JSONArray(s);
                for(int i=0;i<ja.length();i++){
                    JSONObject jo = ja.getJSONObject(i);
                    String writedate = jo.getString("writedate");
                    String line = jo.getString("line");
                    String stationID = jo.getString("stationID");
                    String stationName = jo.getString("stationName");
                    int onCount = jo.getInt("onCount");
                    int offCount = jo.getInt("offCount");
                    stationDTO item = new stationDTO(writedate,line,stationID,stationName,onCount,offCount);
                    list.add(item);
                }
            } catch (JSONException e) {
                Log.d("OwnerAppLog", "onPostExecute: "+e.toString());
                e.printStackTrace();
            }
            button.setEnabled(true);
        }
    }*/

