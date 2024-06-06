package core.renderer;

import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class OrthographicCamera
{
	private Matrix4f projectionMatrix = new Matrix4f();
	private Matrix4f viewMatrix = new Matrix4f();
	private Matrix4f viewProjectionMatrix = new Matrix4f();
	
	private float aspectRatio = 1f;
	private float zoomLevel = 1f;
	
	public OrthographicCamera(final float left, final float right, final float bottom, final float top)
	{
		setProjection(left, right, bottom, top);
	}
	
	public OrthographicCamera(final float aspectRatio, final float zoomLevel)
	{
		setAspectRatio(aspectRatio);
		setZoomLevel(zoomLevel);
	}
	
	private void recalculateViewProjectionMatrix()
	{
		this.viewProjectionMatrix = new Matrix4f().mul(this.projectionMatrix);
		this.viewProjectionMatrix.mul(this.viewMatrix);
	}
	public void setTransform(final Vector3f position, final float rotation)
	{
		this.viewMatrix.identity();
		this.viewMatrix.translationRotate(position.x, position.y, position.z, new Quaternionf().rotateZ((float) Math.toRadians(rotation)));
		this.viewMatrix.invert();

		recalculateViewProjectionMatrix();
	}
	private void updateProjection()
	{
		final float left = -this.aspectRatio * this.zoomLevel;
		final float right = this.aspectRatio * this.zoomLevel;
		final float bottom = -this.zoomLevel;
		final float top = this.zoomLevel;

		this.projectionMatrix.identity();
		this.projectionMatrix.ortho(left, right, bottom, top, -1f, 1f);

		recalculateViewProjectionMatrix();
	}
	
	public void setProjection(final float left, final float right, final float bottom, final float top)
	{
		this.aspectRatio = right / this.zoomLevel;
		this.zoomLevel = top;
		
		updateProjection();
	}

	public float getAspectRatio() 
	{
		return this.aspectRatio;
	}
	public void setAspectRatio(final float aspectRatio)
	{
		this.aspectRatio = aspectRatio;
		
		updateProjection();
	}
	public float getZoomLevel() 
	{
		return this.zoomLevel;
	}
	public void setZoomLevel(final float zoomLevel)
	{
		this.zoomLevel = zoomLevel;
		
		updateProjection();
	}
	
	public Matrix4f getProjectionMatrix()
	{
		return this.projectionMatrix;
	}
	public Matrix4f getViewMatrix()
	{
		return this.viewMatrix;
	}	
	public Matrix4f getViewProjectionMatrix()
	{
		return this.viewProjectionMatrix;
	}
}
