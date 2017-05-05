package actioninja.burningsun

import net.minecraft.entity.Entity
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
            if(Config.BSConfig.Player.burnInSun)
            event.player.setFire(8)
            if(Config.BSConfig.Player.hyperLethal)
            event.player.attackEntityFrom(DamageSource.generic, Config.BSConfig.Player.hypterLethalDamage.toFloat())
        }
    }
}