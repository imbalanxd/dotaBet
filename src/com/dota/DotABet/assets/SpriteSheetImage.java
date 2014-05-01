package com.dota.DotABet.assets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created with IntelliJ IDEA.
 * User: Imbalanxd
 * Date: 2014/04/20
 * Time: 8:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class SpriteSheetImage extends ImageView
{
	SpriteSheet m_spriteSheet;
	String m_name;

	public SpriteSheetImage(Context _context, SpriteSheet _spriteSheet, String _name)
	{
		super(_context);
		setSpriteSheet(_spriteSheet);
		setName(_name);
	}

	public void setName(String _name)
	{
		m_name = _name;
	}

	public void setSpriteSheet(SpriteSheet _spriteSheet)
	{
		m_spriteSheet = _spriteSheet;
	}

	@Override
	public void draw(Canvas _canvas)
	{
		if(m_name != null && m_spriteSheet != null)
			m_spriteSheet.draw(_canvas, m_name);
	}
}
