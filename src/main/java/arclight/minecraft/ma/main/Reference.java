package arclight.minecraft.ma.main;

public class Reference
{
	public static final String MOD_ID = "mysticaladdons";
	public static final String NAME = "Mystical Addons";
	public static final String VERSION = "1.0.0";
	
	public static final String DEPENDENCIES =
			"required-after:cucumber@[1.0.0,);" +
			"required-after:mysticalagriculture@[1.6.3,)";
	public static final String MINECRAFT = "[1.12]";
	
	public static final String GUI_FACTORY = "arclight.minecraft.ma.config.GuiFactory";
}
