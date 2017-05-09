package actioninja.burningsun

import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.common.config.Configuration
import net.minecraftforge.fml.client.event.ConfigChangedEvent
import net.minecraftforge.fml.common.eventhandler.EventPriority
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import java.io.File
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

/**
 * Created by actioninja on 5/8/17.
 */

object BurningSunConfig
{
    lateinit var config:Configuration

    //globals
    val CATEGORY_NAME_GLOBAL = "category_global"
    var burnInSun = true
    var hyperLethal = false
    var hyperLethalDamage = 10
    var helmetsBlockSun = false
    var helmetsTakeDamage = false
    var helmetDamageMultiplier = 1
    var spawnWithSunBlock = true
    var spawnWithSunBlockLength = 120


    fun preInit(file: File)
    {
        config = Configuration(file)

        syncFromFile()
    }

    fun preInitClient()
    {
        MinecraftForge.EVENT_BUS.register(ConfigEventHandler())
    }

    fun syncFromFile()
    {
       syncConfig(true, true)
    }

    fun syncFromGUI()
    {
        syncConfig(false, true)
    }

    fun syncFromFields()
    {
        syncConfig(false, false)
    }

    fun syncConfig(loadConfigFromFile: Boolean, readFieldsFromConfig: Boolean)
    {
        if (loadConfigFromFile)
            config.load()

        var propBurnInSun = config.get(CATEGORY_NAME_GLOBAL, "burnInSun", true, "Whether you burn in the sun or not")
        propBurnInSun.languageKey = "gui.burnInSun"

        var propHyperLethal = config.get(CATEGORY_NAME_GLOBAL, "hyperLethal", false, "An extra 'hyper lethal' mode that does a configurable amount of damage per tick")
        propHyperLethal.languageKey = "gui.hyperLethal"

        var propHyperLethalDamage = config.get(CATEGORY_NAME_GLOBAL, "hyperLethalDamage", 10)
        propHyperLethalDamage.languageKey = "gui.hyperLethalDamage"

        var propHelmetsBlockSun = config.get(CATEGORY_NAME_GLOBAL, "helmetsBlockSun", false, "Whether helmets block sun")
        propHelmetsBlockSun.languageKey = "gui.helmetsBlockSun"

        var propHelmetsTakeDamage = config.get(CATEGORY_NAME_GLOBAL, "helmetsTakeDamage", false, "Whether helmets take damage while blocking sun")
        propHelmetsTakeDamage.languageKey = "gui.helmetsTakeDamage"

        var propHelmetDamageMultiplier = config.get(CATEGORY_NAME_GLOBAL, "helmetDamageMultiplier", 1, "Multiplier on helmet damage")
        propHelmetDamageMultiplier.languageKey = "gui.helmetDamageMultiplier"

        var propSpawnWithSunBlock = config.get(CATEGORY_NAME_GLOBAL, "spawnWithSunBlock", true, "Whether you spawn with the sun block effect")
        propSpawnWithSunBlock.languageKey = "gui.spawnWithSunBlock"

        var propSpawnWithSunBlockLength = config.get(CATEGORY_NAME_GLOBAL, "spawnWithSunBlockLength", 120, "How long sun block lasts after spawning (in seconds)")
        propSpawnWithSunBlockLength.languageKey = "gui.spawnWithSunBlockLength"

        val propOrderGlobal: MutableList<String> = mutableListOf()
        propOrderGlobal.add(propBurnInSun.name)
        propOrderGlobal.add(propHyperLethal.name)
        propOrderGlobal.add(propHyperLethalDamage.name)
        propOrderGlobal.add(propHelmetsBlockSun.name)
        propOrderGlobal.add(propHelmetsTakeDamage.name)
        propOrderGlobal.add(propHelmetDamageMultiplier.name)
        propOrderGlobal.add(propSpawnWithSunBlock.name)
        propOrderGlobal.add(propSpawnWithSunBlockLength.name)
        config.setCategoryPropertyOrder(CATEGORY_NAME_GLOBAL, propOrderGlobal)
        config.setCategoryComment(CATEGORY_NAME_GLOBAL, "These are settings that apply globally to all dimensions")

        if(readFieldsFromConfig)
        {
            burnInSun = propBurnInSun.boolean
            hyperLethal = propHyperLethal.boolean
            hyperLethalDamage = propHyperLethalDamage.int
            helmetsBlockSun = propHelmetsBlockSun.boolean
            helmetsTakeDamage = propHelmetsTakeDamage.boolean
            helmetDamageMultiplier = propHelmetDamageMultiplier.int
            spawnWithSunBlock = propSpawnWithSunBlock.boolean
            spawnWithSunBlockLength = propSpawnWithSunBlockLength.int
        }

        propHelmetsBlockSun.set(helmetsBlockSun)

        if(config.hasChanged())
            config.save()
    }


    class ConfigEventHandler
    {
        @SubscribeEvent(priority = EventPriority.NORMAL)
        fun onConfigChanged(event: ConfigChangedEvent.OnConfigChangedEvent)
        {
            if(event.modID.equals(BurningSun.MODID) && !event.isWorldRunning)
            {
               if(event.configID.equals(CATEGORY_NAME_GLOBAL))
               {
                   syncFromGUI()
               }
            }
        }
    }
}
