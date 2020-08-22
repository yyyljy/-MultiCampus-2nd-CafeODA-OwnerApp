package com.example.ownercafeoda.MenuAdd;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ownercafeoda.R;


public class OwnerMenuInsertActivity extends AppCompatActivity {
    Button btn;
    Button btn2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.owner_menu_insert);
        btn = findViewById(R.id.owner_insert_btn1);
        btn2 = findViewById(R.id.owner_insert_btn2);


        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(OwnerMenuInsertActivity.this);
                builder.setTitle("메뉴추가");
                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.alert_dialog,null);
                final Spinner sp1 = findViewById(R.id.Spin_menuname);
                Spinner sp2 = findViewById(R.id.Spin_menucountry);

                ArrayAdapter sp1_adapter = ArrayAdapter.createFromResource(dialogView.getContext(),
                        R.array.coffee_menu,android.R.layout.simple_spinner_dropdown_item);
                ArrayAdapter sp2_adapter = ArrayAdapter.createFromResource(dialogView.getContext(),
                        R.array.coffee_country,android.R.layout.simple_spinner_dropdown_item);


                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                builder.setView(dialogView);
                builder.show();
            }
        });
    }
}

/*class DialogListner implements DialogInterface.OnClickListener{
    EditText meunName;
    EditText menuInfo;

    @Override
    public void onClick(DialogInterface dialog, int which) {
        AlertDialog inputAlert = (AlertDialog) dialog;
        meunName = inputAlert.findViewById(R.id.alert_name);
        menuInfo = inputAlert.findViewById(R.id.alert_info);


        String data = meunName.getText().toString();
        Log.d("test====>",data);

    }
}*/
