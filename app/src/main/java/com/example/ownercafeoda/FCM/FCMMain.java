package com.example.ownercafeoda.FCM;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class FCMMain {
    String token;
    String myid="";

    //버튼을 클릭했을 때 requestThread호출
    public void request(String sendId, String recvId){
        myid = sendId;
        /*========================
         * ========================
         * 1. 내 토큰 값을 받아오기
         *========================
         *========================*/
        getToken();
        new requestThread(sendId,recvId).start();
    }
    //토큰을 생성하고 만드는 작업
    public void getToken(){
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                //토큰을 가져오다 실패하면 실행하게 된다.
                if (!task.isSuccessful()) {
                    Log.d("myfcm", "getInstanceId failed", task.getException());
                    return;
                }
                String token = task.getResult().getToken();
                Log.d("myfcm", token);
                /*============================================================
                ============================================================
                2. 내 토큰값 오라클 DB에 있는지 확인(없으면 웹서버가 오라클DB에 저장함)
                ============================================================
                ============================================================*/
                new SendTokenThread(token).start();
            }
        });
    }

    class SendTokenThread extends Thread{
        String token;
        public SendTokenThread(String token) {
            this.token = token;
        }
        @Override
        public void run() {
            super.run();
            try {
                OkHttpClient client = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                builder = builder.url("http://172.30.1.46:8088/cafeoda/fcm/fcm_check?id="+myid+"&token="+token);
                Request request = builder.build();
                Call newcall = client.newCall(request);
                newcall.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    class requestThread extends Thread{
        String sendId;
        String recvId;
        public requestThread(String sendId, String recvId) {
            this.sendId = sendId;
            this.recvId = recvId;
        }

        @Override
        public void run() {
            super.run();
            try {
                OkHttpClient client = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                builder = builder.url("http://172.30.1.46:8088/cafeoda/fcm/sendCompleteClient?sendId="+sendId+"&recvId="+recvId);
                Request request = builder.build();
                Call newcall = client.newCall(request);
                newcall.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
