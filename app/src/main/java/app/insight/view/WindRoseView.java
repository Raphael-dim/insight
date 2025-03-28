package app.insight.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

public class WindRoseView extends View {
    private Paint circlePaint;
    private Paint trianglePaint;
    private float[] windValues;
    private float maxWindValue;
    private static final int DIRECTIONS = 16;

    public WindRoseView(Context context) {
        super(context);
        init();
    }

    public WindRoseView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        // Initialisation du Paint pour le cercle extérieur
        circlePaint = new Paint();
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setColor(Color.GRAY);
        circlePaint.setStrokeWidth(2);
        circlePaint.setAntiAlias(true);

        // Initialisation du Paint pour les triangles
        trianglePaint = new Paint();
        trianglePaint.setStyle(Paint.Style.FILL);
        trianglePaint.setColor(Color.rgb(135, 206, 235)); // Bleu clair
        trianglePaint.setAntiAlias(true);

        // Initialisation du tableau des valeurs (à remplir plus tard)
        windValues = new float[DIRECTIONS];
    }

    public void setWindValues(float[] values) {
        if (values.length != DIRECTIONS) {
            throw new IllegalArgumentException("Il faut exactement 16 valeurs de vent");
        }
        windValues = values;
        maxWindValue = 0;
        for (float value : values) {
            maxWindValue = Math.max(maxWindValue, value);
        }
        invalidate(); // Redessiner la vue
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float width = getWidth();
        float height = getHeight();
        float centerX = width / 2;
        float centerY = height / 2;
        float radius = Math.min(width, height) / 2 * 0.9f; // Marge de 10%

        // Déplacer le point d'origine au centre
        canvas.translate(centerX, centerY);

        // Dessiner le cercle extérieur
        canvas.drawCircle(0, 0, radius, circlePaint);

        // Dessiner les triangles pour chaque direction
        for (int i = 0; i < DIRECTIONS; i++) {
            float angle = (float) (i * (360.0 / DIRECTIONS));
            float value = windValues[i];
            float triangleHeight = (value / maxWindValue) * radius;

            // Sauvegarder l'état du canvas
            canvas.save();

            // Rotation pour la direction actuelle
            canvas.rotate(angle);

            // Dessiner le triangle
            Path path = new Path();
            float triangleWidth = (float) (Math.tan(Math.toRadians(360.0 / (DIRECTIONS * 2))) * triangleHeight);
            path.lineTo(-triangleWidth, -triangleHeight);
            path.lineTo(triangleWidth, -triangleHeight);
            path.lineTo(0, 0);
            path.close();

            canvas.drawPath(path, trianglePaint);

            // Restaurer l'état du canvas
            canvas.restore();
        }
    }
}