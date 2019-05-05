package br.ufop.tomaz.util;

import javafx.scene.image.Image;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.InputStream;

@Embeddable
public class CustomImage extends Image {

    private String url;

    public CustomImage(String url) {
        super(url);
        System.out.println("Image URL :" + url);
        this.url = url;
    }

    public CustomImage(InputStream is,String path) {
        super(is);
        this.url = path;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
