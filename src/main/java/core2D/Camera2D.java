package core2D;

import java.util.ArrayList;
import java.util.List;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector2i;
import org.joml.Vector3f;

import core.Application;
import core.ecs.Behaviour;
import core.renderer.Camera;
import core.renderer.OrthographicCamera;

public class Camera2D extends Behaviour implements Camera
{
	private OrthographicCamera orthographicCamera;
	
	private List<CanvasLayer> canvasLayers = new ArrayList<CanvasLayer>();
	
	public Camera2D() 
	{
		final Vector2i windowSize = Application.getWindow().getSize();
		final float aspectRatio = ((float) windowSize.x) / windowSize.y;
		
		this.orthographicCamera = new OrthographicCamera(aspectRatio, 5);
	}
	
	@Override
	protected void onUpdate() 
	{
		updateTransform();

		for(int i = 0; i < this.canvasLayers.size(); i++)
		{
			CanvasLayer layer = this.canvasLayers.get(i);
			for(int j = 0; j < layer.getObjects().size(); j++)
			{
				Behaviour behaviour = layer.getObjects().get(j);
				
				behaviour.update();
			}
		}
	}
	
	private void updateTransform()
	{
		final Vector2i windowSize = Application.getWindow().getSize();
		final float aspectRatio = ((float) windowSize.x) / windowSize.y;
		
		this.orthographicCamera.setAspectRatio(aspectRatio);
		
		Vector2f position = new Vector2f(0f, 0f);
		float rotation = Transform2D.getValues(this.gameObject, position, new Vector2f());
		
		this.orthographicCamera.setTransform(new Vector3f(position.x, position.y, -1f), rotation);
	}
	
	public void setZoomLevel(final float zoomLevel)
	{
		this.orthographicCamera.setZoomLevel(zoomLevel);
	}
	
	public void addCanvasLayer(final CanvasLayer canvasLayer)
	{
		this.canvasLayers.add(canvasLayer);
	}
	public void removeCanvasLayer(final CanvasLayer canvasLayer)
	{
		this.canvasLayers.remove(canvasLayer);
	}
	public List<CanvasLayer> getCanvasLayers()
	{
		return this.canvasLayers;
	}
	
	public Matrix4f getProjectionMatrix()
	{
		return this.orthographicCamera.getProjectionMatrix();
	}
	public Matrix4f getViewMatrix()
	{
		return this.orthographicCamera.getViewMatrix();
	}	
	public Matrix4f getViewProjectionMatrix()
	{
		return this.orthographicCamera.getViewProjectionMatrix();
	}
	
	public Vector2f screenToWorldPosition(final Vector2i screenPosition)
	{
		final Vector2f worldPosition = Transform2D.getValues(this.gameObject);
		
		final Vector2i screenSize = Application.getWindow().getSize();
		Vector2f positionScreenProportion = new Vector2f(((float) screenPosition.x) / screenSize.x, ((float) screenPosition.y) / screenSize.y);
		positionScreenProportion.mul(this.orthographicCamera.getZoomLevel() * 2f);

		//Set the camera position as the "center"
		positionScreenProportion.x *= this.orthographicCamera.getAspectRatio();
		//The Y axis is inverted
		positionScreenProportion.y = -positionScreenProportion.y;

		positionScreenProportion.x -= this.orthographicCamera.getZoomLevel() * this.orthographicCamera.getAspectRatio();
		positionScreenProportion.y += this.orthographicCamera.getZoomLevel();
			
		return new Vector2f(worldPosition).add(positionScreenProportion);
	}
}
