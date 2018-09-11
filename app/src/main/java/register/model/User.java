package register.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class User extends RealmObject {
    @PrimaryKey
    private String userName;

    private byte[] icon;

    private RealmList<Collection> list;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public byte[] getIcon() {
        return icon;
    }

    public void setIcon(byte[] icon) {
        this.icon = icon;
    }

    public RealmList<Collection> getList() {
        return list;
    }

    public void setList(RealmList<Collection> list) {
        this.list = list;
    }
}
