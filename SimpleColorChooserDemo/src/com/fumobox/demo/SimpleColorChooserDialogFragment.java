package com.fumobox.demo;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class SimpleColorChooserDialogFragment extends DialogFragment {

	private OnColorChangedListener _listener;
	
	public static void show(int title, int color, OnColorChangedListener listener, Activity act) {
		SimpleColorChooserDialogFragment d = new SimpleColorChooserDialogFragment();
		d._listener = listener;
		Bundle args = new Bundle();
		args.putInt("title", title);
		args.putInt("color", color);
		d.setArguments(args);
		d.show(act.getFragmentManager(), "dialog");
	}
	
	public interface OnColorChangedListener {
		void colorChanged(int color, int r, int g, int b);
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		SimpleColorChooserDialog d = new SimpleColorChooserDialog(this.getActivity());
		return d;
	}
	
	private class SimpleColorChooserDialog extends Dialog {

		private int _color;
		private int _r, _g, _b;
		
		private CheckBox _cb_mono;

		private TextView _tv01;

		private ColorBoxView _colbox;

		private SeekBar[] _sb;

		public SimpleColorChooserDialog(Context context) {
			super(context);
		}

		public void onCreate(Bundle savedInstanceState) {
			setCanceledOnTouchOutside(true);
			setContentView(this.getLayoutInflater().inflate(R.layout.color_chooser, null));
			
			setTitle(getArguments().getString("title"));
			
			_color = getArguments().getInt("color");
			
			_colbox = (ColorBoxView) findViewById(R.id.colbox);
			_sb = new SeekBar[3];
			_sb[0] = (SeekBar) findViewById(R.id.sb_gap);
			_sb[0].setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
				@Override
				public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
					if (!fromUser) {
						return;
					}
					_r = progress;
					if (_cb_mono.isChecked()) {
						_sb[1].setProgress(progress);
						_sb[2].setProgress(progress);
						_g = progress;
						_b = progress;
					}
					combineColor();
				}

				@Override
				public void onStartTrackingTouch(SeekBar seekBar) {
				}

				@Override
				public void onStopTrackingTouch(SeekBar seekBar) {
				}
			});

			_sb[1] = (SeekBar) findViewById(R.id.sb_g);
			_sb[1].setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
				@Override
				public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
					if (!fromUser) {
						return;
					}
					_g = progress;
					if (_cb_mono.isChecked()) {
						_sb[0].setProgress(progress);
						_sb[2].setProgress(progress);
						_r = progress;
						_b = progress;
					}
					combineColor();
				}

				@Override
				public void onStartTrackingTouch(SeekBar seekBar) {
				}

				@Override
				public void onStopTrackingTouch(SeekBar seekBar) {
				}
			});

			_sb[2] = (SeekBar) findViewById(R.id.sb_b);
			_sb[2].setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
				@Override
				public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
					if (!fromUser) {
						return;
					}
					_b = progress;
					if (_cb_mono.isChecked()) {
						_sb[0].setProgress(progress);
						_sb[1].setProgress(progress);
						_r = progress;
						_g = progress;
					}
					combineColor();
				}

				@Override
				public void onStartTrackingTouch(SeekBar seekBar) {
				}

				@Override
				public void onStopTrackingTouch(SeekBar seekBar) {
				}
			});

			_tv01 = (TextView) findViewById(R.id.tv01);

			_cb_mono = (CheckBox) findViewById(R.id.cb_marge);
			_cb_mono.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					if (_cb_mono.isChecked()) {
						_r = _sb[0].getProgress();
						_sb[1].setProgress(_r);
						_sb[2].setProgress(_r);
						_g = _r;
						_b = _r;
						combineColor();
					} else {

					}
				}
			});

			Button bt_ok = (Button) findViewById(R.id.bt_ok);
			bt_ok.setOnClickListener(new android.view.View.OnClickListener() {
				@Override
				public void onClick(View v) {
					SimpleColorChooserDialog.this.dismiss();
					if (_listener != null) {
						_listener.colorChanged(_color, _r, _g, _b);
					}
				}
			});

			Button bt_cancel = (Button) findViewById(R.id.bt_cancel);
			bt_cancel.setOnClickListener(new android.view.View.OnClickListener() {
				@Override
				public void onClick(View v) {
					dismiss();
				}
			});
			
			adjustBar();
		}

		public void adjustBar() {
			_r = (_color >>> 16) & 0xff;
			_g = (_color >>> 8) & 0xff;
			_b = (_color >>> 0) & 0xff;
			_sb[0].setProgress(_r);
			_sb[1].setProgress(_g);
			_sb[2].setProgress(_b);
			_colbox.setColor(_color);
			_tv01.setText("#" + Integer.toHexString(_color & 0x00ffffff) + " (" + _r + ", " + _g + ", " + _b + ")");
		}

		public void combineColor() {
			_color = 0xff000000;
			_color |= _r << 16;
			_color |= _g << 8;
			_color |= _b;
			_colbox.setColor(_color);
			_tv01.setText("#" + Integer.toHexString(_color & 0x00ffffff) + " (" + _r + ", " + _g + ", " + _b + ")");
		}

	}
	
}
