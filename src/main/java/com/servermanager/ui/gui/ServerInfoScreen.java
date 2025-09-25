package com.servermanager.ui.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class ServerInfoScreen extends Screen {
    private static final int GUI_WIDTH = 300;
    private static final int GUI_HEIGHT = 200;
    
    private final Screen parent;
    private int leftPos;
    private int topPos;

    public ServerInfoScreen(Screen parent) {
        super(Component.translatable("gui.servermanager.server_info.title"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        super.init();
        
        this.leftPos = (this.width - GUI_WIDTH) / 2;
        this.topPos = (this.height - GUI_HEIGHT) / 2;

        // Back button
        Button backButton = Button.builder(
                Component.translatable("gui.servermanager.back"),
                button -> this.minecraft.setScreen(parent)
        ).bounds(leftPos + 110, topPos + 160, 80, 20).build();
        this.addRenderableWidget(backButton);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        
        // Draw background panel
        guiGraphics.fill(leftPos, topPos, leftPos + GUI_WIDTH, topPos + GUI_HEIGHT, 0xC0101010);
        guiGraphics.fill(leftPos + 1, topPos + 1, leftPos + GUI_WIDTH - 1, topPos + GUI_HEIGHT - 1, 0xC0C0C0C0);
        
        // Draw title
        guiGraphics.drawCenteredString(this.font, this.title, this.width / 2, topPos + 10, 0xFFFFFF);
        
        // Draw server information
        int yOffset = 40;
        if (this.minecraft.getCurrentServer() != null) {
            String serverName = this.minecraft.getCurrentServer().name;
            String serverIP = this.minecraft.getCurrentServer().ip;
            
            guiGraphics.drawString(this.font, Component.translatable("gui.servermanager.server_name", serverName), 
                    leftPos + 20, topPos + yOffset, 0xFFFFFF);
            yOffset += 15;
            
            guiGraphics.drawString(this.font, Component.translatable("gui.servermanager.server_ip", serverIP), 
                    leftPos + 20, topPos + yOffset, 0xFFFFFF);
            yOffset += 15;
        } else {
            guiGraphics.drawString(this.font, Component.translatable("gui.servermanager.singleplayer"), 
                    leftPos + 20, topPos + yOffset, 0xFFFFFF);
            yOffset += 15;
        }
        
        if (this.minecraft.player != null) {
            // 使用getDisplayName()获取正确的玩家显示名称，避免编码问题
            Component playerNameComponent = this.minecraft.player.getDisplayName();
            String playerName = playerNameComponent != null ? 
                playerNameComponent.getString() : 
                this.minecraft.player.getName().getString();
            
            guiGraphics.drawString(this.font, Component.translatable("gui.servermanager.player_name", playerName), 
                    leftPos + 20, topPos + yOffset, 0xFFFFFF);
            yOffset += 15;
            
            int playerLevel = this.minecraft.player.experienceLevel;
            guiGraphics.drawString(this.font, Component.translatable("gui.servermanager.player_level", playerLevel), 
                    leftPos + 20, topPos + yOffset, 0xFFFFFF);
            yOffset += 15;
        }
        
        super.render(guiGraphics, mouseX, mouseY, partialTick);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}