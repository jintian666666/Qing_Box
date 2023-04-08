package cn.gdust.qing_box.bean;

public class Express {

    private String context;
    private String time;

    public Express(String context, String time) {
        this.context = context;
        this.time = time;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Express{" +
                "context='" + context + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
