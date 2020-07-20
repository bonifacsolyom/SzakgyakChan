package org.github.bobobot.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Board {

	int ID;
	String shortName;
	String longName;
	List<Thread> threads = new ArrayList<>();

	public Board(int ID, String shortName, String longName) {
		this.ID = ID;
		this.shortName = shortName;
		this.longName = longName;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Board board = (Board) o;
		return ID == board.ID &&
				shortName.equals(board.shortName) &&
				longName.equals(board.longName) &&
				threads.equals(board.threads);
	}

	@Override
	public int hashCode() {
		return Objects.hash(ID, shortName, longName, threads);
	}

	public List<Thread> getThreads() {
		return threads;
	}

	public void setThreads(List<Thread> threads) {
		this.threads = threads;
	}

	public int getID() {
		return ID;
	}


	public void setID(int ID) {
		this.ID = ID;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getLongName() {
		return longName;
	}

	public void setLongName(String longName) {
		this.longName = longName;
	}
}
