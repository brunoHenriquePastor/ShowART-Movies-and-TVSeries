package br.ufop.tomaz.util;

public enum Screen {
    LOGIN("FXMLLogin.fxml"), REGISTER("FXMLRegister.fxml"), MAIN("FXMLMain.fxml"),
    SERIES_LIBRARY("FXMLSeriesLibrary.fxml");

    private final String fxmlName;

    Screen(String fxmlName){
        this.fxmlName = fxmlName;
    }

    public String getPath() {
        return "/br/ufop/tomaz/fxml/".concat(fxmlName);
    }
}
