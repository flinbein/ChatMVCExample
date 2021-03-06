package ru.flinbein.chatmvcexample.controller;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import ru.flinbein.chatmvc.controller.Bind;
import ru.flinbein.chatmvc.controller.Hide;
import ru.flinbein.chatmvc.controller.MVVMController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ExampleInventoryController extends MVVMController {

    public String mode = "inventoryView";
    public int invPage = 0;
    public ItemStack selectedItem = null;

    public Player getPlayer() {return (Player) this.commandSender;}
    public int getInvPage() {return invPage;}
    public String getMode() {return mode;}
    public ItemStack getSelectedItem() {return selectedItem;}

    private void rerender() {
        render("template/inventory/inventory_example.ftlx");
    }

    public void prevPage(int currentPage) {
        changePage(currentPage-1);
    }

    public void nextPage(int currentPage) {
        changePage(currentPage+1);
    }

    @Bind(tab = "suggestPages")
    public void setPage(String arg) {
        if (arg == null) return;
        changePage(Integer.parseInt(arg)-1);
    }

    @Hide()
    public List<String> suggestPages(String arg) {
        if (arg == null) return null;
        int pages = Math.abs(getPlayer().getInventory().getStorageContents().length / 9);
        return IntStream.range(1, pages+1).mapToObj(Integer::toString).collect(Collectors.toList());
    }

    public void backToInventory() {
        selectedItem = null;
        mode = "inventoryView";
        rerender();
    }

    public void selectItem(int itemSlot) {
        var item = getPlayer().getInventory().getItem(itemSlot);
        if (item == null) return;
        mode = "itemView";
        selectedItem = item;
        rerender();
    }

    private void changePage(int page) {
        invPage = page;
        if (invPage < 0) invPage = 0;
        else if (invPage > 3) invPage = 3;
        rerender();
    }
}
