package com.ff.paint.gradient;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ff.paint.widget.BitmapShaderLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * description: BitmapShader中TileMode的效果
 * author: FF
 * time: 2019/4/1 17:53
 */
public class BitmapShaderFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return new BitmapShaderLayout(getContext());
    }
}
