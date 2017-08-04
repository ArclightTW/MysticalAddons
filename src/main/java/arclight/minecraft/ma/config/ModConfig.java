package arclight.minecraft.ma.config;

import arclight.minecraft.ma.main.MysticalAddons;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ModConfig
{
	public static Configuration config;
	public static ModConfig instance;
	
	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event)
	{
		if(!event.getModID().equals(MysticalAddons.MOD_ID))
		{
			return;
		}
		
		syncConfig();
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
