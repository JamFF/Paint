package com.ff.paint.xfermode;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ff.paint.R;
import com.ff.paint.widget.XfermodeView;

/**
 * description: 离屏绘制
 * author: FF
 * time: 2019/4/1 17:56
 */
public class XfermodeFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_xfermode, container, false);
        ToggleButton tb = view.findViewById(R.id.tb);
        final XfermodeView xfv = view.findViewById(R.id.xfv);
        tb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    xfv.setOffScreen(true);
                } else {
                    xfv.setOffScreen(false);
                }
                xfv.invalidate();
            }
        });
        return view;
    }
}
