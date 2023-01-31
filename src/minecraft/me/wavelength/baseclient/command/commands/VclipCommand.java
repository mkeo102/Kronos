package me.wavelength.baseclient.command.commands;


import me.wavelength.baseclient.command.Command;

public class VclipCommand extends Command {

   public XRayCommand() {
		super("Vclip", "vclip <distance", "move a certain distance vertically");
   }
   
   @Override
	 public String executeCommand(String line, String[] args) {
  
      if(args.length < 1){
        return;
      }
  
      double clippedDistance = Double.parseDouble(args[1];
      
      Minecraft.getMinecraft().thePlayer.posY = Minecraft.getMinecraft().thePlayer.posY + clippedDistance;
  
   }
}
