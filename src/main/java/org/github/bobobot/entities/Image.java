package org.github.bobobot.entities;

import java.util.Objects;

public class Image {
    int ID;
    boolean exists;
    String path;

    public Image(int ID, boolean exists, String path) {
        this.ID = ID;
        this.exists = exists;
        this.path = path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Image image = (Image) o;
        return ID == image.ID &&
                exists == image.exists &&
                Objects.equals(path, image.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, exists, path);
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public boolean isExists() {
        return exists;
    }

    public void setExists(boolean exists) {
        this.exists = exists;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
