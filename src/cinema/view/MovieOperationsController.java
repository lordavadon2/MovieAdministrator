package cinema.view;

import cinema.model.Movie;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import static cinema.util.Validator.*;

public class MovieOperationsController {

        @FXML
        private TextField filmNameField;
        @FXML
        private TextField ganreField;
        @FXML
        private TextField durabilityField;
        @FXML
        private TextField birthdayField;


        private Stage dialogStage;
        private Movie movie;
        private boolean okClicked = false;

        @FXML
        private void initialize() {
        }

        public void setDialogStage(Stage dialogStage) {
            this.dialogStage = dialogStage;
        }


        public void setMovie(Movie movie) {
            this.movie = movie;

            filmNameField.setText(movie.getNameMovie());
            ganreField.setText(movie.getGanre());
            durabilityField.setText(Integer.toString(movie.getDurability()));
            birthdayField.setText(Integer.toString(movie.getDateBirthday()));
        }


        public boolean isOkClicked() {
            return okClicked;
        }

        @FXML
        private void handleOk() {
            if (isInputValid()) {
                movie.setNameMovie(filmNameField.getText());
                movie.setGanre(ganreField.getText());
                movie.setDurability(Integer.parseInt(durabilityField.getText()));
                movie.setDateBirthday(Integer.parseInt(birthdayField.getText()));

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

            if (filmNameField.getText() == null || filmNameField.getText().length() == 0) {
                errorMessage += "Введите имя фильма!\n";
            }
            if (ganreField.getText() == null || ganreField.getText().length() == 0
                    || checkInputData(ganreField.getText(), "^[а-яА-Я]*$")) {
                errorMessage += "Введите жанр!\n";
            }

            if (durabilityField.getText() == null || durabilityField.getText().length() == 0
                    || checkInputData(durabilityField.getText(),
                    "^[6-9]{1}[0-9]{1}$|^[1]{1}[0-7]{1}[0-9]{1}$|^180$")) {
                errorMessage += "Ввведите длительность фильма!\n";
            }
            if (birthdayField.getText() == null || birthdayField.getText().length() == 0
                    || checkInputData(birthdayField.getText(), "^[2]{1}[0]{1}[0-2]{1}[0-9]{1}$|^2030$")) {
                errorMessage += "Ввведите дату выпуска фильма!\n";
            }

            if (errorMessage.length() == 0) {
                return true;
            } else {
                // Показываем сообщение об ошибке.
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
