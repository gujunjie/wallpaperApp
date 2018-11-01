package register.model;

import io.realm.RealmObject;

public class Collection extends RealmObject{

    private String collectionImageUrl;

    private int imageWidth;

    private int imageHeight;

    public int getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
    }

    public String getCollectionImageUrl() {
        return collectionImageUrl;
    }

    public void setCollectionImageUrl(String collectionImageUrl) {
        this.collectionImageUrl = collectionImageUrl;
    }
}
