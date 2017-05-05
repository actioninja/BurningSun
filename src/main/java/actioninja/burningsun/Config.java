package actioninja.burningsun;

/**
 * Created by actioninja on 5/2/17.
 */
public class Config {
    @net.minecraftforge.common.config.Config(modid = BurningSun.MODID)
    public static class BSConfig {

        public static Developer developer = new Developer();
        public static class Player {
            @net.minecraftforge.common.config.Config.Comment({"Makes you burn when in the sun in daylight.", "I'm not sure why you'd want to disable this, but it's here anyways"})
            public static boolean burnInSun = true;
            @net.minecraftforge.common.config.Config.Comment({"Makes going into sun hyper-lethal.", "Adds damage per tick, which is configurable"})
            public static boolean hyperLethal = false;
            public static int hypterLethalDamage = 10;

        }
        public static class Developer {
            @net.minecraftforge.common.config.Config.Comment({"Turns on a bunch of debug logging stuff.", "Don't enable this unless you're a developer or want large log files."})
            public static boolean debugLogging = true;
        }
    }
}
