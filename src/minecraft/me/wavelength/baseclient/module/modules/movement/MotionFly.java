package me.wavelength.baseclient.module.modules.movement;

import org.lwjgl.input.Keyboard;

import me.wavelength.baseclient.event.events.UpdateEvent;
import me.wavelength.baseclient.module.AntiCheat;
import me.wavelength.baseclient.module.Category;
import me.wavelength.baseclient.module.Module;
import me.wavelength.baseclient.utils.MovementUtil;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C13PacketPlayerAbilities;
import net.minecraft.util.BlockPos;

public class MotionFly extends Module {
	
	public MotionFly() {
		super("motionFly", "allows you to fly" , Keyboard.KEY_H, Category.MOVEMENT, AntiCheat.VANILLA, AntiCheat.FLAMEAC);
	}
	
	public void setup() {
		moduleSettings.addDefault("speed", 1f);
	}
	
	float speed = moduleSettings.getFloat("speed");
	
	int timerTime = 0;
	
	
	public void onEnable() {
		
		
		
	}
	
	public void onUpdate(UpdateEvent event) {
				
		mc.thePlayer.motionY = 0;
		mc.thePlayer.onGround = true;
		mc.thePlayer.isAirBorne = false;
		MovementUtil.setSpeed(speed);
		mc.timer.timerSpeed = 1.1f;
		
		if(MovementUtil.isMoving()) {
			if(mc.gameSettings.isKeyDown(mc.gameSettings.keyBindJump) == true) {
				mc.thePlayer.motionY = 0.419999986886978;
			} else {
				mc.thePlayer.motionY = 0;
			}
		}
			
		timerTime++;
		
	}
	
	public void onDisable() {
		
		mc.timer.timerSpeed = 1f;
		
	}

}
