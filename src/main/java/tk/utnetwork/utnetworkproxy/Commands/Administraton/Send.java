package tk.utnetwork.utnetworkproxy.Commands.Administraton;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;
import tk.utnetwork.utnetworkproxy.Misc.Utils;
import tk.utnetwork.utnetworkproxy.UTNetworkProxy;

import java.util.*;
import java.util.stream.Collectors;

public class Send extends Command implements TabExecutor {
    UTNetworkProxy plugin;
    public Send(UTNetworkProxy plugin, String name, String permission, String... aliases) {
        super(name, permission, aliases);
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length != 2) {
            sender.sendMessage(Utils.chat("%tUsage: /send <player> <server>"));
            return;
        }

        ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(Utils.chat("%tPlayer " + args[0] + " is not online."));
            return;
        }

        ServerInfo server = ProxyServer.getInstance().getServerInfo(args[1]);
        if (server == null) {
            sender.sendMessage(Utils.chat("%tThe server " + args[0] + " doesn't exist."));
            return;
        }

        if (target.getServer().getInfo().equals(server)) {
            sender.sendMessage(Utils.chat("%t" + target.getDisplayName() + " %tis already connected to " + server.getName() + "%t."));
            return;
        }

        target.connect(server, ServerConnectEvent.Reason.COMMAND);
        sender.sendMessage(Utils.chat("%pAttempted to send %s" + target.getDisplayName() + " %pto %s" + server.getName() + "%p."));
        target.sendMessage(Utils.chat("%pYou've been sent to %s" + server.getName() + " %pby %s" + ((sender instanceof ProxiedPlayer) ? ((ProxiedPlayer)sender).getDisplayName() : plugin.getConfigManager().getConfig().getString("console_displayname") == null ? "&c&lConsole" : plugin.getConfigManager().getConfig().getString("console_displayname")) + "%p."));
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        List<String> results = new ArrayList<>();
        if (args.length == 1) {
            for (ProxiedPlayer p : ProxyServer.getInstance().getPlayers()) {
                if (p.getName().toLowerCase().startsWith(args[0].toLowerCase())) {
                    results.add(p.getName());
                }
            }
            if ("all".startsWith(args[0].toLowerCase())) results.add("all");

            for (Map.Entry<String, ServerInfo> s : ProxyServer.getInstance().getServersCopy().entrySet()) {
                if (s.getValue().getName().toLowerCase().startsWith(args[0].toLowerCase())) {
                    results.add(s.getValue().getName());
                }
            }
        } else if (args.length == 2) {
            for (Map.Entry<String, ServerInfo> s : ProxyServer.getInstance().getServersCopy().entrySet()) {
                if (s.getValue().getName().toLowerCase().startsWith(args[1].toLowerCase())) {
                    results.add(s.getValue().getName());
                }
            }
        }
        return results;
    }
}
