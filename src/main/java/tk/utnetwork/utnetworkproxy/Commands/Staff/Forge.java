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

public class Forge extends Command implements TabExecutor {
    UTNetworkProxy plugin;
    public Forge(UTNetworkProxy plugin, String name, String permission, String... aliases) {
        super(name, permission, aliases);
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(Utils.chat("%tUsage: /forge <player>"));
            return;
        }

        ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(Utils.chat("%tPlayer " + args[0] + " is not online."));
            return;
        }

        if (target.isForgeUser()) {
            sender.sendMessage(Utils.chat("%s" + target.getDisplayName() + " %pis on forge with the following mods: %s" + String.join("%p, %s", target.getModList().values())));
        } else {
            sender.sendMessage(Utils.chat("%s" + target.getDisplayName() + " %pis not on forge."));
        }
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        List<String> results = new ArrayList<>();
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
