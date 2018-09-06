package cinema.view;

import cinema.model.Movie;
import cinema.model.Session;
import cinema.util.Parser;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sun.util.resources.LocaleData;

import java.time.LocalDate;

import static cinema.util.Validator.checkInputData;

public class SessionOperationsController {

        @FXML
        private TextField filmName2Field;
        @FXML
        private DatePicker dp;
        @FXML
        private TextField timeField;


        private Stage dialogStage;
        private Movie movie;
        private Session session;
        private boolean okClicked = false;

        @FXML
        private void initialize() {
        }

        public void setDialogStage(Stage dialogStage) {
            this.dialogStage = dialogStage;
        }


        public void setSession(Movie movie, Session session) {
            this.movie = movie;
            this.session = session;
            String[] dateTime = Parser.encodeDataTime(session.getDataTime());
            String[] dateSplit = dateTime[0].split("\\.");

            filmName2Field.setText(movie.getNameMovie());
            filmName2Field.setEditable(false);

            dp.setValue(LocalDate.of(Integer.parseInt(dateSplit[0]), Integer.parseInt(dateSplit[1]),
                    Integer.parseInt(dateSplit[2])));
            dp.setStyle("-fx-font-size:14px;/*-fx-font-weight:bold;*/");
            timeField.setText(dateTime[1]);
            dateTime = null;
            dateSplit = null;
        }


        public boolean isOkClicked() {
            return okClicked;
        }

        @FXML
        private void handleOk() {
            if (isInputValid()) {

                String dateTime = Parser.decodeDataTime(dp.getValue().toString().replace('-', '.'),
                        timeField.getText());
                session.setNameMovie(filmName2Field.getText());
                session.setDataTime(dateTime);

                okClicked = true;
                dialogStage.close();
            }
        }

        @FXML
        private void handleCancel() {
            dialogStage.close();
        }


        private boolean isInputValid() {
            String errorMessage = "";

            if (timeField.getText() == null || timeField.getText().length() == 0
                    || checkInputData(timeField.getText(), "^(([0,1][0-9])|(2[0-3])):[0-5][0-9]$")) {
                errorMessage += "Ввведите время!\n";
            }

            if (errorMessage.length() == 0) {
                return true;
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initOwner(dialogStage);
                alert.setTitle("Неверное значение");
                alert.setHeaderText("Пожалуйста введите правльное значение в поле:");
                alert.setContentText(errorMessage);

                alert.showAndWait();

                return false;
            }
        }
}
