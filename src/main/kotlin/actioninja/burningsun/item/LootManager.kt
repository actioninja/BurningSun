package actioninja.burningsun.item

import actioninja.burningsun.BurningSun
import actioninja.burningsun.BurningSunConfig
import net.minecraft.world.storage.loot.LootEntryItem
import net.minecraft.world.storage.loot.RandomValueRange
import net.minecraft.world.storage.loot.conditions.LootCondition
import net.minecraft.world.storage.loot.conditions.RandomChance
import net.minecraft.world.storage.loot.functions.LootFunction
import net.minecraft.world.storage.loot.functions.SetCount
import net.minecraftforge.common.DungeonHooks
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.event.LootTableLoadEvent
import net.minecraftforge.fml.common.Mod

/**
 * Created by actioninja on 5/10/17.
 */

class LootManager
{
    @Mod.EventHandler
    fun initLoot(event: LootTableLoadEvent)
    {
        if(!event.name.resourceDomain.equals("minecraft") && !(event.name.resourcePath.contains("CHEST")))
            return

        var conditions = ArrayList<LootCondition>()
        conditions.add(RandomChance(0.10F))
        var lchance = arrayOf(conditions[0])

        var functions = ArrayList<LootFunction>()
        functions.add(SetCount(lchance, RandomValueRange(0F, 1F)))
        var lcount = arrayOf(functions[0])

        var ring = LootEntryItem(ItemRegistry.itemSunBlockRing, 100, 10, lcount, lchance, BurningSun.prependModId("ringLoot"))
        var unbreakableRing = LootEntryItem(ItemRegistry.itemUnbreakableSunBlockRing, 50, 5, lcount, lchance, BurningSun.prependModId("unbreakableRingLoot"))

        var main = event.table.getPool("main")
        if(BurningSunConfig.ringSpawnsInChests)
            main.addEntry(ring)
        if(BurningSunConfig.unbreakableRingSpawnsInChests)
            main.addEntry(unbreakableRing)
    }
}