package app.insight.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import app.insight.R;
import app.insight.model.SolData;

public class SolAdapter extends RecyclerView.Adapter<SolAdapter.SolViewHolder> {
    private List<SolData> sols = new ArrayList<>();

    @NonNull
    @Override
    public SolViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_sol, parent, false);
        return new SolViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SolViewHolder holder, int position) {
        SolData sol = sols.get(position);
        holder.solNumberTextView.setText("Sol " + sol.getSolNumber());
        holder.temperatureTextView.setText("Température: " + sol.getTemperature() + "°C");
        holder.pressureTextView.setText("Pression: " + sol.getPressure() + " Pa");
    }

    @Override
    public int getItemCount() {
        return sols.size();
    }

    public void setSols(List<SolData> sols) {
        this.sols = sols;
        notifyDataSetChanged();
    }

    static class SolViewHolder extends RecyclerView.ViewHolder {
        TextView solNumberTextView;
        TextView temperatureTextView;
        TextView pressureTextView;

        SolViewHolder(View itemView) {
            super(itemView);
            solNumberTextView = itemView.findViewById(R.id.solNumberTextView);
            temperatureTextView = itemView.findViewById(R.id.temperatureTextView);
            pressureTextView = itemView.findViewById(R.id.pressureTextView);
        }
    }
}
