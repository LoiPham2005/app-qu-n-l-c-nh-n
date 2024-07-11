package poly.edu.vn.asm.settingscreen;

public class Setting {
    String nameSetting;
    int iconSetting;

    public Setting(String nameSetting, int iconSetting) {
        this.nameSetting = nameSetting;
        this.iconSetting = iconSetting;
    }

    public String getNameSetting() {
        return nameSetting;
    }

    public void setNameSetting(String nameSetting) {
        this.nameSetting = nameSetting;
    }

    public int getIconSetting() {
        return iconSetting;
    }

    public void setIconSetting(int iconSetting) {
        this.iconSetting = iconSetting;
    }
}
