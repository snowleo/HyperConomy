package regalowl.hyperconomy;

import org.bukkit.entity.Player;

public class Setshop {
	Setshop(String[] args, Player player) {
		HyperConomy hc = HyperConomy.hc;
		LanguageFile L = hc.getLanguageFile();
		EconomyManager em = hc.getEconomyManager();
		if (args.length >= 2) {
			if (args[0].equalsIgnoreCase("p1")) {
				String name = args[1].replace(".", "").replace(":", "");
				if (em.shopExists(name)) {
					em.getShop(name).setPoint1(player.getLocation());
				} else {
					Shop shop = new ServerShop(name, hc.getEconomyManager().getGlobalShopAccount().getEconomy(), hc.getEconomyManager().getGlobalShopAccount());
					shop.setPoint1(player.getLocation());
					shop.setPoint2(player.getLocation());
					shop.setDefaultMessages();
					em.addShop(shop);
				}
				player.sendMessage(L.get("P1_SET"));
			} else if (args[0].equalsIgnoreCase("p2")) {
				String name = args[1].replace(".", "").replace(":", "");
				if (em.shopExists(name)) {
					em.getShop(name).setPoint2(player.getLocation());
				} else {
					Shop shop = new ServerShop(name, hc.getEconomyManager().getGlobalShopAccount().getEconomy(), hc.getEconomyManager().getGlobalShopAccount());
					shop.setPoint1(player.getLocation());
					shop.setPoint2(player.getLocation());
					shop.setDefaultMessages();
					em.addShop(shop);
				}
				player.sendMessage(L.get("P2_SET"));
			}
		} else {
			player.sendMessage(L.get("SETSHOP_INVALID"));
		}
	}
}