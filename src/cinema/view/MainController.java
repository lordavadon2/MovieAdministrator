package cinema.view;

import cinema.ObservableWrapper;
import cinema.model.Movie;
import cinema.model.Session;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.File;

public class MainController {

    @FXML
    private TableView<Movie> movieTable;
    @FXML
    private TableColumn<Movie, String> nameMovieColumn;
    @FXML
    private TableColumn<Movie, String> ganreColumn;
    @FXML
    private TableColumn<Movie, Integer> durabilityColumn;
    @FXML
    private TableColumn<Movie, Integer> birthdayColumn;

    @FXML
    private TableView<Session> sessionTable;
    @FXML
    private TableColumn<Session, String> nameMovie2Column;
    @FXML
    private TableColumn<Session, String> dataTimeColumn;
    @FXML
    private TextField filterField;

    private ObservableWrapper wrapper;

    public MainController() {
    }

    @FXML
    private void initialize() {
        nameMovieColumn.setCellValueFactory(cellData -> cellData.getValue().nameMovieProperty());
        ganreColumn.setCellValueFactory(cellData -> cellData.getValue().ganreProperty());
        durabilityColumn.setCellValueFactory(cellData -> cellData.getValue().durabilityProperty().asObject());
        birthdayColumn.setCellValueFactory(cellData -> cellData.getValue().dateBirthdayProperty().asObject());

        nameMovie2Column.setCellValueFactory(cellData -> cellData.getValue().nameMovieProperty());
        dataTimeColumn.setCellValueFactory(cellData -> cellData.getValue().dataTimeProperty());
    }

    public void setWrapper( ObservableWrapper wrapper) {
        this.wrapper = wrapper;

        movieTable.setItems(wrapper.getMovieData());
        sessionTable.setItems(wrapper.getSessionData());
    }

//    @FXML
//    private void handleFilterTable(){
//        Movie movie = movieTable.getSelectionModel().getSelectedItem();
//        if (movie != null){
//            filterField.setText(movie.getNameMovie());
//            FilteredList<Session> filteredData = new FilteredList<>(wrapper.getSessionData(), p -> false);
//            filterField.textProperty().addListener((observable, oldValue, newValue) -> {
//                filteredData.setPredicate(sess -> {
//                    // If filter text is empty, display all persons.
//                    if (newValue == null || newValue.isEmpty()) {
//                        return true;
//                    }
//
//                    // Compare first name and last name of every person with filter text.
//                    String lowerCaseFilter = newValue.toLowerCase();
//
//
//                    if (sess.getNameMovie().toLowerCase().indexOf(lowerCaseFilter) != -1) {
//                        return true; // Filter matches first name.
//                    }
//                    return false; // Does not match.
//                });
//            });
//            SortedList<Session> sortedData = new SortedList<>(filteredData);
//            sortedData.comparatorProperty().bind(sessionTable.comparatorProperty());
//            sessionTable.setItems(sortedData);
//        }
//    }

    @FXML
    private void handleDeleteMovie(){
        int selectedIndex = movieTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            movieTable.getItems().remove(selectedIndex);
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(wrapper.getPrimaryStage());
            alert.setTitle("Неверный выбор");
            alert.setHeaderText("Не выбран фильм для удаления");
            alert.setContentText("Пожалуйста выберите фильм в таблице!");

            alert.showAndWait();
        }
    }

    @FXML
    private void handleNewMovie() {
        Movie tempMovie = new Movie();
        boolean okClicked = wrapper.showMovieEditDialog(tempMovie);
        if (okClicked) {
            wrapper.getMovieData().add(tempMovie);
        }
    }

    @FXML
    private void handleEditMovie() {
        Movie selectedMovie = movieTable.getSelectionModel().getSelectedItem();
        if (selectedMovie != null) {
            boolean okClicked = wrapper.showMovieEditDialog(selectedMovie);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(wrapper.getPrimaryStage());
            alert.setTitle("Запись не выбрана");
            alert.setHeaderText("Выберите фильм");
            alert.setContentText("Выберите фильм в таблице!");

            alert.showAndWait();
        }
    }

    @FXML
    private void handleDeleteSession(){
        int selectedIndex = sessionTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            sessionTable.getItems().remove(selectedIndex);
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(wrapper.getPrimaryStage());
            alert.setTitle("Неверный выбор");
            alert.setHeaderText("Не выбрана сессия для удаления");
            alert.setContentText("Пожалуйста выберите сессию в таблице!");

            alert.showAndWait();
        }
    }

    @FXML
    private void handleNewSession() {
        Session tempSession = new Session();
        Movie selectedMovie = movieTable.getSelectionModel().getSelectedItem();
        if (selectedMovie != null) {
            boolean okClicked = wrapper.showSessionEditDialog(tempSession, selectedMovie);
            if (okClicked) {
                wrapper.getSessionData().add(tempSession);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(wrapper.getPrimaryStage());
            alert.setTitle("Запись не выбрана");
            alert.setHeaderText("Выберите фильм");
            alert.setContentText("Выберите фильм в таблице!");

            alert.showAndWait();
        }
    }

    @FXML
    private void handleEditSession() {
        Session selectedSession = sessionTable.getSelectionModel().getSelectedItem();
        Movie selectedMovie = movieTable.getSelectionModel().getSelectedItem();
        if (selectedSession != null && selectedMovie != null) {
            boolean okClicked = wrapper.showSessionEditDialog(selectedSession, selectedMovie);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(wrapper.getPrimaryStage());
            alert.setTitle("Запись не выбрана");
            alert.setHeaderText("Выберите сессию и фильм");
            alert.setContentText("Выберите сессию и фильм в таблице!");

            alert.showAndWait();
        }
    }

    @FXML
    private void handleSaveAsMovie() {
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Виберети куда сохранить данные о фильмах");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

        // Задаём фильтр расширений
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Показываем диалог сохранения файла
        File file = fileChooser.showSaveDialog(wrapper.getPrimaryStage());

        if (file != null) {
            // Make sure it has the correct extension
            if (!file.getPath().endsWith(".xml")) {
                file = new File(file.getPath() + ".xml");
            }
            wrapper.getUtil().saveMovieDataToFile(file);
        }
    }

    @FXML
    private void handleSaveAsSession() {
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Виберети куда сохранить данные о сеансах");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

        // Задаём фильтр расширений
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Показываем диалог сохранения файла
        File file = fileChooser.showSaveDialog(wrapper.getPrimaryStage());

        if (file != null) {
            // Make sure it has the correct extension
            if (!file.getPath().endsWith(".xml")) {
                file = new File(file.getPath() + ".xml");
            }
            wrapper.getUtil().saveSessionDataToFile(file);
        }
    }

    @FXML
    private void handleOpenMovie() {
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Виберети файл с данными о фильмах");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

        // Задаём фильтр расширений
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Показываем диалог загрузки файла
        File file = fileChooser.showOpenDialog(wrapper.getPrimaryStage());

        if (file != null) {
            wrapper.getUtil().loadMovieDataFromFile(file);
        }
    }

    @FXML
    private void handleOpenSession() {
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Виберети файл с данными о сеансах");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

        // Задаём фильтр расширений
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Показываем диалог загрузки файла
        File file = fileChooser.showOpenDialog(wrapper.getPrimaryStage());

        if (file != null) {
            wrapper.getUtil().loadSessionDataFromFile(file);
        }
    }

}
