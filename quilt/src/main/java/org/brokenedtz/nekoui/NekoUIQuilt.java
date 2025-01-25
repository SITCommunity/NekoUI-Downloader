package org.brokenedtz.nekoui;

import org.brokenedtz.nekoui.core.Constants;

import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;

public class NekoUIQuilt implements ModInitializer {
    @Override
    public void onInitialize(ModContainer mod) {
        try {
            Constants.GAME_LOADER = "quilt";
            NekoUI.init();
        } catch (Throwable ignored) {
        }
    }
}