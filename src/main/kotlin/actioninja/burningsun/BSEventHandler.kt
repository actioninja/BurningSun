package actioninja.burningsun

import net.minecraft.entity.Entity
import net.minecraft.inventory.EntityEquipmentSlot
import net.minecraft.item.ItemStack
import net.minecraft.util.DamageSource
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent
import org.apache.logging.log4j.Logger

/**
 * Created by actioninja on 5/4/17.
 */

class BSEventHandler {
    @SubscribeEvent
    fun playerTickEvent(event: TickEvent.PlayerTickEvent)
    {
        if(event.player.world.isDaytime && event.player.world.canSeeSky(event.player.position) && !event.player.isCreative)
        {
            var flag = true
            var itemstack = event.player.getItemStackFromSlot(EntityEquipmentSlot.HEAD)

            if(itemstack != null && Config.BSConfig.Player.helmetsBlockSun)
            {
                if(itemstack.isItemStackDamageable && Config.BSConfig.Player.helmetsTakeDamage) {
                    itemstack.itemDamage = itemstack.itemDamage + 1 * Config.BSConfig.Player.helmetDamageMultiplier
                    if (itemstack.itemDamage >= itemstack.maxDamage) {
                        event.player.renderBrokenItemStack(itemstack)
                        event.player.setItemStackToSlot(EntityEquipmentSlot.HEAD, null)
                    }
                }
                flag = false
            }

            if(flag)
            {
                if(Config.BSConfig.Player.burnInSun)
                    event.player.setFire(8)
                if(Config.BSConfig.Player.hyperLethal)
                    event.player.attackEntityFrom(DamageSource.generic, Config.BSConfig.Player.hyperLethalDamage.toFloat())
            }
        }
    }
}