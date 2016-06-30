package com.gatis.leksika;

import android.os.Bundle;

import com.gatis.leksika.game.Board;
import com.gatis.leksika.game.Game;

import java.util.Date;

public class GameSaverTransient extends GameSaver {

	private final Bundle bundle;

	public GameSaverTransient(Bundle bundle) {
		this.bundle = bundle;
	}

	@Override
	public boolean hasSavedGame() {
		return bundle.getBoolean(ACTIVE_GAME, false);
	}

	@Override
	public int readWordCount() {
		return bundle.getInt(WORD_COUNT, DEFAULT_WORD_COUNT);
	}

	@Override
	public String[] readWords() {
		return safeSplit(bundle.getString(WORDS));
	}

	@Override
	public int readMaxTimeRemaining() {
		return bundle.getInt(MAX_TIME_REMAINING, DEFAULT_MAX_TIME_REMAINING);
	}

	@Override
	public int readTimeRemaining() {
		return bundle.getInt(TIME_REMAINING, DEFAULT_TIME_REMAINING);
	}

	@Override
	public String[] readGameBoard() {
		return safeSplit(bundle.getString(GAME_BOARD));
	}

	@Override
	public int readBoardSize() {
		return bundle.getInt(BOARD_SIZE, DEFAULT_BOARD_SIZE);
	}

	@Override
	public Game.GameStatus readStatus() {
		String status = bundle.getString(STATUS);
		return status == null ? null : Game.GameStatus.valueOf(status);
	}

	@Override
	public Date readStart() {
		long start = bundle.getLong(START, 0);
		return start == 0 ? null : new Date(start);
	}

	@Override
	public void save(Board board, int timeRemaining, int maxTimeRemaining, String wordListToString, int wordCount, Date start, Game.GameStatus status) {
		bundle.putInt(BOARD_SIZE, board.getSize());

		bundle.putString(GAME_BOARD, board.toString());
		bundle.putInt(TIME_REMAINING, timeRemaining);
		bundle.putInt(MAX_TIME_REMAINING, maxTimeRemaining);
		bundle.putString(WORDS, wordListToString);
		bundle.putInt(WORD_COUNT, wordCount);
		bundle.putLong(START, start == null ? 0 : start.getTime());
		bundle.putString(STATUS, status.toString());

		bundle.putBoolean(ACTIVE_GAME, true);
	}
}
