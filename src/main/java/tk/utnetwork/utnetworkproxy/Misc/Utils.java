package tk.utnetwork.utnetworkproxy.Misc;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.config.ServerInfo;
import tk.utnetwork.utnetworkproxy.UTNetworkProxy;

public class Utils {
    static UTNetworkProxy plugin;
    public Utils(UTNetworkProxy plugin) {
        this.plugin = plugin;
    }
    public static BaseComponent chat(String textToTranslate) {
        return new ComponentBuilder((plugin.getConfigManager().getConfig().getString("prefix") + textToTranslate)
                .replaceAll("%p", getPrimaryColour())
                .replaceAll("%s", getSecondaryColour())
                .replaceAll("%t", getTertiaryColour())
                .replaceAll("&", "§"))
                .getCurrentComponent();
    }

    public static void log(String message) {
        ProxyServer.getInstance().getConsole().sendMessage(new ComponentBuilder(message.replaceAll("%p", getPrimaryColour())
                .replaceAll("%s", getSecondaryColour())
                .replaceAll("%t", getTertiaryColour())
                .replaceAll("&", "§"))
                .getCurrentComponent());
    }

    public static String getPrimaryColour() {
        return plugin.getConfigManager().getConfig().getString("colours.primary") == null ?
                "&3" : plugin.getConfigManager().getConfig().getString("colours.primary");
    }
    public static String getSecondaryColour() {
        return plugin.getConfigManager().getConfig().getString("colours.secondary") == null ?
                "&b" : plugin.getConfigManager().getConfig().getString("colours.secondary");
    }
    public static String getTertiaryColour() {
        return plugin.getConfigManager().getConfig().getString("colours.tertiary") == null ?
                "&c" : plugin.getConfigManager().getConfig().getString("colours.tertiary");
    }

    public boolean broadcast(String message, boolean colours) {
        if (colours) {
            ProxyServer.getInstance().broadcast(chat(message));
        } else {
            ProxyServer.getInstance().broadcast(new ComponentBuilder(message).getCurrentComponent());
        }
        return true;
    }

}
