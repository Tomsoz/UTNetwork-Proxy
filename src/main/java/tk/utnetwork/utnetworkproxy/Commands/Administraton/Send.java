package tk.utnetwork.utnetworkproxy.Commands.Administraton;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Command;
import tk.utnetwork.utnetworkproxy.Misc.Utils;

public class Send extends Command {
    public Send(String name, String permission, String... aliases) {
        super(name, permission, aliases);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length != 2) {
            sender.sendMessage(Utils.chat("&cUsage: /send <player> <server>"));
            return;
        }

        ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(Utils.chat("&cPlayer " + args[0] + " is not online."));
            return;
        }

        ServerInfo server = ProxyServer.getInstance().getServerInfo(args[1]);
        if (server == null) {
            sender.sendMessage(Utils.chat("&cThe server " + args[0] + " doesn't exist."));
            return;
        }

        target.connect(server, ServerConnectEvent.Reason.COMMAND);
        sender.sendMessage(Utils.chat("&aAttempted to send &2" + target.getDisplayName() + " &ato &2" + server.getName() + "&a."));
    }
}
