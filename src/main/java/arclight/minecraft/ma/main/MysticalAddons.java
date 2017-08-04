package arclight.minecraft.ma.main;

import com.blakebr0.cucumber.registry.ModRegistry;

import arclight.minecraft.ma.proxy.CommonProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(
		modid						= Reference.MOD_ID,
		name						= Reference.NAME,
		version						= Reference.VERSION,
		dependencies				= Reference.DEPENDENCIES,
		acceptedMinecraftVersions	= Reference.MINECRAFT,
		guiFactory	 				= Reference.GUI_FACTORY
	)
public class MysticalAddons
{
	public static final ModRegistry REGISTRY = new ModRegistry(Reference.MOD_ID);
	
	public static CreativeTabs tabAddons = new CreativeTabs("tabMysticalAddons")
	{
		@Override
		public ItemStack getTabIconItem()
		{
			return new ItemStack(Items.NETHER_STAR);
		}
	};
	
	@Mod.Instance(Reference.MOD_ID)
	public static MysticalAddons INSTANCE;
	
	@SidedProxy(
		clientSide = Reference.CLIENT_PROXY,
		serverSide = Reference.SERVER_PROXY
				)
	public static CommonProxy proxy;
	
	@Mod.EventHandler
	public void onPreInitialization(FMLPreInitializationEvent event)
	{
		proxy.onPreInitialization(event);
	}
	
	@Mod.EventHandler
	public void onInitialization(FMLInitializationEvent event)
	{
		proxy.onInitialization(event);
	}
	
	@Mod.EventHandler
	public void onPostInitialization(FMLPostInitializationEvent event)
	{
		proxy.onPostInitialization(event);
	}
}
