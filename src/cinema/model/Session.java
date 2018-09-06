package cinema.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

public class Session {

    private final StringProperty nameMovie;
    private final StringProperty dataTime;

    public Session() {
        this(null);
    }

    public Session(String nameMovie) {
        this.nameMovie = new SimpleStringProperty(nameMovie);
        this.dataTime = new SimpleStringProperty("2018.01.01 12:00");

    }

    public String getNameMovie() {
        return nameMovie.get();
    }

    public StringProperty nameMovieProperty() {
        return nameMovie;
    }

    public void setNameMovie(String nameMovie) {
        this.nameMovie.set(nameMovie);
    }

    public String getDataTime() {
        return dataTime.get();
    }

    public StringProperty dataTimeProperty() {
        return dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime.set(dataTime);
    }
}
