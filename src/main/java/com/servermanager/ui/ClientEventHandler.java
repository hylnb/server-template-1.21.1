package com.servermanager.ui;

import com.servermanager.ui.gui.ServerManagerScreen;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import org.lwjgl.glfw.GLFW;
import net.minecraft.client.KeyMapping;

@EventBusSubscriber(modid = ServerManagerMod.MODID, value = Dist.CLIENT)
public class ClientEventHandler {
    
    public static final KeyMapping OPEN_GUI_KEY = new KeyMapping(
            "key.servermanager.open_gui",
            GLFW.GLFW_KEY_G,
            "key.categories.servermanager"
    );

    @SubscribeEvent
    public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(OPEN_GUI_KEY);
    }

    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        Minecraft minecraft = Minecraft.getInstance();
        
        if (minecraft.screen == null && OPEN_GUI_KEY.consumeClick()) {
            minecraft.setScreen(new ServerManagerScreen());
        }
    }
}