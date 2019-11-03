package cn.dozyx;

import java.io.Serializable;

/**
 * @author dozyx
 * @date 2019-11-02
 */
public class SerializableUser implements Serializable {
    private static final long serialVersionUID = 87113688010083046L;
    public int userId;
    public String userName;
    public boolean isMale;

    public SerializableUser(int userId, String userName, boolean isMale) {
        this.userId = userId;
        this.userName = userName;
        this.isMale = isMale;
    }

    @Override
    public String toString() {
        return "SerializableUser{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", isMale=" + isMale +
                '}' + super.toString();
    }

}
