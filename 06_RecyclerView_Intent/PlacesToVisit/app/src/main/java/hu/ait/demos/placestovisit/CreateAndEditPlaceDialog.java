package hu.ait.demos.placestovisit;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Date;

import hu.ait.demos.placestovisit.data.Place;

public class CreateAndEditPlaceDialog extends DialogFragment {

    public interface PlaceHandler {
        public void onNewPlaceCreated(Place place);

        public void onPlaceUpdated(Place place);
    }

    private PlaceHandler placeHandler;
    private Spinner spinnerPlaceType;
    private EditText etPlace;
    private EditText etPlaceDesc;
    private Place placeToEdit = null;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof PlaceHandler) {
            placeHandler = (PlaceHandler)context;
        } else {
            throw new RuntimeException(
                    getString(R.string.error_wrong_interface));
        }
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.title_new_place));

        View rootView = getActivity().getLayoutInflater().inflate(R.layout.dialog_create_place, null);


        spinnerPlaceType = (Spinner) rootView.findViewById(R.id.spinnerPlaceType);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.placetypes_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPlaceType.setAdapter(adapter);

        etPlace = (EditText) rootView.findViewById(R.id.etPlaceName);
        etPlaceDesc = (EditText) rootView.findViewById(R.id.etPlaceDesc);

        if (getArguments() != null &&
                getArguments().containsKey(MainActivity.KEY_EDIT)) {
            Place placeToEdit = (Place) getArguments().getSerializable(MainActivity.KEY_EDIT);
            etPlace.setText(placeToEdit.getPlaceName());
            etPlaceDesc.setText(placeToEdit.getDescription());
            spinnerPlaceType.setSelection(placeToEdit.getPlaceTypeAsEnum().getValue());
        }

        builder.setView(rootView);

        builder.setPositiveButton(getString(R.string.btn_save), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        return builder.create();
    }

    //onStart() is where dialog.show() is actually called on
    //the underlying dialog, so we have to do it there or
    //later in the lifecycle.
    //Doing it in onResume() makes sure that even if there is a config change
    //environment that skips onStart then the dialog will still be functioning
    //properly after a rotation.
    @Override
    public void onResume()
    {
        super.onResume();
        final AlertDialog d = (AlertDialog)getDialog();
        if(d != null)
        {
            Button positiveButton = (Button) d.getButton(Dialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if (!TextUtils.isEmpty(etPlace.getText())) {
                        if (getArguments() != null &&
                                getArguments().containsKey(MainActivity.KEY_EDIT)) {
                            Place placeToEdit = (Place) getArguments().getSerializable(MainActivity.KEY_EDIT);
                            placeToEdit.setPlaceName(etPlace.getText().toString());
                            placeToEdit.setDescription(etPlaceDesc.getText().toString());
                            placeToEdit.setPlaceType(spinnerPlaceType.getSelectedItemPosition());

                            placeHandler.onPlaceUpdated(placeToEdit);
                        } else {
                            Place place = new Place(
                                    etPlace.getText().toString(),
                                    etPlaceDesc.getText().toString(),
                                    System.currentTimeMillis(),
                                    spinnerPlaceType.getSelectedItemPosition()
                            );

                            placeHandler.onNewPlaceCreated(place);
                        }

                        d.dismiss();

                    } else {
                        etPlace.setError(getString(R.string.error_field_empty));
                    }

                }
            });
        }
    }
}
