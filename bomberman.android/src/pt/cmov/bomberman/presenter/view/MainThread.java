package pt.cmov.bomberman.presenter.view;

import pt.cmov.bomberman.R;
import pt.cmov.bomberman.model.GameLevel;
import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.SurfaceHolder;

public class MainThread extends Thread {
	
	private static final String TAG = MainThread.class.getSimpleName();
	private SurfaceHolder surfaceHolder;
	private MainGamePanel gamePanel; 
	private boolean running;
	
	public MainThread(SurfaceHolder surfaceHolder, MainGamePanel gamePanel) {
		super();
		this.surfaceHolder = surfaceHolder;
		this.gamePanel = gamePanel;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}
	
	@SuppressLint("WrongCall")
	@Override
	public void run() {
		Canvas canvas;
		Log.d(TAG, "Starting game loop");
		while (running) {
			canvas = null;
			try {
				canvas = this.surfaceHolder.lockCanvas();
			
				synchronized (surfaceHolder) {
					if (GameLevel.getInstance().isGameOver()) {
						running = false;
						canvas.drawColor(Color.BLACK);
						Bitmap gameover = BitmapFactory.decodeResource(gamePanel.getResources(), R.drawable.gameover);
						canvas.drawBitmap(gameover, 0, 0, null);
					} else {
						this.gamePanel.onDraw(canvas);
					}
				}
			} 
			finally {
				if (canvas != null) {
					surfaceHolder.unlockCanvasAndPost(canvas);
				}
			}
		}
	}
}
