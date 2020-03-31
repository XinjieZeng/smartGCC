package smartgcc.model;

public enum Layout {
    EDITOR_PANEL("layout/EditorPanel.fxml"),
    SWITCH_USER_PANEL("layout/UserSwitchDialogue.fxml"),
    TOOLBOX_PANEL("layout/ToolBoxPanel.fxml"),
    HELP_PANEL("layout/HelpPanel.fxml");

    public String getPath() {
        return path;
    }

    private String path;
    Layout(String path) {
        this.path = path;
    }
}
