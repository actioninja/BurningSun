package actioninja.burningsun.item

import actioninja.burningsun.potion.PotionRegistry
import baubles.api.BaubleType
import baubles.api.IBauble
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.EntityLivingBase
import net.minecraft.init.SoundEvents
import net.minecraft.item.EnumRarity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.potion.PotionEffect

/**
 * Created by actioninja on 5/10/17.
 */

class ItemUnbreakableSunBlockRing : Item(), IBauble
{
    init{
        this.maxStackSize = 1
        this.maxDamage = 0
        this.creativeTab = CreativeTabs.TOOLS
    }

    override fun getBaubleType(item:ItemStack):BaubleType
    {
        return BaubleType.RING
    }

    override fun onWornTick(itemstack:ItemStack?, player:EntityLivingBase?)
    {
        if(player!!.ticksExisted % 39 == 0)
            player.addPotionEffect(PotionEffect(PotionRegistry.sunBlock, 40, 0, false, false))
    }

    override fun hasEffect(stack:ItemStack?):Boolean
    {
        return true
    }

    override fun getRarity(stack:ItemStack?):EnumRarity
    {
        return EnumRarity.EPIC
    }

    override fun onEquipped(itemstack:ItemStack?, player:EntityLivingBase?)
    {
        player!!.playSound(SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 0.75F, 1.9F)
    }

    override fun onUnequipped(itemstack:ItemStack?, player:EntityLivingBase?)
    {
        player!!.playSound(SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 0.75F, 2.0F)
    }

}