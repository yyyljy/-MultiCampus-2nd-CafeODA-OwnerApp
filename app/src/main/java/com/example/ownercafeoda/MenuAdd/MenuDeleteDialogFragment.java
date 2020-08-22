package com.example.ownercafeoda.MenuAdd;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.ownercafeoda.OwnerLogin.OwnerLoginMainActivity;
import com.example.ownercafeoda.OwnerMain.OwnerMainPageActivity;
import com.example.ownercafeoda.R;
import com.example.ownercafeoda.zzzNetwork.StringURLHttpHandler;

import static com.example.ownercafeoda.OwnerLogin.OwnerLoginMainActivity.ip;

public class MenuDeleteDialogFragment extends DialogFragment implements View.OnClickListener {

    // 다이얼로그가 닫힐때 리사이클러뷰를 새로고침해보기 위해 리스너 오버라이드
    private DialogInterface.OnDismissListener onDismissListener;


    public static final String TAG_DeleteConfrim_Dialog = "DeleteMenuDialog";
    Button bt_dtconfirm, bt_dtcancel;
    TextView txt_dtmenuname, txt_dtmenucountry, txt_dtmenunum;


    public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener){
        this.onDismissListener = onDismissListener;
    }

    @Override
    public void onDismiss(DialogInterface dialog){
        super.onDismiss(dialog);
        if(onDismissListener != null){
            onDismissListener.onDismiss(dialog);
        }
    }

    public MenuDeleteDialogFragment() {
    }

    public static MenuDeleteDialogFragment getInstance() {
        MenuDeleteDialogFragment mddf = new MenuDeleteDialogFragment();
        return mddf;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.menudelete_dialog, container);
        bt_dtconfirm = v.findViewById(R.id.bt_dtconfirm);
        bt_dtcancel = v.findViewById(R.id.bt_dtcancel);
        txt_dtmenuname = v.findViewById(R.id.txt_dtmenuname);
        txt_dtmenucountry = v.findViewById(R.id.txt_dtmenucountry);
        txt_dtmenunum = v.findViewById(R.id.txt_dtmenunum);

        String dtmenuname = ((OwnerMainPageActivity) getActivity()).tempDtMenuName;
        String dtmenucountry = ((OwnerMainPageActivity) getActivity()).tempDtMenuCountry;
        final String dtmenunum = ((OwnerMainPageActivity) getActivity()).tempDtMenuNum;
        txt_dtmenuname.setText(dtmenuname);
        txt_dtmenucountry.setText(dtmenucountry);
        txt_dtmenunum.setText(dtmenunum + "");

        bt_dtconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteMenuHttpTask dmht = new deleteMenuHttpTask(dtmenunum);
                dmht.execute();
                dismiss();
            }
        });
        bt_dtcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return v;

    }

    @Override
    public void onClick(View v) {


        dismiss();

    }



    class deleteMenuHttpTask extends AsyncTask<Void, Void, String> {

        String url;
        public deleteMenuHttpTask(String menunum){
            url = "http://"+ip+":8088/cafeoda/menudelete.do?";
            //url = "http://"+ip+"/cafeoda/cafelogin.do?";
            url += "menunum="+menunum;
        }
        @Override
        protected String doInBackground(Void... voids) {//여기서 커넥션 시작한다.
            //백그라운드로 커넥션 돌린다.
            return StringURLHttpHandler.requestData(url);
        }
        @Override
        protected void onPostExecute(String s) {

            if(getContext() != null) {
                Toast.makeText(getContext(), "메뉴가 삭제되었습니다.", Toast.LENGTH_SHORT).show();
            }

            if(s!=null) {
                Log.d("---", s);

            }

        }
    }


}
