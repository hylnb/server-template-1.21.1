package com.servermanager.ui.gui;

import com.servermanager.ui.Config;
import com.servermanager.ui.util.CommandUtils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Checkbox;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.GameRules;

import java.util.HashMap;
import java.util.Map;

public class GameRulesScreen extends Screen {
    private static final int GUI_WIDTH = 300;
    private static final int GUI_HEIGHT = 240;
    
    private final Screen parent;
    private int leftPos;
    private int topPos;
    private int scrollOffset = 0;
    
    // Store original values and current widgets
    private final Map<String, Object> originalValues = new HashMap<>();
    private final Map<String, Object> currentValues = new HashMap<>();
    private final Map<String, Object> widgets = new HashMap<>();

    public GameRulesScreen(Screen parent) {
        super(Component.translatable("gui.servermanager.gamerules.title"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        super.init();
        
        this.leftPos = (this.width - GUI_WIDTH) / 2;
        this.topPos = (this.height - GUI_HEIGHT) / 2;

        // Common game rules
        createBooleanGameRule("doMobSpawning", topPos + 40);
        createBooleanGameRule("keepInventory", topPos + 65);
        createBooleanGameRule("doDaylightCycle", topPos + 90);
        createBooleanGameRule("doWeatherCycle", topPos + 115);
        createIntegerGameRule("randomTickSpeed", topPos + 140);
        createIntegerGameRule("spawnRadius", topPos + 165);
        createIntegerGameRule("playersSleepingPercentage", topPos + 190);

        // Apply button
        this.addRenderableWidget(Button.builder(
                Component.translatable("gui.servermanager.apply"),
                button -> {
                    // Update current values from widgets before applying
                    updateCurrentValuesFromWidgets();
                    
                    // Only apply changed game rules
                    for (String ruleName : originalValues.keySet()) {
                        Object originalValue = originalValues.get(ruleName);
                        Object currentValue = currentValues.get(ruleName);
                        
                        if (!originalValue.equals(currentValue)) {
                            String command = CommandUtils.getGameRuleCommand(ruleName, currentValue.toString());
                            CommandUtils.sendCommandToServer(command);
                        }
                    }
                    this.minecraft.setScreen(this.parent);
                })
                .bounds(leftPos + 20, topPos + 200, 100, 20)
                .build());

        // Back button
        this.addRenderableWidget(Button.builder(
                Component.translatable("gui.servermanager.back"),
                button -> this.minecraft.setScreen(this.parent))
                .bounds(leftPos + 130, topPos + 200, 100, 20)
                .build());
    }

    private void createBooleanGameRule(String ruleName, int yPos) {
        // Get current game rule value (for now, default to false)
        boolean currentValue = false; // TODO: Get actual current value from server
        originalValues.put(ruleName, currentValue);
        currentValues.put(ruleName, currentValue);
        
        Checkbox checkbox = Checkbox.builder(
                Component.translatable("gamerule." + ruleName),
                this.font
        ).pos(leftPos + 20, yPos).selected(currentValue).build();
        
        // Store widget reference
        widgets.put(ruleName, checkbox);
        
        this.addRenderableWidget(checkbox);
    }

    private void createIntegerGameRule(String ruleName, int yPos) {
        // Get current game rule value (for now, default to 3 for randomTickSpeed, 10 for spawnRadius)
        int defaultValue = ruleName.equals("randomTickSpeed") ? 3 : 10;
        originalValues.put(ruleName, defaultValue);
        currentValues.put(ruleName, defaultValue);
        
        EditBox editBox = new EditBox(this.font, leftPos + 150, yPos, 100, 20, 
                Component.translatable("gamerule." + ruleName));
        editBox.setValue(String.valueOf(defaultValue));
        editBox.setResponder(value -> {
            try {
                int intValue = Integer.parseInt(value);
                currentValues.put(ruleName, intValue);
            } catch (NumberFormatException e) {
                // Invalid input, keep previous value
            }
        });
        
        // Store widget reference
        widgets.put(ruleName, editBox);
        
        this.addRenderableWidget(editBox);
    }
    
    private void updateCurrentValuesFromWidgets() {
        for (Map.Entry<String, Object> entry : widgets.entrySet()) {
            String ruleName = entry.getKey();
            Object widget = entry.getValue();
            
            if (widget instanceof Checkbox) {
                Checkbox checkbox = (Checkbox) widget;
                currentValues.put(ruleName, checkbox.selected());
            } else if (widget instanceof EditBox) {
                EditBox editBox = (EditBox) widget;
                try {
                    int value = Integer.parseInt(editBox.getValue());
                    currentValues.put(ruleName, value);
                } catch (NumberFormatException e) {
                    // Keep original value if parsing fails
                }
            }
        }
    }

    // Remove the old applyGameRules method since we now handle it in the button click

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        
        // Draw title
        guiGraphics.drawCenteredString(this.font, this.title, this.width / 2, topPos + 10, 0xFFFFFF);
        
        // Draw labels for integer game rules at correct positions
        // randomTickSpeed at topPos + 140, spawnRadius at topPos + 165, playersSleepingPercentage at topPos + 190
        if (originalValues.containsKey("randomTickSpeed")) {
            Component randomTickLabel = Component.translatable("gamerule.randomTickSpeed");
            guiGraphics.drawString(this.font, randomTickLabel, leftPos + 20, topPos + 145, 0xFFFFFF);
        }
        
        if (originalValues.containsKey("spawnRadius")) {
            Component spawnRadiusLabel = Component.translatable("gamerule.spawnRadius");
            guiGraphics.drawString(this.font, spawnRadiusLabel, leftPos + 20, topPos + 170, 0xFFFFFF);
        }
        
        if (originalValues.containsKey("playersSleepingPercentage")) {
            Component playersSleepingLabel = Component.translatable("gamerule.playersSleepingPercentage");
            guiGraphics.drawString(this.font, playersSleepingLabel, leftPos + 20, topPos + 195, 0xFFFFFF);
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}