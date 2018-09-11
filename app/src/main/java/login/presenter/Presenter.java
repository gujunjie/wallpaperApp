package login.presenter;

import android.content.Context;

import base.BasePresenter;
import login.model.IModel;
import login.model.LoginModel;
import login.view.IView;

public class Presenter extends BasePresenter<IView> implements IModel.onGetLoginMessageListener{


    private IView iView;
    private IModel iModel;

    public Presenter(IView iView)
    {
        this.iView=iView;
        iModel=new LoginModel();
    }

    public void getLoginMessage(String userName,String password)
    {
        iModel.getLoginMessage(userName,password,this);
    }

    public void upDateLoginState(Context context,String userName)
    {
        iModel.upDateLoginState(context,userName);
    }

    @Override
    public void onSuccess(String retcode, String msg) {
        iView.login(retcode,msg);
    }

    @Override
    public void onFailure() {
           iView.netWorkError();
    }
}
