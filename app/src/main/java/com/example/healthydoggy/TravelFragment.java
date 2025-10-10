package com.example.healthydoggy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import androidx.fragment.app.DialogFragment;  // 在 TravelFragment 顶部添加

public class TravelFragment extends Fragment {
    private ListView spotListView;
    private SpotAdapter spotAdapter;
    private List<Spot> spotList; // 保存景点列表数据，用于点击事件

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_travel, container, false);
        spotListView = view.findViewById(R.id.spotListView);
        setupListViewListener(); // 初始化列表点击监听
        loadSpots(); // 加载景点数据
        return view;
    }

    // 初始化列表点击事件监听
    private void setupListViewListener() {
        spotListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 检查数据是否有效
                if (spotList != null && position >= 0 && position < spotList.size()) {
                    Spot clickedSpot = spotList.get(position);
                    showSpotDetailDialog(clickedSpot); // 显示详情对话框
                } else {
                    showToast("数据加载中，请稍后再试");
                }
            }
        });
    }

    // 显示景点详情对话框
    private void showSpotDetailDialog(Spot spot) {
        // 确保Fragment已附加到Activity
        if (!isAdded()) return;

        FragmentManager fragmentManager = getChildFragmentManager();
        // 创建并显示详情对话框
        SpotDetailDialogFragment dialogFragment = SpotDetailDialogFragment.newInstance(spot);
        // 设置对话框样式（可选，如需要全屏或特定动画）
        //目前不需要，暂且移除
        //dialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialogStyle);

        dialogFragment.show(fragmentManager, "SpotDetail_" + spot.getId());
    }

    private void loadSpots() {
        SpotService spotService = RetrofitClient.getSpotService();
        if (spotService == null) {
            showToast("网络服务初始化失败");
            return;
        }

        Call<SpotResponse> call = spotService.getSpots();

        call.enqueue(new Callback<SpotResponse>() {
            @Override
            public void onResponse(Call<SpotResponse> call, Response<SpotResponse> response) {
                if (!isAdded()) return;

                if (response.isSuccessful() && response.body() != null) {
                    SpotResponse spotResponse = response.body();
                    if (spotResponse.getData() != null && !spotResponse.getData().isEmpty()) {
                        spotList = spotResponse.getData(); // 保存数据引用
                        spotAdapter = new SpotAdapter(getContext(), spotList);
                        spotListView.setAdapter(spotAdapter);
                    } else {
                        showToast("暂无景点数据");
                    }
                } else {
                    showToast("请求失败，状态码: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<SpotResponse> call, Throwable t) {
                if (isAdded()) {
                    showToast("网络错误: " + (t.getMessage() != null ? t.getMessage() : "未知错误"));
                }
            }
        });
    }

    private void showToast(String message) {
        if (getContext() != null) {
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        }
    }
}