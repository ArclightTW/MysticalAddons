package arclight.minecraft.ma.init;

import com.blakebr0.cucumber.registry.ModRegistry;

import arclight.minecraft.ma.main.MysticalAddons;

public class ModBlocks
{
	public static void onInitialize()
	{
		final ModRegistry registry = MysticalAddons.REGISTRY;
		
		// registry.register(block, name, itemblock);
	}
	
	public static void registerTileEntities()
	{
		// GameRegistry.registerTileEntity(class, name);
	}
	
	public static void registerOreDictionary()
	{
		// OreDictionary.registerOre(name, block);
	}
}
