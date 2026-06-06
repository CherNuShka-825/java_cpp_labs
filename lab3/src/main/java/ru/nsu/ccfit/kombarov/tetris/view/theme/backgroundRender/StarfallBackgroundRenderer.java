package ru.nsu.ccfit.kombarov.tetris.view.theme.backgroundRender;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import ru.nsu.ccfit.kombarov.tetris.view.theme.palette.ColorPalette;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StarfallBackgroundRenderer extends AbstractBackgroundRenderer {

    private static final int STAR_COUNT = 200;

    private static final double MIN_SPEED = 20.0;
    private static final double MAX_SPEED = 90.0;

    private static final double MIN_SIZE = 1.0;
    private static final double MAX_SIZE = 3.0;

    private static final double MIN_ALPHA = 0.25;
    private static final double MAX_ALPHA = 0.9;

    private final List<Star> stars = new ArrayList<>();
    private final Random random = new Random();

    private double lastTime = -1.0;
    private double width = 1.0;
    private double height = 1.0;

    public StarfallBackgroundRenderer(ColorPalette palette) {
        super(palette);
    }

    @Override
    public void update(double time) {
        if (lastTime < 0) {
            lastTime = time;
            return;
        }

        double deltaTime = time - lastTime;
        lastTime = time;

        for (Star star : stars) {
            star.y += star.speed * deltaTime;

            if (star.y > height) {
                resetStarAtTop(star);
            }
        }
    }

    @Override
    public void render(GraphicsContext gc, double width, double height) {
        this.width = width;
        this.height = height;

        createStarsIfNeeded(width, height);

        fillBackground(gc, width, height);
        drawStars(gc);

        gc.setGlobalAlpha(1.0);
    }

    private void createStarsIfNeeded(double width, double height) {
        if (!stars.isEmpty()) {
            return;
        }

        for (int i = 0; i < STAR_COUNT; i++) {
            Star star = new Star();

            star.x = random.nextDouble() * width;
            star.y = random.nextDouble() * height;
            star.speed = randomBetween(MIN_SPEED, MAX_SPEED);
            star.size = randomBetween(MIN_SIZE, MAX_SIZE);
            star.alpha = randomBetween(MIN_ALPHA, MAX_ALPHA);

            stars.add(star);
        }
    }

    private void drawStars(GraphicsContext gc) {
        gc.setFill(Color.WHITE);

        for (Star star : stars) {
            gc.setGlobalAlpha(star.alpha);
            gc.fillOval(star.x, star.y, star.size, star.size);
        }
    }

    private void resetStarAtTop(Star star) {
        star.x = random.nextDouble() * width;
        star.y = -star.size;
        star.speed = randomBetween(MIN_SPEED, MAX_SPEED);
        star.size = randomBetween(MIN_SIZE, MAX_SIZE);
        star.alpha = randomBetween(MIN_ALPHA, MAX_ALPHA);
    }

    private double randomBetween(double min, double max) {
        return min + random.nextDouble() * (max - min);
    }

    private static class Star {
        private double x;
        private double y;
        private double speed;
        private double size;
        private double alpha;
    }
}