package tk.utnetwork.utnetworkproxy.Commands.General;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.TabExecutor;
import net.md_5.bungee.api.plugin.Command;
import tk.utnetwork.utnetworkproxy.Misc.Utils;
import tk.utnetwork.utnetworkproxy.UTNetworkProxy;

import java.util.ArrayList;
import java.util.List;

public class Hub extends Command implements TabExecutor {
    UTNetworkProxy plugin;
    public Hub(UTNetworkProxy plugin, String name, String permission, String... aliases) {
        super(name, permission, aliases);
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 0) {
            if (!(sender instanceof ProxiedPlayer)) {
                sender.sendMessage(Utils.chat("%tUsage: /hub <player>"));
            }
            ProxiedPlayer p = (ProxiedPlayer) sender;
            Utils.sendHub(p);
        } else {
            if (sender instanceof ProxiedPlayer) {
                ProxiedPlayer p = (ProxiedPlayer) sender;
                if (!p.hasPermission("proxy.hub.others")) {
                    Utils.sendHub(p);
                    return;
                }
            }
            ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[0]);
            if (target == null) {
                sender.sendMessage(Utils.chat("%tPlayer " + args[0] + " is not online."));
                return;
            }
            Utils.sendHub(target);
            sender.sendMessage(Utils.chat("%s" + target.getDisplayName() + " %phas been sent to the hub."));
            target.sendMessage(Utils.chat("%pYou've been sent to the hub by %s" + ((sender instanceof ProxiedPlayer) ? ((ProxiedPlayer)sender).getDisplayName() : plugin.getConfigManager().getConfig().getString("console_displayname") == null ? "&c&lConsole" : plugin.getConfigManager().getConfig().getString("console_displayname")) + "%p."));
        }
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        List<String> results = new ArrayList<>();
        if (args.length == 0) return results;
        if (sender instanceof ProxiedPlayer) {
            if (!((ProxiedPlayer)sender).hasPermission("proxy.hub.others")) {
                return results;
            }
        }
        for (ProxiedPlayer p : ProxyServer.getInstance().getPlayers()) {
            if (p.getName().startsWith(args[0])) {
                results.add(p.getName());
            }
        }
        return results;
    }
}
