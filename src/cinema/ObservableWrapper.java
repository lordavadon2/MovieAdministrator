package cinema;

import cinema.model.Movie;
import cinema.model.Session;
import cinema.util.DataUtil;
import cinema.view.MainController;
import cinema.view.MovieOperationsController;
import cinema.view.RootLayoutController;
import cinema.view.SessionOperationsController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class ObservableWrapper {

    private Stage primaryStage;
    private BorderPane borderPane;
    private DataUtil util;

    private ObservableList<Movie> movieData = FXCollections.observableArrayList();
    private ObservableList<Session>  sessionData = FXCollections.observableArrayList();

    public ObservableWrapper(Stage primaryStage) {
        this.primaryStage = primaryStage;
        util = new DataUtil(movieData, sessionData);
    }

    public void appRun(){
        this.primaryStage.setTitle("Система управления фильмами и сеансами");
        this.primaryStage.setResizable(false);

        // Set the application icon.
        this.primaryStage.getIcons().add(
                new Image("file:src/cinema/resources/ico1.png"));

        initRootLayout();

        showMainWindow();
    }

    public ObservableList<Movie> getMovieData() {
        return movieData;
    }

    public ObservableList<Session> getSessionData() {
        return sessionData;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public DataUtil getUtil() {
        return util;
    }

    private void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/RootLayout.fxml"));
            borderPane = (BorderPane) loader.load();


            Scene scene = new Scene(borderPane);
            primaryStage.setScene(scene);

            RootLayoutController controller = loader.getController();
            controller.setWrapper(this);

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        util.loader();
    }

    private void showMainWindow() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/MainWindow.fxml"));
            Pane mainOverview = (Pane) loader.load();

            borderPane.setCenter(mainOverview);

            MainController controller = loader.getController();
            controller.setWrapper(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean showMovieEditDialog(Movie movie) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/MovieOperations.fxml"));
            Pane page = (Pane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Редактирование фильма");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.setResizable(false);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            MovieOperationsController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setMovie(movie);

            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean showSessionEditDialog(Session session, Movie movie) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/SessionOperations.fxml"));
            Pane page = (Pane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Редактирование сессии");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.setResizable(false);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            SessionOperationsController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setSession(movie, session);

            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


}
