package poly.edu.vn.asm.chats;

public class Friends {
    String name;
    String message;
    int avatar;

    public Friends(String name, String message, int avatar) {
        this.name = name;
        this.message = message;
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }
}
