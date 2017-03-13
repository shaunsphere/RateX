package xevate.com.ratex;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TienArrayAdapter extends ArrayAdapter<String> {
	private final Context context;
	private final String[] values;

	public TienArrayAdapter(Context context, String[] values) {
		super(context, R.layout.rowlayout, values);
		this.context = context;
		this.values = values;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.rowlayout, parent, false);
		TextView textView = (TextView) rowView.findViewById(R.id.tvtien);
		ImageView imageView = (ImageView) rowView.findViewById(R.id.flagicon);
		textView.setText(values[position]);

		// Change icon based on name
		String s = values[position];

		System.out.println(s);

		if (s.contains("AUD")) {
			imageView.setImageResource(R.drawable.aud);
		} else if (s.contains("BGN")) {
			imageView.setImageResource(R.drawable.bgn);
		} else if (s.contains("BRL")) {
			imageView.setImageResource(R.drawable.brl);
		}  else if (s.contains("CAD")) {
			imageView.setImageResource(R.drawable.cad);
		} else if (s.contains("CHF")) {
			imageView.setImageResource(R.drawable.chf);
		} else if (s.contains("CNY")) {
			imageView.setImageResource(R.drawable.cny);
		} else if (s.contains("CZK")) {
			imageView.setImageResource(R.drawable.czk);
		} else if (s.contains("DKK")) {
			imageView.setImageResource(R.drawable.android_logo);
		} else if (s.contains("GBP")) {
			imageView.setImageResource(R.drawable.gbp);
		} else if (s.contains("HKD")) {
			imageView.setImageResource(R.drawable.hkd);
		} else if (s.contains("HRK")) {
			imageView.setImageResource(R.drawable.hrk);
		} else if (s.contains("HUF")) {
			imageView.setImageResource(R.drawable.huf);
		} else if (s.contains("IDR")) {
			imageView.setImageResource(R.drawable.idr);
		} else if (s.contains("ILS")) {
			imageView.setImageResource(R.drawable.ils);
		} else if (s.contains("INR")) {
			imageView.setImageResource(R.drawable.inr);
		} else if (s.contains("JPY")) {
			imageView.setImageResource(R.drawable.jpy);
		}


		else if (s.contains("KRW")) {
			imageView.setImageResource(R.drawable.krw);
		} else if (s.contains("MXN")) {
			imageView.setImageResource(R.drawable.mxn);
		} else if (s.contains("MYR")) {
			imageView.setImageResource(R.drawable.xrate50);
		} else if (s.contains("NOK")) {
			imageView.setImageResource(R.drawable.nok);
		} else if (s.contains("NZD")) {
			imageView.setImageResource(R.drawable.nzd);
		} else if (s.contains("PHP")) {
			imageView.setImageResource(R.drawable.php);
		} else if (s.contains("PLN")) {
			imageView.setImageResource(R.drawable.pln);
		} else if (s.contains("RON")) {
			imageView.setImageResource(R.drawable.ron);
		} else if (s.contains("RUB")) {
			imageView.setImageResource(R.drawable.rub);
		} else if (s.contains("SGD")) {
			imageView.setImageResource(R.drawable.xrate50);
		} else if (s.contains("THB")) {
			imageView.setImageResource(R.drawable.thb);
		} else if (s.contains("TRY")) {
			imageView.setImageResource(R.drawable.tre);
		} else if (s.contains("ZAR")) {
			imageView.setImageResource(R.drawable.zar);
		}else if (s.contains("EUR")) {
			imageView.setImageResource(R.drawable.eur);
		}



		else {
			imageView.setImageResource(R.drawable.xrate50);
		}

		return rowView;
	}
}
