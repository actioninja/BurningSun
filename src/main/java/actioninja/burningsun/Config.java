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
            public static int hyperLethalDamage = 10;
            @net.minecraftforge.common.config.Config.Comment({"Whether or not the hyper-lethal damage bypasses the normal damage cooldown","This is currently nonfunctional"})
            public static boolean hyperLethalBypassesDamageCooldown = true;
            @net.minecraftforge.common.config.Config.Comment({"Makes helmets block sunlight, like how they work on zombies and skeletons"})
            public static boolean helmetsBlockSun = false;
            @net.minecraftforge.common.config.Config.Comment({"Makes helmets take damage while blocking sunlight","This is a bit too fast right now."})
            public static boolean helmetsTakeDamage = true;
            @net.minecraftforge.common.config.Config.Comment({"Acts as a multiplier to the previous setting", "Integers only."})
            public static int helmetDamageMultiplier = 1;
            @net.minecraftforge.common.config.Config.Comment({"Spawn with \"Sun block\" status effect", "Leave this on unless you have a good reason, disabling it can make the game unplayable"})
            public static boolean spawnWithSunBlock = true;
            @net.minecraftforge.common.config.Config.Comment({"Length (in something (ticks?)) of the \"Sun Block\" effect after spawning"})
            public static int spawnWithSunBlockLength = 400;
        }
    }
}
