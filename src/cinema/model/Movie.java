package cinema.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Movie {

    private final StringProperty nameMovie;
    private final StringProperty ganre;
    private final IntegerProperty durability;
    private final IntegerProperty dateBirthday;

    public Movie() {
        this(null);
    }

    public Movie(String nameMovie) {
        this.nameMovie = new SimpleStringProperty(nameMovie);

        this.ganre = new SimpleStringProperty("Some ganre");
        this.durability = new SimpleIntegerProperty(120);
        this.dateBirthday = new SimpleIntegerProperty(2018);
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

    public String getGanre() {
        return ganre.get();
    }

    public StringProperty ganreProperty() {
        return ganre;
    }

    public void setGanre(String ganre) {
        this.ganre.set(ganre);
    }

    public int getDurability() {
        return durability.get();
    }

    public IntegerProperty durabilityProperty() {
        return durability;
    }

    public void setDurability(int durability) {
        this.durability.set(durability);
    }

    public int getDateBirthday() {
        return dateBirthday.get();
    }

    public IntegerProperty dateBirthdayProperty() {
        return dateBirthday;
    }

    public void setDateBirthday(int dateBirthday) {
        this.dateBirthday.set(dateBirthday);
    }
}
