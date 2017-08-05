package arclight.minecraft.ma.handler;

import arclight.minecraft.ma.container.ContainerSeedReprocessor;
import arclight.minecraft.ma.gui.GuiSeedReprocessor;
import arclight.minecraft.ma.tileentity.TileEntitySeedReprocessorTiered;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler
{
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		return new GuiSeedReprocessor(player.inventory, (TileEntitySeedReprocessorTiered)world.getTileEntity(new BlockPos(x, y, z)));
	}
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		return new ContainerSeedReprocessor(player.inventory, (TileEntitySeedReprocessorTiered)world.getTileEntity(new BlockPos(x, y, z)));
	}
}

