package actioninja.burningsun.item

import actioninja.burningsun.BurningSunConfig
import net.minecraft.init.Items
import net.minecraftforge.fml.common.registry.GameRegistry
import net.minecraftforge.oredict.ShapedOreRecipe

/**
 * Created by actioninja on 5/10/17.
 */

object RecipeManager
{
    fun initRecipes()
    {
        if (BurningSunConfig.ringCraftable)
            GameRegistry.addRecipe(ShapedOreRecipe(ItemRegistry.itemSunBlockRing,
                    "III",
                    "IMI",
                    "III", 'I', "ingotIron", 'M', Items.MAGMA_CREAM))
    }
}