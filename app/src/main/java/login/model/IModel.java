package login.model;

import android.content.Context;

public interface IModel {

    void getLoginMessage(String userName,String password,onGetLoginMessageListener listener);

        interface onGetLoginMessageListener
        {
            void onSuccess(String retcode,String msg);
            void onFailure();
        }

        void upDateLoginState(Context context,String userName);
}
