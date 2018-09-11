package register.view;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abc.kantu.R;


import base.BaseActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import register.presenter.Presenter;

public class RegisterActivity extends BaseActivity<IView, Presenter> implements IView {


    @BindView(R.id.btn_back)
    Button btnBack;
    @BindView(R.id.iv_icon)
    ImageView ivIcon;
    @BindView(R.id.et_userName)
    EditText etUserName;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_ConfirmPassword)
    EditText etConfirmPassword;
    @BindView(R.id.tv_showLoginMessage)
    TextView tvShowLoginMessage;
    @BindView(R.id.btn_register)
    Button btnRegister;
    private Presenter presenter;

    private String userName;

    private String password;

    private String password_confirm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);



        immersionBar();
    }

    @Override
    public Presenter createPresenter() {
        presenter = new Presenter(this);
        return presenter;
    }


    public void immersionBar() {
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }//沉浸式模式

    @Override
    public void register(String retcode, String msg) {

        if(retcode.equals("200"))
        {
            presenter.saveInDataBase(userName);//将新用户名写入数据库
        }
        else if(retcode.equals("22802"))
        {
            tvShowLoginMessage.setText("请输入您的昵称");
        }else if(retcode.equals("22803"))
        {
            tvShowLoginMessage.setText("请输入您的密码");
        }else if(retcode.equals("22806"))
        {
            tvShowLoginMessage.setText("此用户名已存在");
        }

    }//根据服务器返回的信息判断是否注册成功,成功则将用户数据写入数据库

    @Override
    public void save() {

        Toast.makeText(this,"注册成功",Toast.LENGTH_SHORT).show();
        Intent intent=getIntent();
        intent.putExtra("username",userName);
        setResult(1,intent);
        finish();
    }

    public boolean getUserInputData()
    {
        userName=etUserName.getText().toString().trim();
        password=etPassword.getText().toString().trim();
        password_confirm=etConfirmPassword.getText().toString().trim();
        if(password.equals(password_confirm))
        {
            return true;
        }else
        {
            return false;
        }
    }//获取用户的输入


    @OnClick({R.id.btn_back, R.id.btn_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_register:
                if(getUserInputData())
                {
                    presenter.getRegisterMessage(userName,password);//请求注册
                }else
                {
                    tvShowLoginMessage.setText("两次输入密码不一致,请重新输入");
                    etConfirmPassword.setText("");
                }
                break;
        }
    }

    @Override
    public void netWorkError(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }
}
