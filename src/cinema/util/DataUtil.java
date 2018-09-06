package cinema.util;

import cinema.Main;
import cinema.model.Movie;
import cinema.model.MovieListWrapper;
import cinema.model.Session;
import cinema.model.SessionListWrapper;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.prefs.Preferences;

public class DataUtil {

    private ObservableList<Movie> movieData;
    private ObservableList<Session>  sessionData;

    public DataUtil(ObservableList<Movie> movieData, ObservableList<Session> sessionData) {
        this.movieData = movieData;
        this.sessionData = sessionData;
    }

    public File getMovieFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(Main.class);
        String filePath = prefs.get("filePath1", null);
        if (filePath != null) {
            return new File(filePath);
        } else {
            return null;
        }
    }

    public File getSessionFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(Main.class);
        String filePath = prefs.get("filePath2", null);
        if (filePath != null) {
            return new File(filePath);
        } else {
            return null;
        }
    }

    public void setMovieFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(Main.class);
        if (file != null) {
            prefs.put("filePath1", file.getPath());
        } else {
            prefs.remove("filePath1");
        }
    }

    public void setSessionFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(Main.class);
        if (file != null) {
            prefs.put("filePath2", file.getPath());
        } else {
            prefs.remove("filePath2");
        }
    }

    public void loadMovieDataFromFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(MovieListWrapper.class);
            Unmarshaller um = context.createUnmarshaller();

            // Чтение XML из файла и демаршализация.
            MovieListWrapper wrapper = (MovieListWrapper) um.unmarshal(file);

            movieData.clear();
            movieData.addAll(wrapper.getMovies());

            // Сохраняем путь к файлу в реестре.
            setMovieFilePath(file);

        } catch (Exception e) { // catches ANY exception
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Не удалось загрузить данные\n");
            alert.setContentText("Не удалось загрузить данные из файла:\n" + file.getPath());

            alert.showAndWait();
        }
    }

    public void loadSessionDataFromFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(SessionListWrapper.class);
            Unmarshaller um = context.createUnmarshaller();

            // Чтение XML из файла и демаршализация.
            SessionListWrapper wrapper = (SessionListWrapper) um.unmarshal(file);

            sessionData.clear();
            sessionData.addAll(wrapper.getSessions());

            // Сохраняем путь к файлу в реестре.
            setSessionFilePath(file);

        } catch (Exception e) { // catches ANY exception
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Не удалось загрузить данные\n");
            alert.setContentText("Не удалось загрузить данные из файла:\n" + file.getPath());

            alert.showAndWait();
        }
    }

    public void saveMovieDataToFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(MovieListWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Обёртываем наши данные об адресатах.
            MovieListWrapper wrapper = new MovieListWrapper();
            wrapper.setMovies(movieData);

            // Маршаллируем и сохраняем XML в файл.
            m.marshal(wrapper, file);

            // Сохраняем путь к файлу в реестре.
            setMovieFilePath(file);
        } catch (Exception e) { // catches ANY exception
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Не удалось загрузить данные\n");
            alert.setContentText("Не удалось загрузить данные из файла:\n" + file.getPath());

            alert.showAndWait();
        }
    }

    public void saveSessionDataToFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(SessionListWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Обёртываем наши данные об адресатах.
            SessionListWrapper wrapper = new SessionListWrapper();
            wrapper.setSessions(sessionData);

            // Маршаллируем и сохраняем XML в файл.
            m.marshal(wrapper, file);

            // Сохраняем путь к файлу в реестре.
            setSessionFilePath(file);
        } catch (Exception e) { // catches ANY exception
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Не удалось загрузить данные\n");
            alert.setContentText("Не удалось загрузить данные из файла:\n" + file.getPath());

            alert.showAndWait();
        }
    }

    public void loader(){
        File fileM = getMovieFilePath();
        if (fileM != null) {
            loadMovieDataFromFile(fileM);
        }
        File fileS = getSessionFilePath();
        if (fileS != null) {
            loadSessionDataFromFile(fileS);
        }
    }
}
