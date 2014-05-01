package com.dota.DotABet.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AlphaAnimation;
import android.widget.*;
import com.dota.DotABet.R;
import com.dota.DotABet.application.D2LApplication;
import com.dota.DotABet.display.DisplayUtility;
import com.dota.DotABet.download.Downloader;
import com.dota.DotABet.model.Item;
import com.dota.DotABet.ui.UIInterfaces.ItemDeselectListener;
import com.dota.DotABet.ui.UIInterfaces.ItemSelectedListener;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Imbalanxd
 * Date: 2014/03/21
 * Time: 6:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class ItemHolder extends FrameLayout implements View.OnClickListener, ItemDeselectListener
{
	private static final HashMap<Integer, Integer> scrollMap = new HashMap<Integer, Integer>();

	private HorizontalScrollView scroller;
	private LinearLayout imageContainer;
	private LinearLayout indicator;
	private FrameLayout indicatorContainer;
	private LinearLayout indicatorLayer;
	private ArrayList<ImageView> itemImages;
	private ArrayList<ImageView> itemIndicators;
	private ArrayList<Object> items;
	private int currentSelectedIndex = -1;
	private UIInterfaces.ItemSelectedListener listener;
	public int identifier = -1000;

	private int imgWidth, imgHeight;
	private int imgPadding = 10;
	private int totalItems;
	private int maxVisible = 0;

	private float lastTouchX = 0;
	private int currentFocusIndex = 0;
	private float scrollStartPosition;
	private long scrollStartTime;
	private double flingThreshold = 0.5;
	private float scrollMovePosition;

	private AlphaAnimation fadeIn;
	private AlphaAnimation fadeOut;
	private boolean isFadedIn = false;
	private long fadeInTime;

	public ItemHolder(Context context) {
		super(context);
		init();
	}

	public ItemHolder(Context context, AttributeSet attrs) {
		super(context, attrs);
		getAttributes(context, attrs);
		init();
	}

	public ItemHolder(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		getAttributes(context, attrs);
		init();
	}

	private void getAttributes(Context context, AttributeSet attrs)
	{
		TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ItemHolder, 0, 0);
		try
		{
			imgWidth = (int)ta.getDimension(R.styleable.ItemHolder_imageWidth, 10.0f);
			imgHeight = (int)ta.getDimension(R.styleable.ItemHolder_imageHeight, 10.0f);
		}
		finally
		{
			ta.recycle();
		}
	}

	public void setOnItemSelectedListener(ItemSelectedListener _listener)
	{
		listener = _listener;
	}

	private boolean shouldSelectItem(Object _item)
	{
		Item item = (Item)_item;
		Item selectedItem = (Item)listener.getItemSelected();
		if(listener.getDeselectIdentifier() == this.identifier && selectedItem != null && selectedItem.getIdentifier() == item.getIdentifier())
		{
			return true;
		}
		return false;
	}

	private void init()
	{
		itemImages = new ArrayList<ImageView>();
		items = new ArrayList<Object>();

		setupScroller();
		setupImageContainer();

		addView(scroller);
		scroller.addView(imageContainer);
	}

	private void setupScroller()
	{
		scroller = new HorizontalScrollView(getContext());
		scroller.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		scroller.setHorizontalFadingEdgeEnabled(false);
		scroller.setHorizontalScrollBarEnabled(false);
		scroller.setSmoothScrollingEnabled(true);
		scroller.setOnTouchListener( new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event)
			{
				if(MotionEvent.ACTION_DOWN == event.getAction())
				{
					scrollStartPosition = event.getX();
					scrollStartTime = System.currentTimeMillis();
				}
				else if(MotionEvent.ACTION_UP == event.getAction())
				{
					int prelimFocusIndex = getFocusIndex(scroller.getScrollX());
					double flingSpeed = (event.getX() - scrollStartPosition)/(System.currentTimeMillis() - scrollStartTime);
					if(Math.abs(flingSpeed) > flingThreshold && currentFocusIndex == prelimFocusIndex)
						setFocusIndex(prelimFocusIndex - (int)Math.round(flingSpeed / flingThreshold));
					else
						setFocusIndex(prelimFocusIndex);
					int scrollPosition = Math.max(0, Math.min(totalItems, currentFocusIndex)) * (imgWidth + 2 * imgPadding);
					scroller.smoothScrollTo(scrollPosition, 0);
					if(indicator != null)
					{
						moveIndicator(scrollPosition);
					}
					scrollMap.put(getIdentifier(), scrollPosition);
					return true;
				}

				if(MotionEvent.ACTION_MOVE == event.getAction())
				{
					if(indicator != null)
					{
						fadeIndicatorIn();
						moveIndicator(-1);
					}
				}
				return false;
			}
		});
		ViewTreeObserver vto = scroller.getViewTreeObserver();
		vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
			public void onGlobalLayout()
			{
				scroller.scrollTo(scrollMap.containsKey(getIdentifier()) ? scrollMap.get(getIdentifier()) : 0, 0);
				if(indicator != null)
				{
					moveIndicator((scrollMap.containsKey(getIdentifier()) ? scrollMap.get(getIdentifier()) : 0));
				}
			}
		});
	}

	private void setupImageContainer()
	{
		imageContainer = new LinearLayout(getContext());
		imageContainer.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		imageContainer.setOrientation(LinearLayout.HORIZONTAL);
		imageContainer.setVisibility(View.INVISIBLE);
		imageContainer.setOnTouchListener( new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event)
			{
				lastTouchX = event.getX();
				return false;
			}
		});
		imageContainer.setOnClickListener(this);
	}

	private void setupIndicator()
	{
		indicatorContainer = new FrameLayout(getContext());
		indicatorContainer.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		indicator = new LinearLayout(getContext());
		indicator.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		indicator.setBackground(getResources().getDrawable(R.drawable.container_item_indicator));
		indicatorContainer.addView(indicator);
		indicatorLayer = new LinearLayout(getContext());
		indicatorLayer.setOrientation(LinearLayout.VERTICAL);
		indicatorLayer.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		indicatorLayer.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);

		int indicatorWidth = (int)DisplayUtility.convertDpToPixel(11.0f);
		LinearLayout selectorLayerOne = new LinearLayout(getContext());
		selectorLayerOne.setLayoutParams(new LayoutParams(indicatorWidth * maxVisible, 4));
		selectorLayerOne.setBackgroundColor(R.color.item_key);
		LinearLayout selectorLayerTwo = new LinearLayout(getContext());
		selectorLayerTwo.setLayoutParams(new LayoutParams(indicatorWidth * maxVisible, 4));
		selectorLayerTwo.setBackgroundColor(R.color.item_key);

		addView(indicatorLayer);
		indicatorLayer.addView(selectorLayerOne);
		indicatorLayer.addView(indicatorContainer);
		indicatorLayer.addView(selectorLayerTwo);
		indicatorContainer.setTranslationX((float)((0.5 * indicatorWidth * items.size())-(0.5 * indicatorWidth * maxVisible)));

		AlphaAnimation initialInvis = new AlphaAnimation(1.0f, 0.0f);
		initialInvis.setDuration(0);
		indicatorContainer.startAnimation(initialInvis);
		fadeIn = new AlphaAnimation(0.0f, 1.0f);
		fadeIn.setDuration(300);
		fadeOut = new AlphaAnimation(1.0f, 0.0f);
		fadeOut.setDuration(1500);
		fadeOut.setFillAfter(true);

		itemIndicators = new ArrayList<ImageView>();
	}

	private int findIndexForTranslation(int _translation)
	{
		int indicatorWidth = (int)DisplayUtility.convertDpToPixel(11.0f);
		int adjustedTranslation = (int)((_translation * -1) + (float)((0.5 * indicatorWidth * items.size())-(0.5 * indicatorWidth * maxVisible)));
		return (int)Math.floor(adjustedTranslation / indicatorWidth);
	}

	private void moveIndicator(int _position)
	{
		int indicatorWidth = (int)DisplayUtility.convertDpToPixel(11.0f);
		final float convertValue = (float)indicatorWidth / (float)(imgWidth + 2 * imgPadding);
		int targetTranslation = (int)((0.5 * indicatorWidth * items.size())-(0.5 * indicatorWidth * maxVisible) - ((_position == -1 ? scroller.getScrollX() : _position) * convertValue));

		int previousPosition = findIndexForTranslation((int)indicatorContainer.getTranslationX());
		int newPosition = findIndexForTranslation((int)targetTranslation);

		indicatorContainer.setTranslationX(targetTranslation);

		if(previousPosition != newPosition)
		{
			for(int i = 0; i < maxVisible; i++)
			{
				itemIndicators.get(previousPosition + i).setSelected(false);
			}
			for(int i = 0; i < maxVisible; i++)
			{
				itemIndicators.get(newPosition + i).setSelected(true);
			}
		}
	}

	private void fadeIndicatorIn()
	{
		fadeInTime = System.currentTimeMillis();
		if(isFadedIn)
		{
			return;
		}
		indicatorLayer.startAnimation(fadeIn);
		isFadedIn = true;
		new Handler().postDelayed(new Runnable() {
			public void run() {
				processFadeQueue();
			}
		}, 500);
	}

	private void fadeIndicatorOut()
	{
		indicatorLayer.startAnimation(fadeOut);
		isFadedIn = false;
	}

	private void processFadeQueue()
	{
		long timePassed = System.currentTimeMillis() - fadeInTime;
		if(timePassed >= 500)
		{
			fadeIndicatorOut();
		}
		else
		{
			new Handler().postDelayed(new Runnable() {
				public void run() {
					processFadeQueue();
				}
			}, 3000 - timePassed);
		}
	}

	private int getFocusIndex(int position)
	{
		return (int)(position/(imgWidth + 2.0 * imgPadding) + 0.5);
	}

	private void setFocusIndex(int index)
	{
		currentFocusIndex = Math.max(0, Math.min(totalItems, index));
	}

	@Override
	protected void onSizeChanged(int xNew, int yNew, int xOld, int yOld)
	{
		super.onSizeChanged(xNew, yNew, xOld, yOld);
		calculateSize(xNew);
	}

	private void calculateSize(int width)
	{
		maxVisible = (int)Math.floor(width / (imgWidth + 2 * imgPadding));
		scroller.setLayoutParams(new LayoutParams(maxVisible * (imgWidth + 2 * imgPadding), LayoutParams.WRAP_CONTENT));
		imageContainer.setVisibility(View.VISIBLE);
		if(items.size() > maxVisible)
		{
			Handler handler = new Handler(Looper.getMainLooper());
			handler.post(new Runnable() {
				@Override
				public void run() {
					for( int i = 0; i < items.size(); i++)
						addItemIndicator();
					fadeIndicatorIn();
				}
			});
		}
	}

	public void addImage(ImageView image)
	{
		scroller.measure(getWidth(), getHeight());
		LayoutParams sizeParams = new LayoutParams((int)imgWidth + 2*imgPadding, (int)imgHeight + 2*imgPadding);
		image.setPadding(imgPadding, imgPadding, imgPadding, imgPadding);
		image.setLayoutParams(sizeParams);
		image.setScaleType(ImageView.ScaleType.FIT_XY);
		imageContainer.addView(image);
		itemImages.add(image);
		totalItems++;
	}

	//This function will be the overriden one for the general case
	public void addItemDisplay(Object _item)
	{
		Item item = (Item)_item;
		addImage(Downloader.downloadBitmap(item.getCode(), new ImageView(D2LApplication.getContext())));
		if(shouldSelectItem(_item))
		{
			itemImages.get(itemImages.size()-1).setBackgroundColor(R.color.item_selected);
		}
	}

	private void addItemIndicator()
	{
		if(indicator == null)
			setupIndicator();
		ImageView itemIndicator = new ImageView(getContext());
		itemIndicator.setImageDrawable(getResources().getDrawable(R.drawable.item_holder_indicator));
		itemIndicator.setScaleType(ImageView.ScaleType.FIT_XY);
		int padding = (int)DisplayUtility.convertDpToPixel(3.0f);
		itemIndicator.setPadding(padding, padding, padding, padding);
		itemIndicators.add(itemIndicator);
		if(itemIndicators.size() <= maxVisible)
			itemIndicator.setSelected(true);
		indicator.addView(itemIndicator);
	}

	public void addItem(Object item)
	{
		items.add(item);
		addItemDisplay(item);
	}

	public void addItems(ArrayList<Object> _items)
	{
		items = _items;
		for(int i = 0; i < _items.size(); i++)
			addItemDisplay(_items.get(i));
	}

	@Override
	public void onClick(View v)
	{
		deselectItem();
		int selected = (int)Math.min(items.size() - 1, ((int) lastTouchX / (imgWidth + 2*imgPadding)));
		currentSelectedIndex = selected;
		if(listener != null)
		{
			listener.onItemSelected(items.get(selected), this);
			itemImages.get(selected).setBackgroundColor(R.color.item_selected);
		}
	}

	@Override
	public void deselectItem()
	{
		if(currentSelectedIndex >= 0)
		{
			itemImages.get(currentSelectedIndex).setBackgroundColor(Color.TRANSPARENT);
		}
	}

	@Override
	public int getIdentifier()
	{
		return identifier;
	}
}
