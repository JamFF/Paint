package com.ff.paint.xfermode;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ff.paint.widget.XfermodeEraserView;

/**
 * description: 刮刮卡
 * author: FF
 * time: 2019/4/1 17:56
 */
public class XfermodeEraserFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return new XfermodeEraserView(getContext());
    }
}
