package com.servermanager.ui.gui;

import com.servermanager.ui.config.CustomButtonConfig;
import com.servermanager.ui.config.ConfigManager;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 按钮配置界面
 * 用于编辑自定义命令按钮的名称和命令
 */
public class ButtonConfigScreen extends Screen {
    private final Screen parent;
    private final String screenType;
    private final List<CustomButtonConfig.ButtonData> buttons;
    
    private final List<EditBox> nameBoxes = new ArrayList<>();
    private final List<EditBox> commandBoxes = new ArrayList<>();
    private final List<Button> enableButtons = new ArrayList<>();
    
    private int currentPage = 0;
    private static final int BUTTONS_PER_PAGE = 2; // 一次显示2个按钮的配置，界面更加简洁
    
    public ButtonConfigScreen(Screen parent, String screenType) {
        super(Component.translatable("screen.servermanager.button_config"));
        this.parent = parent;
        this.screenType = screenType;
        this.buttons = CustomButtonConfig.getButtonsForScreen(screenType);
    }
    
    @Override
    protected void init() {
        super.init();
        
        nameBoxes.clear();
        commandBoxes.clear();
        enableButtons.clear();
        
        int startY = 80;
        int spacing = 70;
        
        // 计算当前页的按钮范围
        int startIndex = currentPage * BUTTONS_PER_PAGE;
        int endIndex = Math.min(startIndex + BUTTONS_PER_PAGE, buttons.size());
        
        for (int i = 0; i < BUTTONS_PER_PAGE; i++) {
            int buttonIndex = startIndex + i;
            
            if (buttonIndex >= buttons.size()) break;
            
            CustomButtonConfig.ButtonData buttonData = buttons.get(buttonIndex);
            int y = startY + i * spacing;
            
            // 按钮名称输入框
            EditBox nameBox = new EditBox(this.font, this.width / 2 - 150, y, 120, 20, Component.literal("Button Name"));
            nameBox.setValue(buttonData.getName());
            nameBox.setMaxLength(20);
            nameBoxes.add(nameBox);
            this.addRenderableWidget(nameBox);
            
            // 命令输入框
            EditBox commandBox = new EditBox(this.font, this.width / 2 - 20, y, 120, 20, Component.literal("Command"));
            commandBox.setValue(buttonData.getCommand());
            commandBox.setMaxLength(100);
            commandBoxes.add(commandBox);
            this.addRenderableWidget(commandBox);
            
            // 启用/禁用按钮
            final int finalButtonIndex = buttonIndex;
            Button enableButton = Button.builder(
                Component.literal(buttonData.isEnabled() ? "启用" : "禁用"),
                button -> {
                    CustomButtonConfig.ButtonData data = buttons.get(finalButtonIndex);
                    data.setEnabled(!data.isEnabled());
                    button.setMessage(Component.literal(data.isEnabled() ? "启用" : "禁用"));
                }
            ).bounds(this.width / 2 + 110, y, 50, 20).build();
            
            enableButtons.add(enableButton);
            this.addRenderableWidget(enableButton);
        }
        
        // 计算总页数
        int totalPages = (int) Math.ceil((double) buttons.size() / BUTTONS_PER_PAGE);
        
        // 翻页按钮 - 改进布局，使其更明显
        int buttonY = this.height - 100;
        
        // 上一页按钮
        if (currentPage > 0) {
            this.addRenderableWidget(Button.builder(
                Component.literal("上一页"),
                button -> {
                    currentPage--;
                    this.clearWidgets();
                    this.init();
                }
            ).bounds(this.width / 2 - 120, buttonY, 60, 20).build());
        }
        
        // 下一页按钮
        if (currentPage < totalPages - 1) {
            this.addRenderableWidget(Button.builder(
                Component.literal("下一页"),
                button -> {
                    currentPage++;
                    this.clearWidgets();
                    this.init();
                }
            ).bounds(this.width / 2 + 60, buttonY, 60, 20).build());
        }
        
        // 保存按钮
        this.addRenderableWidget(Button.builder(
            Component.translatable("gui.done"),
            button -> {
                saveChanges();
                this.minecraft.setScreen(parent);
            }
        ).bounds(this.width / 2 - 100, this.height - 40, 80, 20).build());
        
        // 取消按钮
        this.addRenderableWidget(Button.builder(
            Component.translatable("gui.cancel"),
            button -> this.minecraft.setScreen(parent)
        ).bounds(this.width / 2 + 20, this.height - 40, 80, 20).build());
        
        // 重置当前页按钮
        this.addRenderableWidget(Button.builder(
            Component.literal("重置当前页"),
            button -> {
                int resetStartIndex = currentPage * BUTTONS_PER_PAGE;
                int resetEndIndex = Math.min(resetStartIndex + BUTTONS_PER_PAGE, buttons.size());
                
                for (int i = resetStartIndex; i < resetEndIndex; i++) {
                    CustomButtonConfig.ButtonData buttonData = buttons.get(i);
                    buttonData.setName("");
                    buttonData.setCommand("");
                    buttonData.setEnabled(false);
                }
                
                // 保存重置后的配置
                ConfigManager.saveConfig();
                
                this.clearWidgets();
                this.init();
            }
        ).bounds(this.width / 2 - 40, this.height - 70, 80, 20).build());
    }
    
    private void saveChanges() {
        int startIndex = currentPage * BUTTONS_PER_PAGE;
        
        for (int i = 0; i < Math.min(nameBoxes.size(), commandBoxes.size()); i++) {
            int buttonIndex = startIndex + i;
            if (buttonIndex >= buttons.size()) break;
            
            CustomButtonConfig.ButtonData buttonData = buttons.get(buttonIndex);
            buttonData.setName(nameBoxes.get(i).getValue());
            buttonData.setCommand(commandBoxes.get(i).getValue());
        }
        
        // 保存配置到文件
        ConfigManager.saveConfig();
    }
    
    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        
        // 绘制标题
        guiGraphics.drawCenteredString(this.font, this.title, this.width / 2, 20, 0xFFFFFF);
        
        // 绘制页码信息
        int totalPages = (int) Math.ceil((double) buttons.size() / BUTTONS_PER_PAGE);
        String pageInfo = String.format("第 %d/%d 页", currentPage + 1, totalPages);
        guiGraphics.drawCenteredString(this.font, Component.literal(pageInfo), this.width / 2, 40, 0xFFFFFF);
        
        // 绘制列标题
        guiGraphics.drawString(this.font, "按钮名称", this.width / 2 - 150, 65, 0xFFFFFF);
        guiGraphics.drawString(this.font, "执行命令", this.width / 2 - 20, 65, 0xFFFFFF);
        guiGraphics.drawString(this.font, "状态", this.width / 2 + 110, 65, 0xFFFFFF);
        
        // 绘制按钮编号
        int startIndex = currentPage * BUTTONS_PER_PAGE;
        for (int i = 0; i < Math.min(BUTTONS_PER_PAGE, buttons.size() - startIndex); i++) {
            int y = 80 + i * 70;
            int buttonNumber = startIndex + i + 1;
            guiGraphics.drawString(this.font, "按钮 " + buttonNumber + ":", this.width / 2 - 200, y + 5, 0xFFFFFF);
        }
        
        super.render(guiGraphics, mouseX, mouseY, partialTick);
    }
    
    @Override
    public boolean isPauseScreen() {
        return false;
    }
}