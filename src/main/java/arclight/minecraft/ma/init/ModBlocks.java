package arclight.minecraft.ma.init;

import com.blakebr0.cucumber.registry.ModRegistry;

import arclight.minecraft.ma.block.BlockSeedReprocessorTiered;
import arclight.minecraft.ma.item.ItemBlockSeedReprocessorTiered;
import arclight.minecraft.ma.main.MysticalAddons;
import arclight.minecraft.ma.tileentity.TileEntitySeedReprocessorTiered;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModBlocks
{
	public static BlockSeedReprocessorTiered inferium_seed_reprocessor = new BlockSeedReprocessorTiered(0, "inferium_seed_reprocessor");
	public static BlockSeedReprocessorTiered prudentium_seed_reprocessor = new BlockSeedReprocessorTiered(1, "prudentium_seed_reprocessor");
	public static BlockSeedReprocessorTiered intermedium_seed_reprocessor = new BlockSeedReprocessorTiered(2, "intermedium_seed_reprocessor");
	public static BlockSeedReprocessorTiered superium_seed_reprocessor = new BlockSeedReprocessorTiered(3, "superium_seed_reprocessor");
	public static BlockSeedReprocessorTiered supremium_seed_reprocessor = new BlockSeedReprocessorTiered(4, "supremium_seed_reprocessor");
	public static BlockSeedReprocessorTiered ultimate_seed_reprocessor = new BlockSeedReprocessorTiered(5, "ultimate_seed_reprocessor");
	
	public static void onInitialize()
	{
		final ModRegistry registry = MysticalAddons.REGISTRY;
		
		registry.register(inferium_seed_reprocessor, "inferium_seed_reprocessor", new ItemBlockSeedReprocessorTiered(inferium_seed_reprocessor));
		registry.register(prudentium_seed_reprocessor, "prudentium_seed_reprocessor", new ItemBlockSeedReprocessorTiered(prudentium_seed_reprocessor));
		registry.register(intermedium_seed_reprocessor, "intermedium_seed_reprocessor", new ItemBlockSeedReprocessorTiered(intermedium_seed_reprocessor));
		registry.register(superium_seed_reprocessor, "superium_seed_reprocessor", new ItemBlockSeedReprocessorTiered(superium_seed_reprocessor));
		registry.register(supremium_seed_reprocessor, "supremium_seed_reprocessor", new ItemBlockSeedReprocessorTiered(supremium_seed_reprocessor));
		registry.register(ultimate_seed_reprocessor, "ultimate_seed_reprocessor", new ItemBlockSeedReprocessorTiered(ultimate_seed_reprocessor));
	}
	
	public static void registerTileEntities()
	{
		GameRegistry.registerTileEntity(TileEntitySeedReprocessorTiered.class, "seed_reprocessor_tiered");
	}
	
	public static void registerOreDictionary()
	{
		// OreDictionary.registerOre(name, block);
	}
}
