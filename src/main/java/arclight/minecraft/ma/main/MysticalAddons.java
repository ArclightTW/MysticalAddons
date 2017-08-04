package arclight.minecraft.ma.main;

import com.blakebr0.cucumber.registry.ModRegistry;

import net.minecraftforge.fml.common.Mod;

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
}
