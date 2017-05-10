package actioninja.burningsun

import actioninja.burningsun.potion.PotionRegistry
import net.minecraft.inventory.EntityEquipmentSlot
import net.minecraft.potion.PotionEffect
import net.minecraft.util.DamageSource
import net.minecraftforge.event.entity.player.PlayerEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent

/**
 * Created by actioninja on 5/4/17.
 */

class BurningSunEventHandler
{

    @SubscribeEvent
    fun onPlayerRespawn(event:PlayerEvent.Clone)
    {
        if (event.isWasDeath && BurningSunConfig.spawnWithSunBlock)
        {
            event.entityPlayer.addPotionEffect(PotionEffect(PotionRegistry.sunBlock, BurningSunConfig.spawnWithSunBlockLength * 20))
        }
    }

    @SubscribeEvent
    fun playerTickEvent(event:TickEvent.PlayerTickEvent)
    {
        if (BurningSunConfig.activeDimensionsAsInts.contains(event.player.dimension))
        {
            if (event.player.world.isDaytime && event.player.world.canSeeSky(event.player.position) && !event.player.isCreative && !event.player.isPotionActive(PotionRegistry.sunBlock))
            {
                var flag = true
                var itemstack = event.player.getItemStackFromSlot(EntityEquipmentSlot.HEAD)

                if (itemstack != null && BurningSunConfig.helmetsBlockSun)
                {
                    if (itemstack.isItemStackDamageable && BurningSunConfig.helmetsTakeDamage)
                    {
                        itemstack.itemDamage = itemstack.itemDamage + 1 * BurningSunConfig.helmetDamageMultiplier
                        if (itemstack.itemDamage >= itemstack.maxDamage)
                        {
                            event.player.renderBrokenItemStack(itemstack)
                            event.player.setItemStackToSlot(EntityEquipmentSlot.HEAD, null)
                        }
                    }
                    flag = false
                }

                if (flag)
                {
                    var dimString = getStringOfDimRange(event.player.dimension)
                    if (dimString != "none")
                    {
                        if (BurningSunConfig.dimensionConfigSettings[dimString]!!.burnInSun)
                            event.player.setFire(8)
                        if (BurningSunConfig.dimensionConfigSettings[dimString]!!.hyperLethal)
                            event.player.attackEntityFrom(DamageSource.generic, BurningSunConfig.dimensionConfigSettings[dimString]!!.hyperLethalDamage.toFloat())
                    } else
                        BurningSun.log.error("Just attempted to access data for a dimension not in active dimensions!  Report this to the author!")
                }
            }
        }

    }
}

fun getStringOfDimRange(inputInt:Int):String
{
    for ((key, value) in BurningSunConfig.dimensionConfigSettings)
    {
        if (key.contains("-"))
        {
            var splitKey = key.split("-")
            var lowDim = splitKey[0].toInt()
            var highDim = splitKey[1].toInt()
            if (inputInt in lowDim..highDim)
                return key
        } else
        {
            if (key == inputInt.toString())
                return key
        }
    }
    return "none"
}

