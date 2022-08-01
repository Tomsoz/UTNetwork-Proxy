package tk.utnetwork.utnetworkproxy.Commands.Staff;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;
import tk.utnetwork.utnetworkproxy.Misc.Utils;
import tk.utnetwork.utnetworkproxy.UTNetworkProxy;

import java.util.ArrayList;
import java.util.List;

public class ToggleStaffMessages extends Command implements TabExecutor {
    UTNetworkProxy plugin;
    public ToggleStaffMessages(UTNetworkProxy plugin, String name, String permission, String... aliases) {
        super(name, permission, aliases);
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 0) {
            if (!(sender instanceof ProxiedPlayer)) {
                sender.sendMessage(Utils.chat("%tUsage: /togglestaffmessages <player>"));
                return;
            }
            ProxiedPlayer p = (ProxiedPlayer) sender;
            if (Utils.toggleStaffMessages(p)) {
                p.sendMessage(Utils.chat("%pYou've successfully %sdisabled%p your staff messages."));
            } else {
                p.sendMessage(Utils.chat("%pYou've successfully %senabled%p your staff messages."));
            }
        } else {
            if (sender instanceof ProxiedPlayer) {
                if (!((ProxiedPlayer)sender).hasPermission("proxy.tsm.others")) {
                    ProxiedPlayer p = (ProxiedPlayer) sender;
                    if (Utils.toggleStaffMessages(p)) {
                        p.sendMessage(Utils.chat("%pYou've successfully %sdisabled%p your staff messages."));
                    } else {
                        p.sendMessage(Utils.chat("%pYou've successfully %senabled%p your staff messages."));
                    }
                }
            }
            String name = ((sender instanceof ProxiedPlayer) ? ((ProxiedPlayer)sender).getDisplayName() : plugin.getConfigManager().getConfig().getString("console_displayname") == null ? "&c&lConsole" : plugin.getConfigManager().getConfig().getString("console_displayname"));

            ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[0]);
            if (target == null) {
                sender.sendMessage(Utils.chat("%tPlayer " + args[0] + " is not online."));
                return;
            }

            if (Utils.toggleStaffMessages(target)) {
                sender.sendMessage(Utils.chat("%pYou've successfully %sdisabled " + target.getDisplayName() + "%p's staff messages."));
                target.sendMessage(Utils.chat("%s" + name + " %phas %sdisabled %pyour staff messages."));
            } else {
                sender.sendMessage(Utils.chat("%pYou've successfully %senabled " + target.getDisplayName() + "%p's staff messages."));
                target.sendMessage(Utils.chat("%s" + name + " %phas %senabled %pyour staff messages."));
            }
        }
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        List<String> results = new ArrayList<>();
        if (sender instanceof ProxiedPlayer) {
            ProxiedPlayer me = (ProxiedPlayer) sender;
            if (!me.hasPermission("proxy.tsm.others")) {
                return results;
            }
        }
        if (args.length == 1) {
            for (ProxiedPlayer p : ProxyServer.getInstance().getPlayers()) {
                if (p.getName().startsWith(args[0])) {
                    results.add(p.getName());
                }
            }
        }
        return results;
    }
}
