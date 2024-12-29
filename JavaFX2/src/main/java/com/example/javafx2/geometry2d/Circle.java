package com.example.javafx2.geometry2d;


import com.example.javafx2.exceptions.InvalidRadiusException;
import javafx.scene.paint.Color;

public class Circle implements Figure {
    private double radius;
    private double x;
    private double y;
    private Color color;

    public Circle(double radius) throws InvalidRadiusException {
        if (radius <= 0) {
            throw new InvalidRadiusException("Радиус должен быть положительным");
        }
        this.radius = radius;
        this.color = Color.BLACK;
    }

    public double area() {
        return Math.PI * radius * radius;
    }

    public double perimeter() {
        return 2 * Math.PI * radius;
    }

    public String toString() {
        return "Circle with radius: " + radius;
    }

    public double getRadius() {
        return radius;
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