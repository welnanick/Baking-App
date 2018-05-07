package com.nickwelna.bakingapp;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.nickwelna.bakingapp.models.Step;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class StepDetailsFragment extends Fragment implements OnClickListener {

    ArrayList<Step> steps;
    int numberOfSteps;
    int currentStep;
    ExoPlayer exoPlayer;
    @BindView(R.id.step_video)
    PlayerView stepVideo;
    @Nullable
    @BindView(R.id.description_text_view)
    TextView descriptionTextView;
    @Nullable
    @BindView(R.id.previous_step_button)
    Button previousStepButton;
    @Nullable
    @BindView(R.id.next_step_button)
    Button nextStepButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

            steps = getArguments().getParcelableArrayList(getString(R.string.recipe_steps_key));
            numberOfSteps = getArguments().getInt(getString(R.string.number_of_steps_key));
            currentStep = getArguments().getInt(getString(R.string.current_step_key));

        }

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_step_details, container, false);
        ButterKnife.bind(this, rootView);

        String videoUri = steps.get(currentStep).getVideoURL();

        if (videoUri != null && !TextUtils.isEmpty(videoUri)) {

            if (savedInstanceState == null) {

                initializePlayer(videoUri, -1);

            }
            else {

                long position = savedInstanceState.getLong(getString(R.string.video_position_key));
                initializePlayer(videoUri, position);

            }

        }
        else {

            stepVideo.setVisibility(View.GONE);

        }
        if (getActivity().getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_LANDSCAPE &&
                !getActivity().getResources().getBoolean(R.bool.isTablet)) {

            ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
            getActivity().getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        }
        else {

            descriptionTextView.setText(steps.get(currentStep).getDescription());

            if (!getActivity().getResources().getBoolean(R.bool.isTablet)) {

                if (steps.get(currentStep).getId() == 0) {

                    previousStepButton.setEnabled(false);

                }
                else {

                    previousStepButton.setOnClickListener(this);

                }

                if (steps.get(currentStep).getId() < numberOfSteps - 1) {

                    nextStepButton.setOnClickListener(this);

                }
                else {

                    nextStepButton.setEnabled(false);

                }

            }

        }

        return rootView;

    }

    private void initializePlayer(String uriString, long position) {

        TrackSelector trackSelector = new DefaultTrackSelector();
        LoadControl loadControl = new DefaultLoadControl();
        exoPlayer = ExoPlayerFactory
                .newSimpleInstance(new DefaultRenderersFactory(getContext()), trackSelector,
                        loadControl);

        stepVideo.requestFocus();
        stepVideo.setPlayer(exoPlayer);

        DefaultDataSourceFactory dataFactory = new DefaultDataSourceFactory(getContext(), "ua");
        MediaSource mediaSource = new ExtractorMediaSource.Factory(dataFactory)
                .createMediaSource(Uri.parse(uriString));

        exoPlayer.prepare(mediaSource);
        if (position != -1) {

            exoPlayer.seekTo(position);

        }
        exoPlayer.setPlayWhenReady(true);

    }

    /**
     * Release ExoPlayer.
     */
    private void releasePlayer() {

        if (exoPlayer != null) {

            exoPlayer.stop();
            exoPlayer.release();
            exoPlayer = null;

        }

    }

    /**
     * Release Player when the view is destroyed.
     */
    @Override
    public void onDestroyView() {

        super.onDestroyView();
        releasePlayer();

    }

    @Override
    public void onPause() {

        super.onPause();
        if (exoPlayer != null) {

            exoPlayer.setPlayWhenReady(false);

        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {

        super.onSaveInstanceState(outState);
        outState.putLong(getString(R.string.video_position_key), exoPlayer.getContentPosition());

    }

    @Override
    public void onClick(View v) {

        if (v.equals(previousStepButton)) {

            StepDetailsFragment stepDetailsFragment = new StepDetailsFragment();

            Bundle arguments = new Bundle();
            arguments.putParcelableArrayList(getString(R.string.recipe_steps_key), steps);
            arguments.putInt(getString(R.string.current_step_key), currentStep - 1);
            arguments.putInt(getString(R.string.number_of_steps_key), numberOfSteps);

            stepDetailsFragment.setArguments(arguments);

            getFragmentManager().beginTransaction()
                                .replace(R.id.fragment_holder, stepDetailsFragment)
                                .addToBackStack(getString(R.string.fragment_tag_key)).commit();

        }
        else if (v.equals(nextStepButton)) {

            StepDetailsFragment stepDetailsFragment = new StepDetailsFragment();

            Bundle arguments = new Bundle();
            arguments.putParcelableArrayList(getString(R.string.recipe_steps_key), steps);
            arguments.putInt(getString(R.string.current_step_key), currentStep + 1);
            arguments.putInt(getString(R.string.number_of_steps_key), numberOfSteps);

            stepDetailsFragment.setArguments(arguments);

            getFragmentManager().beginTransaction()
                                .replace(R.id.fragment_holder, stepDetailsFragment)
                                .addToBackStack(getString(R.string.fragment_tag_key)).commit();

        }

    }

}
