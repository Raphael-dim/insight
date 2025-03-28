package app.insight.view;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import app.insight.R;
import app.insight.activity.SolDetailActivity;
import app.insight.model.SolData;

public class SolAdapter extends RecyclerView.Adapter<SolAdapter.SolViewHolder> {
    private List<SolData> sols;

    public SolAdapter() {
        this.sols = new ArrayList<>();
    }

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
        holder.bind(sol);

        // Set click listener
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), SolDetailActivity.class);
            intent.putExtra(SolDetailActivity.EXTRA_SOL_DATA, sol);
            intent.putExtra(SolDetailActivity.EXTRA_SOLS_LIST, new ArrayList<>(sols));
            intent.putExtra(SolDetailActivity.EXTRA_CURRENT_INDEX, position);
            v.getContext().startActivity(intent);
        });
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
        private final TextView solNumberTextView;
        private final TextView temperatureTextView;
        private final TextView pressureTextView;

        public SolViewHolder(@NonNull View itemView) {
            super(itemView);
            solNumberTextView = itemView.findViewById(R.id.solNumberTextView);
            temperatureTextView = itemView.findViewById(R.id.temperatureTextView);
            pressureTextView = itemView.findViewById(R.id.pressureTextView);
        }

        public void bind(SolData sol) {
            solNumberTextView.setText("Sol n°" + sol.getSolKey());
            temperatureTextView.setText(String.format("Température : avg: %.2f min: %.2f max: %.2f",
                    sol.getTemperature().getAverage(),
                    sol.getTemperature().getMin(),
                    sol.getTemperature().getMax()));
            pressureTextView.setText(String.format("Pression : avg: %.2f min: %.2f max: %.2f",
                    sol.getPressure().getAverage(),
                    sol.getPressure().getMin(),
                    sol.getPressure().getMax()));
        }
    }
}
