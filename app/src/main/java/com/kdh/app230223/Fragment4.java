package com.kdh.app230223;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class Fragment4 extends Fragment {
    Button btn1, btn2;
    TextView tv1, tv2;

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

        return view;
    }
}