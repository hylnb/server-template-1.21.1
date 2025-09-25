package com.servermanager.ui.gui;

import com.servermanager.ui.Config;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class ServerManagerScreen extends Screen {
    private static final ResourceLocation BACKGROUND_TEXTURE = ResourceLocation.fromNamespaceAndPath("servermanager", "textures/gui/server_manager_bg.png");
    private static final int GUI_WIDTH = 256;
    private static final int GUI_HEIGHT = 200;
    
    private int leftPos;
    private int topPos;

    public ServerManagerScreen() {
        super(Component.translatable("gui.servermanager.title"));
    }

    @Override
    protected void init() {
        super.init();
        
        this.leftPos = (this.width - GUI_WIDTH) / 2;
        this.topPos = (this.height - GUI_HEIGHT) / 2;

        // Game Rules button
        Button gameRulesButton = Button.builder(
                Component.translatable("gui.servermanager.gamerules"),
                button -> this.minecraft.setScreen(new GameRulesScreen(this))
        ).bounds(leftPos + 20, topPos + 40, 100, 20).build();
        
        if (Config.SHOW_TOOLTIPS.get()) {
            gameRulesButton.setTooltip(Tooltip.create(Component.translatable("gui.servermanager.gamerules.tooltip")));
        }
        this.addRenderableWidget(gameRulesButton);

        // Custom Commands button
        Button customCommandsButton = Button.builder(
                Component.translatable("gui.servermanager.custom_commands"),
                button -> this.minecraft.setScreen(new CustomCommandsScreen(this))
        ).bounds(leftPos + 136, topPos + 40, 100, 20).build();
        
        if (Config.SHOW_TOOLTIPS.get()) {
            customCommandsButton.setTooltip(Tooltip.create(Component.translatable("gui.servermanager.custom_commands.tooltip")));
        }
        this.addRenderableWidget(customCommandsButton);

        // Server Info button
        Button serverInfoButton = Button.builder(
                Component.translatable("gui.servermanager.server_info"),
                button -> this.minecraft.setScreen(new ServerInfoScreen(this))
        ).bounds(leftPos + 20, topPos + 70, 100, 20).build();
        
        if (Config.SHOW_TOOLTIPS.get()) {
            serverInfoButton.setTooltip(Tooltip.create(Component.translatable("gui.servermanager.server_info.tooltip")));
        }
        this.addRenderableWidget(serverInfoButton);

        // Player Management button
        Button playerManagementButton = Button.builder(
                Component.translatable("gui.servermanager.player_management"),
                button -> this.minecraft.setScreen(new PlayerManagementScreen(this))
        ).bounds(leftPos + 136, topPos + 70, 100, 20).build();
        
        if (Config.SHOW_TOOLTIPS.get()) {
            playerManagementButton.setTooltip(Tooltip.create(Component.translatable("gui.servermanager.player_management.tooltip")));
        }
        this.addRenderableWidget(playerManagementButton);

        // Close button
        Button closeButton = Button.builder(
                Component.translatable("gui.servermanager.close"),
                button -> this.onClose()
        ).bounds(leftPos + 78, topPos + 160, 100, 20).build();
        this.addRenderableWidget(closeButton);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        
        // Draw background panel
        guiGraphics.fill(leftPos, topPos, leftPos + GUI_WIDTH, topPos + GUI_HEIGHT, 0xC0101010);
        guiGraphics.fill(leftPos + 1, topPos + 1, leftPos + GUI_WIDTH - 1, topPos + GUI_HEIGHT - 1, 0xC0C0C0C0);
        
        // Draw title
        guiGraphics.drawCenteredString(this.font, this.title, this.width / 2, topPos + 10, 0xFFFFFF);
        
        super.render(guiGraphics, mouseX, mouseY, partialTick);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}