package top.touchstudio.cup.modules.login;

import java.io.Serializable;

public class PlayerData implements Serializable {
    private final String password;
    private final boolean registered;

    public PlayerData(String password, boolean registered) {
        this.password = password;
        this.registered = registered;
    }

    public String getPassword() {
        return password;
    }

    public boolean isRegistered() {
        return registered;
    }
}
