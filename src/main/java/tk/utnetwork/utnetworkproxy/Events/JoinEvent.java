package tk.utnetwork.utnetworkproxy.Events;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import tk.utnetwork.utnetworkproxy.Misc.Utils;
import tk.utnetwork.utnetworkproxy.UTNetworkProxy;

public class JoinEvent implements Listener {
    UTNetworkProxy plugin;
    public JoinEvent(UTNetworkProxy plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(ServerConnectEvent e) {
        ProxiedPlayer p = e.getPlayer();
        LuckPerms api = LuckPermsProvider.get();
        User u = api.getUserManager().getUser(p.getUniqueId());
        String colour = api.getGroupManager().getGroup(u.getPrimaryGroup()).getCachedData().getMetaData().getPrefixes().get(0);

        p.setDisplayName((colour + p.getName()).replaceAll("&", "§"));
        if (e.getReason().equals(ServerConnectEvent.Reason.JOIN_PROXY)) {
            if (p.hasPermission("staff")) {
                Utils.sendStaffMessage(p.getDisplayName() + " &bhas connected" + (e.getTarget() == null ? "" : (" to &3" + e.getTarget().getName() + "&b")) + ".");
            } else {
                ProxyServer.getInstance().getConsole().sendMessage(new ComponentBuilder((plugin.getConfigManager().getConfig().getString("staffPrefix") == null ? "&3&lStaff &8- " : plugin.getConfigManager().getConfig().getString("staffPrefix") + p.getDisplayName() + " &bhas connected" + (e.getTarget() == null ? "" : (" to &3" + e.getTarget().getName() + "&b")) + ".")
                        .replaceAll("&", "§"))
                        .create());
            }
        } else if (e.getReason().equals(ServerConnectEvent.Reason.LOBBY_FALLBACK) ||
                e.getReason().equals(ServerConnectEvent.Reason.SERVER_DOWN_REDIRECT)) {
            if (e.getPlayer().getServer().getInfo().equals(e.getTarget())) return;
            if (p.hasPermission("staff")) {
                Utils.sendStaffMessage(p.getDisplayName() + " &bhas connected to fallback server &3" + e.getRequest().getTarget().getName() + "&b.");
            } else {
                ProxyServer.getInstance().getConsole().sendMessage(new ComponentBuilder((plugin.getConfigManager().getConfig().getString("staffPrefix") == null ? "&3&lStaff &8- " : plugin.getConfigManager().getConfig().getString("staffPrefix") + p.getDisplayName() + " &bhas connected to fallback server &3" + e.getRequest().getTarget().getName() + "&b.")
                        .replaceAll("&", "§"))
                        .create());
            }
        } else {
            if (e.getPlayer().getServer().getInfo().equals(e.getTarget())) return;
            if (p.hasPermission("staff")) {
                Utils.sendStaffMessage(p.getDisplayName() + " &bhas joined &3" + e.getRequest().getTarget().getName() + "&b from &3" + e.getPlayer().getServer().getInfo().getName() + "&b.");
            } else {
                ProxyServer.getInstance().getConsole().sendMessage(new ComponentBuilder((plugin.getConfigManager().getConfig().getString("staffPrefix") == null ? "&3&lStaff &8- " : plugin.getConfigManager().getConfig().getString("staffPrefix") + p.getDisplayName() + " &bhas joined &3" + e.getRequest().getTarget().getName() + "&b from &3" + e.getPlayer().getServer().getInfo().getName() + "&b.")
                        .replaceAll("&", "§"))
                        .create());
            }
        }
    }
}
