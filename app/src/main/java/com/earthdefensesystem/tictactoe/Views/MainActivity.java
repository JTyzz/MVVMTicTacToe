package com.earthdefensesystem.tictactoe.Views;

import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.earthdefensesystem.tictactoe.Models.Player;
import com.earthdefensesystem.tictactoe.Databinding.ActivityGameBinding;
import com.earthdefensesystem.tictactoe.R;
import com.earthdefensesystem.tictactoe.ViewModel.GameViewModel;

import static com.earthdefensesystem.tictactoe.Utility.StringUtility.isNullOrEmpty;

public class MainActivity extends AppCompatActivity {


    private static final String GAME_BEGIN_DIALOG_TAG = "game_dialog_tag";
    private static final String GAME_END_DIALOG_TAG = "game_end_dialog_tag";
    private static final String NO_WINNER = "No one";
    private GameViewModel gameViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        promptForPlayers();
    }

    public void promptForPlayers() {
        GameBeginDialog dialog = GameBeginDialog.newInstance(this);
        dialog.setCancelable(false);
        dialog.show(getSupportFragmentManager(), GAME_BEGIN_DIALOG_TAG);
    }

    public void onPlayersSet(String player1, String player2) {
        initDataBinding(player1, player2);
    }

    private void initDataBinding(String player1, String player2) {
        ActivityGameBinding activityGameBinding = DataBindingUtil.setContentView(this, R.layout.activity_game);
        gameViewModel = ViewModelProviders.of(this).get(GameViewModel.class);
        gameViewModel.init(player1, player2);
        activityGameBinding.setGameViewModel(gameViewModel);
        setUpOnGameEndListener();
    }

    private void setUpOnGameEndListener() {
        gameViewModel.getWinner().observe(this, this::onGameWinnerChanged);
    }

    @VisibleForTesting
    public void onGameWinnerChanged(Player winner) {
        String winnerName = winner == null || isNullOrEmpty(winner.name) ? NO_WINNER : winner.name;
        GameEndDialog dialog = GameEndDialog.newInstance(this, winnerName);
        dialog.setCancelable(false);
        dialog.show(getSupportFragmentManager(), GAME_END_DIALOG_TAG);
    }
}
