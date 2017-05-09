package actioninja.burningsun

import actioninja.burningsun.potion.PotionRegistry
import net.minecraft.entity.Entity
import net.minecraft.inventory.EntityEquipmentSlot
import net.minecraft.item.ItemStack
import net.minecraft.potion.PotionEffect
import net.minecraft.util.DamageSource
import net.minecraftforge.event.entity.player.PlayerEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent
import org.apache.logging.log4j.Logger

/**
 * Created by actioninja on 5/4/17.
 */

class BSEventHandler {


    @SubscribeEvent
    fun onPlayerRespawn(event: PlayerEvent.Clone) {
        if (event.isWasDeath && BurningSunConfig.BSConfig.Player.spawnWithSunBlock) {
            event.entityPlayer.addPotionEffect(PotionEffect(PotionRegistry.sunBlock, BurningSunConfig.BSConfig.Player.spawnWithSunBlockLength * 20))
        }
    }

    @SubscribeEvent
    fun playerTickEvent(event: TickEvent.PlayerTickEvent) {
        if (event.player.world.isDaytime && event.player.world.canSeeSky(event.player.position) && !event.player.isCreative && !event.player.isPotionActive(PotionRegistry.sunBlock)) {
            var flag = true
            var itemstack = event.player.getItemStackFromSlot(EntityEquipmentSlot.HEAD)

            if (itemstack != null && BurningSunConfig.BSConfig.Player.helmetsBlockSun) {
                if (itemstack.isItemStackDamageable && BurningSunConfig.BSConfig.Player.helmetsTakeDamage) {
                    itemstack.itemDamage = itemstack.itemDamage + 1 * BurningSunConfig.BSConfig.Player.helmetDamageMultiplier
                    if (itemstack.itemDamage >= itemstack.maxDamage) {
                        event.player.renderBrokenItemStack(itemstack)
                        event.player.setItemStackToSlot(EntityEquipmentSlot.HEAD, null)
                    }
                }
                flag = false
            }

            if (flag) {
                if (BurningSunConfig.BSConfig.Player.burnInSun)
                    event.player.setFire(8)
                if (BurningSunConfig.BSConfig.Player.hyperLethal)
                    event.player.attackEntityFrom(DamageSource.generic, BurningSunConfig.BSConfig.Player.hyperLethalDamage.toFloat())
            }
        }

        if (event.player.isPotionActive(PotionRegistry.sunBlock)) {
            event.player.extinguish()
        }
    }
}