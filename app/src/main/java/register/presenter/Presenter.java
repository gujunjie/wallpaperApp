package register.presenter;

import base.BasePresenter;
import register.model.IModel;
import register.model.RegisterModel;
import register.view.IView;

public class Presenter extends BasePresenter<IView> implements IModel.onGetRegisterListener,IModel.onSaveInDataBaseListener{

    private IView iView;
    private IModel iModel;

    public Presenter(IView iView)
    {
        this.iView=iView;
        iModel=new RegisterModel();
    }


    public void getRegisterMessage(String userName,String password)
    {
        iModel.getRegisterMessage(userName,password,this);
    }

    public void saveInDataBase(String userName)
    {
          iModel.saveInDataBase(userName,this);
    }


    @Override
    public void onSuccess(String retcode,String msg) {
        iView.register(retcode,msg);

    }

    @Override
    public void onFailure(String msg) {
        iView.netWorkError(msg);

    }

    @Override
    public void onSuccess() {
         iView.save();
    }

    @Override
    public void onFailure() {

    }
}
