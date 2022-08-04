package tk.utnetwork.utnetworkproxy.Commands.General;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;
import tk.utnetwork.utnetworkproxy.Misc.Utils;
import tk.utnetwork.utnetworkproxy.UTNetworkProxy;

public class TogglePrivateMessages extends Command implements TabExecutor {
    UTNetworkProxy plugin;
    public TogglePrivateMessages(UTNetworkProxy plugin, String name, String permission, String... aliases) {
        super(name, permission, aliases);
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 0) {
            if (!(sender instanceof ProxiedPlayer)) {
                sender.sendMessage(Utils.chat("%tUsage: /tpm <player>"));
                return;
            }
            ProxiedPlayer p = (ProxiedPlayer) sender;
            if (Utils.togglePrivateMessages(p)) {
                p.sendMessage(Utils.chat("%pYou've %sdisabled %pprivate messages."));
            } else {
                p.sendMessage(Utils.chat("%pYou've %senabled %pprivate messages."));
            }
        } else {
            if (sender instanceof ProxiedPlayer) {
                if (!((ProxiedPlayer)sender).hasPermission("proxy.tpm.others")) {
                    ProxiedPlayer p = (ProxiedPlayer) sender;
                    if (Utils.togglePrivateMessages(p)) {
                        p.sendMessage(Utils.chat("%pYou've %sdisabled %pprivate messages."));
                    } else {
                        p.sendMessage(Utils.chat("%pYou've %senabled %pprivate messages."));
                    }
                    return;
                }
            }
            ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[0]);
            if (target == null) {
                sender.sendMessage(Utils.chat("%tPlayer " + args[0] + " is not online."));
                return;
            }
            String name = ((sender instanceof ProxiedPlayer) ? ((ProxiedPlayer)sender).getDisplayName() : plugin.getConfigManager().getConfig().getString("console_displayname") == null ? "&c&lConsole" : plugin.getConfigManager().getConfig().getString("console_displayname"));
            if (Utils.togglePrivateMessages(target)) {
                target.sendMessage(Utils.chat("%pYour %pprivate messages have been %sdisabled %pby %s" + name + "%p."));
                sender.sendMessage(Utils.chat("%pYou've %sdisabled " + target.getDisplayName() + "%s's %pprivate messages"));
            } else {
                target.sendMessage(Utils.chat("%pYour %pprivate messages have been %senabled %pby %s" + name + "%p."));
                sender.sendMessage(Utils.chat("%pYou've %senabled " + target.getDisplayName() + "%s's %pprivate messages"));
            }
        }
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        return null;
    }
}
