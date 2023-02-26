package com.kdh.app230223;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Random;


public class Fragment1 extends Fragment {
    int cnt;
    ImageView [] moles = new ImageView[9];
    MoleThread [] threads = new MoleThread[9];
    TextView tvScore, tvHide;
    TextView tvTime;
    Button btnStart;

    Vibrator vibrator;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_1, container, false);
        tvScore = view.findViewById(R.id.tvScore);
        tvTime = view.findViewById(R.id.tvTime);
        vibrator = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
        tvHide = view.findViewById(R.id.tvHide);
        btnStart = view.findViewById(R.id.btnStart);
        // 두더지 9개 findViewById
        btnStart.setOnClickListener(v->{
            cnt = 0;
            tvScore.setText("0");
            btnStart.setVisibility(View.GONE);
            tvHide.setVisibility(View.GONE);
            new ThreadTime().start();
            for(int i = 0; i< moles.length;i++){
                int imgId = getResources().getIdentifier("imageView"+(i+1),"id",getContext().getPackageName());
                // getContext() 액티비티가 아니라서 적어줌(activity 정보를 다 끌어옴)
                moles[i] = view.findViewById(imgId);
                threads[i] = new MoleThread(moles[i]);
                threads[i].start();
                moles[i].setTag("0");
                moles[i].setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        if(v.getTag().toString().equals("1")){
                            cnt++;
                            Toast.makeText(getContext(), "잡았다 요놈", Toast.LENGTH_SHORT).show();
                            v.setTag("0");
                            tvScore.setText(cnt+"");

                        }else{
                            Toast.makeText(getContext(), "에이~ 아깝다!", Toast.LENGTH_SHORT).show();
                            cnt--;
                            tvScore.setText(cnt+"");
                            vibrator.vibrate(200);
                        }
                    }
                });
            }
        });


        return view;
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            ((ImageView)msg.obj).setImageResource(msg.arg1);
            ((ImageView)msg.obj).setTag(msg.arg2);
        }
    };

    Handler timeHandler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            ((TextView)msg.obj).setText(msg.arg1+"");
        }
    };

    Handler showScoreHandler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            ((TextView)msg.obj).setText("최종 점수 : "+cnt);
            btnStart.setVisibility(View.VISIBLE);
            btnStart.setText("다시하기");
            tvHide.setVisibility(View.VISIBLE);


        }
    };



    class MoleThread extends Thread{
        private ImageView img;
        public MoleThread(ImageView img){
            this.img = img;
        }

        @Override
        public void run() {

            while(true){
             // 랜덤시간만큼 내려간 상태 유지
                int OffTime = new Random().nextInt(5000)+500;

                try {
                    Thread.sleep(OffTime);
                    // 두더지가 올라가는 이미지로 바꿔줄 것임
                    Message msg = new Message();
                    msg.obj = img;
                    msg.arg1 = R.drawable.mole3;
                    msg.arg2 = 1;
                    // 핸들러한테 보내줄 것임
                    handler.sendMessage(msg);

                    int onTime = new Random().nextInt(1000)+500;
                    Thread.sleep(onTime);
                    // 한번 보낸 Message 객체는 재활용 X
                    Message msg2 = new Message();
                    msg2.obj = img;
                    msg2.arg1= R.drawable.mole2;
                    msg2.arg2 = 0;
                    handler.sendMessage(msg2);

                } catch (InterruptedException e) {
                    Message msg3 = new Message();
                    msg3.obj = tvScore;
                    showScoreHandler.sendMessage(msg3);
                    break;
                }


            }
        }
    }

    class ThreadTime extends Thread{
        @Override
        public void run() {
            for(int i = 3;i>=0;i--){
                Message msg = new Message();
                try {
                    Thread.sleep(1000);
                    msg.obj = tvTime;
                    msg.arg1 = i;
                    timeHandler.sendMessage(msg);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            for(int i = 0; i<threads.length; i++){
                threads[i].interrupt();
                moles[i].setEnabled(false);
            }

        }
    }
}