package org.brokenedtz.nekoui;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.brokenedtz.nekoui.core.Constants;

@Mod(Constants.MOD_ID)
@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Bus.MOD, value = Dist.CLIENT)
public class NekoUIForge {
    public NekoUIForge() {
        if(FMLEnvironment.dist.isClient()) {
            Constants.GAME_LOADER = "forge";
            NekoUI.init();
        }
    }
}