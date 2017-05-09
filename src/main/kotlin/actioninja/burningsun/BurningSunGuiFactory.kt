/*package actioninja.burningsun

import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiScreen
import net.minecraft.client.resources.I18n
import net.minecraftforge.fml.client.IModGuiFactory
import net.minecraftforge.fml.client.config.DummyConfigElement
import net.minecraftforge.fml.client.config.GuiConfig
import net.minecraftforge.fml.client.config.IConfigElement



class BurningSunGuiFactory : IModGuiFactory
{
    override fun initialize(minecraftInstance:Minecraft?)
    {

    }

    override fun getHandlerFor(element:IModGuiFactory.RuntimeOptionCategoryElement?):IModGuiFactory.RuntimeOptionGuiHandler?
    {
        return null
    }

    override fun mainConfigGuiClass():Class<out GuiScreen>
    {
        return BurningSunConfigGui.class
    }

    override fun runtimeGuiCategories():MutableSet<IModGuiFactory.RuntimeOptionCategoryElement>?
    {
        return null
    }

    class BurningSunConfigGui(parentScreen: GuiScreen):GuiConfig(parentScreen, getConfigElements(), BurningSun.MODID, false, false, I18n.format("gui.burningSunConfiguration.mainTitle"))
    {

        fun getConfigElements():List<IConfigElement>
        {
            var list = mutableListOf<IConfigElement>()
            list.add(DummyConfigElement.DummyCategoryElement("mainCfg"))
        }
        companion object{

        }


    }
}
*/