package me.wavelength.baseclient.module.modules.world;

import me.wavelength.baseclient.module.Module;
import me.wavelength.baseclient.module.Category;

public class NameProtect extends Module {

  public void Timer(){
    super("Timer", "increases world speed", 0, Category.MOVEMENT);
  }


  public void onEnable(){
    mc.timer.timerSpeed = 1.1f;
  }

  public void onDisable(){
    mc.timer.timerSpeed = 1f
  }
}
