package com.my.examples.flyweight;

public interface IWidget {
    public void setWidgetName(String name);
    public String getWidgetName();
    public void setWidgetContent(String content);
    public void display();
    public void applyTheme(String color,String postion,Layoutmanager manager);
}
