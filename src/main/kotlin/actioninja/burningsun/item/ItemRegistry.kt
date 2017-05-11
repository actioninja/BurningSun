package actioninja.burningsun.item

import actioninja.burningsun.BurningSun
import actioninja.burningsun.BurningSunConfig
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.item.Item
import net.minecraftforge.client.model.ModelLoader
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.Loader
import net.minecraftforge.fml.common.registry.GameRegistry
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

/**
 * Created by actioninja on 5/7/17.
 */

object ItemRegistry
{
    lateinit var itemSunBlockRing:Item
    lateinit var itemUnbreakableSunBlockRing:Item


    fun initCommon()
    {
        if(Loader.isModLoaded("Baubles") && BurningSunConfig.baublesIntegrationEnabled)
        {
            itemSunBlockRing = registerItem(ItemSunBlockRing(), "itemSunBlockRing", "itemSunBlockRing")
            itemUnbreakableSunBlockRing = registerItem(ItemUnbreakableSunBlockRing(), "itemUnbreakableSunBlockRing", "itemUnbreakableSunBlockRing")
        }
    }

    @SideOnly(Side.CLIENT)
    fun initClient()
    {
        if(Loader.isModLoaded("Baubles") && BurningSunConfig.baublesIntegrationEnabled)
        {
            registerItemModel(itemSunBlockRing)
            registerItemModel(itemUnbreakableSunBlockRing)
        }
    }

    fun registerItem(item:Item, name:String, regName:String):Item
    {
        item.setUnlocalizedName(name).setRegistryName(regName)
        GameRegistry.register(item)
        return item
    }

    @SideOnly(Side.CLIENT)
    fun registerItemModel(item:Item)
    {
        val itemModelResourceLocation = ModelResourceLocation(BurningSun.prependModId(item.unlocalizedName))
        ModelLoader.setCustomModelResourceLocation(item, 0, itemModelResourceLocation)
    }
}