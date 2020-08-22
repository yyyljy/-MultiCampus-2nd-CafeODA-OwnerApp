package com.example.ownercafeoda.SalesAverage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ownercafeoda.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Statistic#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Statistic extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Statistic() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Manage.
     */
    // TODO: Rename and change types and number of parameters
    public static Statistic newInstance(String param1, String param2) {
        Statistic fragment = new Statistic();
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
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_statistic, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView textView = (TextView) getView().findViewById(R.id.sales_textView);
        textView.setText("최고 매출 일자 : 10 일 매출액 높은 일자 : 10일, xx일, xx일\n" +
                " 매출액 낮은 일자 : 1일, xx일, xx일");

        BarChart barChart = (BarChart)getView().findViewById(R.id.barchart);
        try{
            ArrayList<BarEntry> barEntries = new ArrayList<BarEntry> ( );
            for(int i=1;i<=3;i++){
                barEntries.add ( new BarEntry(i,(int)(Math.random()*100)+400));
            }
            final BarDataSet barDataSet = new BarDataSet(barEntries,"");
            BarData data = new BarData(barDataSet);
            barDataSet.setColors ( ColorTemplate.COLORFUL_COLORS );
            data.setBarWidth ( 0.5f );
            barChart.setData ( data );
            barChart.animateY ( 1000 );
            barChart.invalidate ();
        }catch(Exception ex){
            ex.getMessage ();
        }
    }
}
