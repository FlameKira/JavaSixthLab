package com.example.javafx2.geometry2d;

import com.example.javafx2.exceptions.InvalidDimensionException;
import javafx.scene.paint.Color;

public class Rectangle implements Figure {
    private double width;
    private double height;
    private double x;
    private double y;
    private Color color;

    public Rectangle(double width, double height) throws InvalidDimensionException {
        if (width <= 0 || height <= 0) {
            throw new InvalidDimensionException("Ширина и высота должны быть положительными");
        }
        this.width = width;
        this.height = height;
        this.color = Color.BLACK;
    }

    public double area() {
        return width * height;
    }

    public double perimeter() {
        return 2 * (width + height);
    }

    public String toString() {
        return "Rectangle with width: " + width + " and height: " + height;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

}