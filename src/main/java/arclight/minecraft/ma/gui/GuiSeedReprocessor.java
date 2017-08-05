package arclight.minecraft.ma.gui;

import org.lwjgl.opengl.GL11;

import arclight.minecraft.ma.container.ContainerSeedReprocessor;
import arclight.minecraft.ma.tileentity.TileEntitySeedReprocessorTiered;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;

public class GuiSeedReprocessor extends GuiContainer
{
	private static final ResourceLocation gui_texture = new ResourceLocation("mysticalagriculture", "textures/gui/seed_reprocessor_gui.png");
	private TileEntitySeedReprocessorTiered compressor;
	
	public GuiSeedReprocessor(InventoryPlayer player, TileEntitySeedReprocessorTiered machine)
	{
		super(new ContainerSeedReprocessor(player, machine));
		compressor = machine;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		renderHoveredToolTip(mouseX, mouseY);
	}
	
	@Override
	public void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		String s = new TextComponentTranslation(compressor.getWorld().getBlockState(compressor.getPos()).getBlock().getUnlocalizedName() + ".display").getFormattedText();
		fontRenderer.drawString(s, xSize / 2 - fontRenderer.getStringWidth(s) / 2, 6, 4210752);
		fontRenderer.drawString(new TextComponentTranslation("container.inventory").getFormattedText(), 8, ySize - 96 + 2, 4210752);
	}
	
	private int getCookProgressScaled(int pixels)
	{
		float percComplete = 1F - (compressor.getTimeLeft() / (float)compressor.getBaseTime());
		return (int)(percComplete * pixels);
	}
	
	@Override
	public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		
		mc.getTextureManager().bindTexture(gui_texture);
		this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		if(compressor.getProgress() > 0)
		{
			int i1 = getCookProgressScaled(24);
			this.drawTexturedModalRect(guiLeft + 79, guiTop + 26, 176, 14, i1 + 1, 16);
		}
	}
}
