package arclight.minecraft.ma.integration;

import java.util.List;

import com.blakebr0.mysticalagriculture.items.ItemSeed;
import com.jaquadro.minecraft.chameleon.integration.IntegrationModule;

import arclight.minecraft.ma.block.BlockSeedReprocessorTiered;
import arclight.minecraft.ma.tileentity.TileEntitySeedReprocessorTiered;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.event.FMLInterModComms;

public class WailaModule extends IntegrationModule
{
	@Override
	public String getModID()
	{
		return "waila";
	}

	@Override
	public void init() throws Throwable
	{
		FMLInterModComms.sendMessage(getModID(), "register", "arclight.minecraft.ma.integration.WailaModule.registerProvider");
	}

	@Override
	public void postInit()
	{
	}
	
	public static void registerProvider(IWailaRegistrar registrar)
	{
		registrar.registerBodyProvider(new WailaDrawer(), BlockSeedReprocessorTiered.class);
	}
	
	public static class WailaDrawer implements IWailaDataProvider
	{
		@Override
		public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
		{
			TileEntity tile = accessor.getTileEntity();
			
			if(tile instanceof TileEntitySeedReprocessorTiered)
			{
				TileEntitySeedReprocessorTiered reprocessor = (TileEntitySeedReprocessorTiered)tile;
				
				ItemStack input = reprocessor.getStackInSlot(0);
				ItemStack output = reprocessor.getStackInSlot(1);
				
				String inputDisplay = input == null || input.isEmpty() ? "Empty" : input.getDisplayName() + String.format(" (Tier: %d) ", ((ItemSeed)input.getItem()).tier) +  "(x" + input.getCount() + ")";
				String outputDisplay = output == null || output.isEmpty() ? "Empty" : output.getDisplayName() + " (x" + output.getCount() + ")";
				
				currenttip.add(new TextComponentTranslation("mysticaladdons:integration.waila.input", inputDisplay).getFormattedText());
				currenttip.add(new TextComponentTranslation("mysticaladdons:integration.waila.output", outputDisplay).getFormattedText());
			}
			
			return currenttip;
		}
	}
}
