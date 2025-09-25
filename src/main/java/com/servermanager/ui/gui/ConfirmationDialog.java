package com.servermanager.ui.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class ConfirmationDialog extends Screen {
    private final Screen parent;
    private final String command;
    private final Runnable onConfirm;
    private final int dialogWidth = 200;
    private final int dialogHeight = 100;

    public ConfirmationDialog(Screen parent, String command, Runnable onConfirm) {
        super(Component.translatable("gui.servermanager.confirm.title"));
        this.parent = parent;
        this.command = command;
        this.onConfirm = onConfirm;
    }

    @Override
    protected void init() {
        int dialogX = (this.width - dialogWidth) / 2;
        int dialogY = (this.height - dialogHeight) / 2;

        // Confirm button
        this.addRenderableWidget(Button.builder(
                Component.translatable("gui.servermanager.confirm.yes"),
                button -> {
                    onConfirm.run();
                    this.minecraft.setScreen(parent);
                })
                .bounds(dialogX + 20, dialogY + 60, 60, 20)
                .build());

        // Cancel button
        this.addRenderableWidget(Button.builder(
                Component.translatable("gui.servermanager.confirm.no"),
                button -> this.minecraft.setScreen(parent))
                .bounds(dialogX + 120, dialogY + 60, 60, 20)
                .build());
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        // Render dark overlay
        guiGraphics.fill(0, 0, this.width, this.height, 0x80000000);

        int dialogX = (this.width - dialogWidth) / 2;
        int dialogY = (this.height - dialogHeight) / 2;

        // Render dialog background
        guiGraphics.fill(dialogX, dialogY, dialogX + dialogWidth, dialogY + dialogHeight, 0xFF2C2C2C);
        guiGraphics.fill(dialogX + 1, dialogY + 1, dialogX + dialogWidth - 1, dialogY + dialogHeight - 1, 0xFF1E1E1E);

        // Render title
        Component title = Component.translatable("gui.servermanager.confirm.dangerous");
        int titleWidth = this.font.width(title);
        guiGraphics.drawString(this.font, title, dialogX + (dialogWidth - titleWidth) / 2, dialogY + 10, 0xFFFFFF);

        // Render command
        String displayCommand = command.length() > 25 ? command.substring(0, 22) + "..." : command;
        Component commandText = Component.literal("/" + displayCommand);
        int commandWidth = this.font.width(commandText);
        guiGraphics.drawString(this.font, commandText, dialogX + (dialogWidth - commandWidth) / 2, dialogY + 30, 0xFFAA00);

        super.render(guiGraphics, mouseX, mouseY, partialTick);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}