package me.wavelength.baseclient.module.modules.render;

import java.util.TimerTask;

import org.lwjgl.input.Keyboard;

import me.wavelength.baseclient.BaseClient;
import me.wavelength.baseclient.event.events.Render2DEvent;
import me.wavelength.baseclient.gui.clickgui.ClickGui;
import me.wavelength.baseclient.gui.clickgui.GuiBind;
import me.wavelength.baseclient.module.Category;
import me.wavelength.baseclient.module.Module;
import me.wavelength.baseclient.utils.RenderUtils;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;




/* BROKEN FOR SOME REASON*/





public class TimerSpeed extends Module{

	public TimerSpeed() {
		super("TimerSpeed", "Shows the value of timer", Keyboard.KEY_NONE, Category.RENDER);
		
		
	}
	
	public void onEnable() {
		
		GuiScreen currentScreen = mc.currentScreen;
		
		if (currentScreen instanceof GuiMainMenu) {
			this.toggle();
		}
	}
	
	public void onUpdate() {
	
		GuiScreen currentScreen = mc.currentScreen;
		
		if (currentScreen instanceof GuiMainMenu) {
			this.toggle();
		}
		
		if (currentScreen != null && !(currentScreen instanceof ClickGui) && !(currentScreen instanceof GuiBind)) {
			return;
		}
		
	}
	
	public void onRender2D(Render2DEvent event) {
		
		float timer = mc.timer.timerSpeed ;
		
		String timerspeed = Float.toString(timer); ;
		
		
		
		int x = mc.displayHeight;
		
		renderText(event, "Timer speed:" + timerspeed , 2);
		
	}
	
	private void renderText(Render2DEvent event, String text, int x) {
		
		RenderUtils.drawStringFromBottomLeft(text, x+ 100, 10, -1);
	}
	
	
	
}








