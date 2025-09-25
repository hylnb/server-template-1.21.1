package com.servermanager.ui;

import net.neoforged.neoforge.common.ModConfigSpec;

// Configuration class for Server Manager UI mod
public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue ENABLE_GUI_KEYBIND = BUILDER
            .comment("Whether to enable the GUI keybind (default: G)")
            .define("enableGuiKeybind", true);

    public static final ModConfigSpec.BooleanValue SHOW_TOOLTIPS = BUILDER
            .comment("Whether to show tooltips in the GUI")
            .define("showTooltips", true);

    public static final ModConfigSpec.BooleanValue CONFIRM_DANGEROUS_COMMANDS = BUILDER
            .comment("Whether to show confirmation dialogs for dangerous commands")
            .define("confirmDangerousCommands", true);

    static final ModConfigSpec SPEC = BUILDER.build();
}