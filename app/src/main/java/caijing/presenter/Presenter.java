package caijing.presenter;

import android.content.Context;

import base.BasePresenter;
import caijing.model.CaiJingModel;
import caijing.model.IModel;
import caijing.view.IView;
import tuijian.model.NewsBean;


public class Presenter extends BasePresenter<IView> implements IModel.onGetNewsBeanListener{

    private IView iView;

    private IModel iModel;

    public Presenter(IView iView)
    {
        this.iView=iView;
        iModel=new CaiJingModel();
    }

    public void getNewsBean(Context context)
    {
        iModel.getNewsBean(context,this);
    }


    @Override
    public void getNewsBeanSuccess(NewsBean.DataBean dataBean) {
        iView.showNewsData(dataBean);
    }

    @Override
    public void getNewsBeanFailure(String message) {
        iView.showError(message);
    }
}
