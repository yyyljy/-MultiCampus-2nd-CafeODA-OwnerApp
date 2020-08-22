package com.example.ownercafeoda.OwnerMain;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ownercafeoda.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class OwnerMainPageFragment extends Fragment {

        private String cafename ="";
        private String address ="";
        private String weekdayopen = "";
        private String weekdayclose = "";
        private String weekendopen ="";
        private String weekendclose = "";
        private String cafetel = "";

  /*  @Override
    public void setUserVisibleHint (boolean isVisibleToUser){
        super.setUserVisibleHint(isVisibleToUser);
        getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        if(isVisibleToUser) {
            Log.d("---","owenerMain Fragment is Visible to User");
            cafename = ((OwnerMainPageActivity) getActivity()).tempcafename;
            address = ((OwnerMainPageActivity) getActivity()).tempaddress;
            weekdayopen = ((OwnerMainPageActivity) getActivity()).tempweekdayopen;
            weekendopen = ((OwnerMainPageActivity) getActivity()).tempweekdayclose;
            weekendclose = ((OwnerMainPageActivity) getActivity()).tempweekendopen;
            cafetel = ((OwnerMainPageActivity) getActivity()).tempcafetel;

            makeUI(this.getView());
        }else{
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }*/


    //정보를 보여줄 객체들
    TextView cafenameText, addressText, weekdayopenText, weekdaycloseText, weekendopenText, weekendcloseText, cafetelText;

    public OwnerMainPageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("---", "test Fragment Oncreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_owner_main, container, false);
        Log.d("---", "test Fragment OncreateView");

        makeUI(view);

        return view;
    }

    //UI를 만든다.
    public void makeUI(View view){//메인액티비티에 저장해놓은 값들을 변수에 넣는다.
        cafename = ((OwnerMainPageActivity) getActivity()).tempcafename;
        address = ((OwnerMainPageActivity) getActivity()).tempaddress;
        weekdayopen = ((OwnerMainPageActivity) getActivity()).tempweekdayopen;
        weekendopen = ((OwnerMainPageActivity) getActivity()).tempweekdayclose;
        weekendclose = ((OwnerMainPageActivity) getActivity()).tempweekendopen;
        cafetel = ((OwnerMainPageActivity) getActivity()).tempcafetel;


        cafenameText = view.findViewById(R.id.cafename);
        addressText = view.findViewById(R.id.cafeAddress);
        weekdayopenText = view.findViewById(R.id.weekday_open);
        weekdaycloseText = view.findViewById(R.id.weekday_close);
        weekendopenText = view.findViewById(R.id.weekend_open);
        weekendcloseText = view.findViewById(R.id.weekend_close);
        cafetelText = view.findViewById(R.id.txt_cafetel);
        Log.d("---", "cafename과 정보들" + cafename + address + weekdayopen + weekdayclose);

        cafenameText.setText(cafename);
        addressText.setText(address);
        weekdayopenText.setText(weekdayopen);
        weekdaycloseText.setText(weekdayclose);
        weekendopenText.setText(weekendopen);
        weekendcloseText.setText(weekendclose);
        cafetelText.setText(cafetel);



    }
}
