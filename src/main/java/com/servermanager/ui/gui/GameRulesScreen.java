package com.servermanager.ui.gui;

import com.servermanager.ui.config.CustomButtonConfig;
import com.servermanager.ui.util.CommandUtils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;

public class GameRulesScreen extends Screen {
    private static final int GUI_WIDTH = 400;
    private static final int GUI_HEIGHT = 250; // 减少高度
    
    private final Screen parent;
    private int leftPos;
    private int topPos;
    
    // 自定义按钮相关
    private final List<CustomButtonConfig.ButtonData> customButtons = CustomButtonConfig.getGameRulesButtons();
    private int currentPage = 0;
    private static final int BUTTONS_PER_PAGE = 5; // 每页最多5个按钮 (1行 x 5列)

    public GameRulesScreen(Screen parent) {
        super(Component.translatable("gui.servermanager.gamerules.title"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        super.init();
        
        this.leftPos = (this.width - GUI_WIDTH) / 2;
        this.topPos = (this.height - GUI_HEIGHT) / 2;

        // Back button
        this.addRenderableWidget(Button.builder(
                Component.translatable("gui.servermanager.back"),
                button -> this.minecraft.setScreen(this.parent))
                .bounds(leftPos + 20, topPos + 40, 80, 20)
                .build());
        
        // 配置按钮
        this.addRenderableWidget(Button.builder(
                Component.literal("配置按钮"),
                button -> this.minecraft.setScreen(new ButtonConfigScreen(this, "gamerules")))
                .bounds(leftPos + 110, topPos + 40, 80, 20)
                .build());
        
        // 添加自定义按钮 (5个一行，支持分页)
        int buttonWidth = 70;
        int buttonHeight = 20;
        int buttonSpacing = 5;
        int startX = leftPos + 20;
        int startY = topPos + 80;
        
        // 计算当前页的按钮
        List<CustomButtonConfig.ButtonData> enabledButtons = new ArrayList<>();
        for (CustomButtonConfig.ButtonData buttonData : customButtons) {
            if (buttonData.isEnabled() && !buttonData.isEmpty()) {
                enabledButtons.add(buttonData);
            }
        }
        
        int totalPages = (int) Math.ceil((double) enabledButtons.size() / BUTTONS_PER_PAGE);
        int startIndex = currentPage * BUTTONS_PER_PAGE;
        int endIndex = Math.min(startIndex + BUTTONS_PER_PAGE, enabledButtons.size());
        
        // 显示当前页的按钮
        for (int i = startIndex; i < endIndex; i++) {
            CustomButtonConfig.ButtonData buttonData = enabledButtons.get(i);
            int buttonIndex = i - startIndex;
            int x = startX + buttonIndex * (buttonWidth + buttonSpacing);
            int y = startY;
            
            Button customButton = Button.builder(
                    Component.literal(buttonData.getName()),
                    button -> {
                        if (!buttonData.getCommand().isEmpty()) {
                            CommandUtils.sendCommandToServer(buttonData.getCommand());
                        }
                    }
            ).bounds(x, y, buttonWidth, buttonHeight).build();
            this.addRenderableWidget(customButton);
        }
        
        // 添加分页按钮
        if (totalPages > 1) {
            // 上一页按钮
            if (currentPage > 0) {
                this.addRenderableWidget(Button.builder(
                        Component.literal("上一页"),
                        button -> {
                            currentPage--;
                            this.clearWidgets();
                            this.init();
                        })
                        .bounds(leftPos + 20, topPos + 200, 60, 20)
                        .build());
            }
            
            // 页码显示
            Component pageInfo = Component.literal((currentPage + 1) + "/" + totalPages);
            
            // 下一页按钮
            if (currentPage < totalPages - 1) {
                this.addRenderableWidget(Button.builder(
                        Component.literal("下一页"),
                        button -> {
                            currentPage++;
                            this.clearWidgets();
                            this.init();
                        })
                        .bounds(leftPos + 90, topPos + 200, 60, 20)
                        .build());
            }
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        
        // Draw title
        guiGraphics.drawCenteredString(this.font, this.title, this.width / 2, topPos + 10, 0xFFFFFF);
        
        // 显示页码信息
        if (customButtons.size() > BUTTONS_PER_PAGE) {
            int totalPages = (customButtons.size() + BUTTONS_PER_PAGE - 1) / BUTTONS_PER_PAGE;
            String pageInfo = String.format("第 %d/%d 页", currentPage + 1, totalPages);
            guiGraphics.drawCenteredString(this.font, Component.literal(pageInfo), leftPos + GUI_WIDTH / 2, topPos + 230, 0xFFFFFF);
        }
        
        super.render(guiGraphics, mouseX, mouseY, partialTick);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}