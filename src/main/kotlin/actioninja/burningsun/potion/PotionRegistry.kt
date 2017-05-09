package actioninja.burningsun.potion

import net.minecraft.potion.Potion
import net.minecraft.util.ResourceLocation
import net.minecraftforge.fml.common.registry.GameRegistry

/**
 * Created by actioninja on 5/5/17.
 */

object PotionRegistry {
    lateinit var sunBlock: Potion

    fun init() {
        sunBlock = registerPotion("Sun Block", ResourceLocation("sunBlock"), false, 0xFFFF66, 0, 0)
    }

    fun registerPotion(name: String, location: ResourceLocation, badEffect: Boolean, potionColor: Int, x: Int, y: Int): Potion {
        val potion: Potion = PotionBurningSun(name, location, badEffect, potionColor, x, y)
        GameRegistry.register(potion.setRegistryName(location.resourcePath))
        return potion
    }
}
