package com.servermanager.ui.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;

import java.util.Arrays;
import java.util.List;

public class CommandUtils {
    
    // 危险命令列表
    private static final List<String> DANGEROUS_COMMANDS = Arrays.asList(
        "ban", "kick", "deop", "stop", "restart", "whitelist remove",
        "kill", "clear", "gamemode", "difficulty", "weather", "time set"
    );
    
    /**
     * 判断命令是否为危险命令
     */
    public static boolean isDangerousCommand(String command) {
        String lowerCommand = command.toLowerCase().trim();
        
        return DANGEROUS_COMMANDS.stream().anyMatch(dangerous -> 
            lowerCommand.startsWith(dangerous) || 
            lowerCommand.contains(" " + dangerous)
        );
    }
    
    /**
     * 发送命令到服务器
     */
    public static void sendCommandToServer(String command) {
        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;
        
        if (player == null) return;
        
        // 确保命令以 / 开头
        String fullCommand = command.startsWith("/") ? command : "/" + command;
        
        // 发送命令
        player.connection.sendCommand(command);
    }
    
    /**
     * 获取格式化的游戏规则命令
     */
    public static String getGameRuleCommand(String rule, Object value) {
        return "gamerule " + rule + " " + value.toString();
    }
    
    /**
     * 获取玩家管理命令
     */
    public static String getPlayerCommand(String action, String playerName) {
        return getPlayerCommand(action, playerName, null);
    }
    
    /**
     * 获取玩家管理命令（带额外参数）
     */
    public static String getPlayerCommand(String action, String playerName, String extra) {
        switch (action.toLowerCase()) {
            case "tp_to":
                return "tp @s " + playerName;
            case "tp_here":
                return "tp " + playerName + " @s";
            case "op":
                return "op " + playerName;
            case "deop":
                return "deop " + playerName;
            case "kick":
                return "kick " + playerName + (extra != null ? " " + extra : "");
            case "ban":
                return "ban " + playerName + (extra != null ? " " + extra : "");
            case "whitelist_add":
                return "whitelist add " + playerName;
            case "whitelist_remove":
                return "whitelist remove " + playerName;
            default:
                return action + " " + playerName;
        }
    }
}