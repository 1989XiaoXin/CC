package com.example.cc;

public class ToDoBean {
    private String thing;
    private String date;

    public ToDoBean(String thing, String date) {
        this.thing = thing;
        this.date = date;
    }

    public void setThing(String thing) {
        this.thing = thing;
    }

    public String getThing() {
        return thing;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }
}
