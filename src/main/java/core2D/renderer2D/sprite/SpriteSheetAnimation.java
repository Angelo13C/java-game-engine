package core2D.renderer2D.sprite;

import org.joml.Vector2f;

import core.ecs.Behaviour;
import core.ecs.update.DisableAutoUpdate;
import core.renderer.Color;
import core.renderer.texture.Sprite;
import core.renderer.texture.SpriteAtlas;
import core.time.Timeline;
import core.util.StateMachine;
import core2D.Transform2D;
import core2D.renderer2D.Renderer2D;

@DisableAutoUpdate
public class SpriteSheetAnimation extends Behaviour
{
	public class Animation
	{
		public class Frame
		{
			public Sprite sprite;
			
			public Frame(final Sprite sprite)
			{
				this.sprite = sprite;
			}
		}
		
		private StateMachine<Frame> frames = new StateMachine<SpriteSheetAnimation.Animation.Frame>();
		private Timeline<Frame> timeline;
		
		public Animation()
		{
			this.timeline = new Timeline<SpriteSheetAnimation.Animation.Frame>((final Frame frame) -> {
				selectFrame(frame);
			});
		}
		
		public void addFrame(final Frame frame, final float time)
		{
			this.frames.addState(frame);
			this.timeline.addKeyFrame(frame, time);
			
			if(getSelectedFrame() == null)
				selectFrame(frame);
		}		
		public void removeFrame(final Frame frame)
		{
			this.frames.removeState(frame);
			this.timeline.removeKeyFrame(frame);
			
			selectFrame(this.timeline.getLatestKeyFrame());
		}
		
		public void addFramesFromSpriteAtlas(final SpriteAtlas spriteAtlas, final float timeBetweenFrames)
		{
			for(int i = 0; i < spriteAtlas.getSprites().size(); i++)
			{
				addFrame(new Frame(spriteAtlas.getSprites().get(i)), i * timeBetweenFrames);
			}
		}
		
		public void update()
		{
			this.timeline.update();
		}
		
		public void resetTime()
		{
			this.timeline.setCurrentTime(0f);
		}
		
		public Frame getSelectedFrame()
		{
			return this.frames.getSelectedState();
		}		
		public void selectFrame(final Frame frame)
		{
			this.frames.selectState(frame);
		}	
		public void selectFrame(final int frameIndex)
		{
			this.frames.selectState(frameIndex);
		}
		
		public void setLoop(final boolean loop)
		{
			this.timeline.setLoop(loop);
		}
		public boolean isLoop()
		{
			return this.timeline.isLoop();
		}
	}
	
	private StateMachine<Animation> animations;

	public SpriteSheetAnimation() 
	{
		this.animations = new StateMachine<Animation>();
	}
	
	@Override
	protected void onUpdate() 
	{
		Vector2f position = new Vector2f();
		Vector2f scale = new Vector2f();
		float rotation = Transform2D.getValues(this.gameObject, position, scale);
		
		Animation selectedAnimation = this.animations.getSelectedState();

		if(selectedAnimation != null)
		{
			selectedAnimation.update();
			Animation.Frame selectedFrame = selectedAnimation.getSelectedFrame();
			if(selectedFrame != null)
				Renderer2D.drawQuad(position, scale, (float) Math.toRadians(rotation), selectedFrame.sprite, Color.WHITE);
		}
	}
	
	public void addAnimation(final Animation animation)
	{
		this.animations.addState(animation);
	}
	
	public void selectAnimation(final Animation animation)
	{
		Animation selectedAnimation = this.animations.getSelectedState();
		if(selectedAnimation != null)
			selectedAnimation.resetTime();
		this.animations.selectState(animation);
	}	
	public void selectAnimation(final int animationIndex)
	{
		Animation selectedAnimation = this.animations.getSelectedState();
		if(selectedAnimation != null)
			selectedAnimation.resetTime();
		this.animations.selectState(animationIndex);
	}
	public void selectAnimationFrame(final int animationFrameIndex)
	{
		this.animations.getSelectedState().selectFrame(animationFrameIndex);
	}
}
