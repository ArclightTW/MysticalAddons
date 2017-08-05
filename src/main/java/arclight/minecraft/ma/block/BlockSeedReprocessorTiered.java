package arclight.minecraft.ma.block;

import java.util.List;
import java.util.Random;

import com.blakebr0.cucumber.iface.IEnableable;
import com.blakebr0.cucumber.lib.Colors;

import arclight.minecraft.ma.main.MysticalAddons;
import arclight.minecraft.ma.main.Reference;
import arclight.minecraft.ma.tileentity.TileEntitySeedReprocessorTiered;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockSeedReprocessorTiered extends BlockContainer implements IEnableable
{
	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	
	private Random rand = new Random();
	private int tier;
	
	public BlockSeedReprocessorTiered(int tier, String blockName)
	{
		super(Material.IRON);
    
		setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
		setSoundType(SoundType.METAL);
		setHardness(8.0F);
		setResistance(12.0F);
		setHarvestLevel("pickaxe", 1);		
		setUnlocalizedName(Reference.MOD_ID + ":" + blockName);
		setRegistryName(blockName);
		setCreativeTab(MysticalAddons.tabAddons);
		
		this.tier = tier;
	}
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced)
	{
		String color = "";
		
		switch(tier)
		{
		case 0:
			color = Colors.YELLOW;
			break;
		case 1:
			color = Colors.GREEN;
			break;
		case 2:
			color = Colors.GOLD;
			break;
		case 3:
			color = Colors.AQUA;
			break;
		case 4:
			color = Colors.RED;
			break;
		case 5:
			color = Colors.DARK_GRAY;
			break;
		}
		
		tooltip.add(new TextComponentTranslation("tooltip.mysticaladdons:efficiency").getFormattedText() + color + String.format("+%d", TileEntitySeedReprocessorTiered.getEfficiency(tier)) + "%");
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TileEntitySeedReprocessorTiered(tier);
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing heldItem, float side, float hitX, float hitY)
	{
		if(world.isRemote)
		{
			return true;
		}

		player.openGui(MysticalAddons.INSTANCE, tier, world, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}
	
	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state)
	{
		setDefaultFacing(world, pos, state);
	}
	
	private void setDefaultFacing(World world, BlockPos pos, IBlockState state)
	{
		if(!world.isRemote)
		{
			IBlockState iblockstate = world.getBlockState(pos.north());
			IBlockState iblockstate1 = world.getBlockState(pos.south());
			IBlockState iblockstate2 = world.getBlockState(pos.west());
			IBlockState iblockstate3 = world.getBlockState(pos.east());
			EnumFacing enumfacing = (EnumFacing)state.getValue(FACING);
			
			if(enumfacing == EnumFacing.NORTH && iblockstate.isFullBlock() && !iblockstate1.isFullBlock())
			{
				enumfacing = EnumFacing.SOUTH;
			}
			else if(enumfacing == EnumFacing.SOUTH && iblockstate1.isFullBlock() && !iblockstate.isFullBlock())
			{
				enumfacing = EnumFacing.NORTH;
			}
			else if(enumfacing == EnumFacing.WEST && iblockstate2.isFullBlock() && !iblockstate3.isFullBlock())
			{
				enumfacing = EnumFacing.EAST;
			}
			else if(enumfacing == EnumFacing.EAST && iblockstate3.isFullBlock() && !iblockstate2.isFullBlock())
			{
				enumfacing = EnumFacing.WEST;
			}
			
			world.setBlockState(pos, state.withProperty(FACING, enumfacing), 2);
		}
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{
		return getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
	} 
    
	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings("incomplete-switch")
	public void randomDisplayTick(IBlockState stateIn, World world, BlockPos pos, Random rand)
	{
		TileEntitySeedReprocessorTiered compressor = (TileEntitySeedReprocessorTiered)world.getTileEntity(pos);
		
		if(compressor.timeLeft())
		{
			EnumFacing enumfacing = (EnumFacing)stateIn.getValue(FACING);
			double d0 = (double)pos.getX() + 0.5;
			double d1 = (double)pos.getY() + rand.nextDouble() * 6.0 / 16.0;
			double d2 = (double)pos.getZ() + 0.5;
			double d4 = rand.nextDouble() * 0.6 - 0.3;
			
			if(rand.nextDouble() < 0.1)
			{
				world.playSound((double)pos.getX() + 0.5, (double)pos.getY(), (double)pos.getZ() + 0.5, SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
			}
			
			switch(enumfacing)
			{
			case WEST:
				world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 - 0.52, d1, d2 + d4, 0, 0, 0, new int[0]);
				break;
			case EAST:
				world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + 0.52, d1, d2 + d4, 0, 0, 0, new int[0]);
				break;
			case NORTH:
				world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4, d1, d2 - 0.52, 0, 0, 0, new int[0]);
				break;
			case SOUTH:
				world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4, d1, d2 + 0.52, 0, 0, 0, new int[0]);
			}
		}
	}
	
	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state)
	{
		TileEntitySeedReprocessorTiered compressor = (TileEntitySeedReprocessorTiered)world.getTileEntity(pos);
		
		if(compressor != null)
		{
			for(int i = 0; i < 2; i++)
			{
				ItemStack itemstack = compressor.getStackInSlot(i);
				
				if(!itemstack.isEmpty())
				{
					float f = rand.nextFloat() * 0.8F + 0.1F;
					float f1 = rand.nextFloat() * 0.8F + 0.1F;
					float f2 = rand.nextFloat() * 0.8F + 0.1F;
					
					EntityItem entityitem = new EntityItem(world, (double)((float) pos.getX() + f), (double)((float) pos.getY() + f1), (double)((float) pos.getZ() + f2), new ItemStack(itemstack.getItem(), itemstack.getCount(), itemstack.getItemDamage()));
					
					if(itemstack.hasTagCompound())
					{
						entityitem.getItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
					}
					
					float f3 = 0.05F;
					entityitem.motionX = (double)((float)rand.nextGaussian() * f3);
					entityitem.motionY = (double)((float)rand.nextGaussian() * f3 + 0.2F);
					entityitem.motionZ = (double)((float)rand.nextGaussian() * f3);
					world.spawnEntity(entityitem);
				}
			}
		}
		
		super.breakBlock(world, pos, state);
	}
    
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state)
	{
		return EnumBlockRenderType.MODEL;
	}

	@Override
	public IBlockState getStateFromMeta(int meta){
		EnumFacing enumfacing = EnumFacing.getFront(meta);
		
		if(enumfacing.getAxis() == EnumFacing.Axis.Y)
		{
			enumfacing = EnumFacing.NORTH;
		}
	
		return getDefaultState().withProperty(FACING, enumfacing);
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return ((EnumFacing)state.getValue(FACING)).getIndex();
	}

	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot)
	{
		return state.withProperty(FACING, rot.rotate((EnumFacing)state.getValue(FACING)));
	}

	@Override
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn)
	{
		return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue(FACING)));
	}
    
	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] {FACING});
	}
	
	@Override
	public boolean isEnabled()
	{
		return true;
	}
}
