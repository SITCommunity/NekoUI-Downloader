package org.brokenedtz.nekoui;

import org.brokenedtz.nekoui.core.Constants;

import net.fabricmc.api.ClientModInitializer;

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
 