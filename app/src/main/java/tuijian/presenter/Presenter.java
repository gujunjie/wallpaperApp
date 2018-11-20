package tuijian.presenter;

import android.content.Context;

import base.BasePresenter;
import tuijian.model.IModel;
import tuijian.model.NewsBean;
import tuijian.model.TuiJianModel;
import tuijian.view.IView;

public class Presenter extends BasePresenter<IView> implements IModel.onGetNewsBeanListener{

    private IView iView;

    private IModel iModel;

    public Presenter(IView iView)
    {
        this.iView=iView;
        iModel=new TuiJianModel();
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
