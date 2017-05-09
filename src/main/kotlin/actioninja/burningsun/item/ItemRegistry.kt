package actioninja.burningsun.item

import actioninja.burningsun.BurningSun
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.item.Item
import net.minecraftforge.client.model.ModelLoader
import net.minecraftforge.fml.common.registry.GameRegistry
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

/**
 * Created by actioninja on 5/7/17.
 */

object ItemRegistry
{
    lateinit var itemTest:Item


    fun initCommon()
    {
        itemTest = registerItem(ItemTest(), "itemTest", "itemTest")
    }

    @SideOnly(Side.CLIENT)
    fun initClient()
    {
        registerItemModel(itemTest)
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