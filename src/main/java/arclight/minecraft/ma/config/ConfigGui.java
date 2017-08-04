package arclight.minecraft.ma.config;

import java.util.List;

import com.google.common.collect.Lists;

import arclight.minecraft.ma.main.MysticalAddons;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;

public class ConfigGui extends GuiConfig
{
	public ConfigGui(GuiScreen parent)
	{
		super(parent, getConfigElements(), MysticalAddons.MOD_ID, false, false, GuiConfig.getAbridgedConfigPath(ModConfig.config.toString()));
	}
	
	private static List<IConfigElement> getConfigElements()
	{
		List<IConfigElement> list = Lists.newArrayList();
		
		for(String category : ModConfig.config.getCategoryNames())
		{
			list.add(new ConfigElement(ModConfig.config.getCategory(category)));
		}
		
		return list;
	}
}
