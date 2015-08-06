package project.tddd80.keval992.liu.ida.se.navigationbase.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.Serializable;
import java.util.List;

import project.tddd80.keval992.liu.ida.se.navigationbase.R;

/**
 * Basic adapter to use when displaying a model with a card layout.
 */
public abstract class ModelRecyclerViewAdapter<Model extends Serializable> extends
        RecyclerView.Adapter<ModelRecyclerViewAdapter.ModelCardViewHolder> {

    private List<Model> models;

    public ModelRecyclerViewAdapter(List<Model> models) {
        this.models = models;
    }

    @Override
    public ModelCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.layout_card, parent, false);
        return new ModelRecyclerViewAdapter.ModelCardViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public List<Model> getModels() {
        return models;
    }

    public static class ModelCardViewHolder extends CardViewHolder {

        public ModelCardViewHolder(View itemView) {
            super(itemView);
        }
    }
}
