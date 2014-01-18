package com.fumobox.demo;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements SimpleColorChooserDialogFragment.OnColorChangedListener {

	private ColorBoxView _colbox;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		_colbox = (ColorBoxView) findViewById(R.id.colbox);
		
		Button bt = (Button) findViewById(R.id.button1);
		bt.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				SimpleColorChooserDialogFragment.show(R.string.button01, _colbox.getColor(), MainActivity.this, MainActivity.this);
			}
		});
	}

	@Override
	public void colorChanged(int color, int r, int g, int b) {
		_colbox.setColor(color);
	}

}
