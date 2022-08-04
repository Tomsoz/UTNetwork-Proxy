package tk.utnetwork.utnetworkproxy.Misc;

import de.myzelyam.api.vanish.BungeeVanishAPI;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import tk.utnetwork.utnetworkproxy.UTNetworkProxy;

import java.util.List;
import java.util.Random;

public class Utils {
    static UTNetworkProxy plugin;
    public Utils(UTNetworkProxy plugin) {
        this.plugin = plugin;
    }
    public static BaseComponent[] chat(String textToTranslate) {
        return new ComponentBuilder((plugin.getConfigManager().getConfig().getString("prefix") + textToTranslate)
                .replaceAll("%p", getPrimaryColour())
                .replaceAll("%s", getSecondaryColour())
                .replaceAll("%t", getTertiaryColour())
                .replaceAll("&", "§"))
                .create();
    }

    public static BaseComponent[] chatRaw(String textToTranslate) {
        return new ComponentBuilder(textToTranslate
                .replaceAll("%p", getPrimaryColour())
                .replaceAll("%s", getSecondaryColour())
                .replaceAll("%t", getTertiaryColour())
                .replaceAll("&", "§"))
                .create();
    }

    public static void log(String message) {
        ProxyServer.getInstance().getConsole().sendMessage(new ComponentBuilder(message.replaceAll("%p", getPrimaryColour())
                .replaceAll("%s", getSecondaryColour())
                .replaceAll("%t", getTertiaryColour())
                .replaceAll("&", "§"))
                .create());
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
            ProxyServer.getInstance().broadcast(new ComponentBuilder(message).create());
        }
        return true;
    }

    public static boolean sendStaffMessage(String message) {
        for (ProxiedPlayer p : ProxyServer.getInstance().getPlayers()) {
            if (p.hasPermission("staff") && !areStaffMessagesOff(p)) {
                p.sendMessage(new ComponentBuilder((plugin.getConfigManager().getConfig().getString("staffPrefix") == null ? "&3&lStaff &8- " : plugin.getConfigManager().getConfig().getString("staffPrefix") + message)
                                .replaceAll("&", "§"))
                                .create());
            }
        }
        ProxyServer.getInstance().getConsole().sendMessage(new ComponentBuilder((plugin.getConfigManager().getConfig().getString("staffPrefix") == null ? "&3&lStaff &8- " : plugin.getConfigManager().getConfig().getString("staffPrefix") + message)
                                                                .replaceAll("&", "§"))
                                                                .create());
        return true;
    }

    public static boolean sendAdminMessage(String message) {
        for (ProxiedPlayer p : ProxyServer.getInstance().getPlayers()) {
            if (p.hasPermission("admin") && !areStaffMessagesOff(p)) {
                p.sendMessage(new ComponentBuilder((plugin.getConfigManager().getConfig().getString("adminPrefix") == null ? "&c&lAdmin &8- " : plugin.getConfigManager().getConfig().getString("adminPrefix") + message)
                        .replaceAll("&", "§"))
                        .create());
            }
        }
        ProxyServer.getInstance().getConsole().sendMessage(new ComponentBuilder((plugin.getConfigManager().getConfig().getString("adminPrefix") == null ? "&c&lAdmin &8- " : plugin.getConfigManager().getConfig().getString("adminPrefix") + message)
                .replaceAll("&", "§"))
                .create());
        return true;
    }

    public static String getRandomElement(List<String> list) {
        Random rand = new Random();
        return list.get(rand.nextInt(list.size()));
    }

    public static boolean sendHub(ProxiedPlayer p) {
        ServerInfo server = ProxyServer.getInstance().getServerInfo(getRandomElement(plugin.getConfigManager().getConfig().getStringList("hubServers")));
        if (server == null || !server.canAccess(p)) {
            return false;
        }
        p.connect(server);
        return true;
    }


    public static boolean isAdminChatOn(ProxiedPlayer p) {
        return plugin.getConfigManager().getData().getBoolean("adminchat." + p.getUniqueId());
    }

    public static boolean toggleAdminChat(ProxiedPlayer p) {
        if (isAdminChatOn(p)) {
            plugin.getConfigManager().getData().set("adminchat." + p.getUniqueId(), false);
            plugin.getConfigManager().saveData();
            return false;
        } else {
            plugin.getConfigManager().getData().set("adminchat." + p.getUniqueId(), true);
            plugin.getConfigManager().saveData();
            return true;
        }
    }

    public static void sendAdminChat(String from, String msg) {
        sendAdminMessage(from + "&8: &b"+msg);
    }




    public static boolean isStaffChatOn(ProxiedPlayer p) {
        return plugin.getConfigManager().getData().getBoolean("staffchat." + p.getUniqueId());
    }

    public static boolean toggleStaffChat(ProxiedPlayer p) {
        if (isStaffChatOn(p)) {
            plugin.getConfigManager().getData().set("staffchat." + p.getUniqueId(), false);
            plugin.getConfigManager().saveData();
            return false;
        } else {
            plugin.getConfigManager().getData().set("staffchat." + p.getUniqueId(), true);
            plugin.getConfigManager().saveData();
            return true;
        }
    }

    public static boolean toggleStaffMessages(ProxiedPlayer p) {
        if (areStaffMessagesOff(p)) {
            plugin.getConfigManager().getData().set("staffmessagesoff." + p.getUniqueId(), false);
            plugin.getConfigManager().saveData();
            return false;
        } else {
            plugin.getConfigManager().getData().set("staffmessagesoff." + p.getUniqueId(), true);
            plugin.getConfigManager().saveData();
            return true;
        }
    }

    public static void sendStaffChat(String from, String msg) {
        sendStaffMessage(from + "&8: &b"+msg);
    }

    public static boolean areStaffMessagesOff(ProxiedPlayer p) {
        return plugin.getConfigManager().getData().getBoolean("staffmessagesoff." + p.getUniqueId());
    }

    public static String[] removeFirstElement(String[] arr) {
        String newArr[] = new String[arr.length - 1];
        for (int i = 1; i < arr.length; i++) {
            newArr[i-1] = arr[i];
        }
        return newArr;
    }

    public static boolean canSee(ProxiedPlayer target, ProxiedPlayer viewer) {
        return BungeeVanishAPI.canSee(viewer, target);
    }

    public static boolean arePrivateMessagesOff(ProxiedPlayer p) {
        return plugin.getConfigManager().getData().getBoolean("dmsoff." + p.getUniqueId());
    }

    public static boolean togglePrivateMessages(ProxiedPlayer p) {
        if (arePrivateMessagesOff(p)) {
            plugin.getConfigManager().getData().set("dmsoff." + p.getUniqueId(), false);
            plugin.getConfigManager().saveData();
            return false;
        } else {
            plugin.getConfigManager().getData().set("dmsoff." + p.getUniqueId(), true);
            plugin.getConfigManager().saveData();
            return true;
        }
    }
}
