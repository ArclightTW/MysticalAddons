package arclight.minecraft.ma.proxy;

import java.io.File;

import arclight.minecraft.ma.config.ModConfig;
import arclight.minecraft.ma.handler.GuiHandler;
import arclight.minecraft.ma.init.ModBlocks;
import arclight.minecraft.ma.init.ModItems;
import arclight.minecraft.ma.integration.IntegrationRegistry;
import arclight.minecraft.ma.main.MysticalAddons;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class CommonProxy
{
	public void onPreInitialization(FMLPreInitializationEvent event)
	{
		ModConfig.init(new File(event.getModConfigurationDirectory(), "mysticaladdons.cfg"));
		
		ModBlocks.onInitialize();
		ModItems.onInitialize();
		
		MinecraftForge.EVENT_BUS.register(MysticalAddons.REGISTRY);
	}
	
	public void onInitialization(FMLInitializationEvent event)
	{
		IntegrationRegistry.instance().init();
		NetworkRegistry.INSTANCE.registerGuiHandler(MysticalAddons.INSTANCE, new GuiHandler());
	}
	
	public void onPostInitialization(FMLPostInitializationEvent event)
	{
		ModBlocks.registerTileEntities();
		
		ModBlocks.registerOreDictionary();
		ModItems.registerOreDictionary();
	}
}
