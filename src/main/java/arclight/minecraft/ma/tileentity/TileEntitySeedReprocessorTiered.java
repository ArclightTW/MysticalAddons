package arclight.minecraft.ma.tileentity;

import com.blakebr0.mysticalagriculture.crafting.ReprocessorManager;
import com.blakebr0.mysticalagriculture.tileentity.TileEntitySeedReprocessor;
import com.blakebr0.mysticalagriculture.util.VanillaPacketDispatcher;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

public class TileEntitySeedReprocessorTiered extends TileEntitySeedReprocessor
{
	private int tier;
	
	private ItemStack input = ItemStack.EMPTY, processing = ItemStack.EMPTY, output = ItemStack.EMPTY;
	private int facing = 2;
	private int progress = 0;
	private int target = 0;
	private int base_time;
	private int time_left;
	private String ingredient;
	
	private int packetCount;
	private boolean packet;
	
	private static final int[] top = new int[]{0};
	private static final int[] sides = new int[]{1};
	
	public static int getEfficiency(int tier)
	{
		switch(tier)
		{
		case 0:
			return 15;
		case 1:
			return 35;
		case 2:
			return 60;
		case 3:
			return 85;
		case 4:
			return 100;
		case 5:
			return 9001;
		}
		
		return 0;
	}
	
	public TileEntitySeedReprocessorTiered()
	{
	}
	
	public TileEntitySeedReprocessorTiered(int tier)
	{
		this.tier = tier;
		base_time = tier == 5 ? 1 : 100 - (getEfficiency(tier) / 2);
	}
	
	@Override
	public boolean timeLeft()
	{
		return getTimeLeft() > 0;
	}
	
	@Override
	public NBTTagCompound writeCustomNBT(NBTTagCompound tag)
	{
		tag.setInteger("Tier", tier);
		
		tag.setInteger("TimeLeft", time_left);
		tag.setShort("Facing", (short) facing);
		
		if(input == null)
		{
			input = ItemStack.EMPTY;
		}
		
		if(input != null || !input.isEmpty())
		{
			NBTTagCompound produce = new NBTTagCompound();
			input.writeToNBT(produce);
			tag.setTag("Input", produce);
		}
		else
		{
			tag.removeTag("Input");
		}
		
		if(processing != null || !processing.isEmpty())
		{
			NBTTagCompound produce = new NBTTagCompound();
			processing.writeToNBT(produce);
			tag.setTag("Processing", produce);
			tag.setInteger("Progress", progress);
			
			if(ingredient != null)
			{
				tag.setString("Ingredient", ingredient);
			}
			else
			{
				tag.removeTag("Ingredient");
			}
		}
		else
		{
			tag.removeTag("Processing");
			tag.removeTag("Progress");
			tag.removeTag("Target");
			tag.removeTag("Ingredient");
		}
		
		if(output != null || !output.isEmpty())
		{
			NBTTagCompound produce = new NBTTagCompound();
			output.writeToNBT(produce);
			tag.setTag("Output", produce);
		}
		else
		{
			tag.removeTag("Output");
		}
		
		return tag;
	}
	
	@Override
	public void readCustomNBT(NBTTagCompound tag)
	{
		tier = tag.getInteger("Tier");
		base_time = 100 - ((tier + 1) * 10);
		
		time_left = tag.getInteger("TimeLeft");
		input = new ItemStack(tag.getCompoundTag("Input"));
		processing = new ItemStack(tag.getCompoundTag("Processing"));
		output = new ItemStack(tag.getCompoundTag("Output"));
		
		if(!processing.isEmpty())
		{
			target = ReprocessorManager.getPrice(processing);
			
			if(target != 0)
			{
				progress = tag.getInteger("Progress");
				
				if(tag.hasKey("Ingredient"))
				{
					this.ingredient = tag.getString("Ingredient");
				}
			}
			else
			{
				processing = ItemStack.EMPTY;
			}
		}
		else
		{
			progress = 0;
			target = 0;
			ingredient = null;
		}
		
		facing = tag.getShort("Facing");
	}
	
