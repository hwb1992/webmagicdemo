package com.hwb.demo.model;

/**
 * Created by hwb
 * 电影Model
 * On 2017/2/4 9:41
 */
public class Film {

    private int id;

    private String imageSrc;

    private String fileName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
