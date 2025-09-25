package com.servermanager.ui.gui;

import com.servermanager.ui.Config;
import com.servermanager.ui.util.CommandUtils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class PlayerManagementScreen extends Screen {
    private static final int GUI_WIDTH = 320;
    private static final int GUI_HEIGHT = 240;
    
    private final Screen parent;
    private int leftPos;
    private int topPos;
    private EditBox playerNameInput;

    public PlayerManagementScreen(Screen parent) {
        super(Component.translatable("gui.servermanager.player_management.title"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        super.init();
        
        this.leftPos = (this.width - GUI_WIDTH) / 2;
        this.topPos = (this.height - GUI_HEIGHT) / 2;

        // Player name input
        this.playerNameInput = new EditBox(this.font, leftPos + 20, topPos + 40, 200, 20, 
                Component.translatable("gui.servermanager.player_name"));
        this.playerNameInput.setMaxLength(16);
        this.playerNameInput.setHint(Component.translatable("gui.servermanager.player_name.hint"));
        
        if (Config.SHOW_TOOLTIPS.get()) {
            this.playerNameInput.setTooltip(Tooltip.create(Component.translatable("gui.servermanager.player_name.tooltip")));
        }
        
        this.addRenderableWidget(this.playerNameInput);
        this.setInitialFocus(this.playerNameInput);

        // Teleport to player
        this.addRenderableWidget(Button.builder(
                Component.translatable("gui.servermanager.teleport_to"),
                button -> {
                    String playerName = this.playerNameInput.getValue().trim();
                    if (playerName.isEmpty()) {
                        this.minecraft.player.displayClientMessage(
                            Component.translatable("gui.servermanager.no_player_name"), 
                            true
                        );
                        return;
                    }
                    executePlayerCommand(CommandUtils.getPlayerCommand("tp_to", playerName));
                })
                .bounds(leftPos + 20, topPos + 80, 100, 20)
                .tooltip(Config.SHOW_TOOLTIPS.get() ? 
                    net.minecraft.client.gui.components.Tooltip.create(
                        Component.translatable("gui.servermanager.teleport_to.tooltip")
                    ) : null)
                .build());

        // Teleport player to me
        this.addRenderableWidget(Button.builder(
                Component.translatable("gui.servermanager.teleport_here"),
                button -> {
                    String playerName = this.playerNameInput.getValue().trim();
                    if (playerName.isEmpty()) {
                        this.minecraft.player.displayClientMessage(
                            Component.translatable("gui.servermanager.no_player_name"), 
                            true
                        );
                        return;
                    }
                    executePlayerCommand(CommandUtils.getPlayerCommand("tp_here", playerName));
                })
                .bounds(leftPos + 130, topPos + 80, 100, 20)
                .tooltip(Config.SHOW_TOOLTIPS.get() ? 
                    net.minecraft.client.gui.components.Tooltip.create(
                        Component.translatable("gui.servermanager.teleport_here.tooltip")
                    ) : null)
                .build());

        // Give OP
        this.addRenderableWidget(Button.builder(
                Component.translatable("gui.servermanager.give_op"),
                button -> {
                    String playerName = this.playerNameInput.getValue().trim();
                    if (playerName.isEmpty()) {
                        this.minecraft.player.displayClientMessage(
                            Component.translatable("gui.servermanager.no_player_name"), 
                            true
                        );
                        return;
                    }
                    executePlayerCommand(CommandUtils.getPlayerCommand("op", playerName));
                })
                .bounds(leftPos + 20, topPos + 110, 100, 20)
                .tooltip(Config.SHOW_TOOLTIPS.get() ? 
                    net.minecraft.client.gui.components.Tooltip.create(
                        Component.translatable("gui.servermanager.give_op.tooltip")
                    ) : null)
                .build());

        // Remove OP
        this.addRenderableWidget(Button.builder(
                Component.translatable("gui.servermanager.remove_op"),
                button -> {
                    String playerName = this.playerNameInput.getValue().trim();
                    if (playerName.isEmpty()) {
                        this.minecraft.player.displayClientMessage(
                            Component.translatable("gui.servermanager.no_player_name"), 
                            true
                        );
                        return;
                    }
                    executePlayerCommand(CommandUtils.getPlayerCommand("deop", playerName));
                })
                .bounds(leftPos + 130, topPos + 110, 100, 20)
                .tooltip(Config.SHOW_TOOLTIPS.get() ? 
                    net.minecraft.client.gui.components.Tooltip.create(
                        Component.translatable("gui.servermanager.remove_op.tooltip")
                    ) : null)
                .build());

        // Kick player
        this.addRenderableWidget(Button.builder(
                Component.translatable("gui.servermanager.kick_player"),
                button -> {
                    String playerName = this.playerNameInput.getValue().trim();
                    if (playerName.isEmpty()) {
                        this.minecraft.player.displayClientMessage(
                            Component.translatable("gui.servermanager.no_player_name"), 
                            true
                        );
                        return;
                    }
                    executePlayerCommand(CommandUtils.getPlayerCommand("kick", playerName));
                })
                .bounds(leftPos + 20, topPos + 140, 100, 20)
                .tooltip(Config.SHOW_TOOLTIPS.get() ? 
                    net.minecraft.client.gui.components.Tooltip.create(
                        Component.translatable("gui.servermanager.kick_player.tooltip")
                    ) : null)
                .build());

        // Ban player
        this.addRenderableWidget(Button.builder(
                Component.translatable("gui.servermanager.ban_player"),
                button -> {
                    String playerName = this.playerNameInput.getValue().trim();
                    if (playerName.isEmpty()) {
                        this.minecraft.player.displayClientMessage(
                            Component.translatable("gui.servermanager.no_player_name"), 
                            true
                        );
                        return;
                    }
                    executePlayerCommand(CommandUtils.getPlayerCommand("ban", playerName));
                })
                .bounds(leftPos + 130, topPos + 140, 100, 20)
                .tooltip(Config.SHOW_TOOLTIPS.get() ? 
                    net.minecraft.client.gui.components.Tooltip.create(
                        Component.translatable("gui.servermanager.ban_player.tooltip")
                    ) : null)
                .build());

        // Add to whitelist
        this.addRenderableWidget(Button.builder(
                Component.translatable("gui.servermanager.whitelist_add"),
                button -> {
                    String playerName = this.playerNameInput.getValue().trim();
                    if (playerName.isEmpty()) {
                        this.minecraft.player.displayClientMessage(
                            Component.translatable("gui.servermanager.no_player_name"), 
                            true
                        );
                        return;
                    }
                    executePlayerCommand(CommandUtils.getPlayerCommand("whitelist_add", playerName));
                })
                .bounds(leftPos + 20, topPos + 170, 100, 20)
                .tooltip(Config.SHOW_TOOLTIPS.get() ? 
                    net.minecraft.client.gui.components.Tooltip.create(
                        Component.translatable("gui.servermanager.whitelist_add.tooltip")
                    ) : null)
                .build());

        // Remove from whitelist
        this.addRenderableWidget(Button.builder(
                Component.translatable("gui.servermanager.whitelist_remove"),
                button -> {
                    String playerName = this.playerNameInput.getValue().trim();
                    if (playerName.isEmpty()) {
                        this.minecraft.player.displayClientMessage(
                            Component.translatable("gui.servermanager.no_player_name"), 
                            true
                        );
                        return;
                    }
                    executePlayerCommand(CommandUtils.getPlayerCommand("whitelist_remove", playerName));
                })
                .bounds(leftPos + 130, topPos + 170, 100, 20)
                .tooltip(Config.SHOW_TOOLTIPS.get() ? 
                    net.minecraft.client.gui.components.Tooltip.create(
                        Component.translatable("gui.servermanager.whitelist_remove.tooltip")
                    ) : null)
                .build());

        // Back button
        this.addRenderableWidget(Button.builder(
                Component.translatable("gui.servermanager.back"),
                button -> this.minecraft.setScreen(this.parent))
                .bounds(leftPos + 75, topPos + 200, 100, 20)
                .build());
    }

    private void executePlayerCommand(String command) {
        if (Config.CONFIRM_DANGEROUS_COMMANDS.get() && CommandUtils.isDangerousCommand(command)) {
            this.minecraft.setScreen(new ConfirmationDialog(this, command, () -> {
                CommandUtils.sendCommandToServer(command);
                this.minecraft.setScreen(this);
            }));
        } else {
            CommandUtils.sendCommandToServer(command);
        }
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