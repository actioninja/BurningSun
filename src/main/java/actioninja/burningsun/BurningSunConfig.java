package actioninja.burningsun;

import net.minecraftforge.common.config.Configuration;

/**
 * Created by actioninja on 5/2/17.
 */
public class BurningSunConfig {

    public static class Player {
        public static boolean burnInSun = true;
        public static boolean hyperLethal = false;
        public static int hyperLethalDamage = 10;
        public static boolean hyperLethalBypassesDamageCooldown = true;
        public static boolean helmetsBlockSun = false;
        public static boolean helmetsTakeDamage = true;
        public static int helmetDamageMultiplier = 1;
        public static boolean spawnWithSunBlock = true;
        public static int spawnWithSunBlockLength = 120;
    }


}
