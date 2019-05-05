package br.ufop.tomaz.model;

import javafx.beans.property.*;
import br.ufop.tomaz.util.CustomImage;

public class Actor {

    private StringProperty name = new SimpleStringProperty();
    private ObjectProperty<CustomImage> image = new SimpleObjectProperty<>();
    private StringProperty webPageLink = new SimpleStringProperty();
    private IntegerProperty nEpisodes = new SimpleIntegerProperty();

    public Actor(String name, CustomImage image, String webPageLink, Integer nEpisodes) {
        setName(name);
        setImage(image);
        setWebPageLink(webPageLink);
        setnEpisodes(nEpisodes);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public CustomImage getImage() {
        return image.get();
    }

    public ObjectProperty<CustomImage> imageProperty() {
        return image;
    }

    public void setImage(CustomImage image) {
        this.image.set(image);
    }

    public String getWebPageLink() {
        return webPageLink.get();
    }

    public StringProperty webPageLinkProperty() {
        return webPageLink;
    }

    public void setWebPageLink(String webPageLink) {
        this.webPageLink.set(webPageLink);
    }

    public int getnEpisodes() {
        return nEpisodes.get();
    }

    public IntegerProperty nEpisodesProperty() {
        return nEpisodes;
    }

    public void setnEpisodes(int nEpisodes) {
        this.nEpisodes.set(nEpisodes);
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
