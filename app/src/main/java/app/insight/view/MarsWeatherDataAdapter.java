package app.insight.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import app.insight.R;
import app.insight.model.MarsWeatherData;

public class MarsWeatherDataAdapter extends ArrayAdapter<MarsWeatherData> {
    private Context context;
    private List<MarsWeatherData> marsWeatherDataList;
    private LayoutInflater inflater;

    public MarsWeatherDataAdapter(Context context, List<MarsWeatherData> marsWeatherDataList) {
        super(context, -1, marsWeatherDataList);
        this.context = context;
        this.marsWeatherDataList = marsWeatherDataList;
        this.inflater = LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.list_items_sols, parent, false);
//        Asteroid asteroid = asteroids.get(position);
//
//        TextView tvName = view.findViewById(R.id.tvListName);
//        TextView tvDist = view.findViewById(R.id.tvListDist);
//        TextView tvMagnitude = view.findViewById(R.id.tvListMagnitude);
//
//        tvName.setText(asteroid.getName());
//        String distance = context.getString(R.string.dist) + ":"
//                + asteroid.getDistance().setScale(2, RoundingMode.HALF_DOWN).toString();
//        tvDist.setText(distance);
//        String magnitude = context.getString(R.string.magnitude) + ":"
//                + asteroid.getMagnitude().setScale(2, BigDecimal.ROUND_HALF_DOWN).toString();
//        tvMagnitude.setText(magnitude);

        return view;

    }

}
