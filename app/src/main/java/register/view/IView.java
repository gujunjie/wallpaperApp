package register.view;

public interface IView {

    void register(String retcode,String msg);

    void save();

    void netWorkError(String msg);

}
