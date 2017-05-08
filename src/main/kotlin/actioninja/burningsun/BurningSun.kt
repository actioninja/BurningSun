package actioninja.burningsun

import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import actioninja.burningsun.Config;
import actioninja.burningsun.item.ItemRegistry
import actioninja.burningsun.potion.PotionRegistry
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

/**
 * Created by actioninja on 5/1/17.
 */


@Mod(modid = BurningSun.MODID, name = "Burning Sun", version = "@VERSION@", modLanguageAdapter = "net.shadowfacts.forgelin.KotlinAdapter", dependencies = "required-after:forgelin@[1.3.1,);")
object BurningSun {
    const val MODID: String = "burningsun"


    @Mod.EventHandler
    fun preInit(event: FMLPreInitializationEvent)
    {
        MinecraftForge.EVENT_BUS.register(BSEventHandler())
        PotionRegistry.init()
        ItemRegistry.initCommon()
    }

    @Mod.EventHandler
    @SideOnly(Side.CLIENT)
    fun preInitClient(event: FMLPreInitializationEvent)
    {
        ItemRegistry.initClient()
    }

    @Mod.EventHandler
    fun init(event: FMLInitializationEvent)
    {

    }

    fun prependModId(string: String):String
    {
        return MODID + ":" + string
    }
}