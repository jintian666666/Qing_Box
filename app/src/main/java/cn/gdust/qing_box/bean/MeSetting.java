package cn.gdust.qing_box.bean;

import lombok.Data;

@Data
public class MeSetting {

    private int image;

    private String textView;

    public MeSetting(int image, String textView) {
        this.image = image;
        this.textView = textView;
    }
}
