package com.servermanager.ui.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import net.neoforged.fml.loading.FMLPaths;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 配置管理器类
 * 负责自定义按钮配置的持久化存储和读取
 */
public class ConfigManager {
    
    private static final String CONFIG_DIR = "servermanager";
    private static final String CONFIG_FILE = "button_config.json";
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    
    /**
     * 配置数据结构
     */
    public static class ConfigData {
        private Map<String, List<CustomButtonConfig.ButtonData>> screenConfigs;
        
        public ConfigData() {
            this.screenConfigs = new HashMap<>();
        }
        
        public Map<String, List<CustomButtonConfig.ButtonData>> getScreenConfigs() {
            return screenConfigs;
        }
        
        public void setScreenConfigs(Map<String, List<CustomButtonConfig.ButtonData>> screenConfigs) {
            this.screenConfigs = screenConfigs;
        }
    }
    
    /**
     * 获取配置文件路径
     */
    private static Path getConfigPath() {
        Path configDir = FMLPaths.CONFIGDIR.get().resolve(CONFIG_DIR);
        try {
            Files.createDirectories(configDir);
        } catch (IOException e) {
            System.err.println("无法创建配置目录: " + e.getMessage());
        }
        return configDir.resolve(CONFIG_FILE);
    }
    
    /**
     * 保存配置到文件
     */
    public static void saveConfig() {
        try {
            ConfigData configData = new ConfigData();
            Map<String, List<CustomButtonConfig.ButtonData>> screenConfigs = new HashMap<>();
            
            // 收集所有界面的按钮配置
            screenConfigs.put("gamerules", CustomButtonConfig.getGameRulesButtons());
            screenConfigs.put("customcommands", CustomButtonConfig.getCustomCommandsButtons());
            screenConfigs.put("serverinfo", CustomButtonConfig.getServerInfoButtons());
            screenConfigs.put("playermanagement", CustomButtonConfig.getPlayerManagementButtons());
            
            configData.setScreenConfigs(screenConfigs);
            
            // 写入JSON文件
            Path configPath = getConfigPath();
            try (FileWriter writer = new FileWriter(configPath.toFile())) {
                GSON.toJson(configData, writer);
                System.out.println("配置已保存到: " + configPath);
            }
            
        } catch (IOException e) {
            System.err.println("保存配置失败: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * 从文件加载配置
     */
    public static void loadConfig() {
        Path configPath = getConfigPath();
        
        if (!Files.exists(configPath)) {
            System.out.println("配置文件不存在，使用默认配置");
            return;
        }
        
        try (FileReader reader = new FileReader(configPath.toFile())) {
            Type configType = new TypeToken<ConfigData>(){}.getType();
            ConfigData configData = GSON.fromJson(reader, configType);
            
            if (configData != null && configData.getScreenConfigs() != null) {
                // 应用加载的配置
                applyLoadedConfig(configData.getScreenConfigs());
                System.out.println("配置已从文件加载: " + configPath);
            }
            
        } catch (IOException e) {
            System.err.println("加载配置失败: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("解析配置文件失败: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * 应用加载的配置数据
     */
    private static void applyLoadedConfig(Map<String, List<CustomButtonConfig.ButtonData>> screenConfigs) {
        // 应用游戏规则界面配置
        applyScreenConfig("gamerules", screenConfigs.get("gamerules"), 
                         CustomButtonConfig.getGameRulesButtons());
        
        // 应用自定义命令界面配置
        applyScreenConfig("customcommands", screenConfigs.get("customcommands"), 
                         CustomButtonConfig.getCustomCommandsButtons());
        
        // 应用服务器信息界面配置
        applyScreenConfig("serverinfo", screenConfigs.get("serverinfo"), 
                         CustomButtonConfig.getServerInfoButtons());
        
        // 应用玩家管理界面配置
        applyScreenConfig("playermanagement", screenConfigs.get("playermanagement"), 
                         CustomButtonConfig.getPlayerManagementButtons());
    }
    
    /**
     * 应用单个界面的配置
     */
    private static void applyScreenConfig(String screenName, 
                                        List<CustomButtonConfig.ButtonData> savedConfig,
                                        List<CustomButtonConfig.ButtonData> currentConfig) {
        if (savedConfig == null || currentConfig == null) {
            return;
        }
        
        // 确保不超过当前配置的按钮数量
        int maxButtons = Math.min(savedConfig.size(), currentConfig.size());
        
        for (int i = 0; i < maxButtons; i++) {
            CustomButtonConfig.ButtonData savedButton = savedConfig.get(i);
            CustomButtonConfig.ButtonData currentButton = currentConfig.get(i);
            
            if (savedButton != null && currentButton != null) {
                currentButton.setName(savedButton.getName());
                currentButton.setCommand(savedButton.getCommand());
                currentButton.setEnabled(savedButton.isEnabled());
            }
        }
        
        System.out.println("已应用 " + screenName + " 界面的配置，共 " + maxButtons + " 个按钮");
    }
    
    /**
     * 删除配置文件
     */
    public static void deleteConfig() {
        try {
            Path configPath = getConfigPath();
            if (Files.exists(configPath)) {
                Files.delete(configPath);
                System.out.println("配置文件已删除: " + configPath);
            }
        } catch (IOException e) {
            System.err.println("删除配置文件失败: " + e.getMessage());
        }
    }
    
    /**
     * 检查配置文件是否存在
     */
    public static boolean configExists() {
        return Files.exists(getConfigPath());
    }
}