package tk.utnetwork.utnetworkproxy.Events;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import tk.utnetwork.utnetworkproxy.Misc.Utils;
import tk.utnetwork.utnetworkproxy.UTNetworkProxy;

public class ChatEvent implements Listener {
    UTNetworkProxy plugin;
    public ChatEvent(UTNetworkProxy plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onChat(net.md_5.bungee.api.event.ChatEvent e) {
        if (!(e.getSender() instanceof ProxiedPlayer) || e.isCommand() || e.isProxyCommand()) return;
        ProxiedPlayer p = (ProxiedPlayer) e.getSender();

        LuckPerms api = LuckPermsProvider.get();
        User u = api.getUserManager().getUser(p.getUniqueId());
        String colour = api.getGroupManager().getGroup(u.getPrimaryGroup()).getCachedData().getMetaData().getPrefixes().get(0);

        p.setDisplayName(ChatColor.translateAlternateColorCodes('&', colour + p.getName()));

        if (e.getMessage().startsWith("a# ")) {
            String msg = e.getMessage().replaceFirst("a# ", "");
            if (p.hasPermission("proxy.adminchat")) {
                e.setCancelled(true);
                if (Utils.areStaffMessagesOff(p)) {
                    p.sendMessage(Utils.chat("%tYou can't use staffchat as your staff messages are disabled."));
                    return;
                }
                Utils.sendAdminChat(p.getDisplayName(), msg);
                return;
            }
        }
        if (e.getMessage().startsWith("# ")) {
            String msg = e.getMessage().replaceFirst("# ", "");
            if (p.hasPermission("proxy.staffchat")) {
                e.setCancelled(true);
                if (Utils.areStaffMessagesOff(p)) {
                    p.sendMessage(Utils.chat("%tYou can't use staffchat as your staff messages are disabled."));
                    return;
                }
                Utils.sendStaffChat(p.getDisplayName(), msg);
                return;
            }
        }
        if (Utils.isAdminChatOn(p)) {
            if (!p.hasPermission("proxy.adminchat")) {
                boolean res = Utils.toggleAdminChat(p);
                while (res) {
                    res = Utils.toggleAdminChat(p);
                }
                return;
            }
            e.setCancelled(true);
            if (Utils.areStaffMessagesOff(p)) {
                p.sendMessage(Utils.chat("%tYou can't use adminchat as your staff messages are disabled."));
                return;
            }
            Utils.sendAdminChat(p.getDisplayName(), e.getMessage());
            return;
        }
        if (Utils.isStaffChatOn(p)) {
            if (!p.hasPermission("proxy.staffchat")) {
                boolean res = Utils.toggleStaffChat(p);
                while (res) {
                    res = Utils.toggleStaffChat(p);
                }
                return;
            }
            e.setCancelled(true);
            if (Utils.areStaffMessagesOff(p)) {
                p.sendMessage(Utils.chat("%tYou can't use staffchat as your staff messages are disabled."));
                return;
            }
            Utils.sendStaffChat(p.getDisplayName(), e.getMessage());
            return;
        }
    }
}
