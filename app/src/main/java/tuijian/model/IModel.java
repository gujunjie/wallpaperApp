package tuijian.model;

import android.content.Context;

public interface IModel {

    void getNewsBean(Context context,onGetNewsBeanListener listener);

    interface onGetNewsBeanListener
    {
        void getNewsBeanSuccess(NewsBean.DataBean dataBean);
        void getNewsBeanFailure(String message);
    }

}
