package actioninja.burningsun.item

import actioninja.burningsun.potion.PotionRegistry
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.potion.PotionEffect
import net.minecraft.util.ActionResult
import net.minecraft.util.EnumHand
import net.minecraft.world.World

/**
 * Created by actioninja on 5/7/17.
 */

class ItemTest(): Item()
{
    init{
        this.maxStackSize = 1
        this.creativeTab = CreativeTabs.MISC
    }

    override fun onItemRightClick(itemStackIn: ItemStack?, worldIn: World?, playerIn: EntityPlayer?, hand: EnumHand?): ActionResult<ItemStack> {

        playerIn?.addPotionEffect(PotionEffect(PotionRegistry.sunBlock, 400))

        return super.onItemRightClick(itemStackIn, worldIn, playerIn, hand)
    }
}