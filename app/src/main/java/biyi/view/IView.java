package biyi.view;

import java.io.File;

public interface IView {

    void showBiyiImage(String url);

    void netWorkError();

    void tipsbiyiDownloadSuccess(File file,String filename);

    void tipsbiyiDownloadFailure();

    void tipsbiyiCollectionSuccess();
}
