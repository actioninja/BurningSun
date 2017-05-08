package actioninja.burningsun.potion

import actioninja.burningsun.BurningSun
import net.minecraft.client.Minecraft
import net.minecraft.entity.EntityLivingBase
import net.minecraft.potion.Potion
import net.minecraft.potion.PotionEffect
import net.minecraft.util.ResourceLocation
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

/**
 * Created by actioninja on 5/4/17.
 */

class PotionBurningSun(name: String, texture: ResourceLocation, badEffect: Boolean, potionColor: Int, iconIndexX: Int, iconIndexY: Int) : Potion(badEffect, potionColor){
    init {
        this.setPotionName(name)
        this.setIconIndex(iconIndexX, iconIndexY)
    }
    companion object{
        val texture = ResourceLocation(BurningSun.MODID, "textures/misc/potions.png")
    }
    override fun shouldRenderInvText(effect: PotionEffect):Boolean {
       return true
    }

    fun apply(entity: EntityLivingBase, duration: Int, level: Int = 0): PotionEffect {
        val effect = PotionEffect(this, duration, level, false, false)
        entity.addPotionEffect(effect)
        return effect
    }

    fun getLevel(entity: EntityLivingBase): Int{
        val effect = entity.getActivePotionEffect(this)
        if(effect != null)
            return effect.amplifier
        return 0
    }

    override fun shouldRender(effect: PotionEffect?): Boolean {
        return true
    }

    @SideOnly(Side.CLIENT)
    override fun getStatusIconIndex(): Int {
        Minecraft.getMinecraft().renderEngine.bindTexture(texture)
        return super.getStatusIconIndex()
    }
}