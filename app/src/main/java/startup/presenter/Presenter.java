package startup.presenter;

import android.content.Context;
import android.widget.Toast;

import base.BasePresenter;
import startup.model.IStartupModel;
import startup.model.StartupModel;
import startup.view.IStartupView;

public class Presenter extends BasePresenter<IStartupView> implements IStartupModel.onGetImageUrlListener{

    private IStartupView iStartView;
    private IStartupModel iStartupModel;

    public Presenter(IStartupView iStartView)
    {
        this.iStartView=iStartView;
        iStartupModel=new StartupModel();
    }

    public void getImageUrl(Context context)
    {
        iStartupModel.getImageUrl(context,this);
    }

    @Override
    public void onGetImageUrlSuccess(String imageUrl) {
           iStartView.showStartupImage(imageUrl);
    }

    @Override
    public void onGetImageUrlFailure() {
        iStartView.showGetImageUrlFailure();
    }
}
