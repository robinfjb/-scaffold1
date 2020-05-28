package robin.scaffold.sample1.widget;


public class BottomTabEntity {
    private String title;
    private String normalPath;
    private String selectPath;
    private int defaultNormalId;
    private int defaultSelectedId;
    private String defaultTitle;
    private int normalTextColor;
    private int selectedTextColor;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNormalPath() {
        return normalPath;
    }

    public void setNormalPath(String normalPath) {
        this.normalPath = normalPath;
    }

    public String getSelectPath() {
        return selectPath;
    }

    public void setSelectPath(String selectPath) {
        this.selectPath = selectPath;
    }

    public int getDefaultNormalId() {
        return defaultNormalId;
    }

    public void setDefaultNormalId(int defaultNormalId) {
        this.defaultNormalId = defaultNormalId;
    }

    public int getDefaultSelectedId() {
        return defaultSelectedId;
    }

    public void setDefaultSelectedId(int defaultSelectedId) {
        this.defaultSelectedId = defaultSelectedId;
    }

    public String getDefaultTitle() {
        return defaultTitle;
    }

    public void setDefaultTitle(String defaultTitle) {
        this.defaultTitle = defaultTitle;
    }

    public int getNormalTextColor() {
        return normalTextColor;
    }

    public void setNormalTextColor(int normalTextColor) {
        this.normalTextColor = normalTextColor;
    }

    public int getSelectedTextColor() {
        return selectedTextColor;
    }

    public void setSelectedTextColor(int selectedTextColor) {
        this.selectedTextColor = selectedTextColor;
    }
}
