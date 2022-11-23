package cn.gdust.qing_box.Bean;

import lombok.Data;

@Data
public class Theme {

    private int color;

    private String text;

    public Theme(int color, String text) {
        this.color = color;
        this.text = text;
    }
}
