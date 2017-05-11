package actioninja.burningsun.item

import actioninja.burningsun.BurningSun
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
    lateinit var itemTest:Item
    lateinit var itemSunBlockRing:Item


    fun initCommon()
    {
        itemTest = registerItem(ItemTest(), "itemTest", "itemTest")
        if(Loader.isModLoaded("Baubles"))
        itemSunBlockRing = registerItem(ItemSunBlockRing(), "itemSunBlockRing", "itemSunBlockRing")
    }

    @SideOnly(Side.CLIENT)
    fun initClient()
    {
        registerItemModel(itemTest)
        if(Loader.isModLoaded("Baubles"))
        registerItemModel(itemSunBlockRing)
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