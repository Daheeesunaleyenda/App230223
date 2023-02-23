package com.kdh.app230223;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {


    BottomNavigationView bnv;
    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bnv = findViewById(R.id.bottomNavigationView);
        frameLayout = findViewById(R.id.frameLayout);

        // bnv를 클릭했을 때 어떤 메뉴가 선택됐는지 검사해서 frameLayout에 들어갈
        // Fragment 갈아끼워주면 됨!

        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new Fragment1()).commit();
        bnv.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // 내가 선택한 메뉴
                if(item.getItemId()==R.id.item1){
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new Fragment1()).commit();
                    // fragment 관리하는 애, 작업 보관하는 곳 준비, (갈아끼울 위치, 끼우고 싶은 fragment객체) commit으로 반영
                }else if(item.getItemId()==R.id.item2){
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new Fragment2()).commit();
                }else if(item.getItemId()==R.id.item3){
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new Fragment3()).commit();
                }else{
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new Fragment4()).commit();
                }
                // 현재 이벤트가 종료되었는지 여부를 리턴 => 특히, LongClick할때
                return true;
            }
        });


    }
}