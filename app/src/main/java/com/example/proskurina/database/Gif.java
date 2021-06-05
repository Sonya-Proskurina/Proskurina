package com.example.proskurina.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Gif {
    @PrimaryKey(autoGenerate = true)
    public long id;

    public String text;

    public String url;

    public int type;

    public Gif(String text, String url, int type) {
        this.text = text;
        this.url = url;
        this.type = type;
    }

    @Override
    public String toString() {
        return "Gif{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", url='" + url + '\'' +
                ", type=" + type +
                '}';
    }
}
