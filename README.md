
# 服务器管理器 UI 模组

一个为 Minecraft 1.21.1 设计的客户端模组，提供直观的图形界面来管理服务器命令和游戏规则。

## 📋 模组信息

- **模组名称**: Server Manager UI
- **版本**: 1.0.0
- **支持版本**: Minecraft 1.21.1
- **模组加载器**: NeoForge
- **许可证**: MIT
- **类型**: 客户端模组

## ✨ 主要功能

### 🎮 游戏规则管理
- 图形化界面管理常用游戏规则
- 预设常用规则按钮：
  - 设置白天/夜晚
  - 保留物品栏 (keepInventory)
  - 调整睡觉比例 (playersSleepingPercentage)
  - 物品随机刻修改 (randomTickSpeed)
- 支持自定义游戏规则命令

### 🔧 自定义命令管理
- 可配置的自定义命令按钮（每个界面最多10个）
- 预设实用命令：
  - 传送到出生点
  - 给予物品
  - 快速命令执行
- 支持命令历史记录

### 📊 服务器信息查看
- 实时显示服务器状态
- 服务器性能监控命令
- 世界保存和管理功能

### 👥 玩家管理
- 玩家状态管理
- 批量玩家操作
- 广播消息功能

### ⚙️ 按钮配置系统
- 完全可自定义的按钮配置
- 支持启用/禁用按钮
- 分页显示（每页2个按钮）
- 配置保存和重置功能

## 🚀 安装方法

### 前置要求
- Minecraft 1.21.1
- NeoForge (最新版本)
- Java 21

### 安装步骤
1. 下载并安装 NeoForge 1.21.1
2. 将模组文件放入 `.minecraft/mods` 文件夹
3. 启动游戏

## 🎯 使用指南

### 打开界面
- **默认快捷键**: `G` 键
- 或通过模组配置修改快捷键

### 界面导航
1. **主界面**: 显示四个主要功能模块
2. **游戏规则**: 管理服务器游戏规则
3. **自定义命令**: 执行预设或自定义命令
4. **服务器信息**: 查看服务器状态和信息
5. **玩家管理**: 管理在线玩家

### 按钮配置
1. 在任意功能界面点击"配置按钮"
2. 编辑按钮名称和对应命令
3. 启用或禁用特定按钮
4. 保存配置

## ⚙️ 配置选项

模组提供以下配置选项：

```toml
# 是否启用GUI快捷键 (默认: true)
enableGuiKeybind = true

# 是否显示工具提示 (默认: true)
showTooltips = true

# 是否对危险命令显示确认对话框 (默认: true)
confirmDangerousCommands = true
```

## 🔧 开发信息

### 项目结构
```
src/main/java/com/servermanager/ui/
├── Config.java                    # 模组配置
├── ServerManagerMod.java          # 主模组类
├── config/
│   ├── CustomButtonConfig.java    # 按钮配置管理
│   └── ButtonData.java           # 按钮数据结构
├── gui/
│   ├── ServerManagerScreen.java   # 主界面
│   ├── GameRulesScreen.java      # 游戏规则界面
│   ├── CustomCommandsScreen.java # 自定义命令界面
│   ├── ServerInfoScreen.java     # 服务器信息界面
│   ├── PlayerManagementScreen.java # 玩家管理界面
│   └── ButtonConfigScreen.java   # 按钮配置界面
└── util/
    └── CommandUtils.java         # 命令工具类
```

### 本地化支持
- 中文 (zh_cn)
- 英文 (en_us)

## 🛠️ 构建项目

### 开发环境设置
1. 克隆项目仓库
2. 使用 IntelliJ IDEA 或 Eclipse 打开项目
3. 运行 `./gradlew build` 构建项目

### 常用 Gradle 任务
```bash
# 构建项目
./gradlew build

# 运行客户端测试
./gradlew runClient

# 清理构建文件
./gradlew clean

# 刷新依赖
./gradlew --refresh-dependencies
```

## 📝 更新日志

### v1.0.0
- 初始版本发布
- 实现基础的服务器管理界面
- 支持游戏规则、自定义命令、服务器信息和玩家管理
- 可配置的按钮系统
- 中英文本地化支持

## 🤝 贡献指南

欢迎提交 Issue 和 Pull Request！

### 开发规范
- 遵循现有代码风格
- 添加适当的注释
- 确保本地化文件完整
- 测试新功能

## 📄 许可证

本项目采用 MIT 许可证。详见 [LICENSE](TEMPLATE_LICENSE.txt) 文件。

## 🔗 相关链接

- [NeoForged 官方文档](https://docs.neoforged.net/)
- [NeoForged Discord](https://discord.neoforged.net/)
- [Minecraft 模组开发指南](https://docs.neoforged.net/docs/gettingstarted/)

## 📞 支持

如果您遇到问题或有建议，请：
1. 查看现有的 Issues
2. 创建新的 Issue 描述问题
3. 提供详细的错误信息和日志

---

**注意**: 这是一个客户端模组，主要用于简化服务器管理操作。请确保您有适当的服务器权限来执行相关命令。

