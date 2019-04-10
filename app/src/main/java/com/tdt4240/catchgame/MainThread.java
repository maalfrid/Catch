package com.tdt4240.catchgame;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class MainThread extends Thread {
    private SurfaceHolder surfaceHolder;
    private GameView gameView;
    private static boolean running;
    public static Canvas canvas;

    public MainThread(SurfaceHolder surfaceHolder, GameView gameView) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.gameView = gameView;
    }

    @Override
    public void run() {
        long startTime = System.nanoTime();

        while (running) {
            canvas = null;

            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized(surfaceHolder) {
                    this.gameView.update();
                    this.gameView.draw(canvas);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                if (canvas != null) {
                    try {
                        this.surfaceHolder.unlockCanvasAndPost(canvas);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            long now = System.nanoTime();
            long waitTime = (now - startTime)/1000000;
            if (waitTime < 10){
                waitTime = 10;
            }
            try {
                this.sleep(waitTime);
            } catch(InterruptedException e){
                e.printStackTrace();
            }
            startTime = System.nanoTime();
        }
    }

    public static void setRunning(boolean isRunning) {
        running = isRunning;
    }
}
