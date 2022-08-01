package tk.utnetwork.utnetworkproxy.Events;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import tk.utnetwork.utnetworkproxy.Misc.Utils;
import tk.utnetwork.utnetworkproxy.UTNetworkProxy;

public class QuitEvent implements Listener {
    UTNetworkProxy plugin;
    public QuitEvent(UTNetworkProxy plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onQuit(PlayerDisconnectEvent e) {
        ProxiedPlayer p = e.getPlayer();

        if (p.hasPermission("staff")) {
            Utils.sendStaffMessage(p.getDisplayName() + " &bhas disconnected" + (e.getPlayer().getServer() == null ? "" : (" from &3" + e.getPlayer().getServer().getInfo().getName() + "&b")) + ".");
        } else {
            ProxyServer.getInstance().getConsole().sendMessage(new ComponentBuilder((plugin.getConfigManager().getConfig().getString("staffPrefix") == null ? "&3&lStaff &8- " : plugin.getConfigManager().getConfig().getString("staffPrefix") + p.getDisplayName() + " &bhas disconnected" + (e.getPlayer().getServer() == null ? "" : (" from &3" + e.getPlayer().getServer().getInfo().getName() + "&b")) + ".")
                    .replaceAll("&", "§"))
                    .create());
        }
    }
}
