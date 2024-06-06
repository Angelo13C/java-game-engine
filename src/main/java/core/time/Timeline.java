package core.time;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Timeline<Event>
{
	private float duration = 0f;
	private float currentTime = 0f;
	private boolean loop = false;
	private int repeatCount = 1;
	
	private float timeMultiplier = 1f;
	
	private class KeyFrame
	{
		public Event event;
		public float time;
		
		public KeyFrame(final Event event, final float time)
		{
			this.event = event;
			this.time = time;
		}
	}
	private List<KeyFrame> keyFrames = new ArrayList<KeyFrame>();

	private Consumer<Event> callback;
	
	public Timeline(final Consumer<Event> callback)
	{
		this.callback = callback;
	}
	
	public void addKeyFrame(final Event event, final float time)
	{
		this.keyFrames.add(new KeyFrame(event, time));
		refresh();
	}	
	public void removeKeyFrame(final Event event)
	{
		for(int i = 0; i < this.keyFrames.size(); i++)
		{
			if(this.keyFrames.get(i).event == event)
			{
				this.keyFrames.remove(i);
				refresh();
			}
		}
	}
	public void removeKeyFrame(final int eventIndex)
	{
		this.keyFrames.remove(eventIndex);
		refresh();
	}
	
	//Refresh the timeline (check if the duration is outside the bounds)
	private void refresh()
	{
		float duration = 0f;
		for(KeyFrame keyFrame : this.keyFrames)
		{
			if(keyFrame.time > duration)
				duration = keyFrame.time;
		}
		
		this.duration = duration;
	}
	
	public void update()
	{
		if(this.duration == 0f || (!this.loop && this.currentTime == this.duration))
			return;
		
		float deltaTime = (float) Time.getDeltaTime() * this.timeMultiplier;
		while(deltaTime > 0f)
		{
			updateKeyFrames(deltaTime);
			if(this.currentTime + deltaTime >= this.duration)
			{
				if(this.loop)
				{
					deltaTime = this.currentTime + deltaTime - this.duration;
					this.currentTime = 0f;
				}
				else
				{
					this.currentTime = this.duration;
					return;
				}
			}
			else
			{
				this.currentTime += deltaTime;
				break;
			}
		}		
	}
	
	private void updateKeyFrames(final float deltaTime)
	{
		for(KeyFrame keyFrame : this.keyFrames)
		{
			if(keyFrame.time > this.currentTime && keyFrame.time < this.currentTime + deltaTime)
			{
				if(this.callback != null)
					this.callback.accept(keyFrame.event);
			}
		}
	}
	
	//Get the last keyframe "used" by the timeline
	public Event getLatestKeyFrame()
	{
		for(int i = 0; i < this.keyFrames.size(); i++)
		{
			if(this.keyFrames.get(i).time <= this.currentTime)
			{
				if(i - 1 >= 0)
					return this.keyFrames.get(i - 1).event;
				else 
					return null;
			}
		}
		
		return null;
	}
	
	public float getDuration() 
	{
		return duration;
	}
	public void setDuration(final float duration) 
	{
		this.duration = duration;
	}
	public float getCurrentTime() 
	{
		return currentTime;
	}
	public void setCurrentTime(final float currentTime) 
	{
		this.currentTime = currentTime;
	}
	public boolean isLoop() 
	{
		return loop;
	}
	public void setLoop(final boolean loop) 
	{
		this.loop = loop;
	}
	public int getRepeatCount() 
	{
		return repeatCount;
	}
	public void setRepeatCount(final int repeatCount)
	{
		this.repeatCount = repeatCount;
	}

	public float getTimeMultiplier() 
	{
		return timeMultiplier;
	}

	public void setTimeMultiplier(final float timeMultiplier) 
	{
		this.timeMultiplier = timeMultiplier;
	}
}
