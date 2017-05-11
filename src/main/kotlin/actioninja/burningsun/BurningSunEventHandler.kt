package actioninja.burningsun

import actioninja.burningsun.item.ItemRegistry
import actioninja.burningsun.potion.PotionRegistry
import baubles.api.BaublesApi
import net.minecraft.inventory.EntityEquipmentSlot
import net.minecraft.potion.PotionEffect
import net.minecraft.util.DamageSource
import net.minecraftforge.event.entity.player.PlayerEvent
import net.minecraftforge.fml.common.Loader
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
            event.entityPlayer.addPotionEffect(PotionEffect(PotionRegistry.sunBlock, BurningSunConfig.spawnWithSunBlockLength * 20, 0, false, true))
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
            } else if (Loader.isModLoaded("Baubles") && BurningSunConfig.ringTakesDamageWhenInSun)
            {
                val baublesInventory = BaublesApi.getBaublesHandler(event.player)
                val ringSlot1 = baublesInventory.getStackInSlot(1)
                val ringSlot2 = baublesInventory.getStackInSlot(2)
                var targetSlot = 0
                var flag = false
                if (ringSlot1 != null)
                {
                    if (ringSlot1.item == ItemRegistry.itemSunBlockRing)
                    {
                        flag = true
                        targetSlot = 1
                    }
                } else if (ringSlot2 != null)
                {
                    if (ringSlot2.item == ItemRegistry.itemSunBlockRing)
                    {
                        flag = true
                        targetSlot = 2
                    }
                }
                if (event.player.world.isDaytime && event.player.world.canSeeSky(event.player.position) && !event.player.isCreative && flag)
                {
                    val item = baublesInventory.getStackInSlot(targetSlot)
                    item.itemDamage = item.itemDamage + 1
                    baublesInventory.setStackInSlot(targetSlot, item)
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

