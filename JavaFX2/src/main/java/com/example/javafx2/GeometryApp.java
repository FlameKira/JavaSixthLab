package com.example.javafx2;

import com.example.javafx2.exceptions.InvalidDimensionException;
import com.example.javafx2.exceptions.InvalidRadiusException;
import com.example.javafx2.geometry2d.Figure;
import com.example.javafx2.geometry2d.Circle;
import com.example.javafx2.geometry2d.Rectangle;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GeometryApp extends Application {
    private final List<Figure> figures = new ArrayList<>();
    private Canvas canvas;
    private final Random random = new Random();
    private Figure selectedFigure = null;
    private double offsetX, offsetY;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Geometry Drawer");

        canvas = new Canvas(900, 700);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        drawFigures(gc);
        drawSpawnArea(gc);

        Button drawCircleButton = new Button("Draw Circle");
        drawCircleButton.setOnAction(e -> {
            try {
                Circle circle = new Circle(random.nextDouble() * 50 + 10);
                circle.setColor(Color.color(random.nextDouble(), random.nextDouble(), random.nextDouble()));
                double x = random.nextDouble() * (canvas.getWidth() - circle.getRadius() * 2);
                double y = random.nextDouble() * (canvas.getHeight() - circle.getRadius() * 2);
                circle.setPosition(x, y);
                figures.add(circle);
                drawFigures(gc);
            } catch (InvalidRadiusException ex) {
                ex.printStackTrace();
            }
        });

        BorderPane root = getBorderPane(gc, drawCircleButton);

        Scene scene = new Scene(root, 900, 700);
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.show();

        setupMouseEvents();
    }

    private BorderPane getBorderPane(GraphicsContext gc, Button drawCircleButton) {
        Button drawRectangleButton = new Button("Draw Rectangle");
        drawRectangleButton.setOnAction(e -> {
            try {
                Rectangle rectangle = new Rectangle(random.nextDouble() * 100 + 20, random.nextDouble() * 100 + 20);
                rectangle.setColor(Color.color(random.nextDouble(), random.nextDouble(), random.nextDouble()));
                double x = random.nextDouble() * (canvas.getWidth() - rectangle.getWidth());
                double y = random.nextDouble() * (canvas.getHeight() - rectangle.getHeight());
                rectangle.setPosition(x, y);
                figures.add(rectangle);
                drawFigures(gc);
            } catch (InvalidDimensionException ex) {
                ex.printStackTrace();
            }
        });

        VBox buttons = new VBox(drawCircleButton, drawRectangleButton);
        BorderPane root = new BorderPane();
        root.setLeft(buttons);
        root.setCenter(canvas);
        return root;
    }

    private void drawFigures(GraphicsContext gc) {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        drawSpawnArea(gc);
        for (Figure figure : figures) {
            if (figure instanceof Circle circle) {
                double radius = circle.getRadius();
                double x = circle.getX();
                double y = circle.getY();
                gc.setFill(circle.getColor());
                gc.fillOval(x, y, radius * 2, radius * 2);
            } else if (figure instanceof Rectangle rectangle) {
                double x = rectangle.getX();
                double y = rectangle.getY();
                gc.setFill(rectangle.getColor());
                gc.fillRect(x, y, rectangle.getWidth(), rectangle.getHeight());
            }
        }
    }

    private void drawSpawnArea(GraphicsContext gc) {
        gc.setStroke(Color.RED);
        gc.setLineWidth(2);
        gc.strokeRect(0, 0, canvas.getWidth(), canvas.getHeight()); // Обводка области спавна
    }

    private void setupMouseEvents() {
        canvas.setOnMousePressed(event -> {
            double mouseX = event.getX();
            double mouseY = event.getY();
            selectedFigure = null;

            for (Figure figure : figures) {
                if (figure instanceof Circle circle) {
                    double radius = circle.getRadius();
                    double centerX = circle.getX() + radius;
                    double centerY = circle.getY() + radius;
                    if (Math.pow(mouseX - centerX, 2) + Math.pow(mouseY - centerY, 2) <= Math.pow(radius, 2)) {
                        selectedFigure = circle;
                        offsetX = mouseX - centerX;
                        offsetY = mouseY - centerY;
                        break;
                    }
                } else if (figure instanceof Rectangle rectangle) {
                    double x = rectangle.getX();
                    double y = rectangle.getY();
                    if (mouseX >= x && mouseX <= x + rectangle.getWidth() && mouseY >= y && mouseY <= y + rectangle.getHeight()) {
                        selectedFigure = rectangle;
                        offsetX = mouseX - x;
                        offsetY = mouseY - y;
                        break;
                    }
                }
            }
        });

        canvas.setOnMouseDragged(event -> {
            if (selectedFigure != null) {
                double newX = event.getX() - offsetX;
                double newY = event.getY() - offsetY;

                if (selectedFigure instanceof Circle circle) {
                    circle.setPosition(newX - circle.getRadius(), newY - circle.getRadius());
                } else if (selectedFigure instanceof Rectangle rectangle) {
                    rectangle.setPosition(newX, newY);
                }
                drawFigures(canvas.getGraphicsContext2D());
            }
        });

        canvas.setOnMouseClicked(event -> {
            if (event.getButton() == javafx.scene.input.MouseButton.SECONDARY && selectedFigure != null) {
                Color newColor = Color.color(random.nextDouble(), random.nextDouble(), random.nextDouble());
                if (selectedFigure instanceof Circle circle) {
                    circle.setColor(newColor);
                } else if (selectedFigure instanceof Rectangle rectangle) {
                    rectangle.setColor(newColor);
                }
                drawFigures(canvas.getGraphicsContext2D());
            }
        });
    }
}