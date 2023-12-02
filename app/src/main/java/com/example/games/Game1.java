package com.example.games;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import androidx.core.content.ContextCompat;

import java.util.Random;

public class Game1 extends Activity {

    private Button[][] botones;
    private GestureDetector gestureDetector;
    private Casilla[][] casillas;
    private boolean seRealizoMovimiento = false;


    Button nuevo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game1);

        casillas = new Casilla[4][4];
        botones = new Button[4][4];

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                casillas[i][j] = new Casilla();

                int buttonId = getResources().getIdentifier("but" + (i * 4 + j + 1), "id", getPackageName());
                botones[i][j] = findViewById(buttonId);
            }
        }
        cambiarTextoEnPosicionAleatoria();
        cambiarTextoEnPosicionAleatoria();

        gestureDetector = new GestureDetector(this, new MyGestureListener());

        nuevo = findViewById(R.id.button);
        nuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nuevo(view);
            }
        });
    }

    public void nuevo(View view) {
        Intent intent = new Intent(this, getClass());
        this.finish();
        this.startActivity(intent);


    }


    private void cambiarTextoEnPosicionAleatoria() {
        Random random = new Random();
        int filaAleatoria, columnaAleatoria;

        do {
            filaAleatoria = random.nextInt(botones.length);
            columnaAleatoria = random.nextInt(botones[0].length);
        } while (casillas[filaAleatoria][columnaAleatoria].isOcupada());

        casillas[filaAleatoria][columnaAleatoria].setOcupada(true);

        botones[filaAleatoria][columnaAleatoria].setBackgroundResource(R.drawable.button_draq);
        botones[filaAleatoria][columnaAleatoria].setText("2");
        botones[filaAleatoria][columnaAleatoria].setTextSize(20.890F);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        return gestureDetector.onTouchEvent(event) || super.onTouchEvent(event);
    }

    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final int SWIPE_THRESHOLD = 100;

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            float diffY = e2.getY() - e1.getY();
            float diffX = e2.getX() - e1.getX();


            if (Math.abs(diffY) > Math.abs(diffX)) {
                //vertical
                if (diffY < -SWIPE_THRESHOLD) {
                    //arriba
                    for (int columna = 0; columna < botones[0].length; columna++) {
                        moverTextosArribaEnColumna(columna);

                    }
                    sumarSiEsPosible();
                    // cambiarTextoEnPosicionAleatoria();
                    return seRealizoMovimiento;
                } else if (diffY > SWIPE_THRESHOLD) {
                    //abajo
                    for (int columna = 0; columna < botones[0].length; columna++) {
                        moverTextosAbajoEnColumna(columna);
                    }
                    sumarSiEsPosible();
                    //cambiarTextoEnPosicionAleatoria();
                    return seRealizoMovimiento;
                }
            } else {
                //horizontal
                if (diffX < -SWIPE_THRESHOLD) {
                    // izquierda
                    for (int fila = 0; fila < botones.length; fila++) {
                        moverTextosIzquierdaEnFila(fila);
                    }
                    sumarSiEsPosible();
                    // cambiarTextoEnPosicionAleatoria();
                    return seRealizoMovimiento;
                } else if (diffX > SWIPE_THRESHOLD) {
                    //derecha
                    for (int fila = 0; fila < botones.length; fila++) {
                        moverTextosDerechaEnFila(fila);
                    }
                    sumarSiEsPosible();
                    //cambiarTextoEnPosicionAleatoria();
                    return seRealizoMovimiento;
                }
            }
            //sumarSiEsPosible();
            return false;
        }
    }




    private void moverTextosArribaEnColumna(int columna) {
        for (int i = 1; i < botones.length; i++) {
            if (casillas[i][columna].isOcupada()) {
                int filaAnterior = i - 1;
                while (filaAnterior >= 0 && !casillas[filaAnterior][columna].isOcupada()) {
                    casillas[filaAnterior][columna].setOcupada(true);
                    casillas[filaAnterior + 1][columna].setOcupada(false);

                    botones[filaAnterior][columna].setBackgroundResource(R.drawable.button_draq);
                    botones[filaAnterior][columna].setText(botones[filaAnterior + 1][columna].getText());
                    botones[filaAnterior + 1][columna].setText(""); // Limpiar el texto del botón

                    filaAnterior--;
                }
            }
        }
    }

    private void moverTextosAbajoEnColumna(int columna) {
        for (int i = botones.length - 2; i >= 0; i--) {
            if (casillas[i][columna].isOcupada()) {
                int filaSiguiente = i + 1;
                while (filaSiguiente < botones.length && !casillas[filaSiguiente][columna].isOcupada()) {
                    casillas[filaSiguiente][columna].setOcupada(true);
                    casillas[filaSiguiente - 1][columna].setOcupada(false);

                    botones[filaSiguiente][columna].setBackgroundResource(R.drawable.button_draq);
                    botones[filaSiguiente][columna].setText(botones[filaSiguiente - 1][columna].getText());
                    botones[filaSiguiente - 1][columna].setText("");

                    filaSiguiente++;
                }
            }
        }
    }

    private void moverTextosIzquierdaEnFila(int fila) {
        for (int j = 1; j < botones[0].length; j++) {
            if (casillas[fila][j].isOcupada()) {
                int columnaAnterior = j - 1;
                while (columnaAnterior >= 0 && !casillas[fila][columnaAnterior].isOcupada()) {
                    casillas[fila][columnaAnterior].setOcupada(true);
                    casillas[fila][columnaAnterior + 1].setOcupada(false);

                    botones[fila][columnaAnterior].setBackgroundResource(R.drawable.button_draq);
                    botones[fila][columnaAnterior].setText(botones[fila][columnaAnterior + 1].getText());
                    botones[fila][columnaAnterior + 1].setText("");

                    columnaAnterior--;
                }
            }
        }
    }

    private void moverTextosDerechaEnFila(int fila) {
        for (int j = botones[0].length - 2; j >= 0; j--) {
            if (casillas[fila][j].isOcupada()) {
                int columnaSiguiente = j + 1;
                while (columnaSiguiente < botones[0].length && !casillas[fila][columnaSiguiente].isOcupada()) {
                    casillas[fila][columnaSiguiente].setOcupada(true);
                    casillas[fila][columnaSiguiente - 1].setOcupada(false);

                    botones[fila][columnaSiguiente].setBackgroundResource(R.drawable.button_draq);
                    botones[fila][columnaSiguiente].setText(botones[fila][columnaSiguiente - 1].getText());
                    botones[fila][columnaSiguiente - 1].setText("");

                    columnaSiguiente++;
                }
            }
        }
    }


    private void sumarSiEsPosible() {
        if (seRealizoMovimiento) {
            for (int i = 0; i < botones.length; i++) {
                for (int j = botones[0].length - 1; j > 0; j--) {
                    if (casillas[i][j].isOcupada() && casillas[i][j - 1].isOcupada()) {
                        int valorActual = Integer.parseInt(botones[i][j].getText().toString());
                        int valorAnterior = Integer.parseInt(botones[i][j - 1].getText().toString());

                        if (valorActual == valorAnterior) {
                            int nuevoValor = valorActual * 2;
                            botones[i][j].setText(String.valueOf(nuevoValor));
                            botones[i][j - 1].setText("");
                            casillas[i][j - 1].setOcupada(false);
                            seRealizoMovimiento = true;

                        }
                    }
                }
            }
        }
    }





}

