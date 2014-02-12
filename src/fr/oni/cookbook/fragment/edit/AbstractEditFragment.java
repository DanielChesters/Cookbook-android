package fr.oni.cookbook.fragment.edit;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import fr.oni.cookbook.dialog.edit.EditDialogListener;
import fr.oni.cookbook.model.Data;

public abstract class AbstractEditFragment extends Fragment implements EditDialogListener {
    protected Data data;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        data = (Data) getActivity().getApplicationContext();
        setHasOptionsMenu(true);
    }
}
