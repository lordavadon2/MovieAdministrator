package cinema;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.File;

public class Main extends Application {

    ObservableWrapper wrapper;

    @Override
    public void stop() throws Exception {
        super.stop();
        File movieFile = wrapper.getUtil().getMovieFilePath();
        File sessionFile = wrapper.getUtil().getSessionFilePath();
        if (movieFile != null && sessionFile != null) {
            wrapper.getUtil().saveMovieDataToFile(movieFile);
            wrapper.getUtil().saveSessionDataToFile(sessionFile);
        }
    }

    @Override
    public void start(Stage primaryStage) {
        wrapper  = new ObservableWrapper(primaryStage);
        wrapper.appRun();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
