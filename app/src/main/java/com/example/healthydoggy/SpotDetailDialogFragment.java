package com.example.healthydoggy;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.viewpager.widget.ViewPager;
import com.squareup.picasso.Picasso;
import java.util.List;

public class SpotDetailDialogFragment extends DialogFragment {
    private static final String ARG_SPOT = "spot";
    private Spot mSpot;

    // 创建实例的静态方法
    public static SpotDetailDialogFragment newInstance(Spot spot) {
        SpotDetailDialogFragment fragment = new SpotDetailDialogFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_SPOT, spot); // 需要Spot类实现Parcelable接口
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mSpot = getArguments().getParcelable(ARG_SPOT);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_spot_detail, container, false);
        initViews(view);
        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 去掉标题栏
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        // 设置对话框宽度为屏幕宽度的90%
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setLayout(
                    (int) (getResources().getDisplayMetrics().widthPixels * 0.9),
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
        }
    }

    private void initViews(View view) {
        if (mSpot == null) return;

        // 图片轮播
        ViewPager viewPager = view.findViewById(R.id.dialog_viewpager);
        List<String> picUrls = mSpot.getPic();
        if (picUrls != null && !picUrls.isEmpty()) {
            SpotImageAdapter adapter = new SpotImageAdapter(getContext(), picUrls);
            viewPager.setAdapter(adapter);
        }

        // 标题
        TextView titleTv = view.findViewById(R.id.dialog_title);
        titleTv.setText(mSpot.getTitle() != null ? mSpot.getTitle() : "");

        // 价格和评分
        TextView priceTv = view.findViewById(R.id.dialog_price);
        priceTv.setText("¥" + mSpot.getPrice() + "/人");
        TextView scoreTv = view.findViewById(R.id.dialog_score);
        scoreTv.setText("评分: " + mSpot.getScore());

        // 地址
        TextView addressTv = view.findViewById(R.id.dialog_address);
        addressTv.setText(mSpot.getAddress() != null ? mSpot.getAddress() : "");

        // 行程信息（日期、时长、人数）
        TextView dateTv = view.findViewById(R.id.dialog_date);
        dateTv.setText("日期: " + (mSpot.getDateTour() != null ? mSpot.getDateTour() : "暂无"));
        TextView durationTv = view.findViewById(R.id.dialog_duration);
        durationTv.setText("时长: " + (mSpot.getDuration() != null ? mSpot.getDuration() : "暂无"));
        TextView peopleTv = view.findViewById(R.id.dialog_people);
        peopleTv.setText("参与人数: " + (mSpot.getTourCount() != null ? mSpot.getTourCount() : "暂无"));

        // 其他信息（距离、床位）
        TextView distanceTv = view.findViewById(R.id.dialog_distance);
        distanceTv.setText("距离: " + (mSpot.getDistance() != null ? mSpot.getDistance() : "暂无"));
        TextView bedTv = view.findViewById(R.id.dialog_bed);
        bedTv.setText("床位: " + mSpot.getBed() + "个");

        // 描述
        TextView descTv = view.findViewById(R.id.dialog_description);
        descTv.setText(mSpot.getDescription() != null ? mSpot.getDescription() : "暂无介绍");

        // 关闭按钮
        ImageView closeIv = view.findViewById(R.id.dialog_close);
        closeIv.setOnClickListener(v -> dismiss());
    }
}