package tk.utnetwork.utnetworkproxy.Commands.General;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;
import tk.utnetwork.utnetworkproxy.Misc.Utils;
import tk.utnetwork.utnetworkproxy.UTNetworkProxy;

import java.util.ArrayList;
import java.util.List;

public class Message extends Command implements TabExecutor {
    UTNetworkProxy plugin;
    public Message(UTNetworkProxy plugin, String name, String permission, String... aliases) {
        super(name, permission, aliases);
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(args.length >= 2)) {
            sender.sendMessage(Utils.chat("%tUsage: /message <player> <message>"));
            return;
        }

        ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(Utils.chat("%tPlayer " + args[0] + " is not online."));
            return;
        }

        if (sender instanceof ProxiedPlayer && !((ProxiedPlayer) sender).hasPermission("proxy.tpm.bypass") && Utils.arePrivateMessagesOff((ProxiedPlayer) sender)) {
            sender.sendMessage(Utils.chat("%tFailed to send as your private messages are disabled."));
            return;
        }
        if ((sender instanceof ProxiedPlayer && !((ProxiedPlayer) sender).hasPermission("proxy.tpm.bypass")) && Utils.arePrivateMessagesOff(target)) {
            sender.sendMessage(Utils.chat("%tFailed to send as " + target.getDisplayName() + "%t's private messages are disabled."));
            return;
        }

        String message = String.join(" ", Utils.removeFirstElement(args));
        String from = ((sender instanceof ProxiedPlayer) ? ((ProxiedPlayer)sender).getDisplayName() : plugin.getConfigManager().getConfig().getString("console_displayname") == null ? "&c&lConsole" : plugin.getConfigManager().getConfig().getString("console_displayname"));

        target.sendMessage(Utils.chatRaw("%p(From %s" + from + "%p)%s " + message));
        sender.sendMessage(Utils.chatRaw("%p(To %s" + target.getDisplayName() + "%p)%s " + message));

        plugin.replyUsers.put((sender instanceof ProxiedPlayer ? ((ProxiedPlayer)sender).getUniqueId().toString() : "Console"), target.getUniqueId().toString());
        if (sender instanceof ProxiedPlayer) {
            plugin.replyUsers.put(target.getUniqueId().toString(), ((ProxiedPlayer)sender).getUniqueId().toString());
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
