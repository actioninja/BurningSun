package actioninja.burningsun

import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.common.config.Configuration
import net.minecraftforge.common.config.Property
import net.minecraftforge.fml.client.event.ConfigChangedEvent
import net.minecraftforge.fml.common.eventhandler.EventPriority
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import java.awt.Dimension
import java.io.File

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

    //Dimensional
    val CATEGORY_NAME_DIMENSIONAL = "category_dimensional"
    var activeDimensions = "0,1"
    var activeDimensionsAsInts = arrayListOf(0,1)
    var dimensionConfigSettings = mutableMapOf<String, DimensionConfig>()

    //Development
    val CATEGORY_NAME_DEBUG = "category_debug"
    var debugLogging = true

    fun preInit(file:File)
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

    fun syncConfig(loadConfigFromFile:Boolean, readFieldsFromConfig:Boolean)
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

        var propActiveDimensions = config.get(CATEGORY_NAME_DIMENSIONAL, "activeDimensions", "0,1")
        propActiveDimensions.languageKey = "gui.activeDimensions"

        var dimStringHolder = propActiveDimensions.string.split(",")
        activeDimensionsAsInts.clear()
        for(dimString in dimStringHolder)
        {
            BurningSun.debugLog("Processing active dim string entry '$dimString' to int")
            if (dimString.contains('-'))
            {
                BurningSun.debugLog("'$dimString' is range, processing range")

                var dimStringRangeHolder = dimString.split("-")
                var dimLow = dimStringRangeHolder[0].toInt()
                var dimHigh = dimStringRangeHolder[1].toInt()
                while(dimLow < dimHigh)
                {
                    activeDimensionsAsInts.add(dimLow)
                    BurningSun.debugLog("added $dimLow to int list")
                    dimLow++
                }
            }else
            {
                BurningSun.debugLog("'$dimString' is value, adding to int list")
                activeDimensionsAsInts.add(dimString.toInt())
            }
        }

        var propDimData = mutableMapOf<String, PropDimensionConfig>()

        for(dimId in dimStringHolder)
        {
            propDimData[dimId] = PropDimensionConfig(config.get(dimId, "burnInSun", true),
                    config.get(dimId, "hyperLethal", false),
                    config.get(dimId, "hyperLethalDamage", 10))
        }

        var propDebugLogging = config.get(CATEGORY_NAME_DEBUG, "debugLogging", true, "Enable debug logging")

        var propOrderGlobal:MutableList<String> = mutableListOf()
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

        var propOrderDimensional = mutableListOf<String>()
        propOrderDimensional.add(propActiveDimensions.name)
        config.setCategoryPropertyOrder(CATEGORY_NAME_DIMENSIONAL, propOrderDimensional)
        config.setCategoryComment(CATEGORY_NAME_DIMENSIONAL, "These are settings that apply per dimension. \n" +
                "By default, only the overworld is active, but other dimensions can be enabled \n" +
                "active dimensions is a string that is comma separated, so to enable the overworld and dimensions 2 and 3, you would put \"0,2,3\"\n" +
                "Ranges are also supported, and are formatted by two numbers with a dash between them\n" +
                "so for example, to enable the overworld, dimension 2, and dimensions 100-105, you would use the string \"0,2,100-105\"\n+" +
                "Once you have entered an active dimensions string, start the game and new config sections will be generated for each dimension")

        if (readFieldsFromConfig)
        {
            burnInSun = propBurnInSun.boolean
            hyperLethal = propHyperLethal.boolean
            hyperLethalDamage = propHyperLethalDamage.int
            helmetsBlockSun = propHelmetsBlockSun.boolean
            helmetsTakeDamage = propHelmetsTakeDamage.boolean
            helmetDamageMultiplier = propHelmetDamageMultiplier.int
            spawnWithSunBlock = propSpawnWithSunBlock.boolean
            spawnWithSunBlockLength = propSpawnWithSunBlockLength.int
            activeDimensions = propActiveDimensions.string
            debugLogging = propDebugLogging.boolean
            for(dims in activeDimensions.split(","))
            {
                if(propDimData[dims] != null)
                dimensionConfigSettings[dims] = DimensionConfig(propDimData[dims]!!.propBurnInSun.boolean,
                        propDimData[dims]!!.propHyperLethal.boolean,
                        propDimData[dims]!!.propHyperLethalDamage.int)
                else
                    BurningSun.log.error("Active dimension config props were not intitialized properly!  Activedim '$dims' was not initialized correctly")

            }
        }

        propHelmetsBlockSun.set(helmetsBlockSun)

        if (config.hasChanged())
            config.save()
    }

    data class DimensionConfig(val burnInSun: Boolean = true, val hyperLethal: Boolean = false, val hyperLethalDamage: Int = 10)
    data class PropDimensionConfig(val propBurnInSun: Property, val propHyperLethal: Property, val propHyperLethalDamage: Property)

    class ConfigEventHandler
    {
        @SubscribeEvent(priority = EventPriority.NORMAL)
        fun onConfigChanged(event:ConfigChangedEvent.OnConfigChangedEvent)
        {
            if (event.modID.equals(BurningSun.MODID) && !event.isWorldRunning)
            {
                if (event.configID.equals(CATEGORY_NAME_GLOBAL))
                {
                    syncFromGUI()
                }
            }
        }
    }
}
