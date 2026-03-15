package com.example.healthydoggy;

import android.os.Bundle;
import android.view.MenuItem;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // 初始化ViewPager2和适配器
        ViewPager2 viewPager = findViewById(R.id.viewPager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(adapter);
        viewPager.setUserInputEnabled(true); // 允许滑动切换

        // 初始化底部导航
        BottomNavigationView navView = findViewById(R.id.bottomNavigation);

        // 核心：强制清除图标着色，显示PNG原图
        navView.setItemIconTintList(null);

        // 调整图标大小（确保放大到32dp，适配不同设备）
        adjustNavIconSize(navView);

        // 底部导航点击事件
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.nav_health) {
                    viewPager.setCurrentItem(0);
                    return true;
                } else if (itemId == R.id.nav_store) {
                    viewPager.setCurrentItem(1);
                    return true;
                } else if (itemId == R.id.nav_forum) {
                    viewPager.setCurrentItem(2);
                    return true;
                } else if (itemId == R.id.nav_travel) {
                    viewPager.setCurrentItem(3);
                    return true;
                } else if (itemId == R.id.nav_profile) {
                    viewPager.setCurrentItem(4);
                    return true;
                }
                return false;
            }
        });

        // 监听ViewPager页面变化，同步导航选中状态
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position) {
                    case 0:
                        navView.setSelectedItemId(R.id.nav_health);
                        break;
                    case 1:
                        navView.setSelectedItemId(R.id.nav_store);
                        break;
                    case 2:
                        navView.setSelectedItemId(R.id.nav_forum);
                        break;
                    case 3:
                        navView.setSelectedItemId(R.id.nav_travel);
                        break;
                    case 4:
                        navView.setSelectedItemId(R.id.nav_profile);
                        break;
                }
            }
        });

        // 默认显示健康页面
        if (savedInstanceState == null) {
            navView.setSelectedItemId(R.id.nav_health);
        }
    }

    // 调整导航图标大小的方法（dp转px确保适配）
    private void adjustNavIconSize(BottomNavigationView navView) {
        int iconSizeDp = 32; // 目标大小：32dp
        // 将dp转换为像素（适配不同屏幕密度）
        int iconSizePx = (int) (iconSizeDp * getResources().getDisplayMetrics().density + 0.5f);

        // 遍历所有菜单项，设置图标边界
        for (int i = 0; i < navView.getMenu().size(); i++) {
            MenuItem item = navView.getMenu().getItem(i);
            Drawable icon = item.getIcon();
            if (icon != null) {
                // 设置图标边界（宽高均为计算后的像素值）
                icon.setBounds(0, 0, iconSizePx, iconSizePx);
                item.setIcon(icon);
            }
        }
    }
}