package cinema.view;

import cinema.ObservableWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

import java.io.File;

public class RootLayoutController {

    private ObservableWrapper wrapper;

    public void setWrapper( ObservableWrapper wrapper) {
        this.wrapper = wrapper;
    }

    @FXML
    private void handleNew() {
        wrapper.getMovieData().clear();
        wrapper.getUtil().setMovieFilePath(null);
        wrapper.getSessionData().clear();
        wrapper.getUtil().setSessionFilePath(null);
    }

    @FXML
    private void handleSave() {
        File movieFile = wrapper.getUtil().getMovieFilePath();
        File sessionFile = wrapper.getUtil().getSessionFilePath();

        if (movieFile != null && sessionFile != null) {
            wrapper.getUtil().saveMovieDataToFile(movieFile);
            wrapper.getUtil().saveSessionDataToFile(sessionFile);
        }
    }

    @FXML
    private void handleAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("О программе.");
        alert.setHeaderText("About");
        alert.setContentText("Автор: Леонид Бондаренко\nСпециально для BrainAcademia");

        alert.showAndWait();
    }

    @FXML
    private void handleExit() {
        System.exit(0);
    }
}
