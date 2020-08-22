package com.example.ownercafeoda.OwnerLogin;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ownercafeoda.OwnerMain.OwnerMainPageActivity;
import com.example.ownercafeoda.R;
import com.example.ownercafeoda.zzzNetwork.StringURLHttpHandler;

public class OwnerLoginMainActivity extends AppCompatActivity {

    Button loginbtn;
    EditText telText;
    EditText passText;
    //public static String ip = "172.30.1.21";
    public static String ip = "172.30.1.46";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.owner_login);

        //액션바 숨기기 //
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        //

        loginbtn = findViewById(R.id.loginbutton);
        telText = findViewById(R.id.editText1);
        passText = findViewById(R.id.editText2);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tel = telText.getText().toString();
                String pass = passText.getText().toString();
                loginHttpTask task = new loginHttpTask(tel,pass);
                task.execute();
            }
        });
    }

    class loginHttpTask extends AsyncTask<Void, Void, String>{

        String url;
        public loginHttpTask(String tel, String pass){
            url = "http://"+ip+":8088/cafeoda/cafelogin.do?";
            //url = "http://"+ip+"/cafeoda/cafelogin.do?";
            url += "tel="+tel+"&pass="+pass;
        }
        @Override
        protected String doInBackground(Void... voids) {//여기서 커넥션 시작한다.
            //백그라운드로 커넥션 돌린다.
            return StringURLHttpHandler.requestData(url);
        }
        @Override
        protected void onPostExecute(String s) {

            if(s!=null) {
                Log.d("---", s);

                if (s.length() > 10) {
                    Log.d("---", "데이터" + s);
                    Toast.makeText(OwnerLoginMainActivity.this, "로그인 성공!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), OwnerMainPageActivity.class);
                    intent.putExtra("cafeinfo", s);
                    startActivity(intent);
                } else {
                    Toast.makeText(OwnerLoginMainActivity.this, "로그인 실패. 비밀번호를 확인하세요.", Toast.LENGTH_SHORT).show();

                }
            }

        }
    }
}
