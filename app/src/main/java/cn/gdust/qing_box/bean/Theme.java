package cn.gdust.qing_box.bean;

import lombok.Data;

@Data
public class Theme {

    private int color;

    private String text;

    public Theme(int color, String text) {
        this.color = color;
        this.text = text;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
