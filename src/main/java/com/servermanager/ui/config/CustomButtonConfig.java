package com.servermanager.ui.config;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义按钮配置类
 * 用于存储和管理每个界面的自定义命令按钮
 * 每个命令集支持可配置数量的自定义命令（默认10个）
 */
public class CustomButtonConfig {
    
    // 每个命令集的按钮数量配置（可修改）
    public static final int BUTTONS_PER_COMMAND_SET = 10;
    
    /**
     * 单个自定义按钮的配置
     */
    public static class ButtonData {
        private String name;
        private String command;
        private boolean enabled;
        
        public ButtonData() {
            this.name = "";
            this.command = "";
            this.enabled = false;
        }
        
        public ButtonData(String name, String command, boolean enabled) {
            this.name = name;
            this.command = command;
            this.enabled = enabled;
        }
        
        public String getName() {
            return name;
        }
        
        public void setName(String name) {
            this.name = name;
        }
        
        public String getCommand() {
            return command;
        }
        
        public void setCommand(String command) {
            this.command = command;
        }
        
        public boolean isEnabled() {
            return enabled;
        }
        
        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }
        
        public boolean isEmpty() {
            return name.trim().isEmpty() || command.trim().isEmpty();
        }
    }
    
    // 每个界面的按钮配置
    private static final List<ButtonData> gameRulesButtons = new ArrayList<>();
    private static final List<ButtonData> customCommandsButtons = new ArrayList<>();
    private static final List<ButtonData> serverInfoButtons = new ArrayList<>();
    private static final List<ButtonData> playerManagementButtons = new ArrayList<>();
    
    static {
        // 初始化每个界面的按钮（数量由BUTTONS_PER_COMMAND_SET配置）
        initializeGameRulesButtons();
        initializeCustomCommandsButtons();
        initializeServerInfoButtons();
        initializePlayerManagementButtons();
        
        // 加载保存的配置
        ConfigManager.loadConfig();
    }
    
    private static void initializeGameRulesButtons() {
        // 初始化指定数量的空按钮，用户可以自定义配置
        for (int i = 0; i < BUTTONS_PER_COMMAND_SET; i++) {
            gameRulesButtons.add(new ButtonData("自定义按钮" + (i+1), "", false));
        }
        
        // 提供一些默认示例（可选）
        if (BUTTONS_PER_COMMAND_SET > 0) gameRulesButtons.get(0).setName("设置白天");
        if (BUTTONS_PER_COMMAND_SET > 0) gameRulesButtons.get(0).setCommand("time set day");
        if (BUTTONS_PER_COMMAND_SET > 0) gameRulesButtons.get(0).setEnabled(true);
        
        if (BUTTONS_PER_COMMAND_SET > 1) gameRulesButtons.get(1).setName("设置夜晚");
        if (BUTTONS_PER_COMMAND_SET > 1) gameRulesButtons.get(1).setCommand("time set night");
        if (BUTTONS_PER_COMMAND_SET > 1) gameRulesButtons.get(1).setEnabled(true);
        
        // 第三个按钮：保留物品栏
        if (BUTTONS_PER_COMMAND_SET > 2) gameRulesButtons.get(2).setName("保留物品栏");
        if (BUTTONS_PER_COMMAND_SET > 2) gameRulesButtons.get(2).setCommand("gamerule keepInventory true");
        if (BUTTONS_PER_COMMAND_SET > 2) gameRulesButtons.get(2).setEnabled(true);
        
        // 第四个按钮：调整睡觉比例
        if (BUTTONS_PER_COMMAND_SET > 3) gameRulesButtons.get(3).setName("睡觉比例100%");
        if (BUTTONS_PER_COMMAND_SET > 3) gameRulesButtons.get(3).setCommand("gamerule playersSleepingPercentage 1");
        if (BUTTONS_PER_COMMAND_SET > 3) gameRulesButtons.get(3).setEnabled(true);
        
        // 第五个按钮：物品随机刻修改
        if (BUTTONS_PER_COMMAND_SET > 4) gameRulesButtons.get(4).setName("随机刻x10");
        if (BUTTONS_PER_COMMAND_SET > 4) gameRulesButtons.get(4).setCommand("gamerule randomTickSpeed 30");
        if (BUTTONS_PER_COMMAND_SET > 4) gameRulesButtons.get(4).setEnabled(true);
    }
    
    private static void initializeCustomCommandsButtons() {
        // 初始化指定数量的空按钮，用户可以自定义配置
        for (int i = 0; i < BUTTONS_PER_COMMAND_SET; i++) {
            customCommandsButtons.add(new ButtonData("自定义命令" + (i+1), "", false));
        }
        
        // 提供一些默认示例（可选）
        if (BUTTONS_PER_COMMAND_SET > 0) customCommandsButtons.get(0).setName("传送spawn");
        if (BUTTONS_PER_COMMAND_SET > 0) customCommandsButtons.get(0).setCommand("tp @s 0 64 0");
        if (BUTTONS_PER_COMMAND_SET > 0) customCommandsButtons.get(0).setEnabled(true);
        
        if (BUTTONS_PER_COMMAND_SET > 1) customCommandsButtons.get(1).setName("给予钻石");
        if (BUTTONS_PER_COMMAND_SET > 1) customCommandsButtons.get(1).setCommand("give @s diamond 64");
        if (BUTTONS_PER_COMMAND_SET > 1) customCommandsButtons.get(1).setEnabled(true);
    }
    
    private static void initializeServerInfoButtons() {
        // 初始化指定数量的空按钮，用户可以自定义配置
        for (int i = 0; i < BUTTONS_PER_COMMAND_SET; i++) {
            serverInfoButtons.add(new ButtonData("服务器命令" + (i+1), "", false));
        }
        
        // 提供一些默认示例（可选）
        if (BUTTONS_PER_COMMAND_SET > 0) serverInfoButtons.get(0).setName("查看TPS");
        if (BUTTONS_PER_COMMAND_SET > 0) serverInfoButtons.get(0).setCommand("forge tps");
        if (BUTTONS_PER_COMMAND_SET > 0) serverInfoButtons.get(0).setEnabled(true);
        
        if (BUTTONS_PER_COMMAND_SET > 1) serverInfoButtons.get(1).setName("保存世界");
        if (BUTTONS_PER_COMMAND_SET > 1) serverInfoButtons.get(1).setCommand("save-all");
        if (BUTTONS_PER_COMMAND_SET > 1) serverInfoButtons.get(1).setEnabled(true);
    }
    
    private static void initializePlayerManagementButtons() {
        // 初始化指定数量的空按钮，用户可以自定义配置
        for (int i = 0; i < BUTTONS_PER_COMMAND_SET; i++) {
            playerManagementButtons.add(new ButtonData("玩家管理" + (i+1), "", false));
        }
        
        // 提供一些默认示例（可选）
        if (BUTTONS_PER_COMMAND_SET > 0) playerManagementButtons.get(0).setName("治疗所有");
        if (BUTTONS_PER_COMMAND_SET > 0) playerManagementButtons.get(0).setCommand("effect give @a instant_health 1 10");
        if (BUTTONS_PER_COMMAND_SET > 0) playerManagementButtons.get(0).setEnabled(true);
        
        if (BUTTONS_PER_COMMAND_SET > 1) playerManagementButtons.get(1).setName("广播消息");
        if (BUTTONS_PER_COMMAND_SET > 1) playerManagementButtons.get(1).setCommand("say 欢迎来到服务器！");
        if (BUTTONS_PER_COMMAND_SET > 1) playerManagementButtons.get(1).setEnabled(true);
    }
    
    public static List<ButtonData> getGameRulesButtons() {
        return gameRulesButtons;
    }
    
    public static List<ButtonData> getCustomCommandsButtons() {
        return customCommandsButtons;
    }
    
    public static List<ButtonData> getServerInfoButtons() {
        return serverInfoButtons;
    }
    
    public static List<ButtonData> getPlayerManagementButtons() {
        return playerManagementButtons;
    }
    
    /**
     * 设置按钮配置并保存到文件
     */
    public static void setButtonData(String screenType, int index, String name, String command, boolean enabled) {
        if (index < 0 || index >= BUTTONS_PER_COMMAND_SET) return;
        
        List<ButtonData> buttons = getButtonsForScreen(screenType);
        if (buttons != null && index < buttons.size()) {
            ButtonData button = buttons.get(index);
            button.setName(name);
            button.setCommand(command);
            button.setEnabled(enabled);
            
            // 保存配置到文件
            ConfigManager.saveConfig();
        }
    }
    
    /**
     * 获取指定界面的按钮列表
     */
    public static List<ButtonData> getButtonsForScreen(String screenType) {
        switch (screenType.toLowerCase()) {
            case "gamerules":
                return gameRulesButtons;
            case "customcommands":
                return customCommandsButtons;
            case "serverinfo":
                return serverInfoButtons;
            case "playermanagement":
                return playerManagementButtons;
            default:
                return null;
        }
    }
    
    /**
     * 重置所有按钮配置并保存
     */
    public static void resetAllButtons() {
        resetButtons(gameRulesButtons);
        resetButtons(customCommandsButtons);
        resetButtons(serverInfoButtons);
        resetButtons(playerManagementButtons);
        
        // 保存重置后的配置
        ConfigManager.saveConfig();
    }
    
    private static void resetButtons(List<ButtonData> buttons) {
        for (ButtonData button : buttons) {
            button.setName("");
            button.setCommand("");
            button.setEnabled(false);
        }
    }
}