package com.github.sameer_s.sbscorer;

import javafx.animation.AnimationTimer;
import javafx.scene.control.Label;

public class Timer extends Label
{
	private long time = 0;

	private long last = 0;
	private AnimationTimer animTimer = new AnimationTimer()
	{
		@Override
		public void handle(long now)
		{
			now = System.nanoTime();

			time -= ((now - last) / 1_000_000);
			last = now;

			Timer.this.update();
		}
	};

	public void setTime(long time)
	{
		this.time = time;
		update();
	}

	private void update()
	{
		long time = this.time < 0 ? 0 : this.time;

		long minutes = (time / 1000) / 60;
		long seconds = (time / 1000) % 60;

		setText(String.format("%d:%02d", minutes, seconds));
	}

	public void stop()
	{
		animTimer.stop();
	}

	public void start()
	{
		last = System.nanoTime();
		animTimer.start();
	}
}
