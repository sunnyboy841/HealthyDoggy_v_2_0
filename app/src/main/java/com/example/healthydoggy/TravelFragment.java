package com.example.healthydoggy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TravelFragment extends Fragment {
    private ListView spotListView;
    private SpotAdapter spotAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_travel, container, false);
        spotListView = view.findViewById(R.id.spotListView);
        loadSpots(); // 加载景点数据
        return view;
    }

    private void loadSpots() {
        // 修正：使用RetrofitClient中实际存在的getSpotService()方法
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
                        spotAdapter = new SpotAdapter(getContext(), spotResponse.getData());
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