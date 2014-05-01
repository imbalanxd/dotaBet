package com.dota.DotABet.model;

/**
 * Created with IntelliJ IDEA.
 * User: Imbalanxd
 * Date: 2014/03/29
 * Time: 4:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class BetHistory
{
	private Bet[] bets;
	private int betsTotal;
	private int betsWon;
	private int itemsWon;
	private int itemsLost;
	private int itemsCurrent;
	private int itemsMax;
	private int itemsMin;

	public BetHistory()
	{

	}

	public BetHistory(Bet[] _bets)
	{
		setBets(_bets);
	}

	public Bet[] getBets() {
		return bets;
	}

	public int getBetsTotal() {
		return betsTotal;
	}

	public int getBetsWon() {
		return betsWon;
	}

	public void setBets(Bet[] bets) {
		this.bets = bets;
		setBetsTotal(bets.length);
		for(int i = 1; i <= bets.length; i++)
		{
			Bet bet = bets[bets.length - i];
			if(bet.isClosed())
				continue;
			if(bet.isWon())
			{
				betsWon += 1;
				bet.setNet(bet.getWinnings().size());
				itemsWon += bet.getNet();
			}
			else if(bet.isLost())
			{
				bet.setNet((-1)*bet.getPlaced().size());
				itemsLost -= bet.getNet();
			}
			findMinAndMax(itemsWon, itemsLost);
		}
		itemsCurrent = itemsWon - itemsLost;
	}

	public void setBetsTotal(int betsTotal) {
		this.betsTotal = betsTotal;
	}

	public void setBetsWon(int betsWon) {
		this.betsWon = betsWon;
	}

	public int getItemsWon() {
		return itemsWon;
	}

	public int getItemsLost() {
		return itemsLost;
	}

	public void setItemsWon(int itemsWon) {
		this.itemsWon = itemsWon;
	}

	public void setItemsLost(int itemsLost) {
		this.itemsLost = itemsLost;
	}

	private void findMinAndMax(int _won, int _lost)
	{
		itemsMax = _won - _lost > itemsMax ? _won - _lost : itemsMax;
		itemsMin = _won - _lost < itemsMin ? _won - _lost : itemsMin;
	}

	public int getItemsMax() {
		return itemsMax;
	}

	public int getItemsMin() {
		return itemsMin;
	}

	public int getItemsCurrent() {
		return itemsCurrent;
	}
}
