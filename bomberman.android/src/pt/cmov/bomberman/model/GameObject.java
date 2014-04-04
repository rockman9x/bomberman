package pt.cmov.bomberman.model;

import java.util.Hashtable;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.SurfaceView;

public abstract class GameObject implements IGameObject {

	public static final float DEFAULT_WIDTH = 43;
	public static final float DEFAULT_HEIGHT = 46;
	private static Hashtable<Integer, Bitmap> bitmapTable;
	
	private int x; // the X coordinate
	private int y; // the Y coordinate

	private int bitmapCode;
	private SurfaceView view;

	static{
		bitmapTable = new Hashtable<Integer, Bitmap>();
	}
	
	public GameObject(SurfaceView view, int bitmapCode, int x, int y) {
		setX(x);
		setY(y);
		setView(view);
		setBitmapCode(bitmapCode);
	}

	/**
	 * @return the x
	 */
	@Override
	public int getX() {
		return x;
	}

	/**
	 * @param x
	 *            the x to set
	 */
	@Override
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	@Override
	public int getY() {
		return y;
	}

	/**
	 * @param y
	 *            the y to set
	 */
	@Override
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * @return the view
	 */
	@Override
	public SurfaceView getView() {
		return view;
	}

	/**
	 * @param view
	 *            the view to set
	 */
	@Override
	public void setView(SurfaceView view) {
		this.view = view;
	}

	/**
	 * @return the bitmap
	 */
	@Override
	public Bitmap getBitmap() {
		//caching of bitmaps :)
		if(bitmapTable.get(getBitmapCode()) == null)
			bitmapTable.put(getBitmapCode(), BitmapFactory.decodeResource(view.getResources(), getBitmapCode()));
		
		return bitmapTable.get(getBitmapCode());
	}

	@Override
	public void draw(Canvas canvas) {
		canvas.drawBitmap(getBitmap(), x - (getBitmap().getWidth() / 2), y - (getBitmap().getHeight() / 2), null);
	}

	/**
	 * @return the bitmapCode
	 */
	public int getBitmapCode() {
		return bitmapCode;
	}

	/**
	 * @param bitmapCode the bitmapCode to set
	 */
	public void setBitmapCode(int bitmapCode) {
		this.bitmapCode = bitmapCode;
	}
}
