package arclight.minecraft.ma.config;

import java.io.File;

import arclight.minecraft.ma.main.Reference;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ModConfig
{
	public static Configuration config;
	
	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event)
	{
		if(!event.getModID().equals(Reference.MOD_ID))
		{
			return;
		}
		
		syncConfig();
	}
	
	public static void init(File file)
	{
		config = new Configuration(file);
		syncConfig();
		
		MinecraftForge.EVENT_BUS.register(new ModConfig());
	}
	
	public static void syncConfig()
	{
		String category;
		
		category = "Settings";
		config.addCustomCategoryComment(category, "Settings for Mystical Addons");
		
		if(!config.hasChanged())
		{
			return;
		}
		
		config.save();
	}
}
