package org.brokenedtz.nekoui;

import net.fabricmc.api.ClientModInitializer;
import org.brokenedtz.nekoui.core.Constants;

public class NekoUIFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        try {
            Constants.GAME_LOADER = "fabric";
            NekoUI.init();
        } catch (Throwable ignored) {
        }
    }
}
 