package com.kdh.app230223;

import android.content.pm.ApplicationInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.sql.Time;


public class Fragment4 extends Fragment {
    Button btn1, btn2;
    TextView tv1, tv2;
    int cnt1, cnt2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       // xml코드를 자바 view 객체로 변환(inflate)
        // java랑 짝 지어진 xml 파일을 View객체(inflate해서)로 만들어서 리턴
        View view = inflater.inflate(R.layout.fragment_4, container, false);
            btn1 = view.findViewById(R.id.btn1);
            btn2 = view.findViewById(R.id.btn2);
            tv1 = view.findViewById(R.id.tv1);
            tv2 = view.findViewById(R.id.tv2);


            // 1. 1~30까지 숫자를 세는 Thread 설계 (클래스로) -> 설계는 1개
            // 2. 버튼 눌렀을 때 Thread 객체를 생성하고 start -> 생성은 2개
            btn1.setOnClickListener(v -> {
                if(cnt1==0) {
                    TimeThread thread = new TimeThread(tv1);
                    thread.start();
                    cnt1++;
                }else{
//                    Toast.makeText(, "누르지마!!!!", Toast.LENGTH_SHORT).show();
                }

            });
            btn2.setOnClickListener(v -> {
               if(cnt2==0) {
                   TimeThread thread = new TimeThread(tv2);
                   thread.start();
                   cnt2++;
               }else{
                   Toast.makeText(getContext().getApplicationContext(), "그만 누르라고~~~", Toast.LENGTH_SHORT).show();
               }
            });
        return view;
    }



    Handler handler = new Handler(){
        // handler에 메세지 보낼 때는 sendMessage 메소드 사용
        // 받은 메세지 처리할 때는 handleMessage 메소드 사용

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
                msg.toString();
            // 매개변수 Message 객체는 Thread에서 보내준 메세지 그대로
            // 받아온 메세지 객체에서 TextView랑 i값 꺼내서 setText 해보기
            ((TextView)msg.obj).setText(String.valueOf(msg.arg1));

        }
    };


    class TimeThread extends Thread{

        // Thread 메소드 간추린 Life Cycle
        // start -> run(커스텀 가능) -> destroy
        private TextView tv;
        public TimeThread(TextView tv){
            this.tv= tv;
        };

        @Override
        public void run() {

            for(int i = 1; i<=30;i++){
                //                tv.setText(i+"");

                // 개발자가 설계한 Thread에서는 UI작업(setText, setImageResource etc..) 못함 <- 뒤죽박죽되니까 android에서 막음
                // Handler에게 요청(message를 보냄)해서 MainThread로 작업을 전달!

                Message msg = new Message();
                // 예전에 배웠던 viewHolder랑 비슷(데이터를 담는 역할만 함!)
                // Object타입 객체 1개(obj), int타입 변수 2개(arg1, arg2)를 담을 수 있음

                // 이 예제에서 Handler한테 보내줘야할 데이터는?
                // -> 숫자를 적어야하는 TextView, 적힐 숫자(i)
                msg.obj = tv;
                msg.arg1 = i;
                handler.sendMessage(msg); // 위에서 구성한 msg 객체 보내기!

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

        }
    }



}
