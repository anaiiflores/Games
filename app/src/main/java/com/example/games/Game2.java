package com.example.games;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Intent;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.TypedValue;

import androidx.appcompat.app.AppCompatActivity;

public class Game2 extends AppCompatActivity {

    ViewGroup transitionsContainer;
    Button nuevo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game2);
        nuevo = findViewById(R.id.button4);
        nuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nuevo(view);
            }
        });

        transitionsContainer = findViewById(R.id.transitions_container);
        setDragListenersForButtons(transitionsContainer);
    }

    private void setDragListenersForButtons(ViewGroup container) {
        for (int i = 0; i < container.getChildCount(); i++) {
            View child = container.getChildAt(i);
            if (child instanceof Button) {
                setDragListenerForButton((Button) child);
            }
        }
    }

    private void setDragListenerForButton(final Button button) {
        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startButtonAnimation(button, 1.0f, 0.8f);
                        //arrastrar
                        ClipData clipData = ClipData.newPlainText(ClipDescription.MIMETYPE_TEXT_PLAIN, "");
                        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
                        v.startDrag(clipData, shadowBuilder, v, 0);
                        v.setVisibility(View.INVISIBLE);
                        break;
                }
                return false;
            }
        });

        button.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                Button draggedButton = (Button) event.getLocalState();
                switch (event.getAction()) {
                    case DragEvent.ACTION_DROP:
                        // Intercambiar botones y cambiar el color a negro
                        Button targetButton = (Button) v;

                        // Obtener el color negro desde color.xml
                        int colorNegro = getColorFromResources(R.color.black);

                        // Establecer el fondo de los botones
                        targetButton.setBackgroundColor(colorNegro);
                        draggedButton.setBackgroundColor(colorNegro);
                        break;
                    case DragEvent.ACTION_DRAG_ENDED:
                        // Hacer visible nuevamente el botón después de soltar
                        draggedButton.setVisibility(View.VISIBLE);
                        break;
                }
                return true;
            }
        });
    }

    private void startButtonAnimation(Button button, float startScale, float endScale) {
        ScaleAnimation scaleAnimation = new ScaleAnimation(
                startScale, endScale,
                startScale, endScale,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );
        scaleAnimation.setDuration(150);
        scaleAnimation.setFillAfter(true);

        button.startAnimation(scaleAnimation);
    }

    public void nuevo(View view) {
        Intent intent = new Intent(this, getClass());
        this.finish();
        this.startActivity(intent);
    }

    private int getColorFromResources(int colorResource) {
        Resources.Theme theme = getTheme();
        TypedValue typedValue = new TypedValue();
        theme.resolveAttribute(colorResource, typedValue, true);
        return typedValue.data;
    }
}
