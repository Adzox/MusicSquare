package project.tddd80.keval992.liu.ida.se.navigationbase.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import project.tddd80.keval992.liu.ida.se.navigationbase.R;
import project.tddd80.keval992.liu.ida.se.navigationbase.adapters.ModelRecyclerViewAdapter;

/**
 * A simple {@link Fragment} subclass with a recyclerView used to display a model in a list..
 */
public abstract class ModelListFragment<Model extends Serializable> extends Fragment {

    private RecyclerView recyclerView;
    private Class modelRecyclerViewAdapterClass;
    private ModelRecyclerViewAdapter<Model> modelRecyclerViewAdapter;

    public ModelListFragment() {
    }

    public final void setModelRecyclerViewAdapterClass(Class modelRecyclerViewAdapterClass) {
        if (ModelRecyclerViewAdapter.class.isAssignableFrom(modelRecyclerViewAdapterClass)) {
            this.modelRecyclerViewAdapterClass = modelRecyclerViewAdapterClass;
        } else {
            throw new IllegalArgumentException("ModelRecyclerViewAdapterClass must extend ModelRecyclerViewAdapter!");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_model_list, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.model_list_list);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(),
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        ModelListFragment.this.itemClicked(view, position);
                    }
                }));
        setUpRecyclerView();
        return view;
    }

    private void setUpRecyclerView() {
        modelRecyclerViewAdapter = getModelRecyclerViewAdapter(new ArrayList<Model>());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(modelRecyclerViewAdapter);
    }

    public final void setItems(List<Model> models) {
        modelRecyclerViewAdapter.getModels().clear();
        modelRecyclerViewAdapter.getModels().addAll(models);
        modelRecyclerViewAdapter.notifyDataSetChanged();
    }

    // For the application to work, this Reflection method must always work!
    // Since all adapters extending ModelRecyclerViewAdapter must have a constructor with the
    // first argument a list, the likelyhood of this method failing is slim.
    private final ModelRecyclerViewAdapter<Model> getModelRecyclerViewAdapter(List<Model> models) {
        try {
            return (ModelRecyclerViewAdapter<Model>) modelRecyclerViewAdapterClass.getConstructor(List.class).newInstance(models);
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected abstract void itemClicked(View view, int position);
}
