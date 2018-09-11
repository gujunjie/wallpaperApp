package login.view;

import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abc.kantu.R;

import base.BaseActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import login.presenter.Presenter;
import register.view.RegisterActivity;

public class LoginActivity extends BaseActivity<IView, Presenter> implements IView {

    @BindView(R.id.et_userName)
    EditText etUserName;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.tv_showLoginMessage)
    TextView tvShowLoginMessage;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.tv_register)
    TextView tvRegister;
    @BindView(R.id.btn_back)
    Button btnBack;

    private Presenter presenter;

    private String userName;

    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);



        immersionBar();//沉浸式模式
    }

    @Override
    public Presenter createPresenter() {
        presenter = new Presenter(this);
        return presenter;
    }

    @OnClick({R.id.btn_back,R.id.btn_login, R.id.tv_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                getUserInputData();
                presenter.getLoginMessage(userName, password);
                break;
            case R.id.tv_register:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.btn_back:
                finish();
                break;
        }
    }

    public void getUserInputData() {
        userName = etUserName.getText().toString().trim();
        password = etPassword.getText().toString().trim();
    }//获取用户输入

    public void immersionBar() {
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 & resultCode == 1) {
            etUserName.setText(data.getStringExtra("username"));
        }
    }

    @Override
    public void login(String retcode, String msg) {
        if (retcode.equals("200")) {
            presenter.upDateLoginState(LoginActivity.this,userName);//更新登陆状态
            Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
            Intent intent=getIntent();
            intent.putExtra("username",userName);
            setResult(1,intent);
            finish();
        } else if(retcode.equals("22802"))
        {
            tvShowLoginMessage.setText("请输入您的账号");
        }else if(retcode.equals("22803"))
        {
            tvShowLoginMessage.setText("请输入您的密码");
        }else if(retcode.equals("22807"))
        {
            tvShowLoginMessage.setText("用户名或密码错误");
        }
    }


    @Override
    public void netWorkError() {
        Toast.makeText(this,"网络错误,请检查您的网络设置",Toast.LENGTH_SHORT).show();
    }
}
