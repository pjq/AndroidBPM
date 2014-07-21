package me.pjq.jacocoandroid.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import at.markushi.ui.CircleButton;
import me.pjq.jacocoandroid.PicassoActivity_;
import me.pjq.jacocoandroid.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CirculeButtonFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class CirculeButtonFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "sectionNumber";

    // TODO: Rename and change types of parameters
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private CircleButton startPcassoButton;
    private int sectionNumber;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param sectionNumber Parameter 2.
     * @return A new instance of fragment CirculeButtonFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CirculeButtonFragment newInstance(int sectionNumber) {
        CirculeButtonFragment fragment = new CirculeButtonFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }
    public CirculeButtonFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            sectionNumber = getArguments().getInt(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_circulebutton, container, false);

        startPcassoButton = (CircleButton) rootView.findViewById(R.id.startPicassoButton);
        startPcassoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                    startPcassoButton.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            Intent intent = new Intent();
//                            intent.setClass(getActivity(), PicassoActivity.class);
//                            startActivity(intent);
//                        }
//                    }, 200);
                Intent intent = new Intent();
                intent.setClass(getActivity(), PicassoActivity_.class);
                startActivity(intent);
            }
        });

       return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(String id) {
        if (mListener != null) {
            mListener.onFragmentInteraction(id);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
