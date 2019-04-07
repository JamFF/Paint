package com.ff.paint;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.ff.paint.colorfilter.ColorFilterFragment;
import com.ff.paint.colorfilter.ColorMatrixFragment;
import com.ff.paint.gradient.BitmapShaderFragment;
import com.ff.paint.gradient.GradientFragment;
import com.ff.paint.xfermode.GraphicsFragment;
import com.ff.paint.xfermode.XfermodeEraserFragment;
import com.ff.paint.xfermode.XfermodeFragment;
import com.ff.paint.xfermode.XfermodesFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

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
            case 0:
                fragment = new GradientFragment();
                break;
            case 1:
                fragment = new BitmapShaderFragment();
                break;
            case 2:
                fragment = new XfermodeFragment();
                break;
            case 3:
                fragment = new GraphicsFragment();
                break;
            case 4:
                fragment = new XfermodesFragment();
                break;
            case 5:
                fragment = new XfermodeEraserFragment();
                break;
            case 6:
                fragment = new ColorFilterFragment();
                break;
            case 7:
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
