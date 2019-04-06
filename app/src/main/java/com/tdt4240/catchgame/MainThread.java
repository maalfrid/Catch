package com.tdt4240.catchgame;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class MainThread extends Thread {
    private SurfaceHolder surfaceHolder;
    private GameView gameView;
    private static boolean running;
    private static boolean pausing;
    public static Canvas canvas;

    public MainThread(SurfaceHolder surfaceHolder, GameView gameView) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.gameView = gameView;
        this.pausing = false;
    }

    @Override
    public void run() {
        while (running) {
            canvas = null;

            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized(surfaceHolder) {
                    this.gameView.update();
                    this.gameView.draw(canvas);
                }
            } catch (Exception e) { e.printStackTrace(); } finally {
                if (canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        /*while (pausing){
            System.out.println("Thread is sleeping");
            //Get input (just call necessary draw/update methods)
        }*/
    }

    public static void setRunning(boolean isRunning) {
        running = isRunning;
    }

    public void setSleep(int millis){
        pausing = true;
        try {
            sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        pausing = false;
    }

    public boolean getIsPausing(){
        return pausing;
    }

    public void setIsPausing(Boolean b){
        pausing = b;
    }
}
