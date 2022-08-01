package tk.utnetwork.utnetworkproxy.Commands.Staff;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;
import tk.utnetwork.utnetworkproxy.Misc.Utils;
import tk.utnetwork.utnetworkproxy.UTNetworkProxy;

import java.util.ArrayList;
import java.util.List;

public class Server extends Command implements TabExecutor {
    UTNetworkProxy plugin;
    public Server(UTNetworkProxy plugin, String name, String permission, String... aliases) {
        super(name, permission, aliases);
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage(Utils.chat("%tThis is a player only command."));
            return;
        }
        ProxiedPlayer p = (ProxiedPlayer) sender;
        if (args.length == 0) {
            ArrayList<String> servers = new ArrayList<>();
            for (ServerInfo s : ProxyServer.getInstance().getServersCopy().values()) {
                servers.add("%s" + s + "%p");
            }
            String list = servers.toString();
            list = list.substring(1);
            list = list.substring(0, list.length() - 1);
            p.sendMessage(Utils.chat("%pThe following servers are available: %s" + list + "%p."));
        } else {
            ServerInfo server = ProxyServer.getInstance().getServerInfo(args[0]);
            if (server == null) {
                p.sendMessage(Utils.chat("%t" + args[0] + " is not a valid server."));
                return;
            }
            p.connect(server);
            p.sendMessage(Utils.chat("%pSending you to %s" + server.getName() + "%p..."));
        }
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        List<String> results = new ArrayList<>();
        if (args.length == 1) {
            for (ServerInfo s : ProxyServer.getInstance().getServersCopy().values()) {
                if (s.getName().startsWith(args[0]) && s.canAccess(sender)) {
                    results.add(s.getName());
                }
            }
        }
        return results;
    }
}
