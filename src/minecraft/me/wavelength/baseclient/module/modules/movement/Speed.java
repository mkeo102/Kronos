package me.wavelength.baseclient.module.modules.movement;

import org.lwjgl.input.Keyboard;

import me.wavelength.baseclient.event.events.UpdateEvent;
import me.wavelength.baseclient.module.AntiCheat;
import me.wavelength.baseclient.module.Category;
import me.wavelength.baseclient.module.Module;
import me.wavelength.baseclient.utils.MovementUtil;

public class Speed extends Module {
	
	public Speed() {
		super("Speed", "Gotta go fast!", Keyboard.KEY_Z, Category.MOVEMENT, AntiCheat.VANILLA, AntiCheat.AAC);
		moduleSettings.addDefault("speed", 0.001f);
	}
	
	float speed =  moduleSettings.getFloat("speed");
	
	public void onUpdate(UpdateEvent event) {
		
		if(mc.thePlayer.onGround) {
			
			mc.thePlayer.setSprinting(true);
			mc.thePlayer.jump();
			
		}
		
		MovementUtil.setSpeed(0.45f);
		
	}

}

