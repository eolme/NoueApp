package website.petrov.noue.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.internal.LinkedTreeMap;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import website.petrov.noue.common.json.rpc.Request;
import website.petrov.noue.common.json.rpc.RequestBuilder;
import website.petrov.noue.common.json.rpc.Response;
import website.petrov.noue.common.viewmodel.BaseMutableGenericContextViewModel;
import website.petrov.noue.model.ProjectItemModel;
import website.petrov.noue.model.request.DefaultRequest;
import website.petrov.noue.repository.data.StorageRepository;
import website.petrov.noue.repository.data.StorageShared;
import website.petrov.noue.repository.fetch.APIService;
import website.petrov.noue.repository.fetch.api.SyncAPI;
import website.petrov.noue.utilities.Provider;

import static website.petrov.noue.utilities.Constants.API.METHOD_PROJECTS;

public final class ProjectsViewModel extends BaseMutableGenericContextViewModel<ProjectItemModel> {
    private Disposable subscription;

    public ProjectsViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public void load() {
        final StorageRepository repo = StorageRepository.getInstance();
        final List<ProjectItemModel> list = repo.getProjects().getValue();
        if (list != null) {
            this.models.setValue(list);
        }

        final SyncAPI api = APIService.getInstance().create(SyncAPI.class);
        final Request request = new RequestBuilder()
                .withMethod(METHOD_PROJECTS)
                .withParams(new Object[]{
                        new DefaultRequest(Provider.getDeviceId(getApplication()),
                                StorageShared.getInstanceId())
                })
                .build();
        final Observable<Response> feed = api.load(request);
        subscription = feed.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(loginResponse -> {
                    if (loginResponse.result != null) {
                        Log.d("LOGIN-OK", loginResponse.result.toString());
                        Log.d("RESULT", loginResponse.result.getClass().toString());

                        final LinkedTreeMap<?, ?> result = (LinkedTreeMap<?, ?>) loginResponse.result;
                        StorageShared.setAccountName((String) result.get("name"));
                        StorageShared.setAccountAbout((String) result.get("about"));
                        StorageShared.setAccountEmail((String) result.get("name"));

                    } else {
                        Log.d("LOGIN-FAIL", loginResponse.error.message);
                    }
                }, error -> {
                    Log.d("FETCH-FAIL", error.getMessage());
                });
    }

    @Override
    protected void onCleared() {
        if (subscription != null) {
            subscription.dispose();
        }
    }
}
