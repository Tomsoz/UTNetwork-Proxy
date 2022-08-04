package tk.utnetwork.utnetworkproxy.Commands.General;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import tk.utnetwork.utnetworkproxy.Misc.Utils;
import tk.utnetwork.utnetworkproxy.UTNetworkProxy;

import java.util.UUID;

public class Reply extends Command {
    UTNetworkProxy plugin;
    public Reply(UTNetworkProxy plugin, String name, String permission, String... aliases) {
        super(name, permission, aliases);
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(args.length > 0)) {
            sender.sendMessage(Utils.chat("%tUsage: /reply <message>"));
            return;
        }
        String key = sender instanceof ProxiedPlayer ? ((ProxiedPlayer)sender).getUniqueId().toString() : "Console";
        if (!plugin.replyUsers.containsKey(key)) {
            sender.sendMessage(Utils.chat("%tYou don't have anybody to reply to."));
            return;
        }
        ProxiedPlayer target = ProxyServer.getInstance().getPlayer(UUID.fromString(plugin.replyUsers.get(key)));
        if (target == null) {
            sender.sendMessage(Utils.chat("%tYou don't have anybody to reply to."));
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

        String from = ((sender instanceof ProxiedPlayer) ? ((ProxiedPlayer)sender).getDisplayName() : plugin.getConfigManager().getConfig().getString("console_displayname") == null ? "&c&lConsole" : plugin.getConfigManager().getConfig().getString("console_displayname"));
        String message = String.join(" ", args);

        target.sendMessage(Utils.chatRaw("%p(From %s" + from + "%p)%s " + message));
        sender.sendMessage(Utils.chatRaw("%p(To %s" + target.getDisplayName() + "%p)%s " + message));
    }
}
