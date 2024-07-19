package poly.edu.vn.asm.homeadapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import poly.edu.vn.asm.R;
import poly.edu.vn.asm.homeadapter.model.HomeModel;
import poly.edu.vn.asm.homeapp.note.NoteActivity;

public class AdapterHome extends RecyclerView.Adapter<AdapterHome.HomeViewHolder> {
    private Context context;
    private ArrayList<HomeModel> listUser;

    public AdapterHome(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<HomeModel> list){
        this.listUser = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_home, parent, false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        HomeModel homeModel = listUser.get(position);
        if(homeModel == null){
            return;
        }
        holder.ivAvatar.setImageResource(homeModel.getImage());
        holder.tvName.setText(homeModel.getName());

        // Xử lý sự kiện click cho ảnh R.drawable.running
        if(homeModel.getImage() == R.drawable.all){
            holder.ivAvatar.setOnClickListener(v -> {
                context.startActivity(new Intent(context, NoteActivity.class));
            });
        }
    }

    @Override
    public int getItemCount() {
        if(listUser != null ){
            return listUser.size();
        }
        return 0;
    }

    public static class HomeViewHolder extends RecyclerView.ViewHolder {
        ImageView ivAvatar;
        TextView tvName;
        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);
            ivAvatar = itemView.findViewById(R.id.imgUser);
            tvName = itemView.findViewById(R.id.tvName);

        }
    }
}
