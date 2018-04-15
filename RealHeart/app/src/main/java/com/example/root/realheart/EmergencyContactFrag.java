package com.example.root.realheart;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EmergencyContactFrag.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EmergencyContactFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EmergencyContactFrag extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    AutoCompleteTextView b1,b2,b3;
    Button save,edit;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    public EmergencyContactFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EmergencyContactFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static EmergencyContactFrag newInstance(String param1, String param2) {
        EmergencyContactFrag fragment = new EmergencyContactFrag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_emergency_contact, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        b1 = (AutoCompleteTextView) view.findViewById(R.id.editText1);
        b2 = (AutoCompleteTextView) view.findViewById(R.id.editText2);
        b3 = (AutoCompleteTextView) view.findViewById(R.id.editText3);
        b1.setEnabled(false);
        b2.setEnabled(false);
        b3.setEnabled(false);
        save=(Button)view.findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String num1=b1.getText().toString();
                String num2=b2.getText().toString();
                String num3=b3.getText().toString();
                if(!num1.equals("") && !num2.equals("") && !num3.equals(""))
                {
                    editor=sp.edit();
                    editor.putString("num1", num1);
                    editor.putString("num2", num2);
                    editor.putString("num3", num3);
                    editor.commit();
                }
            }
        });
        edit=(Button)view.findViewById(R.id.edit);
        edit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                b1.setEnabled(true);
                b2.setEnabled(true);
                b3.setEnabled(true);
            }
        });
        sp = getActivity().getSharedPreferences("emer", Context.MODE_PRIVATE);
        if(sp!=null)
        {
            String num1=sp.getString("num1", null);
            String num2=sp.getString("num2", null);
            String num3=sp.getString("num3", null);
            if(num1!=null && num2!=null && num3!=null )
            {
                b1.setText(num1);
                b2.setText(num2);
                b3.setText(num3);
            }
            else
            {
                b1.setEnabled(true);
                b2.setEnabled(true);
                b3.setEnabled(true);
            }
        }
    }
}
