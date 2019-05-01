package com.ff.paint;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.ff.paint.colorfilter.ColorFilterFragment;
import com.ff.paint.colorfilter.ColorMatrixFragment;
import com.ff.paint.gradient.BitmapShaderFragment;
import com.ff.paint.gradient.GradientFragment;
import com.ff.paint.xfermode.GraphicsFragment;
import com.ff.paint.xfermode.XfermodeEraserFragment;
import com.ff.paint.xfermode.XfermodeFragment;
import com.ff.paint.xfermode.XfermodesFragment;

public class MainActivity extends AppCompatActivity implements MainFragment.OnListItemClickListener {

    private FrameLayout mRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRoot = new FrameLayout(this);
        mRoot.setId(View.generateViewId());
        mRoot.setLayoutParams(new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        setContentView(mRoot);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(mRoot.getId(), new MainFragment())
                    .commit();
        }
    }

    @Override
    public void onListItemClick(int position) {
        Fragment fragment;
        switch (position) {
            case 0:// 着色器
                fragment = new GradientFragment();
                break;
            case 1:// 位图着色器
                fragment = new BitmapShaderFragment();
                break;
            case 2:// 离屏绘制
                fragment = new XfermodeFragment();
                break;
            case 3:// Google的图形混合模式
                fragment = new GraphicsFragment();
                break;
            case 4:// 图形混合模式
                fragment = new XfermodesFragment();
                break;
            case 5:// 刮刮卡效果
                fragment = new XfermodeEraserFragment();
                break;
            case 6:// 三种滤镜效果
                fragment = new ColorFilterFragment();
                break;
            case 7:// 更多滤镜效果
                fragment = new ColorMatrixFragment();
                break;
            default:
                return;

        }
        getSupportFragmentManager().beginTransaction()
                .replace(mRoot.getId(), fragment)
                .addToBackStack(null)
                .commit();
    }
}
