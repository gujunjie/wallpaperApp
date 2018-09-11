package enlarge.view;

import java.io.File;

public interface IView {

    void tipsCollectionSuccess();

    void tipsDownloadSuccess(File file, String fileName);

    void tipsDownloadFailure();
}
