package tk.utnetwork.utnetworkproxy.Commands.Administraton;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.hover.content.Content;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;
import tk.utnetwork.utnetworkproxy.Misc.Utils;
import tk.utnetwork.utnetworkproxy.UTNetworkProxy;

import static tk.utnetwork.utnetworkproxy.Misc.Utils.*;

public class Find extends Command implements TabExecutor {
    UTNetworkProxy plugin;
    public Find(UTNetworkProxy plugin, String name, String permission, String... aliases) {
        super(name, permission, aliases);
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length != 1) {
            sender.sendMessage(Utils.chat("%tUsage: /find <player>"));
            return;
        }

        ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(Utils.chat("%tPlayer " + args[0] + " is not online."));
            return;
        }

        ComponentBuilder builder = new ComponentBuilder((plugin.getConfigManager().getConfig().getString("prefix") + "%s" + target.getDisplayName() + " %pis online at %s" + target.getServer().getInfo().getName() + "%p.")
                .replaceAll("%p", getPrimaryColour())
                .replaceAll("%s", getSecondaryColour())
                .replaceAll("%t", getTertiaryColour())
                .replaceAll("&", "§"));
        builder.event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/server " + target.getServer().getInfo().getName()));
        builder.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Utils.chat("%pClick here to go to %s" + target.getServer().getInfo().getName() + "%p.")));
        sender.sendMessage(builder.create());
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        return null;
    }
}
