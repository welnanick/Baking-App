package com.nickwelna.bakingapp.fragments;

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
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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
import com.nickwelna.bakingapp.R;
import com.nickwelna.bakingapp.models.Step;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * A simple {@link Fragment} subclass.
 */
public class StepDetailsFragment extends Fragment implements OnClickListener {

    private ArrayList<Step> steps;
    private int numberOfSteps;
    private int currentStep;
    private ExoPlayer exoPlayer;
    private long backgroundPosition;
    private boolean initialized;
    @BindView(R.id.step_video)
    PlayerView stepVideo;
    @BindView(R.id.description_text_view)
    TextView descriptionTextView;
    @Nullable
    @BindView(R.id.previous_step_button)
    Button previousStepButton;
    @Nullable
    @BindView(R.id.next_step_button)
    Button nextStepButton;
    @Nullable
    @BindView(R.id.thumbnail_image_view)
    ImageView thumbnail;

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

                initializePlayer(videoUri, -1, true);

            }
            else {

                if (savedInstanceState.containsKey(getString(R.string.video_position_key)) &&
                        savedInstanceState.containsKey(getString(R.string.paused_boolean_key))) {

                    long position =
                            savedInstanceState.getLong(getString(R.string.video_position_key));
                    boolean paused =
                            savedInstanceState.getBoolean(getString(R.string.paused_boolean_key));
                    initializePlayer(videoUri, position, paused);

                }

            }
            initialized = true;

        }
        else {

            stepVideo.setVisibility(View.GONE);

        }

        if (steps.get(currentStep).getThumbnailURL() != null &&
                !TextUtils.isEmpty(steps.get(currentStep).getThumbnailURL())) {

            if (!steps.get(currentStep).getThumbnailURL().contains(".mp4")) {

                Glide.with(this).load(steps.get(currentStep).getThumbnailURL()).into(thumbnail);

            }

        }

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE &&
                !getResources().getBoolean(R.bool.isTablet)) {

            if (initialized) {

                ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
                getActivity().getWindow().getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
                descriptionTextView.setVisibility(View.GONE);

            }
            else {

                descriptionTextView.setText(steps.get(currentStep).getDescription());
                stepVideo.setVisibility(View.GONE);

            }

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

    private void initializePlayer(String uriString, long position, boolean paused) {

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
        exoPlayer.setPlayWhenReady(paused);

    }

    /**
     * Release ExoPlayer.
     */
    private void releasePlayer() {

        Timber.d("releasePlayer");
        if (exoPlayer != null) {

            exoPlayer.stop();
            exoPlayer.release();
            exoPlayer = null;

        }
        initialized = false;

    }

    @Override
    public void onDestroyView() {

        super.onDestroyView();
        Timber.d("onDestroy");
        // We were instructed to release the player here in the lessons. Not in onStop.
        initialized = false;

    }

    @Override
    public void onStop() {

        super.onStop();
        Timber.d("onStop");
        backgroundPosition = exoPlayer.getContentPosition();
        releasePlayer();

    }

    @Override
    public void onStart() {

        super.onStart();
        if (!initialized) {

            initializePlayer(steps.get(currentStep).getVideoURL(), backgroundPosition, false);
            initialized = true;

        }

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {

        super.onSaveInstanceState(outState);
        if (exoPlayer != null) {

            outState.putLong(getString(R.string.video_position_key),
                    exoPlayer.getContentPosition());
            outState.putBoolean(getString(R.string.paused_boolean_key),
                    exoPlayer.getPlayWhenReady());
            exoPlayer.setPlayWhenReady(false);
            Timber.d("onSaveInstanceState");

        }

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

            if (getFragmentManager() != null) {

                getFragmentManager().beginTransaction()
                                    .replace(R.id.fragment_holder, stepDetailsFragment)
                                    .addToBackStack(getString(R.string.fragment_tag_key)).commit();

            }

        }
        else if (v.equals(nextStepButton)) {

            StepDetailsFragment stepDetailsFragment = new StepDetailsFragment();

            Bundle arguments = new Bundle();
            arguments.putParcelableArrayList(getString(R.string.recipe_steps_key), steps);
            arguments.putInt(getString(R.string.current_step_key), currentStep + 1);
            arguments.putInt(getString(R.string.number_of_steps_key), numberOfSteps);

            stepDetailsFragment.setArguments(arguments);

            if (getFragmentManager() != null) {

                getFragmentManager().beginTransaction()
                                    .replace(R.id.fragment_holder, stepDetailsFragment)
                                    .addToBackStack(getString(R.string.fragment_tag_key)).commit();

            }

        }

    }

}
