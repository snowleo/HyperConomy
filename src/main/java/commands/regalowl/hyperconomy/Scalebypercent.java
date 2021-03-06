package regalowl.hyperconomy;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;

import regalowl.databukkit.CommonFunctions;

public class Scalebypercent {
	Scalebypercent(CommandSender sender, String[] args, String playerecon) {
		HyperConomy hc = HyperConomy.hc;
		LanguageFile L = hc.getLanguageFile();
		try {
			HyperEconomy he = hc.getEconomyManager().getEconomy(playerecon);
			CommonFunctions cf = hc.gCF();
			InfoSignHandler isign = hc.getInfoSignHandler();
			ArrayList<String> names = he.getNames();
			boolean onlyItems = false;
			boolean onlyEnchants = false;
			if (args.length == 2 || args.length == 3) {
				if (args.length == 3) {
					if (args[2].contains("item")) {
						onlyItems = true;
					} else if (args[2].contains("enchantment")) {
						onlyEnchants = true;
					}
				} else {
					names = he.getNames();
				}
				String type = args[0];
				Double percent = Double.parseDouble(args[1]);
				percent = percent / 100;
				if (percent >= 0) {
					if (type.equalsIgnoreCase("value") || type.equalsIgnoreCase("staticprice") || type.equalsIgnoreCase("stock") || type.equalsIgnoreCase("median") || type.equalsIgnoreCase("startprice")) {
						if (hc.gYH().gFC("config").getBoolean("config.run-automatic-backups")) {
							new Backup();
						}
						for (int c = 0; c < names.size(); c++) {
							String cname = names.get(c);
							HyperObject ho = he.getHyperObject(cname);
							if (!(ho instanceof HyperItem) && onlyItems) {continue;}
							if (!(ho instanceof HyperEnchant) && onlyEnchants) {continue;}
							if (!(ho instanceof CompositeItem)) {
								if (type.equalsIgnoreCase("value")) {
									ho.setValue(cf.twoDecimals(ho.getValue() * percent));
								} else if (type.equalsIgnoreCase("staticprice")) {
									ho.setStaticprice(cf.twoDecimals(ho.getStaticprice() * percent));
								} else if (type.equalsIgnoreCase("stock")) {
									ho.setStock(Math.floor(ho.getStock() * percent + .5));
								} else if (type.equalsIgnoreCase("median")) {
									ho.setMedian(cf.twoDecimals(ho.getMedian() * percent));
								} else if (type.equalsIgnoreCase("startprice")) {
									ho.setStartprice(cf.twoDecimals(ho.getStartprice() * percent));
								}
							}
						}
						sender.sendMessage(L.get("ADJUSTMENT_SUCCESSFUL"));
						isign.updateSigns();
					} else {
						sender.sendMessage(L.get("SCALEBYPERCENT_TYPES"));
					}
				} else {
					sender.sendMessage(L.get("PERCENT_GREATER_THAN_0"));
				}
			} else {
				sender.sendMessage(L.get("SCALEBYPERCENT_INVALID"));
			}
		} catch (Exception e) {
			sender.sendMessage(L.get("SCALEBYPERCENT_INVALID"));
		}
	}
}
