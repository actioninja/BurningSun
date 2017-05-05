package actioninja.burningsun

import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import actioninja.burningsun.Config;
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.event.FMLInitializationEvent

/**
 * Created by actioninja on 5/1/17.
 */


@Mod(modid = BurningSun.MODID, name = "Burning Sun", version = "@VERSION@", modLanguageAdapter = "net.shadowfacts.forgelin.KotlinAdapter")
object BurningSun {
    const val MODID: String = "burningsun"

    @Mod.EventHandler
    fun preInit(event: FMLPreInitializationEvent)
    {
    }
    @Mod.EventHandler
    fun init(event: FMLInitializationEvent)
    {
        var bsEventHandler: BSEventHandler = BSEventHandler()
        MinecraftForge.EVENT_BUS.register(bsEventHandler)
    }
}