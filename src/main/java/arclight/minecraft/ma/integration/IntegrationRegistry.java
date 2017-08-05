package arclight.minecraft.ma.integration;

import arclight.minecraft.ma.main.Reference;
import net.minecraftforge.fml.common.Loader;

public class IntegrationRegistry
{
	private static IntegrationRegistry instance;
	
	static
	{
		com.jaquadro.minecraft.chameleon.integration.IntegrationRegistry reg = instance();
		
		if(Loader.isModLoaded("waila"))
		{
			reg.add(new WailaModule());
		}
	}
	
	private com.jaquadro.minecraft.chameleon.integration.IntegrationRegistry registry;
	
	private IntegrationRegistry()
	{
		registry = new com.jaquadro.minecraft.chameleon.integration.IntegrationRegistry(Reference.MOD_ID);
	}
	
	public static com.jaquadro.minecraft.chameleon.integration.IntegrationRegistry instance()
	{
		if(instance == null)
		{
			instance = new IntegrationRegistry();
		}
		
		return instance.registry;
	}
}
