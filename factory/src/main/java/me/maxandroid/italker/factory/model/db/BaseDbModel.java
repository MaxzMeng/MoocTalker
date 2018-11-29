package me.maxandroid.italker.factory.model.db;

import com.raizlabs.android.dbflow.structure.BaseModel;

import me.maxandroid.italker.factory.utils.DiffUiDataCallback;

public abstract class BaseDbModel<Model> extends BaseModel
        implements DiffUiDataCallback.UiDataDiffer<Model> {
}
