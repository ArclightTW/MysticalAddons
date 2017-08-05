package arclight.minecraft.ma.container;

import arclight.minecraft.ma.tileentity.TileEntitySeedReprocessorTiered;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerSeedReprocessor extends Container
{
	private TileEntitySeedReprocessorTiered machine;
	
	public ContainerSeedReprocessor(InventoryPlayer inventory, TileEntitySeedReprocessorTiered reprocessor)
	{
		this.machine = reprocessor;
		
		for(int x = 0; x < 9; x++)
		{
			addSlotToContainer(new Slot(inventory, x, 8 + x * 18, 142));
		}
		
		for(int y = 0; y < 3; y++)
		{
			for(int x = 0; x < 9; x++)
			{
				addSlotToContainer(new Slot(inventory, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));
			}
		}
		
		addSlotToContainer(new Slot(machine, 0, 56, 27));
		addSlotToContainer(new Slot(machine, 1, 116, 27)
		{
			@Override
			public boolean isItemValid(ItemStack stack)
			{
				return false;
			}
		});
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int index)
	{
		return null;
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player)
	{
		return machine.isUsableByPlayer(player);
	}
}