	@Override
	public void update()
	{
		if(packetCount > 0)
		{
			packetCount--;
		}
		
		if(time_left > 0)
		{
			time_left--;
		}
		
		if(world.isRemote)
		{
			return;
		}
		
		if(time_left <= 0)
		{
			if(!input.isEmpty() && (processing.isEmpty()))
			{
				if(!ReprocessorManager.getOutput(input).isEmpty() && (output.isEmpty() || ReprocessorManager.getOutput(input).isItemEqual(output)))
				{
					if(processing.isEmpty())
					{
						processing = ReprocessorManager.getOutput(input);
						target = ReprocessorManager.getCost(input);
						ingredient = ReprocessorManager.getName(input);
						setTimeLeft(base_time);
					}
					
					if(ReprocessorManager.getOutput(input).isItemEqual(processing))
					{
						int needed = target - progress;

						if(needed >= input.getCount())
						{
							progress += input.getCount();
							input = ItemStack.EMPTY;
						}
						else
						{
							progress = target;
							input.shrink(needed);
						}
					}

					markDirty();
					packet = true;
				}
			}
		}
		
		if(time_left <= 0)
		{
			if (progress >= target && !processing.isEmpty() && (output.isEmpty() || (output.isItemEqual(processing) && output.getCount() + processing.getCount() <= output.getMaxStackSize())))
			{
				if(output.isEmpty())
				{
					output = processing.copy();
				}
				else if(output.isItemEqual(processing))
				{
					output.setCount(output.getCount() + processing.getCount());
				}
				
				progress -= target;
				
				if(progress == 0)
				{
					processing = ItemStack.EMPTY;
					ingredient = null;
				}
				
				markDirty();
				packet = true;
			}
		}
		
		if(packet && packetCount <= 0)
		{
			VanillaPacketDispatcher.dispatchTEToNearbyPlayers(this);
			packetCount = 10;
			packet = false;
		}
	}

	@Override
	public int getFacing()
	{
		return facing;
	}
	
	@Override
	public void setFacing(int dir)
	{
		facing = dir;
	}
	
	@Override
	public int getProgress()
	{
		return progress;
	}

	@Override
	public int getTarget()
	{
		return target;
	}

	@Override
	public String getIngredient()
	{
		return ingredient;
	}
    
	@Override
	public ItemStack getStackInSlot(int slot)
	{
		if(slot == 0)
		{
			return input;
		}
		else
		{
			return output;
		}
	}
	
	@Override
	public ItemStack decrStackSize(int slot, int decrement)
	{
		if(slot == 0)
		{
			if(input.isEmpty())
			{
				return ItemStack.EMPTY;
			}
			else
			{
				if(decrement < input.getCount())
				{
					ItemStack take = input.splitStack(decrement);
					
					if(input.getCount() <= 0)
					{
						input = ItemStack.EMPTY;
					}
					
					return take;
				}
				else
				{
					ItemStack take = input;
					input = ItemStack.EMPTY;
					return take;
				}
			}
		}
		else if(slot == 1)
		{
			if(output.isEmpty())
			{
				return ItemStack.EMPTY;
			}
			else
			{
				if(decrement < output.getCount())
				{
					ItemStack take = output.splitStack(decrement);
					
					if(output.getCount() <= 0)
					{
						output = ItemStack.EMPTY;
					}
					
					return take;
				}
				else
				{
					ItemStack take = output;
					output = ItemStack.EMPTY;
					return take;
				}
			}
		}
		
		return ItemStack.EMPTY;
	}
	
	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack)
	{
		if(stack.isEmpty())
		{
			return false;
		}
		
		if(slot == 0)
		{
			if(processing.isEmpty())
			{
				return true;
			}
			
			if(ReprocessorManager.getOutput(stack).isEmpty())
			{
				return false;
			}
			
			if(ReprocessorManager.getOutput(stack).isItemEqual(processing))
			{
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public void setInventorySlotContents(int slot, ItemStack stack)
	{
		if(slot == 0)
		{
			input = stack;
		}
		else if(slot == 1)
		{
			output = stack;
		}
	}
	
	@Override
	public ItemStack removeStackFromSlot(int index)
	{
		return ItemStack.EMPTY;
	}
	
	@Override
	public int[] getSlotsForFace(EnumFacing side)
	{
		return side == EnumFacing.DOWN ? sides : (side == EnumFacing.UP ? top : sides);
	}

	@Override
	public int getTimeLeft()
	{
		return time_left; 
	}
	
	@Override
	public void setTimeLeft(int time_left)
	{
		this.time_left = time_left;
	}

	net.minecraftforge.items.IItemHandler handlerTop = new net.minecraftforge.items.wrapper.SidedInvWrapper(this, net.minecraft.util.EnumFacing.UP);
	net.minecraftforge.items.IItemHandler handlerBottom = new net.minecraftforge.items.wrapper.SidedInvWrapper(this, net.minecraft.util.EnumFacing.DOWN);
	net.minecraftforge.items.IItemHandler handlerSide = new net.minecraftforge.items.wrapper.SidedInvWrapper(this, net.minecraft.util.EnumFacing.WEST);
	
	@Override
	@SuppressWarnings("unchecked")
	public <T> T getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, net.minecraft.util.EnumFacing facing)
	{
		if(facing != null && capability == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
		{
			if(facing == EnumFacing.DOWN)
			{
				return (T)handlerBottom;
			}
			else if(facing == EnumFacing.UP)
			{
				return (T)handlerTop;
			}
			else
			{
				return (T)handlerSide;
			}
		}
		
		return super.getCapability(capability, facing);
	}
	
	public int getBaseTime()
	{
		return base_time;
	}
}
