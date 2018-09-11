package enlargecollection.view;

import java.io.File;

public interface IView {

    void tipsCollectionDownloadSuccess(File file, String fileName);

    void tipsCollectionDownloadFailure();

    void tipsDeleteCollectionSuccess();
}
