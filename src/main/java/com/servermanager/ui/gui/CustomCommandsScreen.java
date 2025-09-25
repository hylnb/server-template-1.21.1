package com.servermanager.ui.gui;

import com.servermanager.ui.Config;
import com.servermanager.ui.util.CommandUtils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;

public class CustomCommandsScreen extends Screen {
    private static final int GUI_WIDTH = 350;
    private static final int GUI_HEIGHT = 280;
    
    private final Screen parent;
    private int leftPos;
    private int topPos;
    
    private EditBox commandInput;
    private final List<String> commandHistory = new ArrayList<>();
    private int historyIndex = -1;

    public CustomCommandsScreen(Screen parent) {
        super(Component.translatable("gui.servermanager.custom_commands.title"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        super.init();
        
        this.leftPos = (this.width - GUI_WIDTH) / 2;
        this.topPos = (this.height - GUI_HEIGHT) / 2;

        // Command input box
        this.commandInput = new EditBox(this.font, leftPos + 20, topPos + 40, GUI_WIDTH - 120, 20, 
                Component.translatable("gui.servermanager.command_input"));
        this.commandInput.setMaxLength(256);
        this.commandInput.setHint(Component.translatable("gui.servermanager.command_input.hint"));
        
        if (Config.SHOW_TOOLTIPS.get()) {
            this.commandInput.setTooltip(Tooltip.create(Component.translatable("gui.servermanager.command_input.tooltip")));
        }
        
        this.addRenderableWidget(this.commandInput);
        this.setInitialFocus(this.commandInput);

        // Execute button
        Button executeButton = Button.builder(
                Component.translatable("gui.servermanager.execute"),
                button -> executeCommand()
        ).bounds(leftPos + GUI_WIDTH - 80, topPos + 40, 60, 20).build();
        
        if (Config.SHOW_TOOLTIPS.get()) {
            executeButton.setTooltip(Tooltip.create(Component.translatable("gui.servermanager.execute.tooltip")));
        }
        this.addRenderableWidget(executeButton);

        // Quick command buttons
        createQuickCommandButton("time set day", topPos + 80, "gui.servermanager.time_day");
        createQuickCommandButton("time set night", topPos + 105, "gui.servermanager.time_night");
        createQuickCommandButton("weather clear", topPos + 130, "gui.servermanager.weather_clear");
        createQuickCommandButton("weather rain", topPos + 155, "gui.servermanager.weather_rain");
        createQuickCommandButton("gamemode creative", topPos + 180, "gui.servermanager.gamemode_creative");
        createQuickCommandButton("gamemode survival", topPos + 205, "gui.servermanager.gamemode_survival");

        // Back button
        Button backButton = Button.builder(
                Component.translatable("gui.servermanager.back"),
                button -> this.minecraft.setScreen(parent)
        ).bounds(leftPos + 20, topPos + 240, 80, 20).build();
        this.addRenderableWidget(backButton);

        // Clear History button
        Button clearHistoryButton = Button.builder(
                Component.translatable("gui.servermanager.clear_history"),
                button -> clearHistory()
        ).bounds(leftPos + 250, topPos + 240, 80, 20).build();
        
        if (Config.SHOW_TOOLTIPS.get()) {
            clearHistoryButton.setTooltip(Tooltip.create(Component.translatable("gui.servermanager.clear_history.tooltip")));
        }
        this.addRenderableWidget(clearHistoryButton);
    }

    private void createQuickCommandButton(String command, int yPos, String translationKey) {
        Button button = Button.builder(
                Component.translatable(translationKey),
                btn -> executeSpecificCommand(command)
        ).bounds(leftPos + 20, yPos, 150, 20).build();
        
        if (Config.SHOW_TOOLTIPS.get()) {
            button.setTooltip(Tooltip.create(Component.translatable(translationKey + ".tooltip")));
        }
        this.addRenderableWidget(button);
    }

    private void executeCommand() {
        String command = this.commandInput.getValue().trim();
        if (command.isEmpty()) return;

        // Add to history
        if (!commandHistory.contains(command)) {
            commandHistory.add(0, command);
            if (commandHistory.size() > 20) { // Keep only last 20 commands
                commandHistory.remove(commandHistory.size() - 1);
            }
        }
        
        // Check if it's a dangerous command and confirmation is enabled
        if (Config.CONFIRM_DANGEROUS_COMMANDS.get() && CommandUtils.isDangerousCommand(command)) {
            this.minecraft.setScreen(new ConfirmationDialog(this, command, () -> {
                CommandUtils.sendCommandToServer(command);
                this.minecraft.setScreen(this);
            }));
        } else {
            CommandUtils.sendCommandToServer(command);
        }
        
        this.commandInput.setValue("");
        historyIndex = -1;
    }

    private void executeSpecificCommand(String command) {
        CommandUtils.sendCommandToServer(command);
        
        // Show feedback
        this.minecraft.player.displayClientMessage(
            Component.translatable("gui.servermanager.command_executed", command), 
            true
        );
    }

    private void clearHistory() {
        commandHistory.clear();
        historyIndex = -1;
        
        if (this.minecraft.player != null) {
            this.minecraft.player.sendSystemMessage(
                Component.translatable("gui.servermanager.history_cleared")
            );
        }
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        // Handle up/down arrows for command history
        if (keyCode == 264 && !commandHistory.isEmpty()) { // Down arrow
            historyIndex = Math.min(historyIndex + 1, commandHistory.size() - 1);
            if (historyIndex >= 0) {
                this.commandInput.setValue(commandHistory.get(historyIndex));
            }
            return true;
        } else if (keyCode == 265 && !commandHistory.isEmpty()) { // Up arrow
            if (historyIndex == -1) historyIndex = 0;
            else historyIndex = Math.max(historyIndex - 1, 0);
            this.commandInput.setValue(commandHistory.get(historyIndex));
            return true;
        } else if (keyCode == 257) { // Enter key
            executeCommand();
            return true;
        }
        
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        
        // Draw background panel
        guiGraphics.fill(leftPos, topPos, leftPos + GUI_WIDTH, topPos + GUI_HEIGHT, 0xC0101010);
        guiGraphics.fill(leftPos + 1, topPos + 1, leftPos + GUI_WIDTH - 1, topPos + GUI_HEIGHT - 1, 0xC0C0C0C0);
        
        // Draw title
        guiGraphics.drawCenteredString(this.font, this.title, this.width / 2, topPos + 10, 0xFFFFFF);
        
        // Draw quick commands label BEFORE the buttons (at topPos + 65)
        guiGraphics.drawString(this.font, Component.translatable("gui.servermanager.quick_commands"), 
                leftPos + 20, topPos + 65, 0xFFFFFF);
        
        super.render(guiGraphics, mouseX, mouseY, partialTick);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}