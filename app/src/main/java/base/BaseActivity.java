package base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public abstract class BaseActivity<V,P extends BasePresenter<V>> extends AppCompatActivity {

    protected P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();
        mPresenter.attach((V)this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detach();
    }

    public abstract P createPresenter();
}
