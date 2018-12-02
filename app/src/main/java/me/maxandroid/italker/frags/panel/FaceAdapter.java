package me.maxandroid.italker.frags.panel;

import android.view.View;

import java.util.List;

import me.maxandroid.italker.R;
import me.maxandroid.italker.common.widget.recycler.RecyclerAdapter;
import me.maxandroid.italker.face.Face;

public class FaceAdapter extends RecyclerAdapter<Face.Bean> {

    public FaceAdapter(List<Face.Bean> beans, AdapterListener<Face.Bean> listener) {
        super(beans, listener);
    }

    @Override
    protected int getItemViewType(int position, Face.Bean bean) {
        return R.layout.cell_face;
    }

    @Override
    protected ViewHolder<Face.Bean> onCreateViewHolder(View root, int viewType) {
        return new FaceHolder(root);
    }
}
