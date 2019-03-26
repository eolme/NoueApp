package website.petrov.noue.viewmodel;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Looper;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import website.petrov.noue.common.json.rpc.Request;
import website.petrov.noue.common.json.rpc.RequestBuilder;
import website.petrov.noue.common.json.rpc.Response;
import website.petrov.noue.common.model.IdentifiableModel;
import website.petrov.noue.common.viewmodel.BaseMutableContextViewModel;
import website.petrov.noue.model.FeedModel;
import website.petrov.noue.repository.data.StorageRepository;
import website.petrov.noue.repository.data.StorageShared;
import website.petrov.noue.repository.fetch.APIService;
import website.petrov.noue.repository.fetch.api.SyncAPI;
import website.petrov.noue.utils.CastUtils;
import website.petrov.noue.utils.ContextUtils;
import website.petrov.noue.utils.DebugUtils;
import website.petrov.noue.view.activity.ProjectActivity;

import static website.petrov.noue.utils.Constants.API.METHOD_FEED;

public final class FeedViewModel extends BaseMutableContextViewModel {
    @Nullable
    private Disposable subscription;

    public FeedViewModel(@NonNull Application application) {
        super(application);
    }

    public static void onClick(@NonNull View view) {
        final Activity activity = ContextUtils.getActivity(view.getContext());
        if (activity == null) {
            return;
        }

        activity.startActivity(new Intent(activity, ProjectActivity.class));
    }

    @Override
    public void setData(@NonNull List<IdentifiableModel> newData) {
        if (!newData.isEmpty()) {
            StorageRepository.getInstance()
                    .insertFeed(CastUtils.toArray(newData, FeedModel[].class));
        }
    }

    @Override
    public void load() {
        final SyncAPI api = APIService.getInstance().create(SyncAPI.class);
        final Request request = new RequestBuilder()
                .withMethod(METHOD_FEED)
                .withParams(new Object[]{
                        ContextUtils.getDeviceId(getApplication()),
                        StorageShared.getInstanceId()
                })
                .build();
        final Observable<Response> feed = api.load(request);
        subscription = feed.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.from(Looper.getMainLooper(), true))
                .subscribe(feedResponse -> {
                    if (feedResponse.result instanceof ArrayList) {
                        DebugUtils.log("Feed is ok", feedResponse);

                        //noinspection unchecked
                        final List<LinkedTreeMap<Object, Object>> result = (ArrayList<LinkedTreeMap<Object, Object>>) feedResponse.result;
                        final List<IdentifiableModel> models = new ArrayList<>(result.size());
                        for (LinkedTreeMap<Object, Object> map : result) {
                            models.add(new FeedModel(
                                    (String) map.get("title"),
                                    (String) map.get("description"),
                                    new ArrayList<>(),
                                    CastUtils.toInteger((Double) map.get("id"))
                            ));
                        }
                        this.models.postValue(models);
                    } else {
                        DebugUtils.log("Feed is corrupted", feedResponse);

                        // TODO: Show dialog
                    }
                }, error -> {
                    DebugUtils.log("Fetch failed", error);

                    // TODO: Show dialog
                });
    }

    @Override
    protected void onCleared() {
        if (subscription != null) {
            subscription.dispose();
        }
    }
}
