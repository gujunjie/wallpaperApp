package register.model;

public interface IModel {

    void getRegisterMessage(String userName,String password,onGetRegisterListener listener);

    interface onGetRegisterListener
    {
        void onSuccess(String retcode,String msg);
        void onFailure(String msg);
    }

    void saveInDataBase(String userName,onSaveInDataBaseListener listener);

        interface onSaveInDataBaseListener
        {
            void onSuccess();
            void onFailure();
        }
}
