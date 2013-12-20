package cz.nuc.wheelgo.androidclient;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import cz.nuc.wheelgo.androidclient.service.dto.ProblemDto;

/**
 * Created by mist on 20.12.13.
 */
public class ProblemToAvoidListFragment extends ListFragment {

    ArrayAdapter<ProblemDto> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        adapter = new ArrayAdapter<ProblemDto>(inflater.getContext(), android.R.layout.simple_list_item_1,((MainActivity)getActivity()).getNavigationProblemsToAvoid());
        setListAdapter(adapter);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        ((MainActivity)getActivity()).showProblemOnMap(adapter.getItem(position));
    }
}