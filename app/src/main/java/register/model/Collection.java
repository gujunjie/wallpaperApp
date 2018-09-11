package register.model;

import io.realm.RealmObject;

public class Collection extends RealmObject{

    private String collectionImageUrl;



    public String getCollectionImageUrl() {
        return collectionImageUrl;
    }

    public void setCollectionImageUrl(String collectionImageUrl) {
        this.collectionImageUrl = collectionImageUrl;
    }
}
