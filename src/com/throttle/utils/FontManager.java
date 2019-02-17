package com.throttle.utils;

import android.content.Context;
import android.graphics.Typeface;

public class FontManager {

	private static FontManager fm;
	private static final String BEBAS = "font/bebas.TTF";
	private static final String DROIDSANS = "font/droidsans.ttf";
	private static final String DROIDSANS_BOLD = "font/droidsans-bold.ttf";
	

	private Typeface eBebas;
	private Typeface eDroidSans;
	private Typeface eDroisSansBold;

	private Context mcontext;

	private FontManager(Context context) {
		mcontext = context;
		readFonts();
	}

	public static FontManager getFontManager(Context context) {
		if (fm == null) {
			fm = new FontManager(context);
		}
		return fm;
	}

	private void readFonts() {

		eBebas = Typeface.createFromAsset(mcontext.getAssets(),
				BEBAS);
		eDroidSans = Typeface.createFromAsset(mcontext.getAssets(),
				DROIDSANS);
		eDroisSansBold = Typeface.createFromAsset(mcontext.getAssets(),
				DROIDSANS_BOLD);
		

	}

	public Typeface geteBebas() {
		return eBebas;
	}

	public void seteBebas(Typeface eBebas) {
		this.eBebas = eBebas;
	}

	public Typeface geteDroidSans() {
		return eDroidSans;
	}

	public void seteDroidSans(Typeface eDroidSans) {
		this.eDroidSans = eDroidSans;
	}

	public Typeface geteDroisSansBold() {
		return eDroisSansBold;
	}

	public void seteDroisSansBold(Typeface eDroisSansBold) {
		this.eDroisSansBold = eDroisSansBold;
	}

	
	

}
