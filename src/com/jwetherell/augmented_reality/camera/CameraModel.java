package com.jwetherell.augmented_reality.camera;

import com.jwetherell.augmented_reality.common.Matrix;
import com.jwetherell.augmented_reality.common.MixVector;

/**
 * Represents the camera and it's view. It also allows a user to project a point given this camera's view.
 * 
 * This file was adapted from Mixare <http://www.mixare.org/>
 * 
 * @author Daniele Gobbetti <info@mixare.org>
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class CameraModel {
	private static Matrix transform = new Matrix();
	private static MixVector lco = new MixVector();

	private int width = 0; 
	private int height = 0;
	private float viewAngle = 0F;
	private float distance = 0F;
	
	public static final float DEFAULT_VIEW_ANGLE = (float) Math.toRadians(45);
	
	public CameraModel(int width, int height) {
		this(width, height, true);
	}

	public CameraModel(int width, int height, boolean init) {
		set(width, height, init);
	}
	
	/**
	 * Set this objects parameters. This should be used instead of creating new objects.
	 * @param width Width of the model.
	 * @param height Height of the model.
	 * @param init Should initialize the internal transform and lco objects.
	 */
	public void set(int width, int height, boolean init) {
		this.width = width;
		this.height = height;

		if (init) {
			transform.set(0, 0, 0, 0, 0, 0, 0, 0, 0);
			transform.toIdentity();
			lco.set(0, 0, 0);
		}
	}
	
	/**
	 * Get transformation matrix.
	 * @return Matrix representation of the transformation.
	 */
	public Matrix getTransform() {
		return transform;
	}
	
	/**
	 * Set the transformation matrix.
	 * @param transform Matrix representing the transformation.
	 */
	public void setTransform(Matrix transform) {
		CameraModel.transform = transform;
	}

	/**
	 * Get the MixVector LCO.
	 * @return MixVector representing the LCO.
	 */
	public MixVector getLco() {
		return lco;
	}
	
	/**
	 * Set the MixVector LCO.
	 * @param lco MixVector representing the LCO.
	 */
	public void setLco(MixVector lco) {
		CameraModel.lco = lco;
	}
	
	/**
	 * Set the View Angle of the camera model.
	 * @param viewAngle float representing the camera model.
	 */
	public void setViewAngle(float viewAngle) {
		this.viewAngle = viewAngle;
		this.distance = (this.width / 2) / (float) Math.tan(viewAngle / 2);
	}
	
	/**
	 * Set the View Angle of the camera model.
	 * @param width Width of the camera model.
	 * @param height Height of the camera model.
	 * @param viewAngle float representing the camera model.
	 */
	public void setViewAngle(int width, int height, float viewAngle) {
		this.viewAngle = viewAngle;
		this.distance = (width / 2) / (float) Math.tan(viewAngle / 2);
	}

	/**
	 * Project point from the origin MixVector to the projected MixVector.
	 * @param orgPoint MixVector representing the origin.
	 * @param prjPoint MixVector representing the projected point.
	 * @param addX Add X to the projected point.
	 * @param addY Add Y to the projected point.
	 */
	public void projectPoint(MixVector orgPoint, MixVector prjPoint, float addX, float addY) {
		prjPoint.x = distance * orgPoint.x / -orgPoint.z;
		prjPoint.y = distance * orgPoint.y / -orgPoint.z;
		prjPoint.z = orgPoint.z;
		prjPoint.x = prjPoint.x + addX + width / 2;
		prjPoint.y = -prjPoint.y + addY + height / 2;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "CAM(" + width + "," + height + "," + viewAngle + "," + distance + ")";
	}
}
