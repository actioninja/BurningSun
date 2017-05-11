package actioninja.burningsun

import actioninja.burningsun.item.ItemRegistry
import actioninja.burningsun.item.LootManager
import actioninja.burningsun.item.RecipeManager
import actioninja.burningsun.potion.PotionRegistry
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.Loader
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import org.apache.logging.log4j.LogManager

/**
 * Created by actioninja on 5/1/17.
 */


@Mod(modid = BurningSun.MODID, name = "Burning Sun", version = BurningSun.VERSION, modLanguageAdapter = "net.shadowfacts.forgelin.KotlinAdapter", dependencies = "required-after:forgelin@[1.3.1,);")
object BurningSun
{
    const val MODID = "burningsun"
    const val VERSION = "@VERSION@"

    var log = LogManager.getLogger(MODID)

    @Mod.EventHandler
    fun preInit(event:FMLPreInitializationEvent)
    {
        BurningSunConfig.preInit(event.suggestedConfigurationFile)
        MinecraftForge.EVENT_BUS.register(BurningSunEventHandler())
        PotionRegistry.init()
        ItemRegistry.initCommon()
        if(Loader.isModLoaded("Baubles") && BurningSunConfig.baublesIntegrationEnabled)
            MinecraftForge.EVENT_BUS.register(LootManager())
    }

    @Mod.EventHandler
    @SideOnly(Side.CLIENT)
    fun preInitClient(event:FMLPreInitializationEvent)
    {
        ItemRegistry.initClient()
        BurningSunConfig.preInitClient()
    }

    @Mod.EventHandler
    fun init(event:FMLInitializationEvent)
    {
        if(Loader.isModLoaded("Baubles") && BurningSunConfig.baublesIntegrationEnabled)
            RecipeManager.initRecipes()
    }

    fun prependModId(string:String):String
    {
        return MODID + ":" + string
    }

    fun debugLog(string:String)
    {
        if (BurningSunConfig.debugLogging)
            log.info(string)
    }
}