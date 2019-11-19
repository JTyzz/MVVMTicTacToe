package com.earthdefensesystem.tictactoe.Models;

import com.earthdefensesystem.tictactoe.Utility.StringUtility;

public class Cell {
    public Player player;

    public Cell(Player player) {
        this.player = player;
    }
    public boolean isEmpty() {
        return player == null || StringUtility.isNullOrEmpty(player.value);
    }
}
