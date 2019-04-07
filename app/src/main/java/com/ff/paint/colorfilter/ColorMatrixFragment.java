package com.ff.paint.colorfilter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.ff.paint.utils.ColorFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * description: ColorMatrixColorFilter多种滤镜效果
 * author: FF
 * time: 2019/4/6 10:52
 */
public class ColorMatrixFragment extends Fragment {

    private static final List<float[]> FLOATS = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        initFilters();

        RecyclerView recyclerView = new RecyclerView(Objects.requireNonNull(getContext()));
        recyclerView.setLayoutParams(new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        ColorFilterAdapter colorFilterAdapter = new ColorFilterAdapter(getLayoutInflater(), FLOATS);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(colorFilterAdapter);

        return recyclerView;
    }

    private void initFilters() {
        FLOATS.add(ColorFilter.colormatrix_heibai);
        FLOATS.add(ColorFilter.colormatrix_fugu);
        FLOATS.add(ColorFilter.colormatrix_gete);
        FLOATS.add(ColorFilter.colormatrix_chuan_tong);
        FLOATS.add(ColorFilter.colormatrix_danya);
        FLOATS.add(ColorFilter.colormatrix_guangyun);
        FLOATS.add(ColorFilter.colormatrix_fanse);
        FLOATS.add(ColorFilter.colormatrix_hepian);
        FLOATS.add(ColorFilter.colormatrix_huajiu);
        FLOATS.add(ColorFilter.colormatrix_jiao_pian);
        FLOATS.add(ColorFilter.colormatrix_landiao);
        FLOATS.add(ColorFilter.colormatrix_langman);
        FLOATS.add(ColorFilter.colormatrix_ruise);
        FLOATS.add(ColorFilter.colormatrix_menghuan);
        FLOATS.add(ColorFilter.colormatrix_qingning);
        FLOATS.add(ColorFilter.colormatrix_yese);
    }
}
